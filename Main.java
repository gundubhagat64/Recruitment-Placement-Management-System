
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<String[]> students = new ArrayList<>();
    static ArrayList<String> companies = new ArrayList<>();
    static ArrayList<String> jobs = new ArrayList<>();
    static ArrayList<String> applications = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println(" RECRUITMENT & PLACEMENT SYSTEM");
        System.out.println("==================================");

        System.out.print("Username: ");
        String u = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();

        if (!u.equals("admin") || !p.equals("1234")) {
            System.out.println("Invalid Login!");
            return;
        }

        System.out.println("Login Successful!");

        int ch;

        do {
            System.out.println("\n========== MAIN MENU ==========");
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

                case 1:
                    System.out.print("Student Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Percentage: ");
                    String marks = sc.nextLine();

                    students.add(new String[]{name, email, marks});
                    System.out.println("Student Added Successfully!");
                    break;

                case 2:
                    if (students.isEmpty()) {
                        System.out.println("No Students Found!");
                    } else {
                        for (String[] s : students) {
                            System.out.println(Arrays.toString(s));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Student Email: ");
                    String search = sc.nextLine();

                    boolean found = false;

                    for (String[] s : students) {
                        if (s[1].equalsIgnoreCase(search)) {
                            System.out.println(Arrays.toString(s));
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("Student Not Found!");
                    }
                    break;

                case 4:
                    System.out.print("Company Name: ");
                    companies.add(sc.nextLine());
                    System.out.println("Company Added Successfully!");
                    break;

                case 5:
                    if (companies.isEmpty()) {
                        System.out.println("No Companies Found!");
                    } else {
                        for (String c : companies) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 6:
                    System.out.print("Enter Job Name: ");
                    jobs.add(sc.nextLine());
                    System.out.println("Job Added Successfully!");
                    break;

                case 7:
                    if (jobs.isEmpty()) {
                        System.out.println("No Jobs Found!");
                    } else {
                        for (String j : jobs) {
                            System.out.println(j);
                        }
                    }
                    break;

                case 8:
                    System.out.print("Enter Student Name: ");
                    String student = sc.nextLine();

                    System.out.print("Enter Job Name: ");
                    String job = sc.nextLine();

                    applications.add(student + " applied for " + job);
                    System.out.println("Application Submitted Successfully!");
                    break;

                case 9:
                    if (applications.isEmpty()) {
                        System.out.println("No Applications Found!");
                    } else {
                        for (String a : applications) {
                            System.out.println(a);
                        }
                    }
                    break;

                case 10:
                    System.out.println("Thank You for Using RPMS!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (ch != 10);

        sc.close();
    }
}
