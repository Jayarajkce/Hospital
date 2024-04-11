package hospital;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    static String url="jdbc:mysql://localhost:1009/hospital";
    static String username="root";
    static String password="8826";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try (Connection connection = DriverManager.getConnection(url,username,password)) {
            System.out.println("Hospital Management System");
            Statement statement=connection.createStatement();
            String query1="CREATE TABLE IF NOT EXISTS Patients (\r\n"
                    + "    patient_id INT PRIMARY KEY,\r\n"
                    + "    patient_name VARCHAR(100) NOT NULL,\r\n"
                    + "    date_of_birth DATE NOT NULL,\r\n"
                    + "    gender ENUM('Male', 'Female', 'Other') NOT NULL,\r\n"
                    + "    address VARCHAR(255) NOT NULL,\r\n"
                    + "    phone_number VARCHAR(10) NOT NULL\r\n"
                    + ");\r\n"
                    + "";
             
            statement.executeUpdate(query1);
            
            // Loop to insert patient details
            while (true) {
                System.out.println("Enter patient details");
                System.out.println("Patient id:");
                int patient_id = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                
                System.out.print("Patient Name: ");
                String patientName = scanner.nextLine();

                System.out.print("Date of Birth (yyyy-MM-dd): ");
                String dateOfBirth = scanner.next();

                System.out.print("Gender (Male/Female/Other): ");
                String gender = scanner.next();

                System.out.print("Address: ");
                String address = scanner.next();

                System.out.print("Phone Number: ");
                String phoneNumber = scanner.next();
                System.out.println("Disease:");
                String Disease=scanner.next();
                // SQL query to insert user input into Patients table
                String insertPatientQuery = "INSERT INTO Patients (patient_id, patient_name, date_of_birth, gender, address, phone_number,Disease) VALUES (?, ?, ?, ?, ?, ?,?)";
                PreparedStatement statement1 = connection.prepareStatement(insertPatientQuery);
                statement1.setInt(1, patient_id);
                statement1.setString(2, patientName);
                statement1.setString(3, dateOfBirth);
                statement1.setString(4, gender);
                statement1.setString(5, address);
                statement1.setString(6, phoneNumber);
                statement1.setString(7, Disease);
                // Execute the insert statement
                int rowsInserted = statement1.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Patient details inserted successfully."+rowsInserted);
                    System.out.println("Patient Details Collected");
                 
                } else {
                    System.out.println("Failed to insert patient details.");
                }
                
                // Ask the user if they want to insert another patient
                System.out.println("Do you want to insert another patient? (yes/no)");
                String choice = scanner.next();
                if (!choice.equalsIgnoreCase("yes")) {
                    break; // Exit the loop if the user does not want to insert another patient
                }
            }
            System.out.println("Enter patient's disease:");
            String disease = scanner.next();

            switch (disease) {
                case "COVID-19":
                    System.out.println("Patient has COVID-19. Follow quarantine protocols.");
                    System.out.println("Thanks For Visiting!!");
                    break;
                case "Ulcer":
                    System.out.println("Patient has Ulcer. Drink more water.");
                    System.out.println("Thanks For Visiting!!");
                    break;
                case "Diabetes":
                    System.out.println("Patient has Diabetes. Monitor blood sugar levels regularly.");
                    System.out.println("Thanks For Visiting!!");
                    break;
                // Add more cases as needed
                default:
                    System.out.println("Unknown disease. Consult a healthcare professional.");
                    System.out.println("Thanks For Visiting!!");
                    
            }

   } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
