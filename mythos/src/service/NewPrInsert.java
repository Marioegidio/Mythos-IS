package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addPr
 */
@WebServlet("/NewPrInsert")
public class NewPrInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String resp = "";

		try {
			String name = request.getParameter("name").trim();
			String lastname = request.getParameter("lastname").trim();
			String password = request.getParameter("password").trim();

			String resource = "addPr.php?name=" + name + "&lastname=" + lastname + "&password=" + password;
			resp = MakeRequest.sendGet(resource);

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
