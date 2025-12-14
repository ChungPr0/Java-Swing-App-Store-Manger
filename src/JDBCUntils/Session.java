package JDBCUntils;

public class Session {
    public static int loggedInStaffID = -1;
    public static String loggedInStaffName = "";
    public static boolean isLoggedIn = false;

    public static String userRole = "";


    public static void clear() {
        loggedInStaffID = -1;
        loggedInStaffName = "";
        userRole = "";
        isLoggedIn = false;
    }

    public static boolean isAdmin() {
        return userRole.equalsIgnoreCase("Admin");
    }
}