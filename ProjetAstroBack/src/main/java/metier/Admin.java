package metier;

import javax.persistence.Entity;

@Entity
public class Admin extends Compte{
	
	public Admin() {}
	
	public Admin(String login, String password) {
		super(login, password);
	}

	public Admin(int id,String login, String password) {
		super(id,login, password);
	}
	@Override
	public String toString() {
		return "Admin [id=" + id + ", login=" + login + ", password=" + password + "]";
	}
	
	
}

