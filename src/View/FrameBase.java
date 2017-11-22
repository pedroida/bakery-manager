package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.Silver;

public class FrameBase extends JFrame implements VisualWindow {

	private JMenuBar jmenubar;
	private JMenu mnfile;
	private JMenuItem seeManage, regProduto, changeUser, exit;
	public PanelBase panelbase;
	public CardLayout cl;

	public FrameBase() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		setLayout();
		setComponents();
		setEvents();
		revalidate();
	}

	@Override
	public void setLook() {

		PlasticLookAndFeel.setPlasticTheme(new DarkStar());
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void setLayout() {
		setTitle("Sistema do Pedro");
		setSize(720, 720);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(720, 720));
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		setLook();
	}

	@Override
	public void setComponents() {
		jmenubar = new JMenuBar();
		mnfile = new JMenu("Menu");
		seeManage = new JMenuItem("Gerenciar estoque");
		regProduto = new JMenuItem("Cadastrar produto");
		changeUser = new JMenuItem("Trocar usuário");
		exit = new JMenuItem("Sair");
		jmenubar.setVisible(false);

		mnfile.add(seeManage);
		mnfile.add(regProduto);
		mnfile.add(changeUser);
		mnfile.add(exit);
		jmenubar.add(mnfile);

		add(jmenubar, BorderLayout.NORTH);

		panelbase = new PanelBase(this);

		cl = new CardLayout();
		cl = (CardLayout) panelbase.getLayout();
		add(panelbase, BorderLayout.CENTER);
	}

	@Override
	public void setEvents() {
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "SIM", "NÃO" };
				int sair = JOptionPane.showOptionDialog(null, "Deseja realmente sair?", "Sair",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}

			}
		});

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (e.getID() == WindowEvent.WINDOW_CLOSING) {
					Object[] options = { "SIM", "NÃO" };
					int sair = JOptionPane.showOptionDialog(null, "Deseja realmente sair?", "Sair",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (sair == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}
			}
		});

		seeManage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanels(PanelBase.MANAGE);

			}
		});

		regProduto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Register.title.setText("Cadastrar produto");
				panelbase.register.clear();
				showPanels(PanelBase.REGISTER);

			}
		});

		changeUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanels(PanelBase.LOGIN);

			}
		});

	}

	public void showPanels(int panelBaseConstants) {
		switch (panelBaseConstants) {
		case 0:
			cl.show(panelbase, "logar");
			jmenubar.setVisible(false);
			break;

		case 1:
			cl.show(panelbase, "manager");
			jmenubar.setVisible(true);
			Manage.verifyUser();

			break;

		case 2:
			cl.show(panelbase, "register");

			break;

		default:
			break;
		}
	}

	@Override
	public void revalidate() {

		super.revalidate();
	}

}
