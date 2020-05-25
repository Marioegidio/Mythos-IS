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
 * Servlet implementation class ReserveTable
 */
@WebServlet("/ReserveTable")
public class ReserveTable extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 2) {

			request.setCharacterEncoding("utf8");
			
			int idTable = Integer.parseInt(request.getParameter("idTable"));
			String reservationName = request.getParameter("reserv_name");
			String pNum = request.getParameter("people_num");
			// float budg= Float.parseFloat(request.getParameter("budget"));
			float budg = 0;
			try {
				ParametersCheck.checkReservationParameters(reservationName, pNum);
			}catch(IllegalArgumentException e) {
				response.sendRedirect("IndexCashier?errorMessage="+e.getMessage());
				return ;
			}
			
			int peopleNumber=Integer.parseInt(pNum);

			ReservationOption ro = (ReservationOption) getServletContext().getAttribute("config");
			
			if(ReservationManager.newReservation(idTable, reservationName, peopleNumber, budg, ro))
				response.getWriter().print("Prenotazione effettuata");
			
			response.sendRedirect("IndexCashier");

		} else
			response.sendRedirect("login.html");
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
