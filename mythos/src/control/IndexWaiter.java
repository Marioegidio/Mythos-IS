package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Table;
import entity.User;
import manager.ReservationManager;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("")
public class IndexWaiter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 1) {

			ArrayList<Table> tables = ReservationManager.tablesList();
			request.setAttribute("tables", tables);

			request.getRequestDispatcher("WEB-INF/jsp/index_waiter.jsp").forward(request, response);

		} else
			response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
