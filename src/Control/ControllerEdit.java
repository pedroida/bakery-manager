package Control;

import java.sql.SQLException;

import Model.Produto;
import Util.ProdutoDAO;
import View.FrameBase;
import View.PanelBase;
import View.Register;

public class ControllerEdit {
	ProdutoDAO produtoDAO = new ProdutoDAO();
	Produto produto;
	FrameBase frameBase;
	
	public ControllerEdit(FrameBase frameBase){
		this.frameBase = frameBase;
	}

	public void editProduto(int id) throws SQLException{
		produto = produtoDAO.getProduto(id);
		frameBase.showPanels(PanelBase.REGISTER);
		frameBase.panelbase.register.edit(produto.getId(), produto.getName(), produto.getCategory(), produto.getDescription(), produto.getCostPrice(),
				      produto.getSellPrice(), produto.getAmount(),produto.getValidity());
		
	}
	
	public void updateProduto(Produto produto) throws SQLException{
		produtoDAO.updateProduto(produto);
	}
}
