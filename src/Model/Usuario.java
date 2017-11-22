package Model;

public class Usuario {
	String name, password;
	static boolean isGerente = false;

	public static boolean isGerente() {
		return isGerente;
	}

	public void setGerente(boolean isGerente) {
		Usuario.isGerente = isGerente;
	}

	public Usuario(String name, String password) {
		setName(name);
		setPassword(password);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
