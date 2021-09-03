package astro.metier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.math.plot.Plot2DPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import astro.repositories.PositionsRepository;
import astro.repositories.SystemeInitRepository;
import astro.repositories.SystemeRepository;

@Component
public class Simulation {
	
	private Integer timestep;
	private boolean calculSimple;
	private int ctpt=0;
	private int cpt;
	
	@Autowired
	SystemeInitRepository sysIRepo2;
	@Autowired
	SystemeRepository sysRepo;
	@Autowired
	PositionsRepository posRepo;
	
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
	
	public void run() throws IOException {
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
				sysRepo.save(getSysteme().get(i));
			}
			for(int i=0;i<getSysteme().size();i++) {
				Position p=new Position(t,getSysteme().get(i).getId(),getSysteme().get(i).getX(),getSysteme().get(i).getY());
				posRepo.save(p);
			}
		}
		affichageTrajectoire();
	}
	
	public void initSimu()
	{
		systeme2=sysIRepo2.findAll();
		for(int i=0;i<systeme2.size();i++) {
			systeme2.get(i).setMasse(systeme2.get(i).getMasseInit());
			systeme2.get(i).setDiametre(systeme2.get(i).getDiametreInit());
			systeme2.get(i).setX(systeme2.get(i).getxInit());
			systeme2.get(i).setY(systeme2.get(i).getyInit());
			systeme2.get(i).setVx(systeme2.get(i).getVxInit());
			systeme2.get(i).setVy(systeme2.get(i).getVyInit());
			systeme2.get(i).setEtat(systeme2.get(i).isEtatInit());
			systeme2.get(i).setNom(systeme2.get(i).getNomInit());
			sysRepo.save(systeme2.get(i));
		}
		setSysteme(sysRepo.findAll());
		System.out.println(getSysteme());
		for(int i=0;i<getSysteme().size();i++) {
			System.out.println(getSysteme().get(i));
			Position p=new Position(0,getSysteme().get(i).getId(),getSysteme().get(i).getxInit(),getSysteme().get(i).getyInit());
			posRepo.save(p);
		}
	}

	public void affichageTrajectoire() throws IOException 
	{
		double [] x;
		double[] y;
		List<double []> lx= new ArrayList();
		List<double []> ly= new ArrayList();
		List<CorpsCeleste> systeme = sysRepo.findAll();
		List<Position> positions;
		for (CorpsCeleste c : systeme) { //parcours les corps du systemes et recuperes les listes de positions x et y
			positions = posRepo.findByIdCorpsCeleste(c.getId());
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
		
		int w = frame.getWidth();
		int h =frame.getHeight();
		BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		frame.print( g2d );
		g2d.dispose();
		java.io.FileOutputStream fos  = new java.io.FileOutputStream("src/trajectoire/trajectoire.png");
		ImageIO.write(image, "png", fos);
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

	public Integer getTimestep() {
		return timestep;
	}

	public boolean isCalculSimple() {
		return calculSimple;
	}

	public int getCtpt() {
		return ctpt;
	}

	public int getCpt() {
		return cpt;
	}

	public SystemeInitRepository getSysIRepo2() {
		return sysIRepo2;
	}

	public SystemeRepository getSysRepo() {
		return sysRepo;
	}

	public PositionsRepository getPosRepo() {
		return posRepo;
	}

	public List<CorpsCeleste> getSysteme2() {
		return systeme2;
	}

	public JFrame getTpt() {
		return tpt;
	}

	public JFrame getAvancement() {
		return avancement;
	}

	public void setTimestep(Integer timestep) {
		this.timestep = timestep;
	}

	public void setCalculSimple(boolean calculSimple) {
		this.calculSimple = calculSimple;
	}

	public void setCtpt(int ctpt) {
		this.ctpt = ctpt;
	}

	public void setCpt(int cpt) {
		this.cpt = cpt;
	}

	public void setSysIRepo2(SystemeInitRepository sysIRepo2) {
		this.sysIRepo2 = sysIRepo2;
	}

	public void setSysRepo(SystemeRepository sysRepo) {
		this.sysRepo = sysRepo;
	}

	public void setPosRepo(PositionsRepository posRepo) {
		this.posRepo = posRepo;
	}

	public void setSysteme2(List<CorpsCeleste> systeme2) {
		this.systeme2 = systeme2;
	}

	public void setTpt(JFrame tpt) {
		this.tpt = tpt;
	}

	public void setAvancement(JFrame avancement) {
		this.avancement = avancement;
	}

	public Simulation() {

	}
	
	
}
