package Control;

import java.sql.SQLException;
import java.util.Vector;

import Util.ProdutoDAO;

public class ControllerManage {
	ProdutoDAO produtoDAO = new ProdutoDAO();
	ControllerRegister controllerRegister;
	
	public Vector<Vector<Object>> getLista() throws SQLException{
		return produtoDAO.getAll();
	}
	
	public void remove(int cod) throws SQLException{
		produtoDAO.removeProduto(cod);
	}
	
	public Vector<Vector<Object>> search(String key) throws SQLException{
		return produtoDAO.searchProduto(key);
	}
}
