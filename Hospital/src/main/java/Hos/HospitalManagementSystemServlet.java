package Hos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HospitalManagementSystem")
public class HospitalManagementSystemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    static final String DB_URL = "jdbc:mysql://localhost:1009/hospital";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "8826";

    // Method to display all patient records from the database
    private void displayPatientRecords(Connection connection, PrintWriter out) throws SQLException {
        String selectQuery = "SELECT * FROM Patients";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectQuery);

        out.println("<h3 style='color: #4CAF50; text-align: center;'>Patient Records</h3>");
        out.println("<table style='width: 100%; border-collapse: collapse; margin-top: 20px;'>");
        out.println("<thead style='background-color: #4CAF50; color: white;'>");
        out.println("<tr><th>Patient ID</th><th>Patient Name</th><th>Disease</th><th>Symptoms</th><th>Treatment</th><th>Follow-up Date</th><th>Follow-up Time</th><th>Delete</th></tr>");
        out.println("</thead>");
        out.println("<tbody>");

        while (resultSet.next()) {
            int patientId = resultSet.getInt("patient_id");
            String patientName = resultSet.getString("patient_name");
            String disease = resultSet.getString("disease");
            String symptoms = resultSet.getString("symptoms");
            String treatmentPlan = resultSet.getString("treatment_plan");
            java.sql.Date followUpDate = resultSet.getDate("follow_up_date");
            String followUpTime = resultSet.getString("follow_up_time");

            out.println("<tr>");
            out.println("<td>" + patientId + "</td>");
            out.println("<td>" + patientName + "</td>");
            out.println("<td>" + disease + "</td>");
            out.println("<td>" + symptoms + "</td>");
            out.println("<td>" + treatmentPlan + "</td>");
            out.println("<td>" + followUpDate + "</td>");
            out.println("<td>" + followUpTime + "</td>");
            out.println("<td><form method='POST' action='HospitalManagementSystem'>" +
                        "<input type='hidden' name='delete_patient_id' value='" + patientId + "'>" +
                        "<button type='submit'>Delete</button>" +
                        "</form></td>");
            out.println("</tr>");
        }
        out.println("</table>");
    }

    // Method to delete a patient from the database
    private void deletePatient(int patientId, Connection connection, PrintWriter out) throws SQLException {
        String deleteQuery = "DELETE FROM Patients WHERE patient_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, patientId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
            	 out.println("<html><head><style>");
                 out.println("body { font-family: Arial, sans-serif; color: #333; background-color: #f9f9f9; }");
                 out.println("h3 { color: #4CAF50; font-size: 20px; }");
                 out.println(".details { font-size: 16px; color: #555; }");
                 out.println(".success { background-color: #d4edda; padding: 15px; border-radius: 5px; border: 1px solid #c3e6cb; }");
                 out.println(".warning { background-color: #fff3cd; padding: 15px; border-radius: 5px; border: 1px solid #ffeeba; }");
                 out.println(".error { background-color: #f8d7da; padding: 15px; border-radius: 5px; border: 1px solid #f5c6cb; }");
                 out.println("</style></head><body>");

                 out.println("<div class='success'>");
                 out.println("<h3>Patient with ID " + patientId + " has been deleted from the system successfully!</h3>");
                 out.println("<p class='details'>Please ensure all necessary patient records are backed up before deletion. If this was done in error, contact system admin immediately.</p>");
                 out.println("</div>");

                 out.println("<div class='success'>");
                 out.println("<h3>Recovery Update: Patient has fully recovered!</h3>");
                 out.println("<p class='details'>We are pleased to inform you that the patient is now in good health. Please take the following steps for post-recovery care:</p>");
                 out.println("<ul class='details'>");
                 out.println("<li>Continue taking prescribed medication as per the doctor's instructions.</li>");
                 out.println("<li>Follow-up visit scheduled for: <strong>Next Tuesday at 10:00 AM</strong>.</li>");
                 out.println("<li>Maintain a balanced diet and avoid stress during the recovery period.</li>");
                 out.println("</ul>");
                 out.println("</div>");

                 out.println("<div class='warning'>");
                 out.println("<h3>Important Reminder:</h3>");
                 out.println("<p class='details'>Always consult your physician if you experience any discomfort or unusual symptoms during your recovery period. Your health is our priority!</p>");
                 out.println("</div>");

                 out.println("<div class='error'>");
                 out.println("<h3>Error: Unable to process the deletion at this time.</h3>");
                 out.println("<p class='details'>There was an issue while deleting the patient record. Please try again later or contact technical support for assistance.</p>");
                 out.println("</div>");

                 out.println("</body></html>");
            } else {
            	out.println("<style>");
            	out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; }");
            	out.println("h3 { color: #d9534f; }"); // Bootstrap danger color
            	out.println("h2 { color: #5bc0de; }"); // Bootstrap info color
            	out.println(".message { padding: 15px; border: 1px solid #d9534f; border-radius: 5px; background-color: #f9d6d5; }");
            	out.println(".button { display: inline-block; padding: 10px 15px; margin-top: 20px; background-color: #5bc0de; color: white; text-decoration: none; border-radius: 5px; }");
            	out.println(".button:hover { background-color: #31b0d5; }"); // Darker shade on hover
            	out.println("</style>");

            	out.println("<div class='message'>");
            	out.println("<h3>Patient ID not found. No patient was deleted.</h3>");
            	out.println("<h2>Please enter a valid Patient ID!</h2>");
            	out.println("<p>If you believe this is an error, please contact support for assistance.</p>");
            	out.println("<p>Alternatively, you can go back to the old page to try again.</p>");
            	out.println("<a href='hospital.html' class='button'>Try Again</a>");
            	out.println("</div>");
                out.println(" <a href=\"HospitalManagementSystem?viewAll=true\">\r\n"
                		+ "        <button>View All Patients</button>\r\n"
                		+ "    </a>");
            }
        }
    }

    // Process POST request for registering or deleting patients
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            out.println("<h3>Error loading database driver!</h3>");
            e.printStackTrace(out);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Handle delete patient request
            if (request.getParameter("delete_patient_id") != null) {
                int patientIdToDelete = Integer.parseInt(request.getParameter("delete_patient_id"));
                deletePatient(patientIdToDelete, connection, out); // Delete the patient
            }

            // Register new patient
            int patientId = Integer.parseInt(request.getParameter("patient_id"));
            String patientName = request.getParameter("patient_name");
            String dateOfBirth = request.getParameter("date_of_birth");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String phoneNumber = request.getParameter("phone_number");
            String disease = request.getParameter("disease");
            String symptoms = request.getParameter("symptoms");
            String treatmentPlan = request.getParameter("treatment_plan");
            String followUpDate = request.getParameter("follow_up_date");
            String followUpTime = request.getParameter("follow_up_time");

            // Insert the patient details into the database
            String insertQuery = "INSERT INTO Patients (patient_id, patient_name, date_of_birth, gender, address, phone_number, disease, symptoms, treatment_plan, follow_up_date, follow_up_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, patientId);
            preparedStatement.setString(2, patientName);
            preparedStatement.setString(3, dateOfBirth);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, phoneNumber);
            preparedStatement.setString(7, disease);
            preparedStatement.setString(8, symptoms);
            preparedStatement.setString(9, treatmentPlan);
            preparedStatement.setString(10, followUpDate);
            preparedStatement.setString(11, followUpTime);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
            	out.println("<div style='background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; padding: 15px; margin-top: 20px; border-radius: 5px; text-align: center;'>");
            	out.println("<h3 style='margin: 0; font-size: 22px;'>Patient details inserted successfully!</h3>");
            	out.println("</div>");
            	out.println("<html><head><style>");
            	out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; padding: 20px; }");
            	out.println("h3 { color: #4CAF50; font-size: 22px; }");
            	out.println(".message-container { background-color: #d4edda; padding: 20px; border-radius: 5px; font-size: 18px; border: 1px solid #c3e6cb; margin-bottom: 20px; }");
            	out.println(".patient-info { background-color: #ffffff; padding: 15px; border-radius: 8px; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); }");
            	out.println(".patient-info h4 { color: #333; font-size: 20px; }");
            	out.println(".patient-info p { font-size: 16px; color: #555; }");
            	out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            	out.println("th, td { padding: 12px; text-align: left; border: 1px solid #ddd; }");
            	out.println(".btn { background-color: #4CAF50; color: white; padding: 10px 15px; text-align: center; text-decoration: none; border-radius: 5px; cursor: pointer; }");
            	out.println(".btn:hover { background-color: #45a049; }");
            	out.println("</style></head><body>");

            	// Success Message
            	out.println("<div class='message-container'>");
            	out.println("<h3>Patient Details Inserted Successfully!</h3>");
            	out.println("<p>Dear " + patientName + ",</p>");
            	out.println("<p>Your details have been successfully inserted into our system. Below is a summary of your health information:</p>");
            	out.println("</div>");

            	// Patient Summary Information
            	out.println("<div class='patient-info'>");
            	out.println("<h4>Patient Information:</h4>");
            	out.println("<p><strong>Patient Name:</strong> " + patientName + "</p>");
            	out.println("<p><strong>Patient ID:</strong> " + patientId + "</p>");
            	out.println("<p><strong>Disease:</strong> " + disease + "</p>");
            	out.println("<p><strong>Symptoms:</strong> " + symptoms + "</p>");
            	out.println("<p><strong>Treatment Plan:</strong> " + treatmentPlan + "</p>");
            	out.println("<p><strong>Follow-up Date:</strong> " + followUpDate + "</p>");
            	out.println("<p><strong>Follow-up Time:</strong> " + followUpTime + "</p>");
            	out.println("</div>");

            	// Assigned Doctors Information
            	out.println("<div class='message-container'>");
            	out.println("<h4>Assigned Doctors:</h4>");
            	out.println("<p>Below are the doctors assigned to your case along with their available times:</p>");
            	out.println("<ul>");
            	out.println("<li><strong>Dr.J.Jayaraj:</strong> Monday, Wednesday, Friday - 9 AM to 12 PM</li>");
            	out.println("<li><strong>Dr.J.Vidhya :</strong> Tuesday, Thursday - 1 PM to 4 PM</li>");
            	
            	out.println("</ul>");
            	out.println("</div>");

            	// Additional Actions (Buttons)
            	out.println("<div class='message-container'>");
            	out.println("<h4>Next Steps:</h4>");
            	out.println("<p>Would you like to perform any of the following actions?</p>");
            	out.println("<a href='hospital.html' class='btn'>Add Another Patient</a>");
            	out.println("</div>");

            	// Display patient records (if required)

            	out.println("</body></html>");
            }else {
                // Error Message if insert fails
            	out.println("<html><head><style>");
            	out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; padding: 20px; }");
            	out.println("h3 { color: #f8d7da; font-size: 22px; }");
            	out.println(".error { background-color: #f8d7da; padding: 20px; border-radius: 5px; font-size: 18px; border: 1px solid #f5c6cb; margin-bottom: 20px; }");
            	out.println(".error p { font-size: 16px; color: #555; }");
            	out.println(".btn { background-color: #dc3545; color: white; padding: 10px 15px; text-align: center; text-decoration: none; border-radius: 5px; cursor: pointer; }");
            	out.println(".btn:hover { background-color: #c82333; }");
            	out.println("</style></head><body>");

            	out.println("<div class='error'>");
            	out.println("<h3>Error: Failed to Insert Patient Details</h3>");
            	out.println("<p>There was an issue while adding the patient details. Please try again later.</p>");
            	out.println("<p>If the problem persists, contact support for assistance.</p>");
            	out.println("<p>Would you like to:</p>");
            	out.println("<ul>");
            	out.println("<li><a href='hospital.html' class='btn'>Go Back to Patient Entry</a></li>");
            	out.println("<li><a href='support.html' class='btn'>Contact Support</a></li>");
            	out.println("</ul>");
            	out.println("</div>");

            	out.println("</body></html>");
            } 

            // Display all patients after insertion
            displayPatientRecords(connection, out);
        } catch (SQLException e) {
        	out.println("<html><head><style>");
        	out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; padding: 20px; }");
        	out.println("h3 { color: #f8d7da; font-size: 22px; }");
        	out.println(".error { background-color: #f8d7da; padding: 20px; border-radius: 5px; font-size: 18px; border: 1px solid #f5c6cb; margin-bottom: 20px; }");
        	out.println(".error p { font-size: 16px; color: #555; }");
        	out.println(".btn { background-color: #dc3545; color: white; padding: 10px 15px; text-align: center; text-decoration: none; border-radius: 5px; cursor: pointer; }");
        	out.println(".btn:hover { background-color: #c82333; }");
        	out.println("</style></head><body>");

        	out.println("<div class='error'>");
        	out.println("<h3>Error: Failed to Insert Patient Details</h3>");
        	out.println("<p>There was an issue while adding the patient details. Please try again later.</p>");
        	out.println("<p>If the problem persists, contact support for assistance.</p>");
        	out.println("<p>Would you like to:</p>");
        	out.println("<ul>");
        	out.println("<li><a href='hospital.html' class='btn'>Go Back to Patient Entry</a></li>");
        	out.println("<li><a href='support.html' class='btn'>Contact Support</a></li>");
        	out.println("</ul>");
        	out.println("</div>");

        	out.println("</body></html>");
            e.printStackTrace(out);
        }
    }

    // Handle GET request to view all patients
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Display patient records if 'viewAll' parameter is true
            if ("true".equals(request.getParameter("viewAll"))) {
                displayPatientRecords(connection, out);
            
            } else {
                // Redirect to the patient registration form if not 'viewAll'
                response.sendRedirect("hospital.html");
                
            }
        } catch (SQLException e) {
            out.println("<h3>Error interacting with the database!</h3>");
           
            e.printStackTrace(out);
        }
    }
}
