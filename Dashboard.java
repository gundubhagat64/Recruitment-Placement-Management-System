
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Dashboard extends JFrame {

    Color blue = new Color(30, 65, 110);
    Color bg = new Color(242, 245, 250);

    ArrayList<String[]> students = new ArrayList<>();
    ArrayList<String> companies = new ArrayList<>();
    ArrayList<String> jobs = new ArrayList<>();
    ArrayList<String[]> applications = new ArrayList<>();

    JPanel main;
    JLabel studentCount, companyCount, jobCount, appCount;

    Dashboard() {

        setTitle("RPMS - Recruitment & Placement System");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel(new GridLayout(12, 1, 5, 5));
        sidebar.setBackground(blue);
        sidebar.setPreferredSize(new Dimension(210, 650));

        JLabel logo = new JLabel("   RPMS", JLabel.CENTER);
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

        studentCount = new JLabel();
        companyCount = new JLabel();
        jobCount = new JLabel();
        appCount = new JLabel();

        addCard(cards, "Total Students", studentCount, students.size());
        addCard(cards, "Total Companies", companyCount, companies.size());
        addCard(cards, "Available Jobs", jobCount, jobs.size());
        addCard(cards, "Applications", appCount, applications.size());

        main.add(cards, BorderLayout.CENTER);

        JLabel footer = new JLabel("RPMS | Admin Panel");
        main.add(footer, BorderLayout.SOUTH);

        refresh();
    }

    void addCard(JPanel panel, String title,
                  JLabel count, int value) {

        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 19));
        heading.setForeground(blue);

        count.setText(String.valueOf(value));
        count.setFont(new Font("Arial", Font.BOLD, 30));
        count.setForeground(new Color(40, 120, 190));

        card.add(heading);
        card.add(count);
        panel.add(card);
    }

    void refresh() {
        if (studentCount != null) studentCount.setText("" + students.size());
        if (companyCount != null) companyCount.setText("" + companies.size());
        if (jobCount != null) jobCount.setText("" + jobs.size());
        if (appCount != null) appCount.setText("" + applications.size());
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
            this, form, "Add Student",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                String n = name.getText().trim();
                String em = email.getText().trim();
                double m = Double.parseDouble(marks.getText());

                if (n.isEmpty() || em.isEmpty() ||
                    !em.contains("@") || m < 0 || m > 100) {
                    throw new IllegalArgumentException();
                }

                students.add(new String[]{n, em, "" + m});

                JOptionPane.showMessageDialog(this,
                    "Student Added Successfully!");

                refresh();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Enter Valid Student Details!");
            }
        }
    }

    void viewStudents() {

        String[] columns = {"Name", "Email", "Percentage"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (String[] s : students) {
            model.addRow(s);
        }

        showTable("Student Details", model);
    }

    void searchStudent() {

        String email = JOptionPane.showInputDialog(
            this, "Enter Student Email:"
        );

        if (email == null) return;

        for (String[] s : students) {
            if (s[1].equalsIgnoreCase(email.trim())) {
                JOptionPane.showMessageDialog(this,
                    "Name: " + s[0] +
                    "\nEmail: " + s[1] +
                    "\nPercentage: " + s[2]);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Student Not Found!");
    }

    void addCompany() {

        String name = JOptionPane.showInputDialog(
            this, "Enter Company Name:"
        );

        if (name != null && !name.trim().isEmpty()) {
            companies.add(name.trim());
            JOptionPane.showMessageDialog(this, "Company Added!");
            refresh();
        }
    }

    void viewCompanies() {

        DefaultTableModel model =
            new DefaultTableModel(new String[]{"Company Name"}, 0);

        for (String c : companies) {
            model.addRow(new String[]{c});
        }

        showTable("Company Details", model);
    }

    void addJob() {

        if (companies.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "First Add a Company!");
            return;
        }

        String job = JOptionPane.showInputDialog(
            this, "Enter Job Name:"
        );

        if (job != null && !job.trim().isEmpty()) {
            jobs.add(job.trim());
            JOptionPane.showMessageDialog(this, "Job Added!");
            refresh();
        }
    }

    void viewJobs() {

        DefaultTableModel model =
            new DefaultTableModel(new String[]{"Job Name"}, 0);

        for (String j : jobs) {
            model.addRow(new String[]{j});
        }

        showTable("Available Jobs", model);
    }

    void applyJob() {

        if (students.isEmpty() || jobs.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Add Student and Job First!");
            return;
        }

        String[] studentNames = new String[students.size()];
        for (int i = 0; i < students.size(); i++) {
            studentNames[i] = students.get(i)[0];
        }

        String student = (String) JOptionPane.showInputDialog(
            this, "Select Student:", "Apply for Job",
            JOptionPane.QUESTION_MESSAGE, null,
            studentNames, studentNames[0]
        );

        if (student == null) return;

        String[] jobNames = jobs.toArray(new String[0]);

        String job = (String) JOptionPane.showInputDialog(
            this, "Select Job:", "Apply for Job",
            JOptionPane.QUESTION_MESSAGE, null,
            jobNames, jobNames[0]
        );

        if (job == null) return;

        applications.add(new String[]{student, job, "Applied"});

        JOptionPane.showMessageDialog(this,
            "Application Submitted Successfully!");

        refresh();
    }

    void viewApplications() {

        DefaultTableModel model =
            new DefaultTableModel(
                new String[]{"Student", "Job", "Status"}, 0
            );

        for (String[] a : applications) {
            model.addRow(a);
        }

        showTable("Applications", model);
    }

    void reports() {

        JOptionPane.showMessageDialog(this,
            "PLACEMENT REPORT\n\n" +
            "Total Students: " + students.size() +
            "\nTotal Companies: " + companies.size() +
            "\nTotal Jobs: " + jobs.size() +
            "\nTotal Applications: " + applications.size()
        );
    }

    void showTable(String title, DefaultTableModel model) {

        JTable table = new JTable(model);
        table.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(table);

        JFrame frame = new JFrame(title);
        frame.add(scroll);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            String username = JOptionPane.showInputDialog(
                null, "Enter Admin Username:"
            );

            if (username == null) return;

            JPasswordField password = new JPasswordField();

            int result = JOptionPane.showConfirmDialog(
                null, password, "Admin Password",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION &&
                username.equals("admin") &&
                new String(password.getPassword()).equals("1234")) {

                new Dashboard().setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login!");
            }
        });
    }
}
