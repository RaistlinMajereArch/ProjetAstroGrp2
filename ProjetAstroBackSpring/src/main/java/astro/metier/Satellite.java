package astro.metier;

import javax.persistence.Entity;

@Entity
public class Satellite extends CorpsCeleste{

	public Satellite() {}
	
	public Satellite(int id, double masse, double diametre, double x, double y, double vx, double vy, boolean etat,
			String nom, CorpsCeleste parent) {
		super(id, masse, diametre, x, y, vx, vy, etat, nom, parent);
		// TODO Auto-generated constructor stub
	}
	
	
	public Satellite(double masse, double diametre, double x, double y, double vx, double vy, String nom, CorpsCeleste parent) 
	{
		super(masse, diametre, x, y, vx, vy, nom, parent);
		
	}


	@Override
	public String toString() {
		return "Satellite [G=" + G + ", id=" + id + ", masse=" + masse + ", diametre=" + diametre + ", x=" + x + ", y="
				+ y + ", vx=" + vx + ", vy=" + vy + ", etat=" + etat + ", nom=" + nom + ", parent=" + parent
				+ "]";
	}

	

	
}
