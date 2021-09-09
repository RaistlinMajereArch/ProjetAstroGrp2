package SoprAjc.ProjetAstroSpringBoot;

import java.io.IOException;
import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;

import astro.metier.Compte;
import astro.metier.CorpsCeleste;
import astro.metier.Etoile;
import astro.metier.Planete;
import astro.metier.Satellite;
import astro.metier.Simulation;
import astro.metier.Utilisateur;
import astro.repositories.CompteRepository;
import astro.repositories.PositionsRepository;
import astro.repositories.SystemeInitRepository;
import astro.repositories.SystemeRepository;


public class AppSpring {
	static Compte connected;
	@Autowired
	CompteRepository cptRepo;
	@Autowired
	SystemeInitRepository sysIRepo;
	@Autowired
	SystemeRepository sysRepo;
	@Autowired
	PositionsRepository posRepo;
	
	@Autowired 
	Simulation sim;
	
	static List<CorpsCeleste> systeme=new ArrayList();
	static List<CorpsCeleste> systeme2= new ArrayList();
	static boolean calculSimple;
	static JFrame tpt = new JFrame("Canard TPT");
	static JFrame avancement = new JFrame("Avancement");
	static int timestep;
	static int cpt=0;
	
	public int saisieInt(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextInt();

	}
	
	public double saisieDouble(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextDouble();
	}
	
	public String saisieString(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextLine();
	}

	public void run(String[] args) throws IOException 
	{

		menuPrincipal();
		String miseAJour = saisieString("\nSouhaitezvous modifier votre systeme ?");
	}

	public void menuPrincipal() throws IOException 
	{// genere le menu principal et propose les options
		System.out.println("\nCree ton systeme solaire!");
		System.out.println("1- Se connecter");
		System.out.println("2- Creation d'un compte utilisateur");
		System.out.println("3- Fermer l'appli");
		int choix = saisieInt("Choisir un menu");
		switch(choix) 
		{
		case 1 : String login = saisieString("\nSaisir login"); String password = saisieString("Saisir password"); Optional<Compte> compteConnex = cptRepo.findByLoginAndPassword(login, password);connected=compteConnex.get();break;
		case 2 : String loginNewAccount = saisieString("\nSaisir login"); String passwordNewAccount = saisieString("Saisir password"); Utilisateur user = new Utilisateur(loginNewAccount,passwordNewAccount);cptRepo.save(user);System.out.println("Compte cree");menuPrincipal();break;
		case 3 : System.exit(0);break;
		}		
		if(connected instanceof Utilisateur)
		{
			menuUtilisateur();
		}
		else 
		{
			System.out.println("Identifiants invalides !");
			menuPrincipal();
		}
	}
	
	public void menuUtilisateur() throws IOException 
	{// genere le menu de l'utilisateur et propose les options
		System.out.println("\nMenu");
		System.out.println("1- Creer un systeme");
		System.out.println("2- Charger un systeme");
		System.out.println("3- Retour a l'etat inital");
		System.out.println("99- Se deconnecter");
		int choix = saisieInt("Choisir un menu");

		switch(choix) 
		{
		case 1 : sysIRepo.deleteAll();creerEtoile();break;
		case 2 : chargerSysteme();break;
		case 3 : retourT0();break;
		case 99 : connected=null;menuPrincipal();break;
		}
		menuUtilisateur();
	}

	public void menuModifier() throws IOException 
	{
		System.out.println("\nModifier un corps");
		System.out.println("1- Modifier une etoile");
		System.out.println("2- Modifier une planete");
		System.out.println("3- Ajout d'une planete");
		System.out.println("4- Modifier un satellite");
		System.out.println("5- Ajout d'un satellite (WIP)");
		System.out.println("6- Revenir en arriere");
		int choix = saisieInt("Choisir un menu");
		switch(choix) 
		{
		case 1 : for (int i=0;i<systeme.size();i++){if(systeme.get(i) instanceof Etoile) {modifEtoile(systeme.get(i));}} ;break;
		case 2 : modifPlanete();break;
		case 3 : Optional<CorpsCeleste> opt = (Optional<CorpsCeleste>) sysIRepo.findById(1);Etoile e=(Etoile) opt.get();creerPlanete(e);break;
		case 4 : modifSatellite();break;
		case 5 : creerSatellite();break;	
		case 6: menuUtilisateur();break;
		}
		menuUtilisateur();	
	}
		
	public void creerEtoile() 
	{
		// cree une etoile au centre du systeme, immobile
		String nomEtoile = saisieString("\nSaisir le nom de l'etoile");
		Double masseEtoile=0d;
		Double diametreEtoile=0.0;
		boolean masseEtoileOk = false;
		boolean diametreEtoileOk = false;
		while (!masseEtoileOk)
		{
			masseEtoile = saisieDouble("Saisir la masse de l'etoile (en kg)");
			if (masseEtoile <= 0d) 
			{
				System.out.println("La masse de l'etoile est incorrecte");
			} 
			else 
			{
				masseEtoileOk=true;		
			}
		}
		while (!diametreEtoileOk)
		{
			diametreEtoile = saisieDouble("Saisir le diametre de l'etoile (en km)");
			if (diametreEtoile <= 0d) 
			{
				System.out.println("Le diametre de l'etoile est incorrect");
			} 
			else 
			{
				diametreEtoileOk=true;		
			}
		}
		Etoile e = new Etoile(masseEtoile, diametreEtoile, nomEtoile);
		systeme.add(e);
		e = (Etoile) sysIRepo.save(e);
		boolean userHasFinished = false;
		boolean userIsCreating = true;
		while (userIsCreating) 
		{
			String message = "";
			if (userHasFinished)
			{
				message = "Voulez-vous ajouter une nouvelle planete ? (y/n)";
			} else {
				message = "Ajouter une planete? (y/n)";
			}
			userHasFinished = true;
			String continuerOuiNon = saisieString(message);
			if (!continuerOuiNon.equalsIgnoreCase("y")) 
			{
				userIsCreating = false;
			} 
			else
			{
				creerPlanete(e);
			}
		}
	}

	public void creerPlanete(Etoile e)
	{ // cree une planete et ses eventuels satellites
		String nomPlanete=saisieString("Saisir le nom de la planete");
		boolean massePlaneteOk = false;
		Double massePlanete = e.getMasse();
		while (!massePlaneteOk) 
		{
			massePlanete = saisieDouble("Saisir la masse de la planete (en kg)");
			if (massePlanete >= e.getMasse() || massePlanete <= 0d) 
			{
				System.out.println("La masse de la planete est incorrecte");
			} 
			else 
			{
				massePlaneteOk=true;		
			}
		}
		boolean diametrePlaneteOk = false;
		Double diametrePlanete= 0d;
		while (!diametrePlaneteOk) 
		{
			diametrePlanete= saisieDouble("Saisir le diametre de la planete (en km)");
			if (diametrePlanete <= 0d) 
			{
				System.out.println("Le diametre de la planete est incorrect");
			} 
			else 
			{
				diametrePlaneteOk=true;		
			}
		}
		Double x0Planete=saisieDouble("Saisir la position x0 de la planete (en km par rapport a l'etoile)");
		Double y0Planete=saisieDouble("Saisir la position y0 de la planete (en km par rapport a l'etoile)");
		Double vitX0Planete=saisieDouble("Saisir la vitesse selon l'axe x de la planete (en km/s par rapport a l'etoile)");
		Double vitY0Planete=saisieDouble("Saisir la vitesse selon l'axe y de la planete (en km/s par rapport a l'etoile)");

		Planete p = new Planete(massePlanete,diametrePlanete,x0Planete,y0Planete,vitX0Planete,vitY0Planete,nomPlanete,e);
		systeme.add(p);
		sysIRepo.save(p);

		boolean satelliteIsPicked = true;
		boolean satelliteIsCreated = false;;
		while (satelliteIsPicked) 
		{
			String satelliteOuiNon = "";
			if (satelliteIsCreated) 
			{
				satelliteOuiNon = saisieString("\nVoulez vous ajouter d'autres Satellites a cette planete ? (y/n)");
			} 
			else 
			{
				satelliteOuiNon = saisieString("\nVoulez vous ajouter un/des Satellites a cette planete ? (y/n)");
			}
			satelliteIsCreated = true;
			if (!satelliteOuiNon.equalsIgnoreCase("y")) 
			{
				satelliteIsPicked = false;
			} 
			else 
			{
				boolean masseSatelliteOk = false;
				boolean diametreSatelliteOk = false;
				Double masseSatellite = 0d;
				Double diametreSatellite = 0d;
				int idPlaneteMere = saisieInt("\nSaisir l'id de la planete autour de laquelle le satellite orbitera ");
				String nomSatellite = saisieString("\nSaisir le nom du satellite'");
				while (!masseSatelliteOk) {
					masseSatellite = saisieDouble("Saisir la masse du satellite (en kg)");
					if (masseSatellite >= p.getMasse() || massePlanete <= 0d)
					{
						System.out.println("La masse du satellite est incorrecte");
					} 
					else 
					{
						masseSatelliteOk=true;		
					}
				}
				while (!diametreSatelliteOk) 
				{
					diametreSatellite = saisieDouble("Saisir le diametre du satellite (en km)");
					if (diametreSatellite <= 0d) 
					{
						System.out.println("Le diametre du satellite est incorrect");
					}
					else 
					{
						diametreSatelliteOk=true;		
					}
				}
				Double x0Satellite=saisieDouble("Saisir la position x0 du satellite (en km par rapport � l'etoile)");
				Double y0Satellite=saisieDouble("Saisir la position y0 du satellite (en km par rapport � l'etoile)");
				Double vitX0Satellite=saisieDouble("Saisir la vitesse selon l'axe x du satellite (en km/s par rapport � l'etoile)");
				Double vitY0Satellite=saisieDouble("Saisir la vitesse selon l'axe y du satellite (en km/s par rapport � l'etoile)");
				
				Optional<CorpsCeleste> opt = (Optional<CorpsCeleste>) sysIRepo.findById(idPlaneteMere);
			    CorpsCeleste pSelect= opt.get();
			    
				Satellite s = new Satellite(masseSatellite, diametreSatellite, x0Satellite, y0Satellite, vitX0Satellite, vitY0Satellite, nomSatellite, pSelect);
				systeme.add(s);
				sysIRepo.save(s);		
			}
		}
	}
	
	public void creerSatellite()
	{ // cree un satellite
		for (int i=0;i<systeme.size();i++)
		{
			if(systeme.get(i) instanceof Planete) 
			{
				System.out.println(systeme.get(i));
			}
		}
	    int selectIdPlanet = saisieInt("A quelle planete voulez vous ajouter un satellite (id)?");
	    
	    Optional<CorpsCeleste> opt = (Optional<CorpsCeleste>) sysIRepo.findById(selectIdPlanet);
	    Planete p=(Planete) opt.get();

		String nomSatellite=saisieString("Saisir le nom du satellite");
		boolean masseSatelliteOk = false;
		Double masseSatellite = p.getMasse();
		while (!masseSatelliteOk) 
		{
			masseSatellite = saisieDouble("Saisir la masse du satellite (en kg)");
			if (masseSatellite >= p.getMasse() || masseSatellite <= 0d) 
			{
				System.out.println("La masse du satellite est incorrecte");
			} else 
			{
				masseSatelliteOk=true;		
			}
		}
		boolean diametreSatelliteOk = false;
		Double diametreSatellite= 0d;
		while (!diametreSatelliteOk) 
		{
			diametreSatellite= saisieDouble("Saisir le diametre du satellite (en km)");
			if (diametreSatellite <= 0d) 
			{
				System.out.println("Le diametre du satellite est incorrect");
			} 
			else 
			{
				diametreSatelliteOk=true;		
			}
		}
		//Double x0Satellite=saisieDouble("Saisir la position x0 du satellite (en km par rapport a la planete)");
		//Double y0Satellite=saisieDouble("Saisir la position y0 du satellite (en km par rapport a la planete)");
		//Double vitX0Satellite=saisieDouble("Saisir la vitesse selon l'axe x du satellite (en km/s par rapport a la planete)");
		//Double vitY0Satellite=saisieDouble("Saisir la vitesse selon l'axe y du satellite (en km/s par rapport a la planete)");

		//Satellite s = new Satellite(masseSatellite,diametreSatellite,x0Satellite,y0Satellite,vitX0Satellite,vitY0Satellite,nomSatellite,selectIdPlanet);
		//systeme.add(s);
		//daoSI.insert(s);
	}

	public void modifEtoile(CorpsCeleste e) throws IOException 
	{
		
		System.out.println(e);
		String choixModif = saisieString("que voulez vous modifier ? (nom/masse/diametre)");
		if (choixModif.equalsIgnoreCase("nom")) 
		{
			e.setNom(saisieString("Saisissez un nouveau nom"));
			sysIRepo.save(e);
			chargerSysteme();			
		} 
		else if (choixModif.equalsIgnoreCase("masse")) 
		{
			boolean masseEtoileOk = false;
			Double nouvelleMasseEtoile = 0d;
			while (!masseEtoileOk) 
			{
				nouvelleMasseEtoile= saisieDouble("Saisissez une nouvelle masse");
				if (nouvelleMasseEtoile <= 0d) 
				{
					System.out.println("La masse de l'etoile est incorrecte");
				} 
				else 
				{
					masseEtoileOk=true;		
				}
			}
			e.setMasse(nouvelleMasseEtoile);
			sysIRepo.save(e);
			chargerSysteme();
		} 
		else if (choixModif.equalsIgnoreCase("diametre"))
		{
			boolean diametreEtoileOk = false;
			Double nouveauDiametreEtoile = 0d;
			while (!diametreEtoileOk) 
			{
				nouveauDiametreEtoile = saisieDouble("Saisir le diametre de l'etoile");
				if (nouveauDiametreEtoile <= 0d) 
				{
					System.out.println("Le diametre de l'etoile est incorrect");
				} 
				else 
				{
					diametreEtoileOk = true;		
				}
			}		
			e.setDiametre(nouveauDiametreEtoile);
			sysIRepo.save(e);
			chargerSysteme();
		}
	}
	
	public void modifPlanete() throws IOException 
	{		
		for (int i=0;i<systeme.size();i++)
		{
			if(systeme.get(i) instanceof Planete) 
			{
				System.out.println(systeme.get(i));
			}
		}
	    int selectId = saisieInt("Quelle planete voulez-vous modifier (id)?");
	    
	    Optional<CorpsCeleste> opt = (Optional<CorpsCeleste>) sysIRepo.findById(selectId);
	    Planete p=(Planete) opt.get();
	    
		String choixModif = saisieString("que voulez vous modifier ? attribut(nom/masse/diametre/positionx/positiony/vitessex/vitessey/satellite) ou (suppression)");
		if (choixModif.equalsIgnoreCase("nom"))
		{
			p.setNom(saisieString("Saisissez un nouveau nom"));
			sysIRepo.save(p);
			chargerSysteme();
			
		} 
		else if (choixModif.equalsIgnoreCase("masse")) 
		{
			boolean massePlaneteOk = false;
			Double nouvelleMassePlanete = 0d;
			while (!massePlaneteOk) {
				nouvelleMassePlanete= saisieDouble("Saisissez une nouvelle masse");
				if (nouvelleMassePlanete <= 0d) 
				{
					System.out.println("La masse de la planete est incorrecte");
				} 
				else 
				{
					massePlaneteOk=true;		
				}
			}
			p.setMasse(nouvelleMassePlanete);
			sysIRepo.save(p);
			chargerSysteme();	
		} 
		else if (choixModif.equalsIgnoreCase("diametre"))
		{
			boolean diametrePlaneteOk = false;
			Double nouveauDiametrePlanete = 0d;
			while (!diametrePlaneteOk) 
			{
				nouveauDiametrePlanete = saisieDouble("Saisir le diametre de la planete");
				if (nouveauDiametrePlanete <= 0d) 
				{
					System.out.println("Le diametre de la planete est incorrect");
				} 
				else 
				{
					diametrePlaneteOk = true;		
				}
			}		
			p.setDiametre(nouveauDiametrePlanete);
			sysIRepo.save(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("positionx"))
		{
			p.setX(saisieDouble("Saisissez une nouvelle position en x par rapport a l'etoile"));
			sysIRepo.save(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("positiony"))
		{
			p.setY(saisieDouble("Saisissez une nouvelle position en y par rapport a l'etoile"));
			sysIRepo.save(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("vitessex"))
		{
			p.setVx(saisieDouble("Saisissez une nouvelle vitesse en x par rapport a l'etoile"));
			sysIRepo.save(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("vitessey"))
		{
			p.setVy(saisieDouble("Saisissez une nouvelle vitesse en y par rapport a l'etoile"));
			sysIRepo.save(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("suppression"))
		{
			sysIRepo.deleteById(selectId);
			chargerSysteme();
		}
	}
	
	public void modifSatellite() throws IOException 
	{
		for (int i=0;i<systeme.size();i++)
		{
			if(systeme.get(i) instanceof Satellite) 
			{
				System.out.println(systeme.get(i));
			}
		}
	    int selectId = saisieInt("Quel satellite voulez-vous modifier (id)?");
	    
	    Optional<CorpsCeleste> opt = (Optional<CorpsCeleste>) sysIRepo.findById(selectId);
	    Satellite s= (Satellite) opt.get();
			    
		String choixModif = saisieString("que voulez vous modifier ? attribut(nom/masse/diametre/positionx/positiony/vitessex/vitessey) ou (suppression)");
		if (choixModif.equalsIgnoreCase("nom"))
		{
			s.setNom(saisieString("Saisissez un nouveau nom"));
			sysIRepo.save(s);
			chargerSysteme();
			
		} 
		else if (choixModif.equalsIgnoreCase("masse")) 
		{
			boolean masseSatelliteOk = false;
			Double nouvelleMasseSatellite = 0d;
			while (!masseSatelliteOk) 
			{
				nouvelleMasseSatellite= saisieDouble("Saisissez une nouvelle masse");
				if (nouvelleMasseSatellite <= 0d)
				{
					System.out.println("La masse du satellite est incorrecte");
				} 
				else 
				{
					masseSatelliteOk=true;		
				}
			}
			s.setMasse(nouvelleMasseSatellite);
			sysIRepo.save(s);
			chargerSysteme();			
		} 
		else if (choixModif.equalsIgnoreCase("diametre"))
		{
			boolean diametreSatelliteOk = false;
			Double nouveauDiametreSatellite = 0d;
			while (!diametreSatelliteOk) 
			{
				nouveauDiametreSatellite = saisieDouble("Saisir le diametre du satellite");
				if (nouveauDiametreSatellite <= 0d) 
				{
					System.out.println("Le diametre deu satellite est incorrect");
				} 
				else 
				{
					diametreSatelliteOk = true;		
				}
			}		
			s.setDiametre(nouveauDiametreSatellite);
			sysIRepo.save(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("positionx"))
		{
			s.setX(saisieDouble("Saisissez une nouvelle position en x par rapport a l'etoile"));
			sysIRepo.save(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("positiony"))
		{
			s.setY(saisieDouble("Saisissez une nouvelle position en y par rapport a l'etoile"));
			sysIRepo.save(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("vitessex"))
		{
			s.setVx(saisieDouble("Saisissez une nouvelle vitesse en x par rapport a l'etoile"));
			sysIRepo.save(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("vitessey"))
		{
			s.setVy(saisieDouble("Saisissez une nouvelle vitesse en y par rapport a l'etoile"));
			sysIRepo.save(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("suppression"))
		{
			sysIRepo.deleteById(selectId);
			chargerSysteme();
		}
	}
	
	public void simulation2() throws IOException 
	{
		timestep=saisieInt("Saisissez le nombre de timestep pour votre simulation (1 timestep=1jour) :");
		String Calcul=saisieString("Voulez vous des calculs simplifies ? (y/n)");
		if (Calcul.equals("n")) 
		{
			calculSimple = false;
		}
		else {
			calculSimple = true;
		}
		sim.setTimestep(timestep);
		sim.setCalculSimple(calculSimple);
		//Simulation sim = new Simulation(timestep,calculSimple);
	    //System.out.println(sim.getSysteme());
		sim.run();
	}
	
	
	public void chargerSysteme() throws IOException
	{
		retourT0();
		systeme=sysIRepo.findAll();
		String modifChoix = saisieString("Voulez vous modifier votre systeme (y/n)?");
		if (modifChoix.equalsIgnoreCase("n")) 
		{
			simulation2();
		} 
		else 
		{
			menuModifier();
		}
	}
	
	public void retourT0() 
	{//supprime les bdd de systeme et positions et r�initialise � t0
		sysRepo.deleteAll();
		posRepo.deleteAll();
	}
	
	public void supprimerSimu() 
	{//supprime les bdd de systeme, positions et systeme init
		sysRepo.deleteAll();
		sysIRepo.deleteAll();
		posRepo.deleteAll();
	}
	
}
