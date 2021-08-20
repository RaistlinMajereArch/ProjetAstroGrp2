package metier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.math.plot.Plot2DPanel;

import DAOjpa.DAOPositionsjpa;
import DAOjpa.DAOSystemeInitjpa;
import DAOjpa.DAOSystemejpa;

public class Simulation {
	
	private Integer timestep;
	private boolean calculSimple;
	private int ctpt=0;
	private int cpt;
	DAOPositionsjpa daoP = new DAOPositionsjpa();
    DAOSystemeInitjpa daoSI = new DAOSystemeInitjpa();
	DAOSystemejpa daoS = new DAOSystemejpa();
	private List<CorpsCeleste> systeme=new ArrayList();
	List<CorpsCeleste> systeme2= new ArrayList();
	JFrame tpt = new JFrame("Canard TPT");
	JFrame avancement = new JFrame("Avancement");
	
	public Simulation(int timestep, boolean calculSimple) {
		this.timestep = timestep;
		this.calculSimple = calculSimple;
	}
	
	/*public void start(int timestep, boolean calculSimple) {
		this.timestep = timestep;
		this.calculSimple = calculSimple;
		run();
	}*/
	
	public void run() {
		initSimu();
		//System.out.println(systeme);
		for (int t=1;t<=timestep;t++) {
			if (ctpt==11) {
				ctpt =0;
			}
			affichageTPT(ctpt);
			ctpt++;
			cpt++;
			avancerTimeStepSysteme();
			for(int i=0;i<getSysteme().size();i++) {
				daoS.update(getSysteme().get(i));
			}
			for(int i=0;i<getSysteme().size();i++) {
				Position p=new Position(t,getSysteme().get(i).getId(),getSysteme().get(i).getX(),getSysteme().get(i).getY());
				daoP.insert(p);
			}
		}
		affichageTrajectoire();
	}
	
	public void initSimu()
	{
		systeme2=daoSI.findAll();
		for(int i=0;i<systeme2.size();i++) {
			daoS.insert(systeme2.get(i));
		}
		setSysteme(daoS.findAll());
		for(int i=0;i<getSysteme().size();i++) {
			Position p=new Position(0,getSysteme().get(i).getId(),getSysteme().get(i).getX(),getSysteme().get(i).getY());
			daoP.insert(p);
		}
	}

	public void affichageTrajectoire() 
	{
		double [] x;
		double[] y;
		List<double []> lx= new ArrayList();
		List<double []> ly= new ArrayList();
		List<CorpsCeleste> systeme = daoS.findAll();
		List<Position> positions;
		for (CorpsCeleste c : systeme) { //parcours les corps du systemes et recuperes les listes de positions x et y
			positions = daoP.findByIdCorps(c.getId());
			x = new double[positions.size()];
			y = new double[positions.size()];
			for (int p=0;p<positions.size();p++) {
				x[p]= positions.get(p).getX();
				y[p]= positions.get(p).getY();
			}
			lx.add(x);
			ly.add(y);
			for (double[] d : lx) {
				System.out.println(Arrays.toString(d));
			}
		}
		
		JFrame frame = new JFrame("Draw a line");
		frame.setBackground(Color.BLACK);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		/*Courbe panel = new Courbe(lx,ly);
		panel.setPreferredSize(new Dimension(2500,2500));
		JScrollPane jsp = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(jsp);*/
		Plot2DPanel plot = new Plot2DPanel();
		for (int i=0;i<lx.size();i++) {
			 plot.addLinePlot(systeme.get(i).getNom(), lx.get(i), ly.get(i));
		}
		
		
		frame.setContentPane(plot);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);	
	}
	
	public void avancerTimeStepSysteme()
	{
		systeme2= getSysteme();
		for (int i=1;i<getSysteme().size();i++) {
			if (calculSimple) {
				avancerTimeStepCorpsSimplifie(getSysteme().get(i));
			}
			else {
				avancerTimeStepCorps(getSysteme().get(i));
			}
		}
		for (int i=0;i<getSysteme().size();i++) {
			for (int j=0;j<getSysteme().size();j++) {
				if (i!=j) {
					double xa=getSysteme().get(i).getX();
					double xb=getSysteme().get(i).getX();
					double ya=getSysteme().get(i).getY();
					double yb=getSysteme().get(i).getY();
					double distance=Math.sqrt(Math.pow((xa-xb),2)+Math.pow((ya-yb),2));
					if(distance < getSysteme().get(i).getDiametre()) {
						//systeme.get(i).fusionne(systeme.get(j));
					}
				}
			}
		}
	}
	
	public void avancerTimeStepCorpsSimplifie(CorpsCeleste c) 
	{
		for (int i=0;i<getSysteme().size();i++) {

			if (c.getParent().getId() == systeme2.get(i).getId()) {
				List<Double[]> forces = new ArrayList();
				forces.add(c.calculForce(systeme2.get(i)));
				for (Double[] f: forces) {
				}
				//System.out.println(forces.toString());
				Double[] accelerations =c.calculAcceleration(forces);
				c.calculVitesse(accelerations);
				c.calculPosition();
			}
		}
	}
	
	public void avancerTimeStepCorps(CorpsCeleste c) 
	{
		for (int i=0;i<getSysteme().size();i++) {
			if (c.getId() != systeme2.get(i).getId()) {
				List<Double[]> forces = new ArrayList();
				forces.add(c.calculForce(systeme2.get(i)));
				for (Double[] f: forces) {	
				}
				//System.out.println(forces.toString());
				Double[] accelerations =c.calculAcceleration(forces);

				c.calculVitesse(accelerations);
				c.calculPosition();
			}
		}
		
	}
	
	public void affichageTPT(int i) {
		avancement.setAlwaysOnTop(true);
		tpt.setAlwaysOnTop(true);
		tpt.getContentPane().removeAll();
		
		avancement.getContentPane().removeAll();
		String[] TPT = {"TPT1.png","TPT2.png","TPT3.png","TPT4.png","TPT5.png","TPT6.png","TPT7.png","TPT8.png","TPT9.jpeg","TPT10.jpeg","TPT11.jpg"};
		//tpt.setExtendedState(JFrame.MAXIMIZED_BOTH);
		tpt.setSize(1600,1000);
		tpt.setLocationRelativeTo(null);
		tpt.setVisible(true);
		avancement.setSize(timestep+100, 150);
		avancement.setLocation(0, 0);
		avancement.setVisible(true);
		String imgUrl = TPT[i];
		ImageIcon icone = new ImageIcon(imgUrl);
		JLabel jlabel = new JLabel(icone, JLabel.CENTER);
		Rectangle loading = new Rectangle(cpt,timestep);
		JScrollPane jsp = new JScrollPane(loading,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		avancement.getContentPane().add(jsp);
		avancement.validate();
		tpt.getContentPane().add(jlabel);
		tpt.validate();
		avancement.setVisible(true);
	}

	public List<CorpsCeleste> getSysteme() {
		return systeme;
	}

	public void setSysteme(List<CorpsCeleste> systeme) {
		this.systeme = systeme;
	}
	
}
