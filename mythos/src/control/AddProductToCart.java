package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.*;
import manager.OrdinationManager;
import utility.ParametersCheck;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/AddProductToCart")
public class AddProductToCart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		// se l'utente e' loggato
		if (u != null && u.getRole().getIdRole() == 1) {

			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			
			String id=request.getParameter("id");
			String qua=request.getParameter("quant");
			try {
				ParametersCheck.checkAddToProductToCartParameters(qua);
			}catch(IllegalArgumentException e) {
				response.getWriter().print(e.getMessage());
				return;
			}
			
			int idProd = Integer.parseInt(id);
			int qta = Integer.parseInt(qua);
			
			Cart cart = (Cart) request.getSession().getAttribute("cartList");
			/*
			 * if(cartList==null) { cartList= new Cart(); }
			 */

			if (!OrdinationManager.addProductToCart(idProd, qta, cart))
				out.println("Errore quantita' richiesta");
			else
				out.println("1");

		} else
			response.sendRedirect("login.html");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
