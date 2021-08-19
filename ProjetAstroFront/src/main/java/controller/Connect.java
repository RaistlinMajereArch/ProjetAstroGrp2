package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOjpa.DAOComptejpa;
import metier.Admin;
import metier.Compte;

@WebServlet("/connect")
public class Connect extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOComptejpa daoC = new DAOComptejpa();
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		Compte c = daoC.seConnecter(login, password);
	
		if(c instanceof Admin) 
		{
			this.getServletContext().getRequestDispatcher("/admin.html").forward(request, response);
		}
		else 
		{
			this.getServletContext().getRequestDispatcher("/connect.html").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOComptejpa daoC = new DAOComptejpa();
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		Compte c = daoC.seConnecter(login, password);
		
		if(c instanceof Admin) 
		{
			this.getServletContext().getRequestDispatcher("/admin.html").forward(request, response);
		}
		else 
		{
			this.getServletContext().getRequestDispatcher("/connect.html").forward(request, response);
		}
	}
}
