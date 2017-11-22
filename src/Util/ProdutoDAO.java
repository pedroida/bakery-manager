package Util;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JOptionPane;

import Model.Produto;

public class ProdutoDAO {

	String sql = "";

	public void adiciona(Produto produto) throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		sql = "insert into produto (nome,categoria,preco_custo,preco_venda,quantidade,validade,descricao) values (?,?,?,?,?,?,?)";
		try {

			stmt = connection.prepareStatement(sql);
			stmt.setString(1, produto.getName());
			stmt.setString(2, produto.getCategory());
			stmt.setDouble(3, produto.getCostPrice());
			stmt.setDouble(4, produto.getSellPrice());
			stmt.setInt(5, produto.getAmount());
			stmt.setDate(6, (java.sql.Date) produto.getValidity());
			stmt.setString(7, produto.getDescription());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro de conexão", "erro", JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException(e);
		} finally {
			stmt.execute();
			stmt.close();
			connection.close();
		}
	}

	public List<String> getCategory() throws SQLException {
		new ConnectionFactory();
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			sql = "select * from categoria order by nome";
			List<String> categorias = new ArrayList<String>();
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				categorias.add(rs.getString("nome"));
			}

			return categorias;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}

	}

	public Vector<Vector<Object>> getAll() throws SQLException {
		new ConnectionFactory();
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Vector<Vector<Object>> produtos = new Vector<Vector<Object>>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
		try {
			sql = "select * from produto order by id_produto";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {

				Produto produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setName(rs.getString(2));
				produto.setCategory(rs.getString(3));
				produto.setDescription(rs.getString(8));
				produto.setCostPrice(rs.getDouble(5));
				produto.setSellPrice(rs.getDouble(4));
				produto.setAmount(rs.getInt(6));
				Date validade = (rs.getDate(7));
				String val = formatter.format(validade);
				produto.setValidityy(val);

				Vector<Object> newProduto = new Vector<>();
				newProduto.add(produto.getId());
				newProduto.add(produto.getName());
				newProduto.add(produto.getCategory());
				newProduto.add(produto.getCostPrice());
				newProduto.add(produto.getSellPrice());
				newProduto.add(produto.getAmount());
				newProduto.add(produto.getValidityy());
				newProduto.add(produto.getDescription());
				produtos.add(newProduto);
			}

			return produtos;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Falha ao carregar produtos! Favor reconectar.");
			throw new RuntimeException(e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
	}

	public void removeProduto(int cod) throws SQLException {
		new ConnectionFactory();
		Connection connection = ConnectionFactory.getConnection();
		sql = "DELETE from produto where id_produto=?";
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cod);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.execute();
			stmt.close();
			connection.close();
		}

	}

	public Produto getProduto(int id) throws SQLException {
		new ConnectionFactory();
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Produto produto = new Produto();
		sql = "SELECT * from produto WHERE id_produto =  ?";
		try {

			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				produto.setId(rs.getInt(1));
				produto.setName(rs.getString(2));
				produto.setCategory(rs.getString(3));
				produto.setCostPrice(rs.getDouble(4));
				produto.setSellPrice(rs.getDouble(5));
				produto.setAmount(rs.getInt(6));
				produto.setValidity(rs.getDate(7));
				produto.setDescription(rs.getString(8));
			}
			return produto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}

	}

	public void updateProduto(Produto produto) throws SQLException {
		new ConnectionFactory();
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		// nome,categoria,preco_custo,preco_venda,quantidade,validade,descricao
		sql = "UPDATE produto SET nome = ?, categoria = ?, preco_custo = ?,"
				+ " preco_venda = ?, quantidade = ?, validade = ?, descricao = ? WHERE id_produto = ?";
		try {
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, produto.getName());
			stmt.setString(2, produto.getCategory());
			stmt.setDouble(3, produto.getCostPrice());
			stmt.setDouble(4, produto.getSellPrice());
			stmt.setInt(5, produto.getAmount());
			stmt.setDate(6, (java.sql.Date) produto.getValidity());
			stmt.setString(7, produto.getDescription());
			stmt.setInt(8, produto.getId());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
		}
	}
	
	public Vector<Vector<Object>> searchProduto(String key) throws SQLException{
		Vector<Vector<Object>> produtos = new Vector<Vector<Object>>();
		sql = "SELECT * FROM produto WHERE MATCH(nome,descricao) AGAINST(? IN BOOLEAN MODE) order by id_produto";
		new ConnectionFactory();
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String keySearch = "";
		try{
			keySearch = key + "*";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, keySearch);
			rs = stmt.executeQuery();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
			while (rs.next() ) {

				Produto produto = new Produto();
				produto.setId(rs.getInt(1));
				produto.setName(rs.getString(2));
				produto.setCategory(rs.getString(3));
				produto.setDescription(rs.getString(8));
				produto.setCostPrice(rs.getDouble(5));
				produto.setSellPrice(rs.getDouble(4));
				produto.setAmount(rs.getInt(6));
				Date validade = (rs.getDate(7));
				String val = formatter.format(validade);
				produto.setValidityy(val);

				Vector<Object> newProduto = new Vector<>();
				newProduto.add(produto.getId());
				newProduto.add(produto.getName());
				newProduto.add(produto.getCategory());
				newProduto.add(produto.getCostPrice());
				newProduto.add(produto.getSellPrice());
				newProduto.add(produto.getAmount());
				newProduto.add(produto.getValidity());
				newProduto.add(produto.getDescription());
				produtos.add(newProduto);
				
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return produtos;
	}

}
