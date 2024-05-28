package adminISI;

import java.sql.SQLException;
import java.util.Set;

import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) throws SQLException {

        DbQuery dbQuery = new DbQuery("root","0000","jdbc:mysql://localhost:3306/emploidutemps_db") ;
        dbQuery.createTablesIfNeeded();
        LoginFrame frame = new LoginFrame();
        frame.setVisible(true);

// sign up exemple
        // Sign up a new user
/*        String name = "John Doe";
        String email = "john@example.com";
        String password = "password123";
        
        boolean signedUp = dbQuery.signUpAcc(name, email, password);
        if (signedUp) {
            System.out.println("User signed up successfully.");
        } else {
            System.out.println("Failed to sign up. Email already exists.");
        }


        boolean isAuthenticated = dbQuery.authenticateUser("john@example.com", "password123");
        if (isAuthenticated) {
            System.out.println("User authenticated successfully.");
        } else {
            System.out.println("Authentication failed. Invalid email or password.");
        }*/

/*

        try {
            // Insert  data into tb_enseignant and tb_cours tables
            dbQuery.insertInstructor("001", "Ahmed Ben Ali", "+216 23 456 789");
            dbQuery.insertInstructor("002", "Fatma Khedher", "+216 98 765 432");
            dbQuery.insertInstructor("003", "Mohamed Hmida", "+216 71 234 567");
            dbQuery.insertInstructor("004", "Amina Ben Salah", "+216 52 987 654");
            dbQuery.insertInstructor("005", "Youssef Ben Amor", "+216 21 345 678");



            dbQuery.insertCourseSession("6eme", "Mathematics", 1, "Monday", "2eme H", "001");
            dbQuery.insertCourseSession("6eme", "Science", 2, "Tuesday", "3eme H", "002");
            dbQuery.insertCourseSession("6eme", "History", 3, "Wednesday", "4eme H", "003");
            dbQuery.insertCourseSession("6eme", "Geography", 4, "Thursday", "5eme H", "001");
            dbQuery.insertCourseSession("6eme", "French", 5, "Friday", "1ere et 2eme H", "004");
            dbQuery.insertCourseSession("6eme", "English", 1, "Monday", "3eme et 4eme H", "005");

            dbQuery.insertCourseSession("IDL", "Programming", 1, "Monday", "2eme H", "002");
            dbQuery.insertCourseSession("IDL", "Database Management", 2, "Tuesday", "3eme H", "003");
            dbQuery.insertCourseSession("IDL", "Data Structures", 3, "Wednesday", "5eme H", "001");

            dbQuery.insertCourseSession("IDISC", "Digital Marketing", 1, "Monday", "5eme H", "004");
            dbQuery.insertCourseSession("IDISC", "Social Media Management", 2, "Tuesday", "6eme H", "005");
            dbQuery.insertCourseSession("IDISC", "Content Creation", 3, "Wednesday", "2eme H", "002");

            dbQuery.insertCourseSession("ISEOC", "Embedded Systems", 1, "Monday", "2eme H", "003");
            dbQuery.insertCourseSession("ISEOC", "Internet of Things (IoT)", 2, "Tuesday", "5eme H", "004");
            dbQuery.insertCourseSession("ISEOC", "Cyber-Physical Systems", 3, "Wednesday", "4eme H", "005");






            // Search for an instructor by name
            String instructorInfo = dbQuery.searchInstructorByName("Ahmed Ben Ali");
            System.out.println("Instructor Information:");
            System.out.println(instructorInfo);



            // Update an instructor's information
             
            System.out.println("Instructor information updated.");

            // Delete an instructor
            dbQuery.deleteInstructor("004");
            System.out.println("Instructor deleted.");

            // Insert a new course session
            dbQuery.insertCourseSession("6eme", "Mathematics", 1, "Monday", "10:00 AM", "001");
            System.out.println("New course session inserted.");

            // Search for course sessions for a specific class and subject
            String courseSessions = dbQuery.searchCourseSessions("6eme", "Mathematics");
            System.out.println("Course sessions for 6eme - Mathematics:");
            System.out.println(courseSessions);

            // Delete a course session by id
            dbQuery.deleteCourseSession(1);
            System.out.println("Course session deleted.");

            // Fetch timetable for a specific class
            String timetable = dbQuery.fetchTimetableForClass("6eme");
            System.out.println("Timetable for class 6eme:");
            System.out.println(timetable);


            Set<String> availableClass = dbQuery.getClassesNames() ;
            System.out.println("available classes");
            System.out.println(availableClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

}