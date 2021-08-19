package metier;

import javax.persistence.Entity;

@Entity
public class Planete extends CorpsCeleste{

	public Planete() {}
	
	public Planete(int id, double masse, double diametre, double x, double y, double vx, double vy, boolean etat,
			String nom, CorpsCeleste parent) 
	{
		super(id, masse, diametre, x, y, vx, vy, etat, nom, parent);
		
	}
	
	public Planete(double masse, double diametre, double x, double y, double vx, double vy, String nom, CorpsCeleste parent) 
	{
		super(masse, diametre, x, y, vx, vy, nom, parent);
		
	}
		

	@Override
	public String toString() {
		return "Planete [G=" + G + ", id=" + id + ", masse=" + masse + ", diametre=" + diametre + ", x=" + x + ", y="
				+ y + ", vx=" + vx + ", vy=" + vy + ", etat=" + etat + ", nom=" + nom + ", parent=" + parent
				+ "]";
	}



}
