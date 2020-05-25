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
 *
 */
@WebServlet("/EditTableReservation")
public class EditTableReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 2) {
			int id_table = Integer.parseInt(request.getParameter("id"));
			
			String name= request.getParameter("name");
			String nump=request.getParameter("people");
			
			try {
				ParametersCheck.checkReservationParameters(name, nump);
			}catch(IllegalArgumentException e) {
				response.getWriter().print(e.getMessage());
				return;
			}
			
			int people = Integer.parseInt(nump);
			
			ReservationOption ro = (ReservationOption) getServletContext().getAttribute("config");

			if(ReservationManager.editReservation(id_table, people, name, ro))
				response.getWriter().print("Prenotazione modificata");

		} else
			response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
