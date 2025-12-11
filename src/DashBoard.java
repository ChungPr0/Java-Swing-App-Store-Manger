import JDBCUntils.DBConnection;
import StaffForm.AddStaffForm;
import StaffForm.ChangeStaffForm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.*;

public class DashBoard extends JFrame {
    private JPanel dashBoardTime;
    private JList<String> listStaff;
    private JButton staffButtonAdd;
    private JButton staffButtonChange;
    private JTextField searchBox;

    public DashBoard(){
        super();
        this.setContentPane(dashBoardTime);
        this.setTitle("Dash Board");
        this.setSize(400,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        addEvents();
        read();

    }

    void read() {
        DefaultListModel<String> model = new DefaultListModel<>();
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT sta_name FROM staffs LIMIT 5"; // MySQL
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addElement(rs.getString("sta_name"));
            }

            listStaff.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void searchStaff(String keyword) {
        DefaultListModel<String> model = new DefaultListModel<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT sta_name FROM staffs WHERE sta_name LIKE ? LIMIT 5";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addElement(rs.getString("sta_name"));
            }

            listStaff.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addEvents() {
        searchBox.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { doSearch(); }
            public void removeUpdate(DocumentEvent e) { doSearch(); }
            public void changedUpdate(DocumentEvent e) { doSearch(); }

            private void doSearch() {
                String key = searchBox.getText().trim();
                if (key.isEmpty()) {
                    read();
                } else {
                    searchStaff(key);
                }
            }
        });

        staffButtonAdd.addActionListener(e -> {
            AddStaffForm addStaffForm = new AddStaffForm(this);
            addStaffForm.setVisible(true);
            if (addStaffForm.isAddedSuccess()) {
                read();
            }
        });

        staffButtonChange.addActionListener(e -> {
            String selectedName = listStaff.getSelectedValue();
            if (selectedName == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên!");
                return;
            }

            ChangeStaffForm changeStaffForm = new ChangeStaffForm(this, selectedName);
            changeStaffForm.setVisible(true);

            if (changeStaffForm.isChangedSuccess()) {
                read();
            }

        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashBoard().setVisible(true));
    }
}
