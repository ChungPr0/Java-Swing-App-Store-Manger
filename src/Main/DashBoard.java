package Main;

import CustomerForm.CustomerManagerPanel;
import HomeForm.HomeManagerPanel;
import InvoiceForm.InvoiceManagerPanel;
import JDBCUntils.Session;
import Login.LoginForm;
import ProductForm.ProductManagerPanel;
import StaffForm.StaffManagerPanel;
import SupplierForm.SupplierManagerPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import static JDBCUntils.Style.*;

public class DashBoard extends JFrame {
    private JPanel mainContainer;
    private JPanel menuPanel, leftMenuPanel, rightMenuPanel;
    private JPanel contentPanel;
    private JLabel btnHome, btnStaff, btnSupplier, btnCustomer, btnProduct, btnInvoice, btnInfo;
    private CardLayout cardLayout;

    private HomeManagerPanel homePanel;
    private StaffManagerPanel staffPanel;
    private SupplierManagerPanel supplierPanel;
    private CustomerManagerPanel customerPanel;
    private ProductManagerPanel productPanel;
    private InvoiceManagerPanel invoicePanel;

    public DashBoard() {
        super("Quản Lý Cửa Hàng");
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        initUI();
        addEvents();
    }

    private void initUI() {
        mainContainer = new JPanel(new BorderLayout());

        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.decode("#2c3e50"));

        leftMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftMenuPanel.setBackground(Color.decode("#2c3e50"));

        btnHome = createMenuLabel("TRANG CHỦ");
        btnStaff = createMenuLabel("NHÂN VIÊN");
        btnSupplier = createMenuLabel("NHÀ CUNG CẤP");
        btnCustomer = createMenuLabel("KHÁCH HÀNG");
        btnProduct = createMenuLabel("SẢN PHẨM");
        btnInvoice = createMenuLabel("HÓA ĐƠN");
        btnInfo = createMenuLabel("TÀI KHOẢN");
        setupUserPopup(btnInfo);

        leftMenuPanel.add(btnHome);
        leftMenuPanel.add(btnStaff);
        leftMenuPanel.add(btnSupplier);
        leftMenuPanel.add(btnCustomer);
        leftMenuPanel.add(btnProduct);
        leftMenuPanel.add(btnInvoice);

        rightMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightMenuPanel.setBackground(Color.decode("#2c3e50"));
        rightMenuPanel.add(btnInfo);

        menuPanel.add(leftMenuPanel, BorderLayout.WEST);
        menuPanel.add(rightMenuPanel, BorderLayout.EAST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        homePanel = new HomeManagerPanel();
        staffPanel = new StaffManagerPanel();
        supplierPanel = new SupplierManagerPanel();
        customerPanel = new CustomerManagerPanel();
        productPanel = new ProductManagerPanel();
        invoicePanel = new InvoiceManagerPanel();

        contentPanel.add(homePanel, "HOME");
        contentPanel.add(staffPanel, "STAFF");
        contentPanel.add(supplierPanel, "SUPPLIER");
        contentPanel.add(customerPanel, "CUSTOMER");
        contentPanel.add(productPanel, "PRODUCT");
        contentPanel.add(invoicePanel, "INVOICE");

        mainContainer.add(menuPanel, BorderLayout.NORTH);
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        this.setContentPane(mainContainer);

        if (!JDBCUntils.Session.isAdmin()) {
            btnStaff.setVisible(false);
        }
    }

    private void addEvents() {
        btnHome.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "HOME");
                homePanel.refreshData();
            }
        });

        btnStaff.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "STAFF");
                staffPanel.refreshData();
            }
        });

        btnSupplier.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "SUPPLIER");
                supplierPanel.refreshData();
            }
        });

        btnCustomer.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "CUSTOMER");
                customerPanel.refreshData();
            }
        });

        btnProduct.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "PRODUCT");
                productPanel.refreshData();
            }
        });

        btnInvoice.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "INVOICE");
                invoicePanel.refreshData();
            }
        });
    }

    public void showInvoiceAndLoad(int invID) {
        cardLayout.show(contentPanel, "INVOICE");
        if (invoicePanel != null) {
            invoicePanel.loadDetail(invID);
        }
    }

    private void setupUserPopup(JComponent targetComponent) {
        JPopupMenu popupProfile = new JPopupMenu();
        popupProfile.setBackground(Color.WHITE);
        popupProfile.setBorder(BorderFactory.createLineBorder(Color.decode("#bdc3c7"), 1));
        popupProfile.setPreferredSize(new Dimension(250, 180));

        JPanel pContent = new JPanel();
        pContent.setLayout(new BoxLayout(pContent, BoxLayout.Y_AXIS));
        pContent.setBackground(Color.WHITE);
        pContent.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Thông tin tài khoản");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.decode("#2c3e50"));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblUser = new JLabel("Người dùng: " + JDBCUntils.Session.loggedInStaffName);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        String role = "Nhân viên";
        if (Objects.equals(Session.userRole, "Admin")) {
            role = "Quản lý";
        }

        JLabel lblRole = new JLabel("Vai trò: " + role);
        lblRole.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblRole.setForeground(Color.GRAY);
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnLogout = createButton("Đăng Xuất",Color.decode("#e74c3c"));
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(220, 35));

        btnLogout.addActionListener(e -> {
            popupProfile.setVisible(false);
            JDBCUntils.Session.clear();
            this.dispose();
            new LoginForm().setVisible(true);

        });

        pContent.add(lblTitle);
        pContent.add(Box.createVerticalStrut(10));
        pContent.add(new JSeparator());
        pContent.add(Box.createVerticalStrut(10));
        pContent.add(lblUser);
        pContent.add(Box.createVerticalStrut(5));
        pContent.add(lblRole);
        pContent.add(Box.createVerticalStrut(20));
        pContent.add(btnLogout);

        popupProfile.add(pContent);

        targetComponent.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x = targetComponent.getWidth() - popupProfile.getPreferredSize().width;
                int y = targetComponent.getHeight();
                popupProfile.show(targetComponent, x, y);
            }
        });
    }
}