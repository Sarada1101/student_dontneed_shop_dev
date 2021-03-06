package shop.servlet.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.model.bean.MemberBeans;
import shop.model.bean.ProductBeans;
import shop.model.service.ErrorCheckService;
import shop.model.service.PurchaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/cartDelete")
public class CartDeleteServlet extends HttpServlet {

    PurchaseService purchaseService = new PurchaseService();
    private Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.trace("{} Start", ErrorCheckService.getMethodName());

        HttpSession        session  = request.getSession();
        List<ProductBeans> cartList = (List<ProductBeans>) session.getAttribute("productList");

        String       memberMail   = ((MemberBeans) session.getAttribute("memberLoginInfo")).getMemberMail();
        int          index        = Integer.parseInt(request.getParameter("index"));
        ProductBeans productBeans = cartList.get(index);
        int          productId    = productBeans.getProductId();
        logger.info("productId={}", productId);

        purchaseService.deleteCart(memberMail, productId);

        logger.trace("{} End", ErrorCheckService.getMethodName());
        response.sendRedirect("cartDisplay");
    }
}
