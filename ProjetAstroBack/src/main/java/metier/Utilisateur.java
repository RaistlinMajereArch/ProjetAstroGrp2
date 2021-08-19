package metier;

import javax.persistence.Entity;

@Entity
public class Utilisateur extends Compte {
	
	public Utilisateur() {}
	
	public Utilisateur(String login, String password) {
		super(login, password);
	}

	public Utilisateur(int id,String login, String password) {
		super(id,login, password);
	}
	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", login=" + login + ", password=" + password + "]";
	}
	
	
}



