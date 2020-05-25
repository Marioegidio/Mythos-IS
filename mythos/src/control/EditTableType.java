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
 * Servlet implementation class EditTable
 */
@WebServlet("/EditTableType")
public class EditTableType extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 3) {

			int id = Integer.parseInt(request.getParameter("id"));

			boolean isLuxus = Boolean.parseBoolean(request.getParameter("isLuxus"));

			ReservationManager.editTableType(id, isLuxus);

		} else
			response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
