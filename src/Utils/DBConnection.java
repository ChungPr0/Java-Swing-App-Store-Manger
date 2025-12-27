package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    // Đường dẫn "cứng" tới file database.
    // "jdbc:sqlite:storedatabase.db" nghĩa là file nằm ngay cạnh file chạy (.jar) hoặc thư mục gốc dự án.
    private static final String DB_URL = "jdbc:sqlite:data/storedatabase.db";

    /**
     * Tạo kết nối tới file SQLite Database và bật hỗ trợ khóa ngoại.
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // 1. Nạp Driver SQLite
        Class.forName("org.sqlite.JDBC");

        // 2. Tạo kết nối trực tiếp
        // Nếu file chưa có, SQLite sẽ TỰ ĐỘNG TẠO file mới rỗng.
        Connection conn = DriverManager.getConnection(DB_URL);

        // 3. Bật hỗ trợ khóa ngoại (Foreign Key) cho mỗi kết nối
        // Đây là bước quan trọng để đảm bảo tính toàn vẹn dữ liệu trong SQLite
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
        }

        return conn;
    }
}