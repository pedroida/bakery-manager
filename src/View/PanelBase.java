package View;

import java.awt.CardLayout;


import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.*;


import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.Silver;

public class PanelBase extends JPanel implements VisualWindow {

	Manage manage;
	public Register register;
	Login login;
	FrameBase framebase;
	public static final int LOGIN = 0;
	public static final int MANAGE = 1;
	public static final int REGISTER = 2;

	public PanelBase(FrameBase frameBase) {
		this.framebase = frameBase;
		setLayout();
		setComponents();
		setEvents();

	}

	@Override
	public void setLayout() {
		setLayout(new CardLayout());
		setLook();
	}

	@Override
	public void setComponents() {
		register = new Register(framebase);
		manage = new Manage(framebase);
		login = new Login(framebase);
		add(login, "logar");
		add(manage, "manager");
		add(register, "register");

	}

	@Override
	public void setEvents() {

	}

	@Override
	public void setLook() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			PlasticLookAndFeel.setPlasticTheme(new DarkStar());
			try {
				UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
			} catch (UnsupportedLookAndFeelException ex) {
				ex.printStackTrace();
			}
		}

	}

}
