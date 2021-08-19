package metier;

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
	int id;
	int id_timeStep;
	int id_corpsCeleste;
	double x;
	double y;
	
	public Position() {}
	
	public Position(int id_timeStep, int id_corpsCeleste, double x, double y) {
		this.id_timeStep = id_timeStep;
		this.id_corpsCeleste = id_corpsCeleste;
		this.x = x;
		this.y = y;
	}
	public int getId_timeStep() {
		return id_timeStep;
	}
	public int getId_corpsCeleste() {
		return id_corpsCeleste;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setId_timeStep(int id_timeStep) {
		this.id_timeStep = id_timeStep;
	}
	public void setId_corpsCeleste(int id_corpsCeleste) {
		this.id_corpsCeleste = id_corpsCeleste;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Position [id_timeStep=" + id_timeStep + ", id_corpsCeleste=" + id_corpsCeleste + ", x=" + x + ", y=" + y
				+ "]";
	}
	
	
}
