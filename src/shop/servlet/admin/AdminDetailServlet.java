package shop.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminDetailsServlet
 */
@WebServlet("/AdminDetailServlet")
public class AdminDetailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        request.getRequestDispatcher("WEB-INF/jsp/admin/admin_detail.jsp").forward(request, response);
	}
}