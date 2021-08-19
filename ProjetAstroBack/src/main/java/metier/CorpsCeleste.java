package metier;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name="systeminit")
@SecondaryTable(name="system")
public abstract class CorpsCeleste {
	
	
	protected Double G=6.6743E-20;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected Double masse;
	protected Double diametre;
	protected Double x;
	protected Double y;
	protected Double vx;
	protected Double vy;
	protected Boolean etat=true;
	protected String nom;
	@ManyToOne(cascade = {CascadeType.ALL})
	protected CorpsCeleste parent;
	//@OneToMany(mappedBy="parent",cascade = {CascadeType.ALL})
	///protected List<CorpsCeleste> enfants;
	
	@Column(table="system",name="masse")
	protected Double masseInit;
	@Column(table="system",name="diametre")
	protected Double diametreInit;
	@Column(table="system",name="x")
	protected Double xInit;
	@Column(table="system",name="y")
	protected Double yInit;
	@Column(table="system",name="vx")
	protected Double vxInit;
	@Column(table="system",name="vy")
	protected Double vyInit;
	@Column(table="system",name="etat")
	protected Boolean etatInit=true;
	@Column(table="system",name="nom")
	protected String nomInit;
	
	public CorpsCeleste() {}

	public CorpsCeleste(int id, Double masse, Double diametre, Double x, Double y, Double vx, Double vy, Boolean etat,
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
	
	public CorpsCeleste(Double masse, Double diametre, Double x, Double y, Double vx, Double vy, String nom,CorpsCeleste parent) {
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

	public Double[] calculForce(CorpsCeleste c) {
		Double [] force= {(double) 0,(double) 0};
		Double distance=Math.sqrt(Math.pow((this.x-c.x),2)+Math.pow((this.y-c.y),2));
		force[0]=G*(this.masse*c.masse)*((c.x-this.x)/Math.pow(distance,3));
		force[1]=G*(this.masse*c.masse)*((c.y-this.y)/Math.pow(distance,3));
		/*Double distance=Math.sqrt(Math.pow((this.x-c.x),2)+Math.pow((this.y-c.y),2));
		Double forceVector=G*(this.masse*c.masse)/Math.pow(distance,2);
		Double angle=Math.atan2((c.y-this.y),(c.x-this.x));
		force[0]=Math.cos(angle)*forceVector;
		force[1]=Math.sin(angle)*forceVector;*/
		
		return force;
	}
	
	public Double[] calculAcceleration(List<Double[]> forces) {
		Double [] accelerations= {(double) 0,(double) 0};
		Double [] sommeForces= {(double) 0,(double) 0};
		Double [] force= {(double) 0,(double) 0};
		
		for (int i=0; i<forces.size();i++) {
			force=forces.get(i);
			sommeForces[0]+=force[0];
			sommeForces[1]+=force[1];
		}
		accelerations[0]=sommeForces[0]/masse;
		accelerations[1]=sommeForces[1]/masse;
		return accelerations;
	}
	
	public void calculVitesse(Double[] accelerations) {
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
	public void setDiametre(Double diametre) {
		this.diametre = diametre;
	}

	public void setEtat(Boolean etat) {
		this.etat = etat;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Double getG() {
		return G;
	}

	public Double getMasse() {
		return masse;
	}

	public Double getDiametre() {
		return diametre;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public Double getVx() {
		return vx;
	}

	public Double getVy() {
		return vy;
	}

	public Boolean isEtat() {
		return etat;
	}

	public void setMasse(Double masse) {
		this.masse = masse;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public void setVx(Double vx) {
		this.vx = vx;
	}

	public void setVy(Double vy) {
		this.vy = vy;
	}
	
	public Double getMasseInit() {
		return masseInit;
	}

	public void setMasseInit(Double masseInit) {
		this.masseInit = masseInit;
	}

	public Double getDiametreInit() {
		return diametreInit;
	}

	public void setDiametreInit(Double diametreInit) {
		this.diametreInit = diametreInit;
	}

	public Double getxInit() {
		return xInit;
	}

	public void setxInit(Double xInit) {
		this.xInit = xInit;
	}

	public Double getyInit() {
		return yInit;
	}

	public void setyInit(Double yInit) {
		this.yInit = yInit;
	}

	public Double getVxInit() {
		return vxInit;
	}

	public void setVxInit(Double vxInit) {
		this.vxInit = vxInit;
	}

	public Double getVyInit() {
		return vyInit;
	}

	public void setVyInit(Double vyInit) {
		this.vyInit = vyInit;
	}

	public Boolean isEtatInit() {
		return etatInit;
	}

	public void setEtatInit(Boolean etatInit) {
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
