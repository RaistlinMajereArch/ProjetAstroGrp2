package astro.metier;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity

@Table(name="systeminit")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
@SecondaryTable(name="system")
public abstract class CorpsCeleste {
	
	
	protected Double G=6.6743E-20;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	@Column(insertable = false, updatable = false) 
	private String type; 
	@Column(name="masse")
	protected Double masseInit;
	@Column(name="diametre")
	protected Double diametreInit;
	@Column(name="x")
	protected Double xInit;
	@Column(name="y")
	protected Double yInit;
	@Column(name="vx")
	protected Double vxInit;
	@Column(name="vy")
	protected Double vyInit;
	@Column(name="etat")
	protected Boolean etatInit=true;
	@Column(name="nom")
	protected String nomInit;
	@ManyToOne(cascade = {CascadeType.ALL})
	protected CorpsCeleste parent;
	//@OneToMany(mappedBy="parent",cascade = {CascadeType.ALL})
	///protected List<CorpsCeleste> enfants;
	
	@Column(table="system",name="G")
	protected Double GInit=6.6743E-20;
	@Column(table="system",name="masse")
	protected Double masse;
	@Column(table="system",name="diametre")
	protected Double diametre;
	@Column(table="system",name="x")
	protected Double x;
	@Column(table="system",name="y")
	protected Double y;
	@Column(table="system",name="vx")
	protected Double vx;
	@Column(table="system",name="vy")
	protected Double vy;
	@Column(table="system",name="etat")
	protected Boolean etat=true;
	@Column(table="system",name="nom")
	protected String nom;
	
	public CorpsCeleste() {}

	public CorpsCeleste(int id, Double masseInit, Double diametreInit, Double xInit, Double yInit, Double vxInit, Double vyInit, Boolean etatInit,
			String nomInit,CorpsCeleste parent) {
		this.id = id;
		this.masseInit = masseInit;
		this.diametreInit = diametreInit;
		this.xInit = xInit;
		this.yInit = yInit;
		this.vxInit = vxInit;
		this.vyInit = vyInit;
		this.etatInit = etatInit;
		this.nomInit = nomInit;
		this.parent = parent;
	}
	
	public CorpsCeleste(Double masseInit, Double diametreInit, Double xInit, Double yInit, Double vxInit, Double vyInit, String nomInit,CorpsCeleste parent) {
		this.masseInit = masseInit;
		this.diametreInit = diametreInit;
		this.xInit = xInit;
		this.yInit = yInit;
		this.vxInit = vxInit;
		this.vyInit = vyInit;
		this.nomInit = nomInit;
		this.parent = parent;
		this.masse = masseInit;
		this.diametre = diametreInit;
		this.x = xInit;
		this.y = yInit;
		this.vx = vxInit;
		this.vy = vyInit;
		this.nom = nomInit;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorpsCeleste other = (CorpsCeleste) obj;
		return id == other.id;
	}
	
}
