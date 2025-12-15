package StaffForm;

import JDBCUntils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static JDBCUntils.Style.*;

public class AddStaffForm extends JDialog {
    private JTextField txtName, txtPhone, txtAddress, txtUsername, txtPassword;
    private JCheckBox chkIsAdmin;
    private JComboBox<String> cbDay, cbMonth, cbYear;
    private JButton btnSave, btnCancel;

    private boolean isAdded = false;

    public AddStaffForm(Frame parent) {
        super(parent, true);
        this.setTitle("Thêm Nhân Viên Mới");
        initUI();
        initComboBoxData();
        addEvents();

        this.pack();
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(Color.WHITE);

        JLabel lblTitle = createHeaderLabel("NHẬP THÔNG TIN");
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(20));

        txtName = new JTextField();
        JPanel pName = createTextFieldWithLabel(txtName, "Tên Nhân Viên:");
        mainPanel.add(pName);
        mainPanel.add(Box.createVerticalStrut(15));

        cbDay = new JComboBox<>();
        cbMonth = new JComboBox<>();
        cbYear = new JComboBox<>();
        JPanel datePanel = createDatePanel("Ngày sinh:", cbDay, cbMonth, cbYear);
        mainPanel.add(datePanel);
        mainPanel.add(Box.createVerticalStrut(16));

        txtPhone = new JTextField();
        JPanel pPhone = createTextFieldWithLabel(txtPhone, "Số điện thoại:");
        mainPanel.add(pPhone);
        mainPanel.add(Box.createVerticalStrut(16));

        txtAddress = new JTextField();
        JPanel pAddress = createTextFieldWithLabel(txtAddress, "Địa chỉ:");
        mainPanel.add(pAddress);
        mainPanel.add(Box.createVerticalStrut(16));

        txtUsername = new JTextField();
        JPanel pUser = createTextFieldWithLabel(txtUsername, "Tài khoản đăng nhập:");
        mainPanel.add(pUser);
        mainPanel.add(Box.createVerticalStrut(16));

        txtPassword = new JTextField();
        JPanel pPass = createTextFieldWithLabel(txtPassword, "Mật khẩu:");
        mainPanel.add(pPass);
        mainPanel.add(Box.createVerticalStrut(16));

        chkIsAdmin = new JCheckBox();
        JPanel pRoleWrapper = createCheckBoxWithLabel(chkIsAdmin, "Vai trò:", "QUẢN TRỊ VIÊN");

        mainPanel.add(pRoleWrapper);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnSave = createButton("Lưu Lại", new Color(46, 204, 113));
        btnCancel = createButton("Hủy Bỏ", new Color(231, 76, 60));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        mainPanel.add(buttonPanel);
        this.setContentPane(mainPanel);

        getRootPane().setDefaultButton(btnSave);
    }

    private void addEvents() {
        btnSave.addActionListener(_ -> {
            if (txtName.getText().trim().isEmpty() || txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
                showError(AddStaffForm.this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {
                String strDate = cbYear.getSelectedItem() + "-" + cbMonth.getSelectedItem() + "-" + cbDay.getSelectedItem();

                String sql = "INSERT INTO Staffs (sta_name, sta_date_of_birth, sta_phone, sta_address, sta_username, sta_password, sta_role) VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtName.getText().trim());
                ps.setString(2, strDate);
                ps.setString(3, txtPhone.getText().trim());
                ps.setString(4, txtAddress.getText().trim());

                ps.setString(5, txtUsername.getText().trim());
                ps.setString(6, txtPassword.getText().trim());
                ps.setString(7, chkIsAdmin.isSelected() ? "Admin" : "Staff");

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    showSuccess(AddStaffForm.this, "Thêm nhân viên thành công!");
                    isAdded = true;
                    dispose();
                }
            } catch (Exception ex) {
                if (ex.getMessage().contains("Duplicate")) {
                    showError(AddStaffForm.this, "Tài khoản '" + txtUsername.getText() + "' đã tồn tại!");
                } else {
                    showError(AddStaffForm.this, "Lỗi: " + ex.getMessage());
                }
            }
        });

        btnCancel.addActionListener(_ -> dispose());
    }

    private void initComboBoxData() {
        for (int i = 1; i <= 31; i++) cbDay.addItem(String.format("%02d", i));
        for (int i = 1; i <= 12; i++) cbMonth.addItem(String.format("%02d", i));
        for (int i = 2025; i >= 1960; i--) cbYear.addItem(String.valueOf(i));
    }

    public boolean isAddedSuccess() {
        return isAdded;
    }
}