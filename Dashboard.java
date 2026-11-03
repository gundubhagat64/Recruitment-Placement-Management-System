
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Dashboard extends JFrame {

    Color blue = new Color(30, 65, 110);
    Color bg = new Color(242, 245, 250);

    static Connection con;

    JPanel main;
    JLabel studentCount, companyCount, jobCount, appCount;

    Dashboard() {

        setTitle("RPMS - Placement Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel(new GridLayout(12, 1, 5, 5));
        sidebar.setBackground(blue);
        sidebar.setPreferredSize(new Dimension(210, 650));

        JLabel logo = new JLabel("RPMS", JLabel.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setForeground(Color.WHITE);
        sidebar.add(logo);

        String[] menu = {
            "Dashboard", "Add Student", "View Students",
            "Search Student", "Add Company", "View Companies",
            "Add Job", "View Jobs", "Apply for Job",
            "Applications", "Reports", "Exit"
        };

        for (String item : menu) {
            JButton btn = new JButton(item);
            btn.setBackground(blue);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 13));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.addActionListener(e -> action(item));
            sidebar.add(btn);
        }

        main = new JPanel(new BorderLayout());
        main.setBackground(bg);
        main.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        add(sidebar, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);

        dashboard();
    }

    void action(String item) {
        switch (item) {
            case "Dashboard": dashboard(); break;
            case "Add Student": addStudent(); break;
            case "View Students": viewStudents(); break;
            case "Search Student": searchStudent(); break;
            case "Add Company": addCompany(); break;
            case "View Companies": viewCompanies(); break;
            case "Add Job": addJob(); break;
            case "View Jobs": viewJobs(); break;
            case "Apply for Job": applyJob(); break;
            case "Applications": viewApplications(); break;
            case "Reports": reports(); break;
            case "Exit": System.exit(0);
        }
    }

    void dashboard() {

        main.removeAll();
        main.setLayout(new BorderLayout(20, 20));

        JLabel title = new JLabel("Welcome to RPMS Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(blue);
        main.add(title, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 2, 20, 20));
        cards.setOpaque(false);

        studentCount = new JLabel("0");
        companyCount = new JLabel("0");
        jobCount = new JLabel("0");
        appCount = new JLabel("0");

        addCard(cards, "Total Students", studentCount);
        addCard(cards, "Total Companies", companyCount);
        addCard(cards, "Available Jobs", jobCount);
        addCard(cards, "Applications", appCount);

        main.add(cards, BorderLayout.CENTER);
        main.add(new JLabel("RPMS | Admin Panel"), BorderLayout.SOUTH);

        refresh();
        main.revalidate();
        main.repaint();
    }

    void addCard(JPanel panel, String title, JLabel count) {

        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 19));
        heading.setForeground(blue);

        count.setFont(new Font("Arial", Font.BOLD, 30));
        count.setForeground(Color.BLUE);

        card.add(heading);
        card.add(count);
        panel.add(card);
    }

    int getCount(String table) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    void refresh() {
        studentCount.setText("" + getCount("students"));
        companyCount.setText("" + getCount("companies"));
        jobCount.setText("" + getCount("jobs"));
        appCount.setText("" + getCount("applications"));
    }

    void addStudent() {

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField marks = new JTextField();

        Object[] form = {
            "Student Name:", name,
            "Email:", email,
            "Percentage:", marks
        };

        int result = JOptionPane.showConfirmDialog(
            this, form, "Add Student", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String n = name.getText().trim();
                String em = email.getText().trim();
                double m = Double.parseDouble(marks.getText());

                if (n.isEmpty() || !em.contains("@") ||
                    m < 0 || m > 100) {
                    throw new IllegalArgumentException();
                }

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students(name,email,percentage) VALUES(?,?,?)");

                ps.setString(1, n);
                ps.setString(2, em);
                ps.setDouble(3, m);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Student Added!");
                refresh();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());
            }
        }
    }

    void viewStudents() {
        showTable("Student Details",
            "SELECT id,name,email,percentage FROM students",
            new String[]{"ID", "Name", "Email", "Percentage"});
    }

    void searchStudent() {

        String email = JOptionPane.showInputDialog(this, "Enter Email:");

        if (email == null) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM students WHERE email=?");

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this,
                    "Name: " + rs.getString("name") +
                    "\nEmail: " + rs.getString("email") +
                    "\nPercentage: " + rs.getDouble("percentage"));
            } else {
                JOptionPane.showMessageDialog(this, "Student Not Found!");
            }
        } catch (SQLException e) {
            showError(e);
        }
    }

    void addCompany() {

        String name = JOptionPane.showInputDialog(this, "Company Name:");

        if (name == null || name.trim().isEmpty()) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO companies(name) VALUES(?)");

            ps.setString(1, name.trim());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Company Added!");
            refresh();

        } catch (SQLException e) {
            showError(e);
        }
    }

    void viewCompanies() {
        showTable("Companies",
            "SELECT * FROM companies",
            new String[]{"ID", "Company Name"});
    }

    void addJob() {

        String job = JOptionPane.showInputDialog(this, "Enter Job Name:");

        if (job == null || job.trim().isEmpty()) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO jobs(job_name) VALUES(?)");

            ps.setString(1, job.trim());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Job Added!");
            refresh();

        } catch (SQLException e) {
            showError(e);
        }
    }

    void viewJobs() {
        showTable("Available Jobs",
            "SELECT * FROM jobs",
            new String[]{"ID", "Job Name"});
    }

    void applyJob() {

        String student = JOptionPane.showInputDialog(
            this, "Enter Student Name:");

        if (student == null || student.trim().isEmpty()) return;

        String job = JOptionPane.showInputDialog(
            this, "Enter Job Name:");

        if (job == null || job.trim().isEmpty()) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO applications(student_name,job_name) VALUES(?,?)");

            ps.setString(1, student.trim());
            ps.setString(2, job.trim());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Application Submitted!");
            refresh();

        } catch (SQLException e) {
            showError(e);
        }
    }

    void viewApplications() {
        showTable("Applications",
            "SELECT * FROM applications",
            new String[]{"ID", "Student Name", "Job Name"});
    }

    void reports() {

        JOptionPane.showMessageDialog(this,
            "PLACEMENT REPORT\n\n" +
            "Students: " + getCount("students") +
            "\nCompanies: " + getCount("companies") +
            "\nJobs: " + getCount("jobs") +
            "\nApplications: " + getCount("applications"));
    }

    void showTable(String title, String sql, String[] columns) {

        try {
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] row = new Object[columns.length];

                for (int i = 0; i < columns.length; i++) {
                    row[i] = rs.getObject(i + 1);
                }

                model.addRow(row);
            }

            JTable table = new JTable(model);
            table.setRowHeight(28);

            JFrame frame = new JFrame(title);
            frame.add(new JScrollPane(table));
            frame.setSize(650, 400);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);

        } catch (SQLException e) {
            showError(e);
        }
    }

    void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                String password = System.getenv("DB_PASSWORD");

                if (password == null || password.isEmpty()) {
                    throw new Exception("Set DB_PASSWORD first!");
                }

                con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/rpms",
                    "root", password);

                JOptionPane.showMessageDialog(null,
                    "Database Connected Successfully!");

                String username = JOptionPane.showInputDialog(
                    null, "Admin Username:");

                if (username == null) {
                    con.close();
                    return;
                }

                JPasswordField pass = new JPasswordField();

                int result = JOptionPane.showConfirmDialog(
                    null, pass, "Admin Password",
                    JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION &&
                    username.equals("admin") &&
                    new String(pass.getPassword()).equals("1234")) {

                    new Dashboard().setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login!");
                    con.close();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Database Error: " + e.getMessage());
            }
        });
    }
}
