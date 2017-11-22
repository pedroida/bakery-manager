package View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.Silver;

import Control.ControllerLogin;
import Model.Usuario;

public class Login extends JPanel implements VisualWindow {

	Image background;
	FrameBase framebase;
	JLabel lblogin, lbPassword;
	JTextField jtlogin;
	JPasswordField jpPassword;
	JButton btlogar, exit;
	JPanel jpLogin;
	Font font;
	ControllerLogin controllerLogin;
	Usuario usuario;

	public Login(FrameBase framebase) {
		controllerLogin = new ControllerLogin();
		this.framebase = framebase;
		
		setComponents();
		setEvents();
		changeFonts();
		startUser();
		setLayout();
	}

	@Override
	public void setLayout() {
		setLayout(null);
		setVisible(true);
		//setLook();
	}

	@Override
	public void setLook() {
		PlasticLookAndFeel.setPlasticTheme(new Silver());
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

	}
	
	@Override
	public void setComponents() {
		background = new ImageIcon(getClass().getResource("../img/login.jpg")).getImage();
		font = new Font("Monospaced", Font.BOLD, 15);

		jpLogin = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
		jpLogin.setSize(400, 200);
		jpLogin.setVisible(true);
		Dimension external = framebase.getSize();
		Dimension internal = jpLogin.getSize();
		jpLogin.setLocation((external.width - internal.width) / 2, (external.height - internal.height) / 2);

		lblogin = new JLabel("Usuário: ");
		jtlogin = new JTextField(20);
		lbPassword = new JLabel("Senha:   ");
		jpPassword = new JPasswordField(20);
		btlogar = new JButton("Entrar");
		exit = new JButton("Sair");

		add(jpLogin);
		jpLogin.add(lblogin);
		jpLogin.add(jtlogin);
		jpLogin.add(lbPassword);
		jpLogin.add(jpPassword);
		jpLogin.add(btlogar);
		jpLogin.add(exit);
		
		
	}
	
	public void startUser(){
		usuario = new Usuario(jtlogin.getText(), jpPassword.getText());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}


	@Override
	public void setEvents() {
		btlogar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "SIM", "NÃO" };
				int exit = JOptionPane.showOptionDialog(null, "Deseja realmente sair?", "Sair",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (exit == JOptionPane.YES_OPTION) {
					System.exit(0);
				}

			}
		});

	}
	
	public void login(){
		try {
			if (controllerLogin.check(getUser())) {
				if(usuario.getName().equals("gerente")){
					usuario.setGerente(true);
				}else{
					usuario.setGerente(false);
				}
				jtlogin.setText("");
				jpPassword.setText("");
				framebase.showPanels(PanelBase.MANAGE);
			} else {
				JOptionPane.showMessageDialog(null, "login e/ou senha incorreto");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public Usuario getUser() {
		usuario.setName(jtlogin.getText());
		usuario.setPassword(jpPassword.getText());
		return usuario;
	}

	public void changeFonts() {
		Component[] c = jpLogin.getComponents();
		for (Component component : c) {
			component.setFont(font);
		}
	}



}
