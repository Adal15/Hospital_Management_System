package com.HospitalManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalDriver {
	private static final String url ="jdbc:mysql://localhost:3306/hospital";
	private static final String username ="root";
	private static final String password ="admin";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner sc=new Scanner(System.in);
		try {
			Connection connection=DriverManager.getConnection(url, username, password);
			Patient patient=new  Patient(connection, sc);
			Doctor doctor=new Doctor(connection);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patients"); 			
				System.out.println("2. View Patients"); 
				System.out.println("3. View Doctors"); 
				System.out.println("4. Book Appointment"); 
				System.out.println("5. Exit"); 
				System.out.println("Enter your Choice: ");
				int choice=sc.nextInt();
				
				switch (choice) {
				case 1: {
					//Add Patient
					patient.addPatient();
					System.out.println("");
					break;
				}
				case 2:{
					//View Patients
					patient.viewPatients();
					System.out.println();
					break;
				}
				case 3:{
//					View Doctors
					doctor.viewDoctors();
					System.out.println();
					break;
				}
				case 4:{
//					Book Appointment
					bookAppointment(patient, doctor, connection, sc);
					System.out.println();
					break;
				}
				case 5:{
					System.out.println("Thanks for visiting");
					return;
					
				}
				default:
					System.out.println("Enter valid choice!!!");
					break;
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.println("Enter Patient Id: ");
		int patientId=scanner.nextInt();
		System.out.println("Enter Doctor Id: ");
		int docotorId =scanner.nextInt();
		System.out.println("Enter Appointment date (YYYY-MM-DD)");
		String appointmentDate=scanner.next();
		if(patient.getPatientById(patientId) && doctor.getDOctorById(docotorId)) {
			if(checkDoctorAvailability(docotorId, appointmentDate, connection)) {
				String appointmentQuery="INSERT INTO appointments(patient_id, doctor_id,appointment_date) VALUES(?, ?, ?)";
				try {
					PreparedStatement ps=connection.prepareStatement(appointmentQuery);
					ps.setInt(1, patientId);
					ps.setInt(2, docotorId);
					ps.setString(3, appointmentDate);
					int rowaAffected=ps.executeUpdate();
					if(rowaAffected>0) {
						System.out.println("Appointment Booked");
					}
					else {
						System.out.println("Failed to Book Appointment");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {
			System.out.println("Either doctor or Patient doesn't exist!!");
		}
		

	}
	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
		String query="SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, query);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count=resultSet.getInt(1);
				if(count==0) {
					return true;
				}
				else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
