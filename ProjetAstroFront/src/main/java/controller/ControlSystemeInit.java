package controller;

import java.io.IOException; 
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Context;
import DAO.IDAOSystemeInit;
import DAOjpa.DAOSystemeInitjpa;
import metier.CorpsCeleste;
import metier.Etoile;
import metier.Planete;
import metier.Satellite;

@WebServlet("/systeminit")
public class ControlSystemeInit extends HttpServlet {
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IDAOSystemeInit daoSI = Context.getInstance().getDaoSI();
		List<CorpsCeleste> corps = daoSI.findAll();

		request.setAttribute("systeminit", corps);
		this.getServletContext().getRequestDispatcher("/Modification.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		IDAOSystemeInit daoSI = Context.getInstance().getDaoSI();
		if(request.getParameter("type_form").equals("PUT"))
		{
			CorpsCeleste e= null;
			String id=request.getParameter("id");
			
			if (request.getParameter("type").equals("etoile")) {
				e = new Etoile();
				e = (Etoile) daoSI.findById(Integer.parseInt(id));
			}
			else if (request.getParameter("type").equals("planete")) {
				e = new Planete();
				e = (Planete) daoSI.findById(Integer.parseInt(id));
			}
			else if (request.getParameter("type").equals("satellite")) {
				e = new Satellite();
				e = (Satellite) daoSI.findById(Integer.parseInt(id));
			}
			
			e.setNomInit(request.getParameter("nom"));
			e.setMasseInit(Double.valueOf(request.getParameter("masse")));
			e.setDiametreInit(Double.valueOf(request.getParameter("diametre")));
			e.setxInit(Double.valueOf(request.getParameter("x0")));
			e.setyInit(Double.valueOf(request.getParameter("y0")));
			e.setVxInit(Double.valueOf(request.getParameter("vx0")));
			e.setVyInit(Double.valueOf(request.getParameter("vy0")));
			e.setVyInit(Double.valueOf(request.getParameter("vy0")));
			e.setParent((CorpsCeleste) request.getAttribute("parent"));
			e.setEtatInit(Boolean.valueOf(request.getParameter("etat")));
			daoSI.update(e);
		}
		else if(request.getParameter("type_form").equals("DELETE"))
		{
			String id=request.getParameter("id");
			daoSI.delete(Integer.parseInt(id));
		}
		else if(request.getParameter("type_form").equals("POST"))
		{
			CorpsCeleste e= null;
			//int id=Integer.valueOf(request.getParameter("id"));
			String nom=request.getParameter("nom");
			double masse=Double.valueOf(request.getParameter("masse"));
			double diametre=Double.valueOf(request.getParameter("diametre"));
			double x0=Double.valueOf(request.getParameter("x0"));
			double y0=Double.valueOf(request.getParameter("y0"));
			double vx0=Double.valueOf(request.getParameter("vx0"));
			double vy0=Double.valueOf(request.getParameter("vy0"));
			
					//(CorpsCeleste) request.getAttribute("parent");
			Boolean etat = Boolean.valueOf(request.getParameter("etat"));
			if (request.getParameter("type").equals("etoile")) {
				e = new Etoile(masse,diametre,nom);
			}
			else if (request.getParameter("type").equals("planete")) {
				CorpsCeleste id_parent=daoSI.findById(Integer.valueOf(request.getParameter("parent_id")));
				e = new Planete(masse,diametre,x0,y0,vx0,vy0,nom,id_parent);
			}
			else if (request.getParameter("type").equals("satellite")) {
				CorpsCeleste id_parent=daoSI.findById(Integer.valueOf(request.getParameter("parent_id")));
				e = new Satellite(masse,diametre,x0,y0,vx0,vy0,nom,id_parent);
			}
			daoSI.insert(e);
			
		}
		doGet(request, response);
	}

}
