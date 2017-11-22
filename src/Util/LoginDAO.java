package Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Usuario;

public class LoginDAO {
    Connection connection;
	boolean check = false;

	public boolean checkLogin(Usuario usuario) throws SQLException {
		new ConnectionFactory();
		connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM login WHERE BINARY login=? and BINARY senha=?";

		try {

			stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getName());
			stmt.setString(2, usuario.getPassword());

			rs = stmt.executeQuery();
			if(rs.next()){
				check = true;
			}else{
				check = false;
			}
			return check;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			stmt.close();
			rs.close();
			connection.close();
		}
		
		
	}
}
