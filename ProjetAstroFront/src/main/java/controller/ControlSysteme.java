package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//Controleur pour lancer la simulation, il faut creer une classe simulation dans le front (creee mais pas finie) qui sera appelee ici
//iil faudra creer des requetes quii demandent le timestep et le type de calcul a l'utilisateur une fois qu'il a valide son syeteme
//renvoyer sur la page wait qui va perdiodiquement checker s le calcul est fini, si oui, elle redirige vers la page resultat qui appelle le jsp result



public class ControlSysteme {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int timestep=Integer.parseInt(request.getParameter("timestep"));
		boolean calculSimple = Boolean.parseBoolean(request.getParameter("calculSimple"));
		
		Simulation simu = new Simulation();
		simu.start(timestep, calculSimple);
	}

}
