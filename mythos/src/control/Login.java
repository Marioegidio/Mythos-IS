package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.ReservationOption;
import entity.User;
import manager.UserManager;
import utility.ParametersCheck;
import manager.ReservationManager;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession sess = request.getSession();
		
		int res=ParametersCheck.checkLoginParameters(username,password);
		
		switch(res) {
			case 1: response.getWriter().print("Il campo username non può essere vuoto"); break;
			case 2: response.getWriter().print("Il campo password non può essere vuoto"); break;
		}
		if(res>0) {
			response.sendRedirect("logError.html");
			return;
		}
		
		User c = UserManager.checkUser(username, password);
		if (c == null) 
			response.getWriter().print("Username o password errati");
		else
			response.getWriter().print("Login ok");

		sess.setAttribute("userLogged", c);
		sess.setMaxInactiveInterval(7200); // dopo 120 min scade la sessione
		
		// setto la variabile di contesto in cui ci sono tutte le info di
		// configurazione(es il costo del tavolo luxus o normale)
		ReservationOption ro = ReservationManager.getReservationsCost();
		getServletContext().setAttribute("config", ro);

		if (c == null)
			response.sendRedirect("logError.html");

		else if (c.getRole().getIdRole() == 1)
			response.sendRedirect(".");

		else if (c.getRole().getIdRole() == 2)
			response.sendRedirect("IndexCashier");

		else
			response.sendRedirect("IndexModerator");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
