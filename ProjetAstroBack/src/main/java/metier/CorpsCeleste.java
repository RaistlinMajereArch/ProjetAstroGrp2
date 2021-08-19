package metier;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name="system")
@SecondaryTable(name="systeminit")
public abstract class CorpsCeleste {
	
	
	protected double G=6.6743E-20;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected double masse;
	protected double diametre;
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
	protected boolean etat=true;
	protected String nom;
	@ManyToOne(cascade = {CascadeType.ALL})
	protected CorpsCeleste parent;
	
	@Column(table="systeminit",name="masse")
	protected double masseInit;
	@Column(table="systeminit",name="diametre")
	protected double diametreInit;
	@Column(table="systeminit",name="x")
	protected double xInit;
	@Column(table="systeminit",name="y")
	protected double yInit;
	@Column(table="systeminit",name="vx")
	protected double vxInit;
	@Column(table="systeminit",name="vy")
	protected double vyInit;
	@Column(table="systeminit",name="etat")
	protected boolean etatInit=true;
	@Column(table="systeminit",name="nom")
	protected String nomInit;
	
	public CorpsCeleste() {}

	public CorpsCeleste(int id, double masse, double diametre, double x, double y, double vx, double vy, boolean etat,
			String nom,CorpsCeleste parent) {
		this.id = id;
		this.masse = masse;
		this.diametre = diametre;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.etat = etat;
		this.nom = nom;
		this.parent = parent;
	}
	
	public CorpsCeleste(double masse, double diametre, double x, double y, double vx, double vy, String nom,CorpsCeleste parent) {
		this.masse = masse;
		this.diametre = diametre;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.nom = nom;
		this.parent = parent;
		this.masseInit = masse;
		this.diametreInit = diametre;
		this.xInit = x;
		this.yInit = y;
		this.vxInit = vx;
		this.vyInit = vy;
		this.nomInit = nom;
	}

	public double[] calculForce(CorpsCeleste c) {
		double [] force= {0,0};
		double distance=Math.sqrt(Math.pow((this.x-c.x),2)+Math.pow((this.y-c.y),2));
		force[0]=G*(this.masse*c.masse)*((c.x-this.x)/Math.pow(distance,3));
		force[1]=G*(this.masse*c.masse)*((c.y-this.y)/Math.pow(distance,3));
		/*double distance=Math.sqrt(Math.pow((this.x-c.x),2)+Math.pow((this.y-c.y),2));
		double forceVector=G*(this.masse*c.masse)/Math.pow(distance,2);
		double angle=Math.atan2((c.y-this.y),(c.x-this.x));
		force[0]=Math.cos(angle)*forceVector;
		force[1]=Math.sin(angle)*forceVector;*/
		
		return force;
	}
	
	public double[] calculAcceleration(List<double[]> forces) {
		double [] accelerations= {0,0};
		double [] sommeForces= {0,0};
		double [] force= {0,0};
		
		for (int i=0; i<forces.size();i++) {
			force=forces.get(i);
			sommeForces[0]+=force[0];
			sommeForces[1]+=force[1];
		}
		accelerations[0]=sommeForces[0]/masse;
		accelerations[1]=sommeForces[1]/masse;
		return accelerations;
	}
	
	public void calculVitesse(double[] accelerations) {
		vx+=accelerations[0]*86400;
		vy+=accelerations[1]*86400;
	}
	
	public void calculPosition() {
		x+=vx*86400;
		y+=vy*86400;
	}
	
	public void fusionne(CorpsCeleste c) {
		if (this.masse>=c.masse) {
			this.masse+=c.masse;
			c.setEtat(false);
		}else {
			c.masse+=this.masse;
			this.setEtat(false);
		}
		
	}

	
	
	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public CorpsCeleste getParent() {
		return parent;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setParent(CorpsCeleste parent) {
		this.parent = parent;
	}
	public void setDiametre(double diametre) {
		this.diametre = diametre;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getG() {
		return G;
	}

	public double getMasse() {
		return masse;
	}

	public double getDiametre() {
		return diametre;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getVx() {
		return vx;
	}

	public double getVy() {
		return vy;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setMasse(double masse) {
		this.masse = masse;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}
	
	public double getMasseInit() {
		return masseInit;
	}

	public void setMasseInit(double masseInit) {
		this.masseInit = masseInit;
	}

	public double getDiametreInit() {
		return diametreInit;
	}

	public void setDiametreInit(double diametreInit) {
		this.diametreInit = diametreInit;
	}

	public double getxInit() {
		return xInit;
	}

	public void setxInit(double xInit) {
		this.xInit = xInit;
	}

	public double getyInit() {
		return yInit;
	}

	public void setyInit(double yInit) {
		this.yInit = yInit;
	}

	public double getVxInit() {
		return vxInit;
	}

	public void setVxInit(double vxInit) {
		this.vxInit = vxInit;
	}

	public double getVyInit() {
		return vyInit;
	}

	public void setVyInit(double vyInit) {
		this.vyInit = vyInit;
	}

	public boolean isEtatInit() {
		return etatInit;
	}

	public void setEtatInit(boolean etatInit) {
		this.etatInit = etatInit;
	}

	public String getNomInit() {
		return nomInit;
	}

	public void setNomInit(String nomInit) {
		this.nomInit = nomInit;
	}

	@Override
	public String toString() {
		return "CorpsCeleste [G=" + G + ", id=" + id + ", masse=" + masse + ", diametre=" + diametre + ", x=" + x
				+ ", y=" + y + ", vx=" + vx + ", vy=" + vy + ", etat=" + etat + ", nom=" + nom + ", parent=" + parent +"]";
	}
	
}
