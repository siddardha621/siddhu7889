// package main;

import features.*;
import models.*;
import util.Utils;


public class Main {
    public static void main(String[] args) {
        UserManagement userMgmt = new UserManagement();
        CourseManagement courseMgmt = new CourseManagement();
        EnrollmentManagement enrollMgmt = new EnrollmentManagement();

        
        User admin = new User(1, "Admin", "admin@mail.com", "1234567890", "admin123", "admin");
        userMgmt.addUser(admin);

        while (true) {
            System.out.println("\n===== Student Course Management System =====");
            String email = Utils.readString("Enter Email: ");
            String password = Utils.readString("Enter Password: ");

            User loggedIn = userMgmt.login(email, password);

            if (loggedIn == null) {
                System.out.println(" Invalid login, try again.");
                continue;
            }

            if (loggedIn.getUserType().equals("admin")) {
                adminMenu(userMgmt, courseMgmt);
            } else {
                studentMenu(loggedIn, courseMgmt, enrollMgmt);
            }
        }
    }

    // --- Admin Menu ---
    private static void adminMenu(UserManagement userMgmt, CourseManagement courseMgmt) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Add Course");
            System.out.println("4. Delete Course");
            System.out.println("5. View Users");
            System.out.println("6. View Courses");
            System.out.println("7. Logout");

            int choice = Utils.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    int id = Utils.readInt("Enter User ID: ");
                    String name = Utils.readString("Enter Name: ");
                    String email = Utils.readString("Enter Email: ");
                    String phone = Utils.readString("Enter Phone: ");
                    String password = Utils.readString("Enter Password: ");
                    String type = Utils.readString("Enter Type (admin/student): ");
                    userMgmt.addUser(new User(id, name, email, phone, password, type));
                    break;
                case 2:
                    int userId = Utils.readInt("Enter User ID to delete: ");
                    userMgmt.deleteUser(userId);
                    break;
                case 3:
                    int cId = Utils.readInt("Enter Course ID: ");
                    String cName = Utils.readString("Enter Course Name: ");
                    String duration = Utils.readString("Enter Course Duration: ");
                    courseMgmt.addCourse(new CourseModel(cId, cName, duration));
                    break;
                case 4:
                    int delId = Utils.readInt("Enter Course ID to delete: ");
                    courseMgmt.deleteCourse(delId);
                    break;
                case 5:
                    System.out.println("All Users: " + userMgmt.getUsers());
                    break;
                case 6:
                    System.out.println("All Courses: " + courseMgmt.getCourses());
                    break;
                case 7:
                    return;
                default:
                    System.out.println(" Invalid choice!");
            }
        }
    }

    // --- Student Menu ---
    private static void studentMenu(User student, CourseManagement courseMgmt, EnrollmentManagement enrollMgmt) {
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View Available Courses");
            System.out.println("2. Enroll in Course");
            System.out.println("3. View My Courses");
            System.out.println("4. Logout");

            int choice = Utils.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    System.out.println("Available Courses: " + courseMgmt.getCourses());
                    break;
                case 2:
                    int courseId = Utils.readInt("Enter Course ID to enroll: ");
                    for (CourseModel c : courseMgmt.getCourses()) {
                        if (c.getCourseId() == courseId) {
                            enrollMgmt.enrollStudent(student, c);
                        }
                    }
                    break;
                case 3:
                    System.out.println("My Courses: " + student.getCourses());
                    break;
                case 4:
                    return;
                default:
                    System.out.println(" Invalid choice!");
            }
        }
    }
}
