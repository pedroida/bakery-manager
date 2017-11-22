	package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionFactory {

	public static Connection getConnection() {
		try {
			
			return DriverManager.getConnection("jdbc:mysql://186.209.225.52/armazememporiona", "armazememporiona", "batata02");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "banco não conectado");
			throw new RuntimeException(e);
			
		}	
	}
}
