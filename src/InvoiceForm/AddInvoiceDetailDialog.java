package InvoiceForm;

import JDBCUntils.ComboItem;
import JDBCUntils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;

import static JDBCUntils.Style.*;

public class AddInvoiceDetailDialog extends JDialog {
    private JComboBox<ComboItem> cbProduct;
    private JTextField txtQuantity;
    private JButton btnAdd, btnCancel;

    private boolean isConfirmed = false;
    private ComboItem selectedProduct = null;
    private int selectedQty = 0;

    public AddInvoiceDetailDialog(Frame parent) {
        super(parent, true);
        setTitle("Thêm Sản Phẩm");
        initUI();
        loadProductData();
        addEvents();

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createHeaderLabel("CHỌN SẢN PHẨM"));
        mainPanel.add(Box.createVerticalStrut(20));

        cbProduct = new JComboBox<>();
        JPanel pPro = createComboBoxWithLabel(cbProduct, "Sản Phẩm (Còn hàng):");
        mainPanel.add(pPro);
        mainPanel.add(Box.createVerticalStrut(15));

        txtQuantity = new JTextField("1");
        JPanel pQty = createTextFieldWithLabel(txtQuantity, "Số Lượng Nhập:");
        mainPanel.add(pQty);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = createButton("Xác Nhận", new Color(46, 204, 113));
        btnCancel = createButton("Hủy Bỏ", new Color(231, 76, 60));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        mainPanel.add(buttonPanel);
        setContentPane(mainPanel);

        getRootPane().setDefaultButton(btnAdd);
    }

    private void loadProductData() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT pro_ID, pro_name, pro_count FROM Products WHERE pro_count > 0";
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("pro_name");
                int count = rs.getInt("pro_count");
                int id = rs.getInt("pro_ID");
                cbProduct.addItem(new ComboItem(name + " (Tồn: " + count + ")", id));
            }
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
    }

    private void addEvents() {
        btnAdd.addActionListener(e -> {
            try {
                String qtyText = txtQuantity.getText().trim();
                if (qtyText.isEmpty()) {
                    showError(this, "Vui lòng nhập số lượng!");
                    return;
                }

                int qty = Integer.parseInt(qtyText);
                if (qty <= 0) {
                    showError(this, "Số lượng phải > 0!");
                    return;
                }

                ComboItem item = (ComboItem) cbProduct.getSelectedItem();
                if (item == null) {
                    showError(this, "Chưa chọn sản phẩm!");
                    return;
                }

                String label = item.toString();
                int stock = Integer.parseInt(label.substring(label.lastIndexOf(": ") + 2, label.lastIndexOf(")")));

                if (qty > stock) {
                    showError(this, "Kho chỉ còn " + stock + ", không đủ hàng!");
                    return;
                }

                this.selectedProduct = item;
                this.selectedQty = qty;
                this.isConfirmed = true;
                dispose();

            } catch (NumberFormatException ex) {
                showError(this, "Số lượng phải là số nguyên!");
            }
        });

        btnCancel.addActionListener(e -> dispose());
    }

    public boolean isConfirmed() { return isConfirmed; }
    public ComboItem getSelectedProduct() { return selectedProduct; }
    public int getSelectedQty() { return selectedQty; }
}
