package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.IDAOSystemeInit;
import metier.CorpsCeleste;
import util.Context;

@WebServlet("/filter")

public class ControlFilter extends HttpServlet {


	private IDAOSystemeInit daoSI = Context.getInstance().getDaoSI();

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		filterCorps(request,response);

	}

	
	
	private void filterCorps(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mot = request.getParameter("search");
		List<CorpsCeleste> corpsCeleste = daoSI.filter(mot);
		for(CorpsCeleste corps : corpsCeleste) 
		{
		
			response.getWriter().println("<tr>");
			response.getWriter().println("<td>"+corps.getId()+"</td>");
			response.getWriter().println("<td>"+corps.getNom()+"</td>");
			response.getWriter().println("<td>"+corps.getMasse()+"</td>");
			response.getWriter().println("<td>"+corps.getDiametre()+"</td>");
			response.getWriter().println("<td>"+corps.getX()+"</td>");
			response.getWriter().println("<td>"+corps.getY()+"</td>");
			response.getWriter().println("<td>"+corps.getVx()+"</td>");
			response.getWriter().println("<td>"+corps.getVy()+"</td>");
			response.getWriter().println("<td>"+corps.getParent()+"</td>");
			response.getWriter().println("<td>");
			response.getWriter().println("<input onclick=\"updatePlan(${corps.id},'${corps.nom}','${corps.masse}','${corps.diametre}','${corps.x}','${corps.y}','${corps.vx}','${corps.vy}','${corps.parent}')\" type=\"button\" class =\"btn btn-warning\" value=\"Modifier\">\n");
			response.getWriter().println("<input onclick=\"deletePlan("+corps.getId()+")\" type=\"button\" class =\"btn btn-danger\" value=\"Supprimer\">");
			response.getWriter().println("</td>");
			response.getWriter().println("</tr>");
		}
	}


}


