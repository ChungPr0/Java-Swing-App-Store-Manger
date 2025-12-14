package InvoiceForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static JDBCUntils.Style.*;

public class EditInvoiceDetailDialog extends JDialog {
    private JTextField txtQuantity;
    private JButton btnSave, btnCancel;

    private boolean isConfirmed = false;
    private int newQuantity = 0;
    private int limit = 0;
    public EditInvoiceDetailDialog(Frame parent, String productName, int currentQty, int limit) {
        super(parent, true);
        setTitle("Chỉnh Sửa Số Lượng");

        this.limit = limit;

        initUI(productName, currentQty, limit);
        addEvents();

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initUI(String productName, int currentQty, int limit) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createHeaderLabel("SỬA SỐ LƯỢNG"));
        mainPanel.add(Box.createVerticalStrut(20));

        JTextField lblName = new JTextField();
        lblName.setText(productName);
        lblName.setEditable(false);
        lblName.setFocusable(false);
        JPanel pName = createTextFieldWithLabel(lblName, "Sản phẩm: ");
        mainPanel.add(pName);
        mainPanel.add(Box.createVerticalStrut(15));

        JTextField pCount = new JTextField();
        pCount.setText(String.valueOf(limit));
        pCount.setEditable(false);
        pCount.setFocusable(false);
        JPanel pCountPanel = createTextFieldWithLabel(pCount, "Tối đa có thể nhập: ");

        mainPanel.add(pCountPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        txtQuantity = new JTextField(String.valueOf(currentQty));
        JPanel pQty = createTextFieldWithLabel(txtQuantity, "Số Lượng Mới:");
        mainPanel.add(pQty);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnSave = createButton("Xác Nhận", new Color(46, 204, 113));
        btnCancel = createButton("Hủy Bỏ", new Color(231, 76, 60));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        mainPanel.add(buttonPanel);
        setContentPane(mainPanel);
    }

    private void addEvents() {
        btnSave.addActionListener(e -> {
            try {
                int qty = Integer.parseInt(txtQuantity.getText().trim());

                if (qty <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                    return;
                }

                if (qty > limit) {
                    JOptionPane.showMessageDialog(this,
                            "Kho không đủ hàng!\n" +
                                    "Bạn chỉ có thể nhập tối đa: " + limit + "\n" +
                                    "(Do trong kho và đơn hàng cộng lại chỉ có bấy nhiêu)");
                    return;
                }

                this.newQuantity = qty;
                this.isConfirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!");
            }
        });

        btnCancel.addActionListener(e -> dispose());
    }

    public boolean isConfirmed() { return isConfirmed; }
    public int getNewQuantity() { return newQuantity; }
}