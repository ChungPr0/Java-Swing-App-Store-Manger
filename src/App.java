import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class App extends JFrame {
    private JTextArea quanLyBanHangTextArea;
    private JButton loginButton;

    public static void main(String[] args) {
        new App();
    }

    public App(){
        super();
        this.setTitle("Quản Lý Bán Hàng");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        loginButton = new JButton("Login");
        loginButton.setBounds(130, 100, 100, 30);
        this.add(loginButton);
        this.setVisible(true);
        run();
    }

    public void run(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginForm();
            }
        });
    }
}
