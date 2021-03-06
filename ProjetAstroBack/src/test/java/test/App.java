package test;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import DAOjpa.DAOComptejpa;
import DAOjpa.DAOPositionsjpa;
import DAOjpa.DAOSystemeInitjpa;
import DAOjpa.DAOSystemejpa;

import metier.Compte;
import metier.CorpsCeleste;
import metier.Etoile;
import metier.Planete;
import metier.Satellite;
import metier.Simulation;
import metier.Utilisateur;

import util.Context;

public class App 
{
	static Compte connected;
	static DAOComptejpa daoC = new DAOComptejpa();
	static DAOSystemeInitjpa daoSI = new DAOSystemeInitjpa();
	static DAOSystemejpa daoS = new DAOSystemejpa();
	static DAOPositionsjpa daoP = new DAOPositionsjpa();
	static List<CorpsCeleste> systeme=new ArrayList();
	static List<CorpsCeleste> systeme2= new ArrayList();
	static boolean calculSimple;
	static JFrame tpt = new JFrame("Canard TPT");
	static JFrame avancement = new JFrame("Avancement");
	static int timestep;
	static int cpt=0;
	
	public static int saisieInt(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextInt();

	}
	
	public static double saisieDouble(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextDouble();
	}
	
	public static String saisieString(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextLine();
	}

	public static void main(String[] args) 
	{

		menuPrincipal();
		String miseAJour = saisieString("\nSouhaitezvous modifier votre systeme ?");
	}

	public static void menuPrincipal() 
	{// genere le menu principal et propose les options
		System.out.println("\nCree ton systeme solaire!");
		System.out.println("1- Se connecter");
		System.out.println("2- Creation d'un compte utilisateur");
		System.out.println("3- Fermer l'appli");
		int choix = saisieInt("Choisir un menu");
		switch(choix) 
		{
		case 1 : String login = saisieString("\nSaisir login"); String password = saisieString("Saisir password"); connected=daoC.seConnecter(login, password);break;
		case 2 : String loginNewAccount = saisieString("\nSaisir login"); String passwordNewAccount = saisieString("Saisir password"); Utilisateur user = new Utilisateur(loginNewAccount,passwordNewAccount);daoC.insert(user);System.out.println("Compte cr??????.");menuPrincipal();break;
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
	
	public static void menuUtilisateur() 
	{// genere le menu de l'utilisateur et propose les options
		System.out.println("\nMenu");
		System.out.println("1- Creer un systeme");
		System.out.println("2- Charger un systeme");
		System.out.println("3- Retour a l'etat inital");
		System.out.println("99- Se deconnecter");
		int choix = saisieInt("Choisir un menu");

		switch(choix) 
		{
		case 1 : daoSI.deleteAll();creerEtoile();break;
		case 2 : chargerSysteme();break;
		case 3 : retourT0();break;
		case 99 : connected=null;menuPrincipal();break;
		}
		menuUtilisateur();
	}

	public static void menuModifier() 
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
		case 3 : creerPlanete((Etoile) daoSI.findById(1));break;
		case 4 : modifSatellite();break;
		case 5 : creerSatellite();break;	
		case 6: menuUtilisateur();break;
		}
		menuUtilisateur();	
	}
		
	public static void creerEtoile() 
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
		e = (Etoile) daoSI.insert(e);
		Context.getInstance().setEtoile(e);
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

	public static void creerPlanete(Etoile e)
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
		Double x0Planete=saisieDouble("Saisir la position x0 de la planete (en km par rapport ??? l'etoile)");
		Double y0Planete=saisieDouble("Saisir la position y0 de la planete (en km par rapport ??? l'etoile)");
		Double vitX0Planete=saisieDouble("Saisir la vitesse selon l'axe x de la planete (en km/s par rapport ??? l'etoile)");
		Double vitY0Planete=saisieDouble("Saisir la vitesse selon l'axe y de la planete (en km/s par rapport ??? l'etoile)");

		Planete p = new Planete(massePlanete,diametrePlanete,x0Planete,y0Planete,vitX0Planete,vitY0Planete,nomPlanete,Context.getInstance().getEtoile());
		systeme.add(p);
		daoSI.insert(p);

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
				Double x0Satellite=saisieDouble("Saisir la position x0 du satellite (en km par rapport ??? l'etoile)");
				Double y0Satellite=saisieDouble("Saisir la position y0 du satellite (en km par rapport ??? l'etoile)");
				Double vitX0Satellite=saisieDouble("Saisir la vitesse selon l'axe x du satellite (en km/s par rapport ??? l'etoile)");
				Double vitY0Satellite=saisieDouble("Saisir la vitesse selon l'axe y du satellite (en km/s par rapport ??? l'etoile)");

				CorpsCeleste pSelect = daoSI.findById(idPlaneteMere);
				Satellite s = new Satellite(masseSatellite, diametreSatellite, x0Satellite, y0Satellite, vitX0Satellite, vitY0Satellite, nomSatellite, pSelect);
				systeme.add(s);
				daoSI.insert(s);		
			}
		}
	}
	
	public static void creerSatellite()
	{ // cree un satellite
		for (int i=0;i<systeme.size();i++)
		{
			if(systeme.get(i) instanceof Planete) 
			{
				System.out.println(systeme.get(i));
			}
		}
	    int selectIdPlanet = saisieInt("A quelle planete voulez vous ajouter un satellite (id)?");
	    
	    Planete p = (Planete) daoSI.findById(selectIdPlanet);

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

	public static void modifEtoile(CorpsCeleste e) 
	{
		
		System.out.println(e);
		String choixModif = saisieString("que voulez vous modifier ? (nom/masse/diametre)");
		if (choixModif.equalsIgnoreCase("nom")) 
		{
			e.setNom(saisieString("Saisissez un nouveau nom"));
			daoSI.update(e);
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
			daoSI.update(e);
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
			daoSI.update(e);
			chargerSysteme();
		}
	}
	
	public static void modifPlanete() 
	{		
		for (int i=0;i<systeme.size();i++)
		{
			if(systeme.get(i) instanceof Planete) 
			{
				System.out.println(systeme.get(i));
			}
		}
	    int selectId = saisieInt("Quelle planete voulez-vous modifier (id)?");
	    
	    Planete p = (Planete) daoSI.findById(selectId);
	    
		String choixModif = saisieString("que voulez vous modifier ? attribut(nom/masse/diametre/positionx/positiony/vitessex/vitessey/satellite) ou (suppression)");
		if (choixModif.equalsIgnoreCase("nom"))
		{
			p.setNom(saisieString("Saisissez un nouveau nom"));
			daoSI.update(p);
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
			daoSI.update(p);
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
			daoSI.update(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("positionx"))
		{
			p.setX(saisieDouble("Saisissez une nouvelle position en x par rapport a l'etoile"));
			daoSI.update(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("positiony"))
		{
			p.setY(saisieDouble("Saisissez une nouvelle position en y par rapport a l'etoile"));
			daoSI.update(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("vitessex"))
		{
			p.setVx(saisieDouble("Saisissez une nouvelle vitesse en x par rapport a l'etoile"));
			daoSI.update(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("vitessey"))
		{
			p.setVy(saisieDouble("Saisissez une nouvelle vitesse en y par rapport a l'etoile"));
			daoSI.update(p);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("suppression"))
		{
			daoSI.delete(selectId);
			chargerSysteme();
		}
	}
	
	public static void modifSatellite() 
	{
		for (int i=0;i<systeme.size();i++)
		{
			if(systeme.get(i) instanceof Satellite) 
			{
				System.out.println(systeme.get(i));
			}
		}
	    int selectId = saisieInt("Quel satellite voulez-vous modifier (id)?");
	    
	    Satellite s = (Satellite) daoSI.findById(selectId);
			    
		String choixModif = saisieString("que voulez vous modifier ? attribut(nom/masse/diametre/positionx/positiony/vitessex/vitessey) ou (suppression)");
		if (choixModif.equalsIgnoreCase("nom"))
		{
			s.setNom(saisieString("Saisissez un nouveau nom"));
			daoSI.update(s);
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
			daoSI.update(s);
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
			daoSI.update(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("positionx"))
		{
			s.setX(saisieDouble("Saisissez une nouvelle position en x par rapport a l'etoile"));
			daoSI.update(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("positiony"))
		{
			s.setY(saisieDouble("Saisissez une nouvelle position en y par rapport a l'etoile"));
			daoSI.update(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("vitessex"))
		{
			s.setVx(saisieDouble("Saisissez une nouvelle vitesse en x par rapport a l'etoile"));
			daoSI.update(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("vitessey"))
		{
			s.setVy(saisieDouble("Saisissez une nouvelle vitesse en y par rapport a l'etoile"));
			daoSI.update(s);
			chargerSysteme();
		}
		else if (choixModif.equalsIgnoreCase("suppression"))
		{
			daoSI.delete(selectId);
			chargerSysteme();
		}
	}
	
	public static void simulation2() 
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
		Simulation sim = new Simulation(timestep,calculSimple);
	    //System.out.println(sim.getSysteme());
		sim.run();
	}
	
	public static void chargerSysteme()
	{
		retourT0();
		systeme=daoSI.findAll();
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
	
	public static void retourT0() 
	{//supprime les bdd de systeme et positions et r???initialise ??? t0
		daoS.deleteAll();
		daoP.deleteAll();
	}
	
	public static void supprimerSimu() 
	{//supprime les bdd de systeme, positions et systeme init
		daoS.deleteAll();
		daoSI.deleteAll();
		daoP.deleteAll();
	}
}
	





