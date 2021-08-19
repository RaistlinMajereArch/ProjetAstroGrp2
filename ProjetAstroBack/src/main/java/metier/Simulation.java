package metier;

public class Simulation {
	
	private Integer timestep;
	private boolean calculSimple;
	
	public void start(int timestep, boolean calculSimple) {
		this.timestep = timestep;
		this.calculSimple = calculSimple;
		run();
	}
	
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
			for(int i=0;i<systeme.size();i++) {
				daoS.update(systeme.get(i));
			}
			for(int i=0;i<systeme.size();i++) {
				Position p=new Position(t,systeme.get(i).getId(),systeme.get(i).getX(),systeme.get(i).getY());
				daoP.insert(p);
			}
		}
		affichageTrajectoire();
	}

}
