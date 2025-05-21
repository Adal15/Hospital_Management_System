package com.HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
private Connection connection;
	

	public Doctor(Connection connection) {
		this.connection = connection;
	}
	
 	
	
	
	public void viewDoctors() {
		String query="select * from doctors";
		try {
			PreparedStatement pr=connection.prepareStatement(query);
			ResultSet resultSet=pr.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+------------+-------------------+------------------+");
			System.out.println("| Dcotor ID | Name               | Specialization   |");
			System.out.println("+------------+-------------------+------------------+");
			
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				String specialization=resultSet.getString("specialization");
				System.out.printf("| %-12s | %-20s | %-16s |\n",id ,name, specialization);
				System.out.println("+------------+-------------------+------------------+");

			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean getDOctorById(int id) {
		String query="SELECT * FROM doctors WHERE id = ?";
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
