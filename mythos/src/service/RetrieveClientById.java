package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RetrieveClientById
 */
@WebServlet("/RetrieveClientById")
public class RetrieveClientById extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String resp = "";
		String clientId = request.getParameter("id");
		try {
			String resource = "retrieveClientById.php?clientId=" + clientId;
			resp = MakeRequest.sendGet(resource.replace(" ", "%20").replace("'", "%27"));

		} catch (IOException e) {
			out.print(e.getMessage());
		}
		out.println(resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
