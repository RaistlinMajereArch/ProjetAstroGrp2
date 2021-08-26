package astro.metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="positions")
public class Position {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	Integer idTimeStep;
	Integer idCorpsCeleste;
	double x;
	double y;
	
	public Position() {}
	
	public Position(Integer id_timeStep, Integer id_corpsCeleste, double x, double y) {
		this.idTimeStep = id_timeStep;
		this.idCorpsCeleste = id_corpsCeleste;
		this.x = x;
		this.y = y;
	}
	public Integer getIdTimeStep() {
		return idTimeStep;
	}
	public Integer getIdCorpsCeleste() {
		return idCorpsCeleste;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setIdTimeStep(Integer id_timeStep) {
		this.idTimeStep = id_timeStep;
	}
	public void setIdCorpsCeleste(Integer id_corpsCeleste) {
		this.idCorpsCeleste = id_corpsCeleste;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Position [id_timeStep=" + idTimeStep + ", id_corpsCeleste=" + idCorpsCeleste + ", x=" + x + ", y=" + y
				+ "]";
	}
	
	
}
