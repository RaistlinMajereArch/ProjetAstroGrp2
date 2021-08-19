package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.Context;
import DAO.IDAOSystemeInit;

@WebServlet("/systeminit")
public class ControlSystemeInit {
	
	private IDAOSystemeInit daoSI = Context.getInstance().getDaoSI();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<CorpsCeleste> corps = daoSI.findAll();

		request.setAttribute("systeminit", corps);
		this.getServletContext().getRequestDispatcher("/Modification.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("type_form").equals("PUT"))
		{
			String id=request.getParameter("id");
			CorpsCeleste e = daoSI.findById(Integer.parseInt(id));
			e.setNom(request.getParameter("nom"));
			e.setType(request.getParameter("type"));
			e.setMasse(request.getParameter("masse"));
			e.setDiametre(request.getParameter("diametre"));
			e.setX0(request.getParameter("x0"));
			e.setY0(request.getParameter("y0"));
			e.setVx0(request.getParameter("vx0"));
			e.setVy0(request.getParameter("vy0"));
			daoSI.update(e);
		}
		else if(request.getParameter("type_form").equals("DELETE"))
		{
			String id=request.getParameter("id");
			daoSI.delete(Integer.parseInt(id));
		}
		else if(request.getParameter("type_form").equals("POST"))
		{
			String nom=request.getParameter("nom");
			String type=request.getParameter("type");
			String masse=request.getParameter("masse");
			String diametre=request.getParameter("diametre");
			String x0=request.getParameter("x0");
			String y0=request.getParameter("y0");
			String vx0=request.getParameter("vx0");
			String vy0=request.getParameter("vy0");
			String id_parent=request.getParameter("id_parent");
			CorpsCeleste e = new CorpsCeleste(nom,type,masse,diametre,x0,y0,vx0,vy0,id_parent);
			daoSI.insert(e);
			
		}
		doGet(request, response);
	}

}
