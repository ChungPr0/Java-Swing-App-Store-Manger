package Login;

import JDBCUntils.DBConnection;
import JDBCUntils.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static JDBCUntils.Style.*;

public class ChangePasswordDialog extends JDialog {

    private JPasswordField txtOldPass, txtNewPass, txtConfirmPass;

    public ChangePasswordDialog(JFrame parent) {
        super(parent, "Đổi Mật Khẩu", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 40, 30, 40));
        mainPanel.setBackground(Color.WHITE);

        JLabel lblTitle = createHeaderLabel("ĐỔI MẬT KHẨU");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(30));

        txtOldPass = new JPasswordField();
        JPanel pOld = createPasswordFieldWithLabel(txtOldPass, "Mật khẩu hiện tại:");
        mainPanel.add(pOld);
        mainPanel.add(Box.createVerticalStrut(20));

        txtNewPass = new JPasswordField();
        JPanel pNew = createPasswordFieldWithLabel(txtNewPass, "Mật khẩu mới:");
        mainPanel.add(pNew);
        mainPanel.add(Box.createVerticalStrut(20));

        txtConfirmPass = new JPasswordField();
        JPanel pConfirm = createPasswordFieldWithLabel(txtConfirmPass, "Xác nhận mật khẩu mới:");
        mainPanel.add(pConfirm);
        mainPanel.add(Box.createVerticalStrut(30));

        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pBtn.setBackground(Color.WHITE);

        JButton btnSave = createButton("Lưu", new Color(46, 204, 113)); // Màu xanh lá
        JButton btnCancel = createButton("Hủy", new Color(231, 76, 60));     // Màu đỏ

        pBtn.add(btnSave);
        pBtn.add(btnCancel);
        mainPanel.add(pBtn);

        add(mainPanel);

        btnCancel.addActionListener(_ -> dispose());
        btnSave.addActionListener(_ -> doChangePassword());
        getRootPane().setDefaultButton(btnSave);
    }

    private void doChangePassword() {
        String oldPass = new String(txtOldPass.getPassword());
        String newPass = new String(txtNewPass.getPassword());
        String confirmPass = new String(txtConfirmPass.getPassword());

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showError(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showError(this, "Mật khẩu xác nhận không trùng khớp!");
            return;
        }

        if (newPass.equals(oldPass)) {
            showError(this, "Mật khẩu mới không được trùng mật khẩu cũ!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String checkSql = "SELECT * FROM Staffs WHERE sta_ID = ? AND sta_password = ?";
            PreparedStatement psCheck = con.prepareStatement(checkSql);
            psCheck.setInt(1, Session.loggedInStaffID);
            psCheck.setString(2, oldPass);

            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) {
                showError(this, "Mật khẩu hiện tại không đúng!");
                return;
            }

            String updateSql = "UPDATE Staffs SET sta_password = ? WHERE sta_ID = ?";
            PreparedStatement psUpdate = con.prepareStatement(updateSql);
            psUpdate.setString(1, newPass);
            psUpdate.setInt(2, Session.loggedInStaffID);

            if (psUpdate.executeUpdate() > 0) {
                showSuccess(this, "Đổi mật khẩu thành công!");
                dispose();
            }

        } catch (Exception ex) {
            showError(this, "Lỗi kết nối: " + ex.getMessage());
        }
    }
}