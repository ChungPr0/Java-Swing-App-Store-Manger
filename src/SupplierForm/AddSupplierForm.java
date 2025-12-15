package SupplierForm;

import JDBCUntils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static JDBCUntils.Style.*;

public class AddSupplierForm extends JDialog {
    private JTextField txtName, txtPhone, txtAddress;
    private JButton btnSave, btnCancel;

    private boolean isAdded = false;

    public AddSupplierForm(Frame parent) {
        super(parent, true);
        this.setTitle("Thêm Nhà Cung Cấp Mới");
        initUI();
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
        JPanel pName = createTextFieldWithLabel(txtName, "Tên Nhà Cung Cấp:");
        mainPanel.add(pName);
        mainPanel.add(Box.createVerticalStrut(15));

        txtPhone = new JTextField();
        JPanel pPhone = createTextFieldWithLabel(txtPhone, "Số điện thoại:");
        mainPanel.add(pPhone);
        mainPanel.add(Box.createVerticalStrut(15));

        txtAddress = new JTextField();
        JPanel pAddress = createTextFieldWithLabel(txtAddress, "Địa chỉ:");
        mainPanel.add(pAddress);
        mainPanel.add(Box.createVerticalStrut(15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnSave = createButton("Lưu Lại", new Color(46, 204, 113));
        btnCancel = createButton("Hủy Bỏ", new Color(231, 76, 60));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        this.setContentPane(mainPanel);

        getRootPane().setDefaultButton(btnSave);
    }

    private void addEvents() {
        btnSave.addActionListener(_ -> {
            if (txtName.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty()) {
                showError(AddSupplierForm.this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {
                String sql = "INSERT INTO Suppliers (sup_name, sup_phone, sup_address) VALUES (?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtName.getText().trim());
                ps.setString(2, txtPhone.getText().trim());
                ps.setString(3, txtAddress.getText().trim());

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    showSuccess(AddSupplierForm.this, "Thêm thành công!");
                    isAdded = true;
                    dispose();
                }
            } catch (Exception ex) {
                showError(AddSupplierForm.this, "Lỗi: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(_ -> dispose());
    }

    public boolean isAddedSuccess() {
        return isAdded;
    }

}