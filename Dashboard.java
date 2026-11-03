
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dashboard extends JFrame {

    Color blue = new Color(30, 65, 110);

    static ArrayList<String> students = new ArrayList<>();

    Dashboard() {

        setTitle("RPMS - Placement Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel side = new JPanel(new GridLayout(8, 1, 5, 5));
        side.setBackground(blue);
        side.setPreferredSize(new Dimension(210, 600));

        JLabel logo = new JLabel("RPMS", JLabel.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setForeground(Color.WHITE);
        side.add(logo);

        String[] menu = {
            "Dashboard", "Add Student", "View Students",
            "Companies", "Jobs", "Applications", "Exit"
        };

        for (String m : menu) {

            JButton b = new JButton(m);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            b.setForeground(Color.WHITE);
            b.setBackground(blue);
            b.setFocusPainted(false);

            b.addActionListener(e -> {

                if (m.equals("Add Student")) {
                    addStudent();
                }
                else if (m.equals("View Students")) {
                    viewStudents();
                }
                else if (m.equals("Exit")) {
                    System.exit(0);
                }
                else {
                    JOptionPane.showMessageDialog(this, m);
                }
            });

            side.add(b);
        }

        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(new Color(242, 245, 250));
        main.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Welcome to RPMS Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(blue);

        main.add(title, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 2, 20, 20));
        cards.setOpaque(false);

        String[] names = {
            "Students", "Companies", "Available Jobs", "Applications"
        };

        for (String name : names) {
            JPanel card = new JPanel();
            card.setBackground(Color.WHITE);
            card.add(new JLabel(name));
            cards.add(card);
        }

        main.add(cards, BorderLayout.CENTER);

        add(side, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);

        setVisible(true);
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

            if (name.getText().trim().isEmpty() ||
                email.getText().trim().isEmpty() ||
                marks.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Fill All Details!");
                return;
            }

            try {
                double percentage = Double.parseDouble(marks.getText());

                if (percentage < 0 || percentage > 100) {
                    JOptionPane.showMessageDialog(this,
                        "Percentage must be between 0 and 100!");
                    return;
                }

                students.add(
                    name.getText() + " | " +
                    email.getText() + " | " + percentage + "%"
                );

                JOptionPane.showMessageDialog(
                    this, "Student Added Successfully!"
                );

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Enter Valid Percentage!");
            }
        }
    }

    void viewStudents() {

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Students Found!");
        } else {
            String data = String.join("\n", students);

            JOptionPane.showMessageDialog(
                this, data, "Student List",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
