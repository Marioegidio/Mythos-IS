package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import manager.WarehouseManager;
import utility.ParametersCheck;

/**
 * Servlet implementation class EditProducts
 */
@WebServlet("/EditProduct")
public class EditProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 3) {
			
			String location_req = request.getParameter("location");
			String price_req = request.getParameter("price");
			String galley_req = request.getParameter("galley");
			String bar_req = request.getParameter("bar");
			String warehouse_req=request.getParameter("warehouse");
			try{
				ParametersCheck.checkEditPRoductParameters(price_req,warehouse_req,galley_req,bar_req);
			}catch(IllegalArgumentException e) {
				response.getWriter().print(e.getMessage());
				return;
			}
			
			int id=Integer.parseInt(request.getParameter("id"));
			
			int location = 0,galley = 0,bar = 0,warehouse = 0;
			float price;
			location = Integer.parseInt(location_req);
			price = Float.parseFloat(price_req);
			galley=Integer.parseInt(galley_req);
			bar = Integer.parseInt(bar_req);
			warehouse=Integer.parseInt(warehouse_req);

			if(WarehouseManager.editProduct(id, location, price, galley, bar, warehouse))
				response.getWriter().print("prodotto modificato");

		} else
			response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
