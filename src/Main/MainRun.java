package Main;

import Login.LoginForm;
import JDBCUntils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainRun {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Color black = Color.BLACK;
            UIManager.put("TextField.inactiveForeground", black);
            UIManager.put("TextField.disabledTextColor", black);
            UIManager.put("ComboBox.disabledForeground", black);
            UIManager.put("TextArea.inactiveForeground", black);
            UIManager.put("PasswordField.inactiveForeground", black);
            UIManager.put("FormattedTextField.inactiveForeground", black);
            UIManager.put("Button.disabledText", Color.GRAY);
            if (checkDatabaseConnection()) {
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
                loginForm.setLocationRelativeTo(null);
            }

        } catch (Exception _) {}
    }

    private static boolean checkDatabaseConnection() {
        try (Connection con = DBConnection.getConnection()) {
            return con != null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi kết nối CSDL: " + e.getMessage(),
                    "Lỗi Khởi Động",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}