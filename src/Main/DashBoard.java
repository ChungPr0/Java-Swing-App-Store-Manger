package Main;

import CustomerForm.CustomerManagerPanel;
import HomeForm.HomeManagerPanel;
import ProductForm.ProductManagerPanel;
import StaffForm.StaffManagerPanel;
import SupplierForm.SupplierManagerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static JDBCUntils.Style.createMenuLabel;

public class DashBoard extends JFrame {
    private JPanel mainContainer;
    private JPanel menuPanel;
    private JPanel contentPanel;
    private JLabel btnHome, btnStaff, btnSupplier, btnCustomer, btnProduct, btnInvoice;
    private CardLayout cardLayout;

    private InvoiceForm.InvoiceManagerPanel invoicePanel;

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

        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        menuPanel.setBackground(Color.decode("#2c3e50"));

        btnHome = createMenuLabel("TRANG CHỦ");
        btnStaff = createMenuLabel("NHÂN VIÊN");
        btnSupplier = createMenuLabel("NHÀ CUNG CẤP");
        btnCustomer = createMenuLabel("KHÁCH HÀNG");
        btnProduct = createMenuLabel("SẢN PHẨM");
        btnInvoice = createMenuLabel("HÓA ĐƠN");

        menuPanel.add(btnHome);
        menuPanel.add(btnStaff);
        menuPanel.add(btnSupplier);
        menuPanel.add(btnCustomer);
        menuPanel.add(btnProduct);
        menuPanel.add(btnInvoice);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new HomeManagerPanel(), "HOME");
        contentPanel.add(new StaffManagerPanel(), "STAFF");
        contentPanel.add(new SupplierManagerPanel(), "SUPPLIER");
        contentPanel.add(new CustomerManagerPanel(), "CUSTOMER");
        contentPanel.add(new ProductManagerPanel(), "PRODUCT");
        invoicePanel = new InvoiceForm.InvoiceManagerPanel();
        contentPanel.add(invoicePanel, "INVOICE");

        mainContainer.add(menuPanel, BorderLayout.NORTH);
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        this.setContentPane(mainContainer);
    }

    private void addEvents() {
        btnHome.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "HOME");
            }
        });

        btnStaff.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "STAFF");
            }
        });

        btnSupplier.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "SUPPLIER");
            }
        });

        btnCustomer.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "CUSTOMER");
            }
        });

        btnProduct.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "PRODUCT");
            }
        });

        btnInvoice.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, "INVOICE");
            }
        });
    }

    public void showInvoiceAndLoad(int invID) {
        cardLayout.show(contentPanel, "INVOICE");
        if (invoicePanel != null) {
            invoicePanel.loadDetail(invID);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashBoard().setVisible(true));
    }
}