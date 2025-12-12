package SupplierForm;

import JDBCUntils.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddSupplierForm extends JDialog {
    private JPanel supplierTime;
    private JTextField name;
    private JTextField address;
    private JTextField phone;
    private JButton addButton;

    private boolean isAdded = false;

    public AddSupplierForm(java.awt.Frame parent) {
        super(parent, true);
        this.setContentPane(supplierTime);
        this.setSize(350, 300);
        this.setTitle("Thêm Nhà Cung Cấp");
        this.setLocationRelativeTo(parent);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });


    }

    private void addSupplier() {
        if (name.getText().trim().isEmpty() || address.getText().trim().isEmpty() || phone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO Suppliers (sup_name, sup_address, sup_phone) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name.getText());
            ps.setString(2, address.getText());
            ps.setString(3, phone.getText());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                isAdded = true;
                dispose();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    public boolean isAddedSuccess() {
        return isAdded;
    }
}
