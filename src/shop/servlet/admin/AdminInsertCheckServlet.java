package shop.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.model.bean.AdminBeans;
import shop.model.service.CommonService;
@WebServlet("/adminInsertCheck")
public class AdminInsertCheckServlet extends HttpServlet {
	 CommonService commonService = new CommonService();

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        HttpSession session = request.getSession();

	        String adminMail     = request.getParameter("adminMail");
	        String adminPassword = request.getParameter("adminPassword");
	        String adminName     = request.getParameter("adminName");
	        String postalCode     = request.getParameter("postalCode");
	        String address        = request.getParameter("address");


	        if (!checkInputText(adminMail, adminPassword, adminName, postalCode, address)) {
	            request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
	            return;
	        }

	        AdminBeans adminBeans = new AdminBeans();
	        adminBeans.setAdminMail(adminMail);
	        adminBeans.setAdminPassword(adminPassword);
	        adminBeans.setAdminName(adminName);
	        adminBeans.setPostalCode(postalCode);
	        adminBeans.setAddress(address);


	        session.setAttribute("adminBeans", adminBeans);

	        request.getRequestDispatcher("WEB-INF/jsp/admin/admin_insert_check.jsp").forward(request, response);
	    }

	    public boolean checkInputText(String memberMail, String memberPassword, String memberName, String postalCode, String address) {
	        if (!commonService.checkLength(memberMail, 100, 1)) return false;
	        if (!commonService.checkLength(memberPassword, 128, 1)) return false;
	        if (!commonService.checkLength(memberName, 20, 1)) return false;
	        if (!commonService.checkLength(postalCode, 7, 7)) return false;
	        if (!commonService.checkLength(address, 50, 1)) return false;
	        return true;
	    }
}
