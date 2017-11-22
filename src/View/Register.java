package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.MaskFormatter;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.BrownSugar;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.DesertGreen;
import com.jgoodies.looks.plastic.theme.DesertRed;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.Silver;
import com.jgoodies.looks.plastic.theme.SkyBlue;
import com.toedter.calendar.JDateChooser;

import Control.ControllerEdit;
import Control.ControllerRegister;
import Exception.InvalidFields;
import Model.Produto;
import Util.JNumberFormatField;

public class Register extends JPanel implements VisualWindow {
	JButton register, back;
	JLabel lbname, lbCategory, lbCP, lbSP, lbAmount, lbval, lbDescription;
	JTextField name, costPrice, salePrice;
	MaskFormatter valMask, amountMask;
	JNumberFormatField amount;
	JTextArea description;
	JComboBox<String> category;
	FrameBase framebase;
	ControllerRegister controllerRegister;
	ControllerEdit controllerEdit;
	Produto produto;
	JPanel internalPanel;
	Image background;
	JDateChooser validity;
	Date date = null;
	public static JLabel title;
	boolean isEditing = false;
	int id = 0;

	public Register(FrameBase frameBase) {
		this.framebase = frameBase;
		controllerRegister = new ControllerRegister();
		controllerEdit = new ControllerEdit(framebase);
		setLayout();
		setComponents();
		setEvents();
		populaComboBox();
	}

	@Override
	public void setLayout() {
		setLayout(null);
		setVisible(true);
		// setLook();

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

	// seta imagem de background
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	public void populaComboBox() {
		ArrayList<String> popula = new ArrayList<>();
		try {
			popula.addAll(controllerRegister.getCategory());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String p : popula) {
			category.addItem(p);
		}
	}

	@Override
	public void setComponents() {

		// inicializa painel interno
		internalPanel = new JPanel(null);
		internalPanel.setSize(500, 550);
		internalPanel.setVisible(true);
		Dimension external = framebase.getSize();
		Dimension internal = internalPanel.getSize();
		internalPanel.setLocation((external.width - internal.width) / 2, (external.height - internal.height) / 2);
		add(internalPanel);

		background = new ImageIcon(getClass().getResource("../img/login.jpg")).getImage();
		title = new JLabel("Cadastrar produto", JLabel.CENTER);
		title.setBounds(0, 20, internalPanel.getWidth(), 50);
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		internalPanel.add(title);

		lbname = new JLabel("Nome: ");
		lbname.setBounds(50, 100, 100, 20);
		internalPanel.add(lbname);

		name = new JTextField();
		name.setBounds(200, 100, 200, 30);
		internalPanel.add(name);

		lbCategory = new JLabel("Categoria: ");
		lbCategory.setBounds(50, 150, 100, 20);
		internalPanel.add(lbCategory);

		category = new JComboBox<>();
		// centraliza texto da comboBox
		((JLabel) category.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		category.setBounds(200, 150, 150, 30);
		internalPanel.add(category);

		lbCP = new JLabel("Preço de custo: ");
		lbCP.setBounds(50, 200, 100, 20);
		internalPanel.add(lbCP);

		costPrice = new JNumberFormatField();
		costPrice.setHorizontalAlignment(SwingConstants.LEFT);
		costPrice.setBounds(200, 200, 85, 30);
		internalPanel.add(costPrice);

		lbSP = new JLabel("Preço de venda: ");
		lbSP.setBounds(50, 250, 100, 20);
		internalPanel.add(lbSP);

		salePrice = new JNumberFormatField();
		salePrice.setHorizontalAlignment(SwingConstants.LEFT);
		salePrice.setBounds(200, 250, 85, 30);
		internalPanel.add(salePrice);

		lbAmount = new JLabel("Quantidade:");
		lbAmount.setBounds(50, 300, 100, 20);
		internalPanel.add(lbAmount);

		amount = new JNumberFormatField(new DecimalFormat("0"));
		amount.setBounds(200, 300, 50, 30);
		internalPanel.add(amount);

		lbval = new JLabel("Validade:");
		lbval.setBounds(50, 350, 100, 20);
		internalPanel.add(lbval);

		validity = new JDateChooser();
		validity.setBounds(200, 350, 120, 30);
		internalPanel.add(validity);

		lbDescription = new JLabel("Descrição: ");
		lbDescription.setBounds(50, 400, 100, 20);
		internalPanel.add(lbDescription);

		description = new JTextArea();
		description.setBounds(200, 400, 200, 50);
		internalPanel.add(description);

		register = new JButton("Confirmar");
		register.setBounds(40, 480, 150, 50);
		internalPanel.add(register);

		back = new JButton("Voltar");
		back.setBounds(300, 480, 150, 50);
		internalPanel.add(back);
	}

	@Override
	public void setEvents() {
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (isEditing == true) {
					if (checkdata()) {
						updateProduto();
					}
				} else {
					if (checkdata()) {
						addProduto();
					}
				}
			}
		});

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				framebase.showPanels(PanelBase.MANAGE);

			}
		});
	}

	public void addProduto() {
		try {
			controllerRegister.registerProduto(populaProduto());
			JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		title.setText("Cadastrar produto");
		clear();
	}

	public void clear() {
		name.setText("");
		costPrice.setText("0.00");
		salePrice.setText("0.00");
		amount.setText("0");
		validity.setDate(null);
		description.setText("");
	}

	public Produto populaProduto() {
		String pName = name.getText();
		String pcategory = category.getSelectedItem().toString();
		String pdescription = description.getText();
		double pCost = Double.parseDouble(costPrice.getText().replaceAll(",", "").replaceAll(Pattern.quote("."), "").substring(2));
		double pSell = Double.parseDouble(salePrice.getText().replaceAll(",", ".").substring(2));
		int pamount = Integer.parseInt(amount.getText());
		java.sql.Date pvalidity = new java.sql.Date(date.getTime());
		Produto produto = controllerRegister.getNewProduto(pName, pcategory, pdescription, pSell, pCost, pamount,
				pvalidity);

		return produto;
	}

	
	//valida campos
	public boolean checkdata() {
		try {
			if (name.getText().trim().isEmpty()) {
				throw new InvalidFields("Nome obrigatório");
			} else {
				if (amount.getText().equals("0")) {
					throw new InvalidFields("Insira quantidade válida");
				} else {
					date = validity.getDate();
					if (date == null) {
						throw new InvalidFields("Data inválida");
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
		return true;
	}

	public void edit(int id, String name, String category, String description, double costPrice, double salePrice,
			int amount, Date val) {
		this.id = id;
		isEditing = true;
		this.name.setText(name);
		for (int i = 0; i < this.category.getItemCount(); i++) {
			if (this.category.getItemAt(i).equals(category)) {
				this.category.setSelectedIndex(i);
			}
		}
		this.description.setText(description);
		this.costPrice.setText(Double.toString(costPrice));
		this.salePrice.setText(Double.toString(salePrice));
		this.amount.setText(Integer.toString(amount));
		this.validity.setDate((java.util.Date) val);
	}

	public void updateProduto() {
		Produto produto = populaProduto();
		produto.setId(id);
		try {
			controllerEdit.updateProduto(produto);
			JOptionPane.showMessageDialog(name, "Editado com sucesso!");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		isEditing = false;
	}
}