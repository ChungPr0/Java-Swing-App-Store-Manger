package ProductForm;

import JDBCUntils.ComboItem;
import JDBCUntils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static JDBCUntils.Style.*;

public class ProductManagerPanel extends JPanel {
    private JList<ComboItem> listProduct;
    private JTextField txtSearch, txtName, txtPrice, txtCount;
    private JButton btnAdd, btnSave, btnDelete, btnEditType, btnAddType;

    private JComboBox<ComboItem> cbType, cbSupplier;
    private JButton btnSort;
    private int currentSortIndex = 0;
    private final String[] sortModes = {"A-Z", "Z-A", "PUP", "PDW", "NEW", "OLD"};
    private int selectedProductID = -1;
    private boolean isDataLoading = false;

    public ProductManagerPanel() {
        initUI();
        loadListData();
        loadTypeData();
        loadSupplierData();
        addEvents();
        addChangeListeners();
    }

    private void initUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBackground(Color.decode("#ecf0f1"));

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setOpaque(false);

        txtSearch = new JTextField();
        btnSort = new JButton("A-Z");
        btnSort.setToolTipText("Đang xếp: Tên A-Z");

        JPanel searchPanel = createSearchWithButtonPanel(txtSearch, btnSort, "Tìm kiếm");

        leftPanel.add(searchPanel, BorderLayout.NORTH);

        listProduct = new JList<>();
        listProduct.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listProduct.setFixedCellHeight(30);

        leftPanel.add(new JScrollPane(listProduct), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        rightPanel.add(createHeaderLabel("THÔNG TIN SẢN PHẨM"));
        rightPanel.add(Box.createVerticalStrut(20));

        txtName = new JTextField();
        rightPanel.add(createTextFieldWithLabel(txtName, "Tên Sản Phẩm:"));
        rightPanel.add(Box.createVerticalStrut(15));

        txtPrice = new JTextField();
        rightPanel.add(createTextFieldWithLabel(txtPrice, "Giá Bán (VND):"));
        rightPanel.add(Box.createVerticalStrut(15));

        txtCount = new JTextField();
        rightPanel.add(createTextFieldWithLabel(txtCount, "Số Lượng Tồn:"));
        rightPanel.add(Box.createVerticalStrut(15));

        cbType = new JComboBox<>();
        btnEditType = createSmallButton("Sửa", Color.LIGHT_GRAY);
        btnAddType = createSmallButton("Thêm", Color.LIGHT_GRAY);
        rightPanel.add(createComboBoxWithLabel(cbType,"Phân Loại:", btnEditType, btnAddType));
        rightPanel.add(Box.createVerticalStrut(15));

        cbSupplier = new JComboBox<>();
        rightPanel.add(createComboBoxWithLabel(cbSupplier, "Nhà Cung Cấp:", null, null));
        rightPanel.add(Box.createVerticalStrut(15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = createButton("Tạo sản phẩm", Color.decode("#3498db"));
        btnSave = createButton("Lưu thay đổi", new Color(46, 204, 113));
        btnDelete = createButton("Xóa sản phẩm", new Color(231, 76, 60));

        btnSave.setVisible(false);
        btnDelete.setVisible(false);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnDelete);

        rightPanel.add(buttonPanel);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);

        if (!JDBCUntils.Session.isAdmin()) {
            btnAdd.setVisible(false);
        }

        enableForm(false);
    }

    private void loadListData() {
        DefaultListModel<ComboItem> model = new DefaultListModel<>();

        String keyword = txtSearch.getText().trim();
        boolean isSearching = !keyword.isEmpty() && !keyword.equals("Tìm kiếm...");

        try (Connection con = DBConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT pro_id, pro_name FROM Products");

            if (isSearching) {
                sql.append(" WHERE pro_name LIKE ?");
            }

            switch (currentSortIndex) {
                case 1: sql.append(" ORDER BY pro_name DESC"); break;
                case 2: sql.append(" ORDER BY pro_price ASC"); break;
                case 3: sql.append(" ORDER BY pro_price DESC"); break;
                case 4: sql.append(" ORDER BY pro_id DESC"); break;
                case 5: sql.append(" ORDER BY pro_id ASC"); break;
                default: sql.append(" ORDER BY pro_name ASC");
            }

            PreparedStatement ps = con.prepareStatement(sql.toString());

            if (isSearching) {
                ps.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("pro_id");
                String name = rs.getString("pro_name");
                model.addElement(new ComboItem(name, id));
            }
            listProduct.setModel(model);
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
    }

    private void loadTypeData() {
        cbType.removeAllItems();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT type_id, type_name FROM ProductTypes";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("type_id");
                String name = rs.getString("type_name");
                cbType.addItem(new ComboItem(name, id));
            }
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
    }

    private void loadSupplierData() {
        cbSupplier.removeAllItems();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT sup_id, sup_name FROM Suppliers";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("sup_id");
                String name = rs.getString("sup_name");
                cbSupplier.addItem(new ComboItem(name, id));
            }
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
    }

    private void loadDetail(int id) {
        isDataLoading = true;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Products WHERE pro_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("pro_name"));
                txtPrice.setText(String.format("%.0f", rs.getDouble("pro_price")));
                txtCount.setText(String.valueOf(rs.getInt("pro_count")));

                int typeID = rs.getInt("type_ID");
                setSelectedComboItem(cbType, typeID);

                int supID = rs.getInt("sup_ID");
                setSelectedComboItem(cbSupplier, supID);

                if (JDBCUntils.Session.isAdmin()) {
                    enableForm(true);
                    btnDelete.setVisible(true);
                    btnSave.setVisible(false);
                    btnAdd.setVisible(true);
                } else {
                    enableForm(false);
                    btnDelete.setVisible(false);
                    btnSave.setVisible(false);
                    btnAdd.setVisible(false);
                }
            }
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
        finally {
            if (!JDBCUntils.Session.isAdmin()) {
                btnSave.setVisible(false);
            }
            isDataLoading = false;
        }
    }

    private void setSelectedComboItem(JComboBox<ComboItem> cb, int id) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (cb.getItemAt(i).getValue() == id) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }

    private void addEvents() {
        listProduct.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                ComboItem selected = listProduct.getSelectedValue();
                if (selected != null) {
                    selectedProductID = selected.getValue();
                    loadDetail(selectedProductID);
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
                case 2: btnSort.setToolTipText("Đang xếp: Giá thấp đến cao"); break;
                case 3: btnSort.setToolTipText("Đang xếp: Giá cao đến thấp"); break;
                case 4: btnSort.setToolTipText("Đang xếp: Mới nhập trước"); break;
                case 5: btnSort.setToolTipText("Đang xếp: Nhập lâu rồi"); break;
            }

            loadListData();
        });

        btnAdd.addActionListener(_ -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddProductForm addProductForm = new AddProductForm(parentFrame);
            addProductForm.setVisible(true);

            if (addProductForm.isAddedSuccess()) {
                loadListData();
            }
        });

        btnSave.addActionListener(_ -> {
            try (Connection con = DBConnection.getConnection()) {
                ComboItem selectedType = (ComboItem) cbType.getSelectedItem();
                ComboItem selectedSup = (ComboItem) cbSupplier.getSelectedItem();

                if (selectedType == null || selectedSup == null) {
                    showError(this, "Vui lòng chọn Loại và Nhà cung cấp!");
                    return;
                }

                String sql = "UPDATE Products SET pro_name=?, pro_price=?, pro_count=?, type_ID=?, sup_ID=? WHERE pro_id=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtName.getText());
                ps.setDouble(2, Double.parseDouble(txtPrice.getText()));
                ps.setInt(3, Integer.parseInt(txtCount.getText()));
                ps.setInt(4, selectedType.getValue());
                ps.setInt(5, selectedSup.getValue());
                ps.setInt(6, selectedProductID);

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
            if(showConfirm(this, "Xóa sản phẩm này?")){
                try (Connection con = DBConnection.getConnection()) {
                    PreparedStatement ps = con.prepareStatement("DELETE FROM Products WHERE pro_id=?");
                    ps.setInt(1, selectedProductID);
                    if (ps.executeUpdate() > 0) {
                        loadListData();
                        clearForm();
                    }
                } catch (Exception ex) {
                    if (ex.getMessage().contains("foreign key")) {
                        showError(this, "Không thể xóa sản phẩm này vì đã có lịch sử giao dịch!\n(Sản phẩm đã nằm trong một hóa đơn nào đó)");
                    } else {
                        showError(this, "Lỗi: " + ex.getMessage());
                    }
                }
            }
        });

        btnAddType.addActionListener(_ -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            TypeEditorDialog dialog = new TypeEditorDialog(parent);
            dialog.setVisible(true);

            if (dialog.isUpdated()) {
                loadTypeData();
                if (cbType.getItemCount() > 0) cbType.setSelectedIndex(cbType.getItemCount() - 1);
            }
        });

        btnEditType.addActionListener(_ -> {
            ComboItem currentItem = (ComboItem) cbType.getSelectedItem();
            if (currentItem == null) return;

            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            TypeEditorDialog dialog = new TypeEditorDialog(parent, currentItem.getValue(), currentItem.toString());
            dialog.setVisible(true);

            if (dialog.isUpdated()) {
                loadTypeData();
            }
        });
    }

    private void addChangeListeners() {
        SimpleDocumentListener docListener = new SimpleDocumentListener(_ -> {
            if (!isDataLoading) btnSave.setVisible(true);
        });
        txtName.getDocument().addDocumentListener(docListener);
        txtPrice.getDocument().addDocumentListener(docListener);
        txtCount.getDocument().addDocumentListener(docListener);
        cbType.addActionListener(_ -> checkChange());
        cbSupplier.addActionListener(_ -> checkChange());
    }

    private void checkChange() {
        if (!isDataLoading) btnSave.setVisible(true);
    }

    private void clearForm() {
        isDataLoading = true;
        txtName.setText(""); txtPrice.setText(""); txtCount.setText("");
        btnSave.setVisible(false);
        btnDelete.setVisible(false);

        enableForm(false);
        isDataLoading = false;
    }

    private void enableForm(boolean enable) {
        boolean isAdmin = JDBCUntils.Session.isAdmin();

        txtName.setEnabled(enable);
        txtPrice.setEnabled(enable);
        txtCount.setEnabled(enable);
        cbType.setEnabled(enable);
        cbSupplier.setEnabled(enable);

        btnEditType.setVisible(enable && isAdmin);
        btnAddType.setVisible(enable && isAdmin);
    }

    public void refreshData() {
        loadTypeData();
        loadSupplierData();
        loadListData();
    }

    @FunctionalInterface
    interface DocumentUpdateListener { void update(DocumentEvent e); }

    static class SimpleDocumentListener implements DocumentListener {
        private final DocumentUpdateListener listener;
        public SimpleDocumentListener(DocumentUpdateListener listener) { this.listener = listener; }
        public void insertUpdate(DocumentEvent e) { listener.update(e); }
        public void removeUpdate(DocumentEvent e) { listener.update(e); }
        public void changedUpdate(DocumentEvent e) { listener.update(e); }
    }
}