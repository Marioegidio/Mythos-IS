package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RetrievePr
 */
@WebServlet("/RetrieveClientsByPr")
public class RetrieveClientsByPr extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String resp = "";
		String prName = request.getParameter("prName");
		String prLastname = request.getParameter("prLastname");
		try {
			String resource = "retrieveClientsByPr.php?prName=" + prName + "&prLastname=" + prLastname;
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
