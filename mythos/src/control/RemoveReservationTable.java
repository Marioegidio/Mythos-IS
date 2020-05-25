package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import manager.ReservationManager;

/**
 * Servlet implementation class DelRevationTable
 */
@WebServlet("/RemoveReservationTable")
public class RemoveReservationTable extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 2) {
			int id_table = Integer.parseInt(request.getParameter("id"));
			if (!ReservationManager.removeReservation(id_table))
				System.out.println("Errore rimozione prenotazione");

		} else
			response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
