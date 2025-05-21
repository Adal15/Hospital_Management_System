package com.HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	
	private Scanner scanner;
	
	

	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public void addPatient() {
		System.out.println("Enter Patient Name: ");
		String name=scanner.next();
		System.out.println("Enter Patient Age: ");
		int age=scanner.nextInt();
		System.out.println("Enter Patirnt Gender: ");
		String gender=scanner.next();
		
		PreparedStatement pr;
		try {
			String query="INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
			pr = connection.prepareStatement(query);
			pr.setString(1, name);
			pr.setInt(2, age);
			pr.setString(3, gender);
			int affectedRows=pr.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Patient Added Successfully");
			}
			else {
				System.out.println("Failed to add Patient");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void viewPatients() {
		String query="select * from patients";
		try {
			PreparedStatement pr=connection.prepareStatement(query);
			ResultSet resultSet=pr.executeQuery();
			System.out.println("patients");
			System.out.println("+------------+--------------------+----------+----------+");
			System.out.println("| Patient ID | Name               | Age      | Gender   |");
			System.out.println("+------------+--------------------+----------+----------+");
			
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				int age=resultSet.getInt("age");
				String gender=resultSet.getString("gender");
				System.out.printf("| %-12s | %-20s | %-10s | %-10s |\n",id ,name, age, gender);
				System.out.println("+------------+--------------------+----------+----------+");
				
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean getPatientById(int id) {
		String query="SELECT * FROM patients WHERE id = ?";
		try {
			PreparedStatement pr=connection.prepareStatement(query);
			pr.setInt(1, id);
			ResultSet resultSet=pr.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
