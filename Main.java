
import java.util.*;
import java.sql.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static String url = "jdbc:mysql://localhost:3306/rpms";
    static String user = "root";
    static String pass = "YOUR_MYSQL_PASSWORD";

    static Connection con;

    public static void main(String[] args) {

        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Database Connected Successfully!");

            System.out.println("\n=== RECRUITMENT & PLACEMENT SYSTEM ===");
            System.out.print("Username: ");
            String u = sc.nextLine();

            System.out.print("Password: ");
            String p = sc.nextLine();

            if (!u.equals("admin") || !p.equals("1234")) {
                System.out.println("Invalid Login!");
                return;
            }

            int ch;

            do {
                System.out.println("\n===== MAIN MENU =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Search Student");
                System.out.println("4. Add Company");
                System.out.println("5. View Companies");
                System.out.println("6. Add Job");
                System.out.println("7. View Jobs");
                System.out.println("8. Apply for Job");
                System.out.println("9. View Applications");
                System.out.println("10. Exit");

                System.out.print("Enter Choice: ");
                ch = Integer.parseInt(sc.nextLine());

                switch (ch) {
                    case 1: addStudent(); break;
                    case 2: viewStudents(); break;
                    case 3: searchStudent(); break;
                    case 4: addCompany(); break;
                    case 5: viewCompanies(); break;
                    case 6: addJob(); break;
                    case 7: viewJobs(); break;
                    case 8: applyJob(); break;
                    case 9: viewApplications(); break;
                    case 10: System.out.println("Thank You!"); break;
                    default: System.out.println("Invalid Choice!");
                }

            } while (ch != 10);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void addStudent() throws SQLException {
        System.out.print("Student Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Percentage: ");
        double marks = Double.parseDouble(sc.nextLine());

        String sql = "INSERT INTO students(name,email,percentage) VALUES(?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setDouble(3, marks);

        ps.executeUpdate();
        System.out.println("Student Added Successfully!");
    }

    static void viewStudents() throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM students");

        while (rs.next()) {
            System.out.println(rs.getInt("id") + " "
                    + rs.getString("name") + " "
                    + rs.getString("email") + " "
                    + rs.getDouble("percentage"));
        }
    }

    static void searchStudent() throws SQLException {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM students WHERE email=?");
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Email: " + rs.getString("email"));
            System.out.println("Percentage: " + rs.getDouble("percentage"));
        } else {
            System.out.println("Student Not Found!");
        }
    }

    static void addCompany() throws SQLException {
        System.out.print("Company Name: ");
        String name = sc.nextLine();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO companies(name) VALUES(?)");
        ps.setString(1, name);
        ps.executeUpdate();

        System.out.println("Company Added!");
    }

    static void viewCompanies() throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM companies");

        while (rs.next()) {
            System.out.println(rs.getInt("id") + " "
                    + rs.getString("name"));
        }
    }

    static void addJob() throws SQLException {
        System.out.print("Enter Job Name: ");
        String job = sc.nextLine();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO jobs(job_name) VALUES(?)");
        ps.setString(1, job);
        ps.executeUpdate();

        System.out.println("Job Added!");
    }

    static void viewJobs() throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM jobs");

        while (rs.next()) {
            System.out.println(rs.getInt("id") + " "
                    + rs.getString("job_name"));
        }
    }

    static void applyJob() throws SQLException {
        System.out.print("Student Name: ");
        String student = sc.nextLine();

        System.out.print("Job Name: ");
        String job = sc.nextLine();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO applications(student_name,job_name) VALUES(?,?)");
        ps.setString(1, student);
        ps.setString(2, job);
        ps.executeUpdate();

        System.out.println("Application Submitted!");
    }

    static void viewApplications() throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM applications");

        while (rs.next()) {
            System.out.println(rs.getInt("id") + " "
                    + rs.getString("student_name") + " applied for "
                    + rs.getString("job_name"));
        }
    }
}
