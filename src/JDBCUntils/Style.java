package JDBCUntils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Style {
    public static JLabel createMenuLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.decode("#2c3e50"));
        label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { label.setBackground(Color.decode("#1abc9c")); }
            public void mouseExited(MouseEvent e) { label.setBackground(Color.decode("#2c3e50")); }
        });
        return label;
    }

    public static JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(Color.decode("#2c3e50"));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(Color.decode("#34495e"));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public static JPanel createTextFieldWithLabel(JTextField tf, String labelText) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        p.add(createTitleLabel(labelText), BorderLayout.NORTH);

        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setPreferredSize(new Dimension(0, 35));

        tf.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.LineBorder(Color.decode("#bdc3c7"), 1),
                new javax.swing.border.EmptyBorder(5, 10, 5, 10)
        ));

        installFocusAnimation(tf);

        p.add(tf, BorderLayout.CENTER);

        return p;
    }

    public static JPanel createComboBoxWithLabel(JComboBox box, String labelText, JButton btn1, JButton btn2) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        p.add(createTitleLabel(labelText), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 0));
        centerPanel.setBackground(Color.WHITE);

        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(0, 35));

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setBackground(Color.WHITE);

        if (btn1 != null) {
            btnPanel.add(btn1);
            btn1.setPreferredSize(new Dimension(80, 35));
            btn1.setMaximumSize(new Dimension(80, 35));
        }

        if (btn1 != null && btn2 != null) {
            btnPanel.add(Box.createHorizontalStrut(5));
        }

        if (btn2 != null) {
            btnPanel.add(btn2);
            btn2.setPreferredSize(new Dimension(80, 35));
            btn2.setMaximumSize(new Dimension(80, 35));
        }

        centerPanel.add(box, BorderLayout.CENTER);
        centerPanel.add(btnPanel, BorderLayout.EAST);
        p.add(centerPanel, BorderLayout.CENTER);

        return p;
    }

    public static JPanel createComboBoxWithLabel(JComboBox box, String labelText) {
        return createComboBoxWithLabel(box, labelText, null, null);
    }

    public static JPanel createComboBoxWithLabel(JComboBox box, String labelText, JButton btn) {
        return createComboBoxWithLabel(box, labelText, btn, null);
    }

    public static JPanel createTextFieldWithPlaceholder(JTextField textField, String labelText) {


        JPanel p = new JPanel(new BorderLayout(5, 5));
        textField.setText("Tìm kiếm...");
        textField.setForeground(Color.GRAY);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        p.add(createTitleLabel(labelText), BorderLayout.NORTH);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(0, 35));

        textField.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.LineBorder(Color.decode("#bdc3c7"), 1),
                new javax.swing.border.EmptyBorder(5, 10, 5, 10)
        ));

        p.add(textField, BorderLayout.CENTER);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals("Tìm kiếm...")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText("Tìm kiếm...");
                }
            }
        });

        return p;
    }

    public static JLabel createSeparator() {
        JLabel lbl = new JLabel(" / ");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(Color.GRAY);
        return lbl;
    }

    public static JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding (Đệm)
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    public static JButton createSmallButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));

        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);

        btn.setPreferredSize(new Dimension(80, 30));

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });

        return btn;
    }

    public static JPanel createDatePanel(String labelText, JComboBox<String> day, JComboBox<String> month, JComboBox<String> year) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        JLabel lbl = createTitleLabel(labelText);
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setBackground(Color.WHITE);
        createStyleComboBox(day);
        createStyleComboBox(month);
        createStyleComboBox(year);
        row.add(day);
        row.add(createSeparator());
        row.add(month);
        row.add(createSeparator());
        row.add(year);
        p.add(lbl, BorderLayout.NORTH);
        p.add(row, BorderLayout.CENTER);
        return p;
    }

    public static JPanel createCard(String title, JLabel lblValue, Color color, String iconPath) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(color);
        card.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel pText = new JPanel(new GridLayout(2, 1));
        pText.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(new Color(255, 255, 255, 200));
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblValue.setForeground(Color.WHITE);

        pText.add(lblTitle);
        pText.add(lblValue);

        JLabel lblIcon = new JLabel();

        lblIcon.setIcon(new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

        lblIcon.setHorizontalAlignment(SwingConstants.RIGHT);

        card.add(pText, BorderLayout.CENTER);
        card.add(lblIcon, BorderLayout.EAST);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        return card;
    }

    public static boolean showConfirm(Component parent, String msg) {
        final boolean[] result = {false};

        Color mainColor = new Color(230, 126, 34);
        String title = "XÁC NHẬN";
        int maxWidth = 400;
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent));
        dialog.setModal(true);
        dialog.setUndecorated(true);
        dialog.setLayout(new BorderLayout());

        ((JPanel)dialog.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setBackground(mainColor);
        pHeader.setPreferredSize(new Dimension(0, 40));
        pHeader.setBorder(new javax.swing.border.EmptyBorder(0, 15, 0, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);
        pHeader.add(lblTitle, BorderLayout.WEST);

        JLabel lblClose = new JLabel("×");
        lblClose.setFont(new Font("Arial", Font.BOLD, 28));
        lblClose.setForeground(Color.WHITE);
        lblClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                result[0] = false;
                dialog.dispose();
            }
        });
        pHeader.add(lblClose, BorderLayout.EAST);

        JPanel pContent = new JPanel(new BorderLayout());
        pContent.setBackground(Color.WHITE);
        pContent.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        JTextPane txtMsg = new JTextPane();
        txtMsg.setText(msg);
        txtMsg.setFont(new Font("Segoe UI", Font.BOLD, 15));
        txtMsg.setForeground(Color.decode("#2c3e50"));
        txtMsg.setEnabled(false);
        txtMsg.setDisabledTextColor(Color.decode("#2c3e50"));
        txtMsg.setOpaque(false);

        javax.swing.text.StyledDocument doc = txtMsg.getStyledDocument();
        javax.swing.text.SimpleAttributeSet center = new javax.swing.text.SimpleAttributeSet();
        javax.swing.text.StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        txtMsg.setSize(new Dimension(maxWidth, Short.MAX_VALUE));
        Dimension prefSize = txtMsg.getPreferredSize();
        txtMsg.setPreferredSize(new Dimension(maxWidth, prefSize.height));

        pContent.add(txtMsg, BorderLayout.CENTER);

        JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pButton.setBackground(Color.WHITE);
        pButton.setBorder(new javax.swing.border.EmptyBorder(0, 0, 15, 0));

        JButton btnYes = createButton("Xác Nhận", mainColor);
        JButton btnNo = createButton("Hủy Bỏ", Color.LIGHT_GRAY);
        btnYes.setPreferredSize(new Dimension(110, 35));
        btnNo.setPreferredSize(new Dimension(110, 35));
        btnNo.setBorder(BorderFactory.createLineBorder(Color.decode("#bdc3c7"), 1));

        btnYes.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });

        btnNo.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });

        btnYes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnYes.setBackground(mainColor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnYes.setBackground(mainColor); }
        });

        btnNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnNo.setBackground(Color.LIGHT_GRAY.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnNo.setBackground(Color.LIGHT_GRAY); }
        });

        pButton.add(btnYes);
        pButton.add(btnNo);

        dialog.add(pHeader, BorderLayout.NORTH);
        dialog.add(pContent, BorderLayout.CENTER);
        dialog.add(pButton, BorderLayout.SOUTH);

        dialog.getRootPane().setDefaultButton(btnYes);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result[0];
    }

    public static void showSuccess(Component parent, String msg) {
        showCustomAlert(parent, msg, true);
    }

    public static void showError(Component parent, String msg) {
        showCustomAlert(parent, msg, false);
    }

    private static void showCustomAlert(Component parent, String msg, boolean isSuccess) {
        Color mainColor = isSuccess ? new Color(46, 204, 113) : new Color(231, 76, 60);
        String title = isSuccess ? "THÀNH CÔNG" : "THẤT BẠI";
        int maxWidth = 400;

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent));
        dialog.setModal(true);
        dialog.setUndecorated(true);
        dialog.setLayout(new BorderLayout());

        ((JPanel)dialog.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setBackground(mainColor);
        pHeader.setPreferredSize(new Dimension(0, 40));
        pHeader.setBorder(new javax.swing.border.EmptyBorder(0, 15, 0, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);
        pHeader.add(lblTitle, BorderLayout.WEST);

        JLabel lblClose = new JLabel("×");
        lblClose.setFont(new Font("Arial", Font.BOLD, 28));
        lblClose.setForeground(Color.WHITE);
        lblClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { dialog.dispose(); }
        });
        pHeader.add(lblClose, BorderLayout.EAST);

        JPanel pContent = new JPanel(new BorderLayout());
        pContent.setBackground(Color.WHITE);
        pContent.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        JTextPane txtMsg = new JTextPane();
        txtMsg.setText(msg);
        txtMsg.setFont(new Font("Segoe UI", Font.BOLD, 15));
        txtMsg.setForeground(Color.decode("#2c3e50"));
        txtMsg.setEnabled(false);
        txtMsg.setDisabledTextColor(Color.decode("#2c3e50"));
        txtMsg.setOpaque(false);

        javax.swing.text.StyledDocument doc = txtMsg.getStyledDocument();
        javax.swing.text.SimpleAttributeSet center = new javax.swing.text.SimpleAttributeSet();
        javax.swing.text.StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        txtMsg.setSize(new Dimension(maxWidth, Short.MAX_VALUE));
        Dimension prefSize = txtMsg.getPreferredSize();
        txtMsg.setPreferredSize(new Dimension(maxWidth, prefSize.height));

        pContent.add(txtMsg, BorderLayout.CENTER);

        JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pButton.setBackground(Color.WHITE);
        pButton.setBorder(new javax.swing.border.EmptyBorder(0, 0, 15, 0));

        JButton btnOK = createButton("Đồng ý", mainColor);
        btnOK.setPreferredSize(new Dimension(110, 35));

        btnOK.addActionListener(e -> dialog.dispose());

        btnOK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnOK.setBackground(mainColor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnOK.setBackground(mainColor); }
        });

        pButton.add(btnOK);

        dialog.add(pHeader, BorderLayout.NORTH);
        dialog.add(pContent, BorderLayout.CENTER);
        dialog.add(pButton, BorderLayout.SOUTH);

        dialog.getRootPane().setDefaultButton(btnOK);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private static void createStyleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(80, 35));
    }

    private static void installFocusAnimation(JTextField tf) {
        final Color normalColor = Color.decode("#bdc3c7");
        final Color focusColor = Color.decode("#3498db");

        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            private javax.swing.Timer timer;
            private float progress = 0f;

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                runAnimation(true);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                runAnimation(false);
            }

            private void runAnimation(boolean isEnter) {
                if (timer != null && timer.isRunning()) timer.stop();

                timer = new javax.swing.Timer(10, evt -> {
                    if (isEnter) {
                        progress += 0.1f;
                        if (progress >= 1f) { progress = 1f; timer.stop(); }
                    } else {
                        progress -= 0.1f;
                        if (progress <= 0f) { progress = 0f; timer.stop(); }
                    }

                    Color newColor = blendColors(normalColor, focusColor, progress);

                    tf.setBorder(new javax.swing.border.CompoundBorder(
                            new javax.swing.border.LineBorder(newColor, 1),
                            new javax.swing.border.EmptyBorder(5, 10, 5, 10)
                    ));
                    tf.repaint();
                });
                timer.start();
            }
        });
    }

    private static Color blendColors(Color c1, Color c2, float ratio) {
        ratio = Math.max(0, Math.min(1, ratio));

        int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);

        return new Color(r, g, b);
    }
}
