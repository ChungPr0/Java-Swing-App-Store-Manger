package Main.HomeManager;

import Utils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static Utils.Style.showError;

public class RevenueChartPanel extends JPanel {
    private final List<String> dates = new ArrayList<>();
    private final List<Double> values = new ArrayList<>();
    private double maxValue = 0;

    public RevenueChartPanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#bdc3c7"), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblTitle = new JLabel("BIỂU ĐỒ DOANH THU 7 NGÀY QUA", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Color.decode("#2c3e50"));
        lblTitle.setBorder(new EmptyBorder(10, 0, 20, 0));
        this.add(lblTitle, BorderLayout.NORTH);
    }

    public void loadChartData() {
        dates.clear();
        values.clear();
        maxValue = 0;

        String sql = "SELECT DATE(inv_date) as d, SUM(inv_price) as total " +
                "FROM Invoices " +
                "WHERE inv_date >= DATE_SUB(NOW(), INTERVAL 7 DAY) " +
                "GROUP BY DATE(inv_date) " +
                "ORDER BY d DESC";

        try (Connection con = DBConnection.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery(sql);
            List<String> tempDates = new ArrayList<>();
            List<Double> tempValues = new ArrayList<>();

            while (rs.next()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
                tempDates.add(sdf.format(rs.getDate("d")));
                double val = rs.getDouble("total");
                tempValues.add(val);
                if (val > maxValue) maxValue = val;
            }

            for (int i = tempDates.size() - 1; i >= 0; i--) {
                dates.add(tempDates.get(i));
                values.add(tempValues.get(i));
            }
            repaint();
        } catch (Exception e) {
            showError(this, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Chữ mịn hơn

        Font fontValue = new Font("Segoe UI", Font.BOLD, 13);
        Font fontDate = new Font("Segoe UI", Font.PLAIN, 12);

        if (values.isEmpty()) {
            g2.setFont(fontValue);
            g2.drawString("Chưa có dữ liệu doanh thu tuần này", getWidth()/2 - 100, getHeight()/2);
            return;
        }

        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int graphHeight = height - 2 * padding;

        int numberOfBars = values.size();
        int slotWidth = (width - 2 * padding) / numberOfBars;
        int maxBarWidth = 80;
        int barWidth = Math.min(slotWidth - 20, maxBarWidth);
        if (barWidth < 10) barWidth = 10;

        g2.setColor(new Color(200, 200, 200));
        g2.drawLine(padding, height - padding, width - padding, height - padding); // Trục X
        g2.drawLine(padding, height - padding, padding, padding); // Trục Y

        double scaleFactor = 0.75;

        for (int i = 0; i < numberOfBars; i++) {
            double val = values.get(i);
            int barHeight = (int) ((val / maxValue) * graphHeight * scaleFactor);
            if (barHeight < 2 && val > 0) barHeight = 2;

            int x = padding + (i * slotWidth) + (slotWidth - barWidth) / 2;
            int y = height - padding - barHeight;

            g2.setColor(new Color(46, 204, 113));
            g2.fillRect(x, y, barWidth, barHeight);
            g2.setColor(new Color(39, 174, 96));
            g2.drawRect(x, y, barWidth, barHeight);

            String priceStr;
            if (val >= 1000000) {
                double tr = val / 1000000.0;
                if (tr == (long) tr) {
                    priceStr = String.format("%d Tr", (long)tr);
                } else {
                    priceStr = String.format("%.1f Tr", tr);
                }
            } else {
                priceStr = String.format("%d K", (long)(val / 1000));
            }
            g2.setFont(fontValue);
            g2.setColor(Color.DARK_GRAY);
            int strWidth = g2.getFontMetrics().stringWidth(priceStr);
            g2.drawString(priceStr, x + (barWidth - strWidth) / 2, y - 5);

            g2.setFont(fontDate);
            g2.setColor(Color.DARK_GRAY);
            String dateStr = dates.get(i);
            int dateWidth = g2.getFontMetrics().stringWidth(dateStr);
            g2.drawString(dateStr, x + (barWidth - dateWidth) / 2, height - padding + 20);
        }
    }
}