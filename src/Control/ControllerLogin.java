package Control;

import java.sql.SQLException;

import Model.Produto;
import Model.Usuario;
import Util.LoginDAO;
import Util.ProdutoDAO;

public class ControllerLogin {
	
	LoginDAO loginDAO = new LoginDAO();
	Usuario usuario;
	
	
	public boolean check(Usuario usuario) throws SQLException{
		return loginDAO.checkLogin(usuario);
	}
}
