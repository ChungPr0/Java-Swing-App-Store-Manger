package Login;

import JDBCUntils.DBConnection;
import JDBCUntils.Session;
import Main.DashBoard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static JDBCUntils.Style.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginForm() {
        setTitle("Đăng Nhập Hệ Thống");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(Color.WHITE);

        JLabel lblTitle = createHeaderLabel("ĐĂNG NHẬP");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(30));

        txtUsername = new JTextField();
        JPanel pUser = createTextFieldWithLabel(txtUsername, "Tài khoản:");
        mainPanel.add(pUser);
        mainPanel.add(Box.createVerticalStrut(20));

        txtPassword = new JPasswordField();
        JPanel pPass = createPasswordFieldWithLabel(txtPassword, "Mật khẩu:");
        mainPanel.add(pPass);
        mainPanel.add(Box.createVerticalStrut(30));


        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pBtn.setBackground(Color.WHITE);
        JButton btnLogin = createButton("Đăng Nhập", new Color(46, 204, 113));
        JButton btnExit = createButton("Thoát", new Color(231, 76, 60));

        pBtn.add(btnLogin);
        pBtn.add(btnExit);
        mainPanel.add(pBtn);

        add(mainPanel);

        btnExit.addActionListener(_ -> System.exit(0));
        btnLogin.addActionListener(_ -> checkLogin());
        getRootPane().setDefaultButton(btnLogin);
    }

    private void checkLogin() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            showError(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Staffs WHERE sta_username = ? AND sta_password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Session.isLoggedIn = true;
                Session.loggedInStaffID = rs.getInt("sta_ID");
                Session.loggedInStaffName = rs.getString("sta_name");

                Session.userRole = rs.getString("sta_role");

                new DashBoard().setVisible(true);
                this.dispose();

            } else {
                showError(this, "Sai tài khoản hoặc mật khẩu!");
            }

        } catch (Exception ex) {
            showError(this, "Lỗi kết nối: " + ex.getMessage());
        }
    }
}