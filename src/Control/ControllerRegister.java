package Control;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import Model.Produto;
import Util.ProdutoDAO;

public class ControllerRegister {
	ProdutoDAO produtoDAO = new ProdutoDAO();
	Produto produto;
	
	
	public Produto getNewProduto(String nome, String categoria, String descricao, double precoVenda, double precoCusto,
			int quantidade, Date validade){
		return new Produto(nome, categoria,descricao,precoVenda,precoCusto,quantidade,validade);
	}
	
	public void registerProduto(Produto produto) throws SQLException{
		produtoDAO.adiciona(produto);
	}
	
	public List<String> getCategory() throws SQLException{
		return produtoDAO.getCategory();
	}

	
}
