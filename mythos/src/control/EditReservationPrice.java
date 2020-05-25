package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.ReservationOption;
import entity.User;
import manager.ReservationManager;
import utility.ParametersCheck;

/**
 * Servlet implementation class EditTablesPrice
 */
@WebServlet("/EditReservationPrice")
public class EditReservationPrice extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 3) {
			String normprice=request.getParameter("priceNormal");
			String luxprice=request.getParameter("priceLuxus");
			
			try {
				ParametersCheck.checkEditReservationPrice(normprice,luxprice);
			}catch(IllegalArgumentException e) {
				response.getWriter().print(e.getMessage());
				return;
			}
			
			float normalTablePrice = Float.parseFloat(normprice);
			float luxusTablePrice = Float.parseFloat(luxprice);
			
			if(ReservationManager.editReservationPrice(normalTablePrice, luxusTablePrice))
				response.getWriter().print("Modifica effettuata");
			
			ReservationOption ro = ReservationManager.getReservationsCost();
			getServletContext().setAttribute("config", ro);

		} else
			response.sendRedirect("login.html");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
