package InvoiceForm;

import JDBCUntils.ComboItem;
import JDBCUntils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Objects;

import static JDBCUntils.Style.*;

public class InvoiceManagerPanel extends JPanel {
    private JList<ComboItem> listInvoice;
    private JTextField txtSearch, txtTotalMoney, txtID, txtDate;
    private JComboBox<ComboItem> cbCustomer, cbStaff;

    private JButton btnAdd, btnSave, btnDelete, btnQuickAddCustomer;

    private JTable tableDetails;
    private DefaultTableModel detailModel;
    private JButton btnAddDetail, btnEditDetail, btnDelDetail;

    private JButton btnPrint;
    private JButton btnSort;
    private int currentSortIndex = 0;
    private final String[] sortModes = {"NEW", "OLD", "PUP", "PDW"};

    private int selectedInvID = -1;
    private boolean isDataLoading = false;

    public InvoiceManagerPanel() {
        initUI();
        loadComboBoxData();
        loadListData();
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
        btnSort = new JButton("NEW");
        btnSort.setToolTipText("Đang xếp: Mới nhất");

        JPanel searchPanel = createSearchWithButtonPanel(txtSearch, btnSort, "Tìm kiếm");

        leftPanel.add(searchPanel, BorderLayout.NORTH);

        listInvoice = new JList<>();
        listInvoice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listInvoice.setFixedCellHeight(30);

        leftPanel.add(new JScrollPane(listInvoice), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        rightPanel.add(createHeaderLabel("THÔNG TIN HÓA ĐƠN"));
        rightPanel.add(Box.createVerticalStrut(20));

        txtID = new JTextField();
        txtID.setEnabled(false);
        txtID.setFocusable(false);
        txtID.setBackground(new Color(250, 250, 250));
        txtID.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(createTextFieldWithLabel(txtID, "Mã Hóa Đơn: "));
        rightPanel.add(Box.createVerticalStrut(10));

        txtDate = new JTextField();
        txtDate.setEnabled(false);
        txtDate.setFocusable(false);
        txtDate.setBackground(new Color(250, 250, 250));
        txtDate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(createTextFieldWithLabel(txtDate, "Ngày Lập Đơn: "));
        rightPanel.add(Box.createVerticalStrut(10));

        cbCustomer = new JComboBox<>();
        btnQuickAddCustomer = createSmallButton("Thêm", Color.LIGHT_GRAY);
        rightPanel.add(createComboBoxWithLabel(cbCustomer, "Khách Hàng:", btnQuickAddCustomer));
        rightPanel.add(Box.createVerticalStrut(10));

        cbStaff = new JComboBox<>();
        rightPanel.add(createComboBoxWithLabel(cbStaff, "Nhân Viên Bán:"));
        rightPanel.add(Box.createVerticalStrut(10));

        txtTotalMoney = new JTextField();
        txtTotalMoney.setEnabled(false);
        txtTotalMoney.setFocusable(false);
        txtTotalMoney.setBackground(new Color(250, 250, 250));
        txtTotalMoney.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(createTextFieldWithLabel(txtTotalMoney, "Tổng Tiền (VND):"));
        rightPanel.add(Box.createVerticalStrut(5));

        JPanel pTableControl = new JPanel(new BorderLayout());
        pTableControl.setBackground(Color.WHITE);
        pTableControl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel lblTable = createHeaderLabel("Danh sách sản phẩm:");
        lblTable.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel pSmallBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pSmallBtns.setBackground(Color.WHITE);

        btnAddDetail = createSmallButton("THÊM", Color.LIGHT_GRAY);
        btnEditDetail = createSmallButton("SỬA", Color.LIGHT_GRAY);
        btnDelDetail = createSmallButton("XÓA", Color.LIGHT_GRAY);

        pSmallBtns.add(btnAddDetail);
        pSmallBtns.add(btnEditDetail);
        pSmallBtns.add(btnDelDetail);

        setDetailButtonsVisible(false);

        pTableControl.add(lblTable, BorderLayout.WEST);
        pTableControl.add(pSmallBtns, BorderLayout.EAST);

        rightPanel.add(pTableControl);
        rightPanel.add(Box.createVerticalStrut(5));

        String[] columns = {"ID SP", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền"};
        detailModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tableDetails = new JTable(detailModel);
        tableDetails.setRowHeight(25);
        tableDetails.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tableDetails.getColumnModel().getColumn(0).setMinWidth(0);
        tableDetails.getColumnModel().getColumn(0).setMaxWidth(0);
        tableDetails.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(tableDetails);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        rightPanel.add(scrollPane);
        rightPanel.add(Box.createVerticalStrut(15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = createButton("Tạo mới", Color.decode("#3498db"));
        btnSave = createButton("Lưu thay đổi", new Color(46, 204, 113));
        btnDelete = createButton("Xóa Hóa Đơn", new Color(231, 76, 60));
        btnPrint = createButton("In Hóa Đơn", Color.decode("#9b59b6"));

        btnPrint.setVisible(false);
        btnSave.setVisible(false);
        btnDelete.setVisible(false);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnPrint);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnDelete);

        rightPanel.add(buttonPanel);

        JScrollPane rightScrollPane = new JScrollPane(rightPanel);
        rightScrollPane.setBorder(null);
        rightScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        rightScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightScrollPane, BorderLayout.CENTER);

        enableForm(false);
    }

    private void loadComboBoxData() {
        try (Connection con = DBConnection.getConnection()) {
            ResultSet rsCus = con.createStatement().executeQuery("SELECT cus_ID, cus_name FROM Customers");
            while (rsCus.next()) cbCustomer.addItem(new ComboItem(rsCus.getString("cus_name"), rsCus.getInt("cus_ID")));

            ResultSet rsSta = con.createStatement().executeQuery("SELECT sta_ID, sta_name FROM Staffs");
            while (rsSta.next()) cbStaff.addItem(new ComboItem(rsSta.getString("sta_name"), rsSta.getInt("sta_ID")));

            if (JDBCUntils.Session.isLoggedIn) {
                int myID = JDBCUntils.Session.loggedInStaffID;

                for (int i = 0; i < cbStaff.getItemCount(); i++) {
                    ComboItem item = cbStaff.getItemAt(i);
                    if (item.getValue() == myID) {
                        cbStaff.setSelectedIndex(i);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
    }

    private void loadListData() {
        DefaultListModel<ComboItem> model = new DefaultListModel<>();

        String keyword = txtSearch.getText().trim();
        boolean isSearching = !keyword.isEmpty() && !keyword.equals("Tìm kiếm...");

        try (Connection con = DBConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT i.inv_ID, i.inv_price, c.cus_name " +
                    "FROM Invoices i LEFT JOIN Customers c ON i.cus_ID = c.cus_ID");

            if (isSearching) {
                sql.append(" WHERE i.inv_ID LIKE ? OR c.cus_name LIKE ?");
            }

            switch (currentSortIndex) {
                case 1: sql.append(" ORDER BY i.inv_ID ASC"); break;
                case 2: sql.append(" ORDER BY i.inv_price ASC"); break;
                case 3: sql.append(" ORDER BY i.inv_price DESC"); break;
                default: sql.append(" ORDER BY i.inv_ID DESC");
            }

            PreparedStatement ps = con.prepareStatement(sql.toString());

            if (isSearching) {
                String searchPattern = "%" + keyword + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("inv_ID");
                String cusName = rs.getString("cus_name");
                if (cusName == null) cusName = "Khách vãng lai";

                String displayText = "HĐ #" + id + " - " + cusName;
                model.addElement(new ComboItem(displayText, id));
            }
            listInvoice.setModel(model);
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
    }

    public void loadDetail(int invID) {
        isDataLoading = true;
        try (Connection con = DBConnection.getConnection()) {
            DecimalFormat df = new DecimalFormat("#,###");
            String sql = "SELECT * FROM Invoices WHERE inv_ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, invID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                selectedInvID = invID;
                txtID.setText("#" + selectedInvID);
                setSelectedComboItem(cbCustomer, rs.getInt("cus_ID"));
                setSelectedComboItem(cbStaff, rs.getInt("sta_ID"));
                java.sql.Timestamp ts = rs.getTimestamp("inv_date");
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                txtDate.setText(ts != null ? sdf.format(ts) : "");

                btnAdd.setVisible(true);
                btnSave.setVisible(false);
                btnPrint.setVisible(true);

                if (JDBCUntils.Session.isAdmin()) {
                    enableForm(true);
                    btnSave.setText("Lưu thay đổi");
                    btnDelete.setVisible(true);
                    setDetailButtonsVisible(true);
                } else {
                    enableForm(false);
                    btnDelete.setVisible(false);
                    setDetailButtonsVisible(false);
                }
            }

            detailModel.setRowCount(0);
            String sqlDetail = "SELECT p.pro_ID, p.pro_name, p.pro_price, d.ind_count " +
                    "FROM Invoice_details d JOIN Products p ON d.pro_ID = p.pro_ID WHERE d.inv_ID = ?";
            PreparedStatement psDetail = con.prepareStatement(sqlDetail);
            psDetail.setInt(1, invID);
            ResultSet rsDetail = psDetail.executeQuery();

            while (rsDetail.next()) {
                double price = rsDetail.getDouble("pro_price");
                int count = rsDetail.getInt("ind_count");
                detailModel.addRow(new Object[]{
                        rsDetail.getInt("pro_ID"),
                        rsDetail.getString("pro_name"),
                        df.format(price),
                        count,
                        df.format(price * count)
                });
            }
            calculateUITotal();

        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        } finally {
            isDataLoading = false;
        }
    }

    private void addEvents() {
        listInvoice.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                ComboItem selected = listInvoice.getSelectedValue();
                if (selected != null) {
                    selectedInvID = selected.getValue();
                    loadDetail(selectedInvID);
                    btnSave.setText("Lưu thay đổi");
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
                case 0: btnSort.setToolTipText("Đang xếp: Mới nhất trước"); break;
                case 1: btnSort.setToolTipText("Đang xếp: Cũ nhất trước"); break;
                case 2: btnSort.setToolTipText("Đang xếp: Giá thấp đến cao"); break;
                case 3: btnSort.setToolTipText("Đang xếp: Giá cao đến thấp"); break;
            }

            loadListData();
        });

        btnAdd.addActionListener(_ -> {
            clearForm();
            enableForm(true);
            setDetailButtonsVisible(true);

            if (!JDBCUntils.Session.isAdmin()) {
                cbStaff.setEnabled(false);
            }

            selectedInvID = -1;

            btnSave.setText("Lưu hóa đơn");
            btnSave.setVisible(true);
            btnAdd.setVisible(false);
            btnDelete.setVisible(false);
            btnPrint.setVisible(false);
        });

        btnDelDetail.addActionListener(_ -> {
            int row = tableDetails.getSelectedRow();
            if (row != -1) {
                detailModel.removeRow(row);
                calculateUITotal();
                btnSave.setVisible(true);
            }
        });

        btnEditDetail.addActionListener(_ -> {
            int row = tableDetails.getSelectedRow();
            if (row == -1) return;

            int proID = Integer.parseInt(detailModel.getValueAt(row, 0).toString());
            String name = tableDetails.getValueAt(row, 1).toString();
            int currentQtyUI = Integer.parseInt(tableDetails.getValueAt(row, 3).toString());

            int maxLimit = 0;
            try (Connection con = DBConnection.getConnection()) {
                int stockInDB = getProductStock(con, proID);
                int qtySavedInDB = 0;

                if (selectedInvID != -1) {
                    String sql = "SELECT ind_count FROM Invoice_details WHERE inv_ID=? AND pro_ID=?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, selectedInvID);
                    ps.setInt(2, proID);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        qtySavedInDB = rs.getInt("ind_count");
                    }
                }

                maxLimit = stockInDB + qtySavedInDB;

            } catch(Exception ex) {
                showError(this, "Lỗi: " + ex.getMessage());
            }

            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            EditInvoiceDetailDialog dialog = new EditInvoiceDetailDialog(parent, name, currentQtyUI, maxLimit);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                int newQty = dialog.getNewQuantity();
                detailModel.setValueAt(newQty, row, 3);

                double price = Double.parseDouble(tableDetails.getValueAt(row, 2).toString().replace(",", ""));
                DecimalFormat df = new DecimalFormat("#,###");
                detailModel.setValueAt(df.format(price * newQty), row, 4);

                calculateUITotal();
                btnSave.setVisible(true);
            }
        });

        btnAddDetail.addActionListener(_ -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddInvoiceDetailDialog dialog = new AddInvoiceDetailDialog(parent);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                try (Connection con = DBConnection.getConnection()) {
                    ComboItem item = dialog.getSelectedProduct();
                    int qtyInput = dialog.getSelectedQty();
                    int proID = item.getValue();

                    int stockDB = getProductStock(con, proID);

                    int currentQtyInTable = 0;
                    int rowExist = -1;

                    for(int i=0; i<detailModel.getRowCount(); i++) {
                        int idOnTable = Integer.parseInt(detailModel.getValueAt(i, 0).toString());
                        if(idOnTable == proID) {
                            currentQtyInTable = Integer.parseInt(detailModel.getValueAt(i, 3).toString());
                            rowExist = i;
                            break;
                        }
                    }

                    if (currentQtyInTable + qtyInput > stockDB) {
                        showError(this, "Tổng số lượng vượt quá tồn kho (" + stockDB + ")!");
                        return;
                    }

                    double price = getProductPrice(proID);
                    DecimalFormat df = new DecimalFormat("#,###");

                    if(rowExist != -1) {
                        int newTotal = currentQtyInTable + qtyInput;
                        detailModel.setValueAt(newTotal, rowExist, 3);
                        detailModel.setValueAt(df.format(price * newTotal), rowExist, 4);
                    } else {
                        String cleanName = item.toString().split(" \\(")[0];
                        detailModel.addRow(new Object[]{ proID, cleanName, df.format(price), qtyInput, df.format(price * qtyInput) });
                    }

                    calculateUITotal();
                    btnSave.setVisible(true);

                } catch (Exception ex) {
                    showError(this, "Lỗi: " + ex.getMessage());
                }
            }
        });

        btnSave.addActionListener(_ -> {
            if (detailModel.getRowCount() == 0) {
                showError(this, "Chưa có sản phẩm nào!");
                return;
            }
            if (selectedInvID == -1) createNewInvoice();
            else saveChangesToDatabase();
        });

        btnQuickAddCustomer.addActionListener(_ -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);

            CustomerForm.AddCustomerForm addForm = new CustomerForm.AddCustomerForm(parent);
            addForm.setVisible(true);

            if (addForm.isAddedSuccess()) {
                loadComboBoxData();

                if (cbCustomer.getItemCount() > 0) {
                    cbCustomer.setSelectedIndex(cbCustomer.getItemCount() - 1);
                }
                showSuccess(this, "Đã cập nhật danh sách khách hàng!");
            }
        });

        btnDelete.addActionListener(_ -> {
            if (selectedInvID == -1) return;
            if (showConfirm(this, "Xóa hóa đơn #" + selectedInvID + "? Hàng sẽ được hoàn kho.")) {
                deleteInvoiceTransaction();
            }
        });

        btnPrint.addActionListener(_ -> printInvoice());
    }


    private void createNewInvoice() {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String sqlInv = "INSERT INTO Invoices (cus_ID, sta_ID, inv_price) VALUES (?, ?, ?)";
            PreparedStatement psInv = con.prepareStatement(sqlInv, Statement.RETURN_GENERATED_KEYS);
            ComboItem cus = (ComboItem) cbCustomer.getSelectedItem();
            ComboItem sta = (ComboItem) cbStaff.getSelectedItem();
            double total = Double.parseDouble(txtTotalMoney.getText().replace(",", ""));

            psInv.setInt(1, Objects.requireNonNull(cus).getValue());
            psInv.setInt(2, Objects.requireNonNull(sta).getValue());
            psInv.setDouble(3, total);
            psInv.executeUpdate();

            ResultSet rsKeys = psInv.getGeneratedKeys();
            int newInvID = 0;
            if (rsKeys.next()) newInvID = rsKeys.getInt(1);

            String sqlDetail = "INSERT INTO Invoice_details (inv_ID, pro_ID, ind_count) VALUES (?, ?, ?)";
            String sqlStock = "UPDATE Products SET pro_count = pro_count - ? WHERE pro_ID = ?";
            PreparedStatement psDetail = con.prepareStatement(sqlDetail);
            PreparedStatement psStock = con.prepareStatement(sqlStock);

            for (int i = 0; i < detailModel.getRowCount(); i++) {
                int proID = Integer.parseInt(detailModel.getValueAt(i, 0).toString());
                int qty = Integer.parseInt(detailModel.getValueAt(i, 3).toString());
                int currentStock = getProductStock(con, proID);

                if (currentStock < qty) throw new Exception("Sản phẩm ID " + proID + " không đủ hàng.");

                psDetail.setInt(1, newInvID); psDetail.setInt(2, proID); psDetail.setInt(3, qty);
                psDetail.executeUpdate();

                psStock.setInt(1, qty); psStock.setInt(2, proID);
                psStock.executeUpdate();
            }

            con.commit();
            showSuccess(this, "Tạo hóa đơn thành công! Mã: #" + newInvID);
            loadListData();

            for (int i = 0; i < listInvoice.getModel().getSize(); i++) {
                ComboItem item = listInvoice.getModel().getElementAt(i);
                if (item.getValue() == newInvID) {
                    listInvoice.setSelectedIndex(i);
                    break;
                }
            }
            loadDetail(newInvID);

        } catch (Exception ex) {
            try { if(con!=null) con.rollback(); } catch(Exception e) {
                showError(this, "Lỗi: " + ex.getMessage());
            }
            showError(this, "Lỗi tạo: " + ex.getMessage());
        } finally {
            try { if(con!=null) { con.setAutoCommit(true); con.close(); } } catch(Exception e) {
                showError(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void saveChangesToDatabase() {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String sqlOld = "SELECT pro_ID, ind_count FROM Invoice_details WHERE inv_ID = ?";
            PreparedStatement psOld = con.prepareStatement(sqlOld);
            psOld.setInt(1, selectedInvID);
            ResultSet rsOld = psOld.executeQuery();
            while (rsOld.next()) {
                PreparedStatement psRestock = con.prepareStatement("UPDATE Products SET pro_count = pro_count + ? WHERE pro_ID = ?");
                psRestock.setInt(1, rsOld.getInt("ind_count"));
                psRestock.setInt(2, rsOld.getInt("pro_ID"));
                psRestock.executeUpdate();
            }

            PreparedStatement psDel = con.prepareStatement("DELETE FROM Invoice_details WHERE inv_ID = ?");
            psDel.setInt(1, selectedInvID);
            psDel.executeUpdate();

            String sqlIns = "INSERT INTO Invoice_details (inv_ID, pro_ID, ind_count) VALUES (?, ?, ?)";
            String sqlDed = "UPDATE Products SET pro_count = pro_count - ? WHERE pro_ID = ?";
            PreparedStatement psIns = con.prepareStatement(sqlIns);
            PreparedStatement psDed = con.prepareStatement(sqlDed);

            for (int i = 0; i < detailModel.getRowCount(); i++) {
                int proID = Integer.parseInt(detailModel.getValueAt(i, 0).toString());
                int qty = Integer.parseInt(detailModel.getValueAt(i, 3).toString());
                int currentStock = getProductStock(con, proID);

                if (currentStock < qty) throw new Exception("Sản phẩm ID " + proID + " không đủ hàng.");

                psIns.setInt(1, selectedInvID); psIns.setInt(2, proID); psIns.setInt(3, qty);
                psIns.executeUpdate();

                psDed.setInt(1, qty); psDed.setInt(2, proID);
                psDed.executeUpdate();
            }

            String sqlHead = "UPDATE Invoices SET cus_ID=?, sta_ID=?, inv_price=? WHERE inv_ID=?";
            PreparedStatement psHead = con.prepareStatement(sqlHead);
            ComboItem cus = (ComboItem) cbCustomer.getSelectedItem();
            ComboItem sta = (ComboItem) cbStaff.getSelectedItem();
            double total = Double.parseDouble(txtTotalMoney.getText().replace(",", ""));

            psHead.setInt(1, Objects.requireNonNull(cus).getValue());
            psHead.setInt(2, Objects.requireNonNull(sta).getValue());
            psHead.setDouble(3, total);
            psHead.setInt(4, selectedInvID);
            psHead.executeUpdate();

            con.commit();
            showSuccess(this, "Cập nhật thành công!");
            loadDetail(selectedInvID);

        } catch (Exception ex) {
            try { if(con!=null) con.rollback(); } catch(Exception _) {}
            showError(this, "Lỗi cập nhật: " + ex.getMessage());
        } finally {
            try { if(con!=null) { con.setAutoCommit(true); con.close(); } } catch(Exception _){}
        }
    }

    private void printInvoice() {
        if (selectedInvID == -1) return;

        try {
            StringBuilder bill = new StringBuilder();
            bill.append("<html>");
            bill.append("<head><style>");
            bill.append("body { font-family: Monospaced; font-size: 12px; }");
            bill.append("h2 { text-align: center; }");
            bill.append(".line { border-bottom: 1px dashed black; }");
            bill.append(".right { text-align: right; }");
            bill.append("table { width: 100%; border-collapse: collapse; }");
            bill.append("td, th { padding: 5px; text-align: left; }");
            bill.append(".num { text-align: right; }");
            bill.append("</style></head><body>");
            bill.append("<h2>HÓA ĐƠN BÁN HÀNG</h2>");
            bill.append("<p style='text-align:center'>Mã số: #").append(selectedInvID).append("</p>");
            bill.append("<p style='text-align:center'>Ngày: ").append(txtDate.getText()).append("</p>");
            bill.append("<div class='line'></div>");
            bill.append("<p><b>Khách hàng:</b> ").append(cbCustomer.getSelectedItem()).append("</p>");
            bill.append("<p><b>Nhân viên:</b> ").append(cbStaff.getSelectedItem()).append("</p>");
            bill.append("<br>");
            bill.append("<table>");
            bill.append("<tr><th>Sản phẩm</th><th class='num'>SL</th><th class='num'>Đ.Giá</th><th class='num'>T.Tiền</th></tr>");

            for (int i = 0; i < detailModel.getRowCount(); i++) {
                String name = detailModel.getValueAt(i, 1).toString();
                String price = detailModel.getValueAt(i, 2).toString();
                String qty = detailModel.getValueAt(i, 3).toString();
                String total = detailModel.getValueAt(i, 4).toString();

                bill.append("<tr>");
                bill.append("<td>").append(name).append("</td>");
                bill.append("<td class='num'>").append(qty).append("</td>");
                bill.append("<td class='num'>").append(price).append("</td>");
                bill.append("<td class='num'>").append(total).append("</td>");
                bill.append("</tr>");
            }
            bill.append("</table>");

            bill.append("<div class='line'></div>");
            bill.append("<h3 class='right'>TỔNG CỘNG: ").append(txtTotalMoney.getText()).append(" VND</h3>");

            bill.append("<br><p style='text-align:center; font-style:italic;'>Cảm ơn quý khách và hẹn gặp lại!</p>");
            bill.append("</body></html>");

            JTextPane printingComponent = new JTextPane();
            printingComponent.setContentType("text/html");
            printingComponent.setText(bill.toString());

            boolean complete = printingComponent.print(null, null, true, null, null, true);
            if (complete) {
                showSuccess(this, "Đã gửi lệnh in thành công!");
            }

        } catch (Exception ex) {
            showError(this, "Lỗi in ấn: " + ex.getMessage());
        }
    }

    private void deleteInvoiceTransaction() {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String sqlGetItems = "SELECT pro_ID, ind_count FROM Invoice_details WHERE inv_ID = ?";
            PreparedStatement psGet = con.prepareStatement(sqlGetItems);
            psGet.setInt(1, selectedInvID);
            ResultSet rs = psGet.executeQuery();
            String sqlRestock = "UPDATE Products SET pro_count = pro_count + ? WHERE pro_ID = ?";
            PreparedStatement psRestock = con.prepareStatement(sqlRestock);

            while (rs.next()) {
                psRestock.setInt(1, rs.getInt("ind_count"));
                psRestock.setInt(2, rs.getInt("pro_ID"));
                psRestock.executeUpdate();
            }

            PreparedStatement psDelDetail = con.prepareStatement("DELETE FROM Invoice_details WHERE inv_ID = ?");
            psDelDetail.setInt(1, selectedInvID);
            psDelDetail.executeUpdate();

            PreparedStatement psDelHead = con.prepareStatement("DELETE FROM Invoices WHERE inv_ID = ?");
            psDelHead.setInt(1, selectedInvID);
            psDelHead.executeUpdate();

            con.commit();
            showSuccess(this, "Đã xóa và hoàn kho!");
            loadListData();
            clearForm();

        } catch (Exception ex) {
            try { if (con != null) con.rollback(); } catch (Exception ignored) {}
            showError(this, "Lỗi xóa: " + ex.getMessage());
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (Exception ignored) {}
        }
    }

    private void calculateUITotal() {
        double total = 0;
        for (int i = 0; i < detailModel.getRowCount(); i++) {
            String val = detailModel.getValueAt(i, 4).toString().replace(",", "");
            total += Double.parseDouble(val);
        }
        txtTotalMoney.setText(String.format("%.0f", total));
    }

    private double getProductPrice(int proID) {
        try (Connection con = DBConnection.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT pro_price FROM Products WHERE pro_ID=" + proID);
            if(rs.next()) return rs.getDouble(1);
        } catch(Exception _){}
        return 0;
    }

    private int getProductStock(Connection con, int proID) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT pro_count FROM Products WHERE pro_ID = ?");
        ps.setInt(1, proID);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getInt("pro_count") : 0;
    }

    private void setSelectedComboItem(JComboBox<ComboItem> cb, int id) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (cb.getItemAt(i).getValue() == id) { cb.setSelectedIndex(i); return; }
        }
    }

    private void enableForm(boolean enable) {
        cbCustomer.setEnabled(enable);
        cbStaff.setEnabled(enable);
        if (btnQuickAddCustomer != null) btnQuickAddCustomer.setEnabled(enable);
        if (btnAddDetail != null) btnAddDetail.setEnabled(enable);
        if (btnEditDetail != null) btnEditDetail.setEnabled(enable);
        if (btnDelDetail != null) btnDelDetail.setEnabled(enable);
    }

    private void setDetailButtonsVisible(boolean visible) {
        if (btnAddDetail != null) btnAddDetail.setVisible(visible);
        if (btnEditDetail != null) btnEditDetail.setVisible(visible);
        if (btnDelDetail != null) btnDelDetail.setVisible(visible);
        if (btnQuickAddCustomer != null) btnQuickAddCustomer.setVisible(visible);
    }

    private void clearForm() {
        isDataLoading = true;
        txtTotalMoney.setText("");
        txtID.setText("[Tự động]");
        txtDate.setText("[Tự động]");

        if (cbCustomer.getItemCount() > 0) cbCustomer.setSelectedIndex(0);

        if (cbStaff.getItemCount() > 0) {
            if (JDBCUntils.Session.isLoggedIn) {
                int myID = JDBCUntils.Session.loggedInStaffID;

                for (int i = 0; i < cbStaff.getItemCount(); i++) {
                    if (cbStaff.getItemAt(i).getValue() == myID) {
                        cbStaff.setSelectedIndex(i);
                        break;
                    }
                }
            } else {
                cbStaff.setSelectedIndex(0);
            }
        }

        detailModel.setRowCount(0);
        selectedInvID = -1;
        listInvoice.clearSelection();
        btnSave.setVisible(false);
        btnDelete.setVisible(false);
        btnPrint.setVisible(false);
        setDetailButtonsVisible(false);

        enableForm(false);

        isDataLoading = false;
    }

    public void refreshData() {
        cbCustomer.removeAllItems();
        cbStaff.removeAllItems();
        loadComboBoxData();
        loadListData();
    }

    private void addChangeListeners() {
        cbCustomer.addActionListener(_ -> checkChange());
        cbStaff.addActionListener(_ -> checkChange());
    }

    private void checkChange() { if (!isDataLoading) btnSave.setVisible(true); }
}