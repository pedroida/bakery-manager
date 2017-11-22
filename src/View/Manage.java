package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.Silver;
import com.jgoodies.looks.plastic.theme.SkyKrupp;

import Control.ControllerEdit;
import Control.ControllerManage;
import Model.Usuario;

public class Manage extends JPanel implements VisualWindow {

	FrameBase framebase;
	ControllerEdit controllerEdit;
	ControllerManage controllerManage;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
	Vector<String> columns;
	Vector<Vector<Object>> produtos = new Vector<Vector<Object>>();
	JButton list;
	static JButton remove;
	JButton addProduto;
	JButton editProduto;
	JButton search;
	JLabel legTable,title;
	JTextField searchBar;

	public Manage(FrameBase frameBase) {

		this.framebase = frameBase;
		controllerManage = new ControllerManage();
		controllerEdit = new ControllerEdit(frameBase);
		setLayout();
		listProdutos();
		setComponents();
		setEvents();

	}

	@Override
	public void setLayout() {

		setLayout(null);
		setVisible(true);
		//setLook();
	}

	public void setColumns() {
		columns = new Vector<>();
		columns.add("Código");
		columns.add("Nome");
		columns.add("Categoria");
		columns.add("Preço custo");
		columns.add("Preço venda");
		columns.add("Quantidade");
		columns.add("Validade");
		columns.add("Descrição");
	}

	@Override
	public void setComponents() {

		setColumns();
		dtm = new DefaultTableModel(produtos, columns) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table = new JTable(dtm);
		jsp = new JScrollPane(table);
		jsp.setBounds(10, 340, 680, 280);
		table.setPreferredScrollableViewportSize(new Dimension(500, 100)); // barra
																			// de
																			// rolagem

		table.setFillsViewportHeight(true); // fundo branco da table

		add(jsp);
		
		
		title = new JLabel("Controle de produto", JLabel.CENTER);
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		title.setBounds(0, 20, 680, 60);
		add(title);

		list = new JButton("Lista completa");
		list.setBounds(20, 200, 130, 50);
		add(list);

		remove = new JButton("Remover");
		remove.setBounds(180, 200, 130, 50);
		add(remove);

		addProduto = new JButton("Adicionar");
		addProduto.setBounds(350, 200, 130, 50);
		add(addProduto);

		editProduto = new JButton("Editar");
		editProduto.setBounds(515, 200, 130, 50);
		add(editProduto);

		legTable = new JLabel("** Clique duas vezes sobre o item para visualizar os dados!");
		legTable.setBounds(10, 320, 680, 20);
		add(legTable);

		searchBar = new JTextField();
		searchBar.setBounds(150, 100, 300, 50);
		add(searchBar);

		search = new JButton("Buscar");
		search.setBounds(445, 100, 80, 50);
		add(search);

	}

	@Override
	public void setEvents() {

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JOptionPane.showMessageDialog(null, showFullProduto());
				}
			}
		});

		list.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				atualizaTable();
			}
		});

		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					removeProduto();
				} catch (Exception ex) {
					String message = "";
					if (produtos.size() == 0) {
						message = "Lista vazia!";
						JOptionPane.showMessageDialog(null, message);
					} else {
						message = "Escolha um item!";
						JOptionPane.showMessageDialog(null, message);
					}
				}
			}
		});

		addProduto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addProduto();
			}
		});

		editProduto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					editProduto();
				} catch (Exception ex) {
					String message = "";
					if (produtos.size() == 0) {
						message = "Lista vazia!";
						JOptionPane.showMessageDialog(null, message);
					} else {
						message = "Escolha um item!";
						JOptionPane.showMessageDialog(null, message);
					}
				}
			}
		});

		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!searchBar.getText().trim().isEmpty()) {
					searchProduto(searchBar.getText());
				} else {
					atualizaTable();
				}
			}
		});
	}
	
	//limpa e lista todos os produtos
	public void atualizaTable() {
		int tam = dtm.getRowCount();
		for (int i = tam - 1; i >= 0; i--) {
			dtm.removeRow(i);
		}

		listProdutos();
		for (int i = 0; i < produtos.size(); i++) {
			dtm.addRow(produtos.get(i));
		}
	}

	public void listProdutos() {
		try {
			produtos = controllerManage.getLista();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addProduto() {
		Register.title.setText("Cadastrar produto");
		framebase.showPanels(PanelBase.REGISTER);
	}

	public void editProduto() {
		int row = table.getSelectedRow();
		int id = (int) produtos.get(row).elementAt(0);
		Register.title.setText("Editar produto");
		try {
			controllerEdit.editProduto(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void removeProduto() {
		int cod = (int) produtos.get(getSelectedProduto()).elementAt(0);

		Object[] options = { "SIM", "NÃO" };
		int remove = JOptionPane.showOptionDialog(null, "Deseja realmente remover?", "Remover",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (remove == JOptionPane.YES_OPTION) {
			dtm.removeRow(getSelectedProduto());
			try {
				controllerManage.remove(cod);
				JOptionPane.showMessageDialog(null, "Removido com sucesso!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void searchProduto(String key) {
		try {
			produtos = controllerManage.search(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int tam = dtm.getRowCount();
		for (int i = tam - 1; i >= 0; i--) {
			dtm.removeRow(i);
		}
		for (int i = 0; i < produtos.size(); i++) {
			dtm.addRow(produtos.get(i));
		}
	}

	public int getSelectedProduto() {
		int row = table.getSelectedRow();
		return row;
	}

	public String showFullProduto() {
		String all = "";
		for (int i = 0; i < 8; i++) {
			all += columns.get(i) + " : " + produtos.get(getSelectedProduto()).elementAt(i).toString() + "\n";
		}

		return all;
	}
	
	public static void verifyUser(){
		if(!Usuario.isGerente()){
			remove.setEnabled(false);
			remove.setToolTipText("Apenas gerente");
		}else{
			remove.setEnabled(true);
		}
	}

	@Override
	public void setLook() {
				// SkyBlue()
				// BrownSugar()
				// DarkStar()
				// DesertGreen()
				// Silver()
				// ExperienceRoyale()
		PlasticLookAndFeel.setPlasticTheme(new DarkStar());
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

	}

}
