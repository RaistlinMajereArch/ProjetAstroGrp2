package metier;

import javax.persistence.Entity;

@Entity
public class Etoile extends CorpsCeleste{

	public Etoile() {}
	
	public Etoile(int id, double masse, double diametre, boolean etat,
			String nom) {
		
		super(id, masse, diametre, 0.0, 0.0, 0.0, 0.0, etat, nom,null);
		// TODO Auto-generated constructor stub
	}
	public Etoile(double masse, double diametre,
			String nom) {
		super(masse, diametre, 0.0, 0.0, 0.0, 0.0, nom,null);
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Etoile [G=" + G + ", id=" + id + ", masse=" + masse + ", diametre=" + diametre + ", x=" + x + ", y=" + y
				+ ", vx=" + vx + ", vy=" + vy + ", etat=" + etat + ", nom=" + nom + "]";
	}

	

}
