package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import manager.WarehouseManager;

/**
 * Servlet implementation class EditProducts
 */
@WebServlet("/EditProductOLD")
public class EditProductOLD extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 3) {

			String flag = request.getParameter("flag");
			
			String location_req = request.getParameter("location");
			String price_req = request.getParameter("price");
			String galley_req = request.getParameter("galley");
			String bar_req = request.getParameter("bar");
			String warehouse_req=request.getParameter("warehouse");
			
			int id=Integer.parseInt(request.getParameter("id"));
			
			int location = 0,galley = 0,bar = 0,warehouse = 0;
			float price = 0;
			
			if(location_req!=null)
				location = Integer.parseInt(location_req);
			
			if(price_req!=null) 
				price = Float.parseFloat(price_req);
			
			if(galley_req !=null) 
				galley=Integer.parseInt(galley_req);
			
			if(bar_req!=null)
				bar = Integer.parseInt(bar_req);
			
			if(warehouse_req!=null) 
				warehouse=Integer.parseInt(warehouse_req);

//			/WarehouseManager.editProduct(id, location, price, galley, bar, warehouse, flag);

		} else
			response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
