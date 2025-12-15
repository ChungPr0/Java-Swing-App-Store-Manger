package CustomerForm;

import JDBCUntils.ComboItem;
import JDBCUntils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.*;

import static JDBCUntils.Style.*;

public class CustomerManagerPanel extends JPanel {
    private JList<ComboItem> listCustomer;
    private JTextField txtSearch, txtName, txtPhone, txtAddress;

    private JButton btnAdd, btnSave, btnDelete;

    private JButton btnSort;
    private int currentSortIndex = 0;
    private final String[] sortModes = {"A-Z", "Z-A", "NEW", "OLD"};

    private int selectedCusID = -1;
    private boolean isDataLoading = false;

    public CustomerManagerPanel() {
        initUI();
        loadListData();
        addEvents();
        addChangeListeners();
    }

    private void initUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setPreferredSize(new Dimension(250, 0));

        txtSearch = new JTextField();
        btnSort = new JButton("A-Z");
        btnSort.setToolTipText("Đang xếp: Tên A-Z");

        JPanel searchPanel = createSearchWithButtonPanel(txtSearch, btnSort, "Tìm kiếm");

        leftPanel.add(searchPanel, BorderLayout.NORTH);

        listCustomer = new JList<>();
        listCustomer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listCustomer.setFixedCellHeight(30);

        leftPanel.add(new JScrollPane(listCustomer), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        rightPanel.add(createHeaderLabel("THÔNG TIN KHÁCH HÀNG"));
        rightPanel.add(Box.createVerticalStrut(20));

        txtName = new JTextField();
        rightPanel.add(createTextFieldWithLabel(txtName, "Tên Khách Hàng:"));
        rightPanel.add(Box.createVerticalStrut(15));

        txtPhone = new JTextField();
        rightPanel.add(createTextFieldWithLabel(txtPhone, "Số điện thoại:"));
        rightPanel.add(Box.createVerticalStrut(15));

        txtAddress = new JTextField();
        rightPanel.add(createTextFieldWithLabel(txtAddress, "Địa chỉ:"));
        rightPanel.add(Box.createVerticalStrut(15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = createButton("Thêm Khách Hàng", Color.decode("#3498db"));

        btnSave = createButton("Lưu thay đổi", new Color(46, 204, 113));
        btnDelete = createButton("Xóa Khách Hàng", new Color(231, 76, 60)); // Đã sửa text lỗi

        btnSave.setVisible(false);
        btnDelete.setVisible(false);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnDelete);

        rightPanel.add(buttonPanel);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);

        enableForm(false);
    }

    private void loadListData() {
        DefaultListModel<ComboItem> model = new DefaultListModel<>();

        String keyword = txtSearch.getText().trim();
        boolean isSearching = !keyword.isEmpty() && !keyword.equals("Tìm kiếm...");

        try (Connection con = DBConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT cus_id, cus_name FROM Customers");

            if (isSearching) {
                sql.append(" WHERE cus_name LIKE ?");
            }

            switch (currentSortIndex) {
                case 1: sql.append(" ORDER BY cus_name DESC"); break;
                case 2: sql.append(" ORDER BY cus_id DESC"); break;
                case 3: sql.append(" ORDER BY cus_id ASC"); break;
                default: sql.append(" ORDER BY cus_name ASC");
            }

            PreparedStatement ps = con.prepareStatement(sql.toString());

            if (isSearching) {
                ps.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("cus_id");
                String name = rs.getString("cus_name");
                model.addElement(new ComboItem(name, id));
            }
            listCustomer.setModel(model);
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
    }

    private void loadDetail(int id) {
        isDataLoading = true;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Customers WHERE cus_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("cus_name"));
                txtPhone.setText(rs.getString("cus_phone"));
                txtAddress.setText(rs.getString("cus_address"));

                enableForm(true);
                btnDelete.setVisible(true);
                btnSave.setVisible(false);
                btnAdd.setVisible(true);
            }
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
        finally {
            isDataLoading = false;
        }
    }

    private void addEvents() {
        listCustomer.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                ComboItem selected = listCustomer.getSelectedValue();
                if (selected != null) {
                    selectedCusID = selected.getValue();
                    loadDetail(selectedCusID);
                }
            }
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { loadListData(); }
            public void removeUpdate(DocumentEvent e) { loadListData(); }
            public void changedUpdate(DocumentEvent e) { loadListData(); }
        });

        btnSort.addActionListener(_ -> {
            currentSortIndex++;
            if (currentSortIndex >= sortModes.length) {
                currentSortIndex = 0;
            }
            btnSort.setText(sortModes[currentSortIndex]);

            switch (currentSortIndex) {
                case 0: btnSort.setToolTipText("Đang xếp: Tên A -> Z"); break;
                case 1: btnSort.setToolTipText("Đang xếp: Tên Z -> A"); break;
                case 2: btnSort.setToolTipText("Đang xếp: Khách mới thêm"); break;
                case 3: btnSort.setToolTipText("Đang xếp: Khách cũ"); break;
            }

            loadListData();
        });

        btnAdd.addActionListener(_ -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddCustomerForm addCustomerForm = new AddCustomerForm(parentFrame);
            addCustomerForm.setVisible(true);

            if (addCustomerForm.isAddedSuccess()) {
                loadListData();
            }
        });

        btnSave.addActionListener(_ -> {
            try (Connection con = DBConnection.getConnection()) {
                String sql = "UPDATE Customers SET cus_name=?, cus_phone=?, cus_address=? WHERE cus_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtName.getText());
                ps.setString(2, txtPhone.getText());
                ps.setString(3, txtAddress.getText());
                ps.setInt(4, selectedCusID);

                if (ps.executeUpdate() > 0) {
                    showSuccess(this, "Cập nhật thành công!");
                    loadListData();
                    btnSave.setVisible(false);
                }
            } catch (Exception ex) {
                showError(this, "Lỗi: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(_ -> {
            if(showConfirm(this, "Xóa khách hàng này?")){
                try (Connection con = DBConnection.getConnection()) {
                    PreparedStatement ps = con.prepareStatement("DELETE FROM Customers WHERE cus_id=?");
                    ps.setInt(1, selectedCusID); // Dùng ID đã lưu
                    if (ps.executeUpdate() > 0) {
                        loadListData();
                        clearForm();
                    }
                } catch (Exception ex) {
                    if (ex.getMessage().contains("foreign key")) {
                        showError(this, "Không thể xóa khách hàng này vì đã có hóa đơn!");
                    } else {
                        if (ex.getMessage().contains("foreign key")) {
                            showError(this, "Không thể xóa khách hàng này vì họ đã có lịch sử mua hàng (Hóa đơn)!");
                        } else {
                            showError(this, "Lỗi: " + ex.getMessage());
                        }
                    }
                }
            }
        });
    }

    private void addChangeListeners() {
        SimpleDocumentListener docListener = new SimpleDocumentListener(_ -> {
            if (!isDataLoading) btnSave.setVisible(true);
        });

        txtName.getDocument().addDocumentListener(docListener);
        txtPhone.getDocument().addDocumentListener(docListener);
        txtAddress.getDocument().addDocumentListener(docListener);
    }

    private void clearForm() {
        isDataLoading = true;
        txtName.setText(""); txtPhone.setText(""); txtAddress.setText("");

        btnSave.setVisible(false);
        btnDelete.setVisible(false);
        enableForm(false);
        isDataLoading = false;
    }

    private void enableForm(boolean enable) {
        txtName.setEnabled(enable);
        txtPhone.setEnabled(enable);
        txtAddress.setEnabled(enable);
    }

    public void refreshData() {
        loadListData();
    }

    @FunctionalInterface
    interface DocumentUpdateListener { void update(DocumentEvent e); }

    static class SimpleDocumentListener implements DocumentListener {
        private final CustomerForm.CustomerManagerPanel.DocumentUpdateListener listener;
        public SimpleDocumentListener(CustomerForm.CustomerManagerPanel.DocumentUpdateListener listener) { this.listener = listener; }
        public void insertUpdate(DocumentEvent e) { listener.update(e); }
        public void removeUpdate(DocumentEvent e) { listener.update(e); }
        public void changedUpdate(DocumentEvent e) { listener.update(e); }
    }
}