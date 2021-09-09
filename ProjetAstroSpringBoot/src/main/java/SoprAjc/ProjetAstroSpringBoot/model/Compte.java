package SoprAjc.ProjetAstroSpringBoot.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="Compte")
public class Compte {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLogin")
	private Long id;
	@Column(name = "login", length = 150, unique = true, nullable = false)
	@NotEmpty
	private String login;
	@Column(name = "password", length = 255, nullable = false)
	@NotEmpty
	private String password;
	//@Enumerated(EnumType.STRING)
	//@Column(name = "role", length = 15, nullable = false)
	//private Role role;	
	
	public Compte() {
	}
	
	public Compte(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compte other = (Compte) obj;
		return Objects.equals(id, other.id) && Objects.equals(login, other.login)
				&& Objects.equals(password, other.password);
	}

	
}



