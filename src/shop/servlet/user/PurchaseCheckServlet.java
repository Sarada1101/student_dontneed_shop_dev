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
import java.util.Map;

@WebServlet("/purchaseCheck")
public class PurchaseCheckServlet extends HttpServlet {

    private PurchaseService purchaseService = new PurchaseService();
    private Logger          logger          = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.trace("{} Start", ErrorCheckService.getMethodName());

        HttpSession                     session     = request.getSession();
        String                          memberMail  = ((MemberBeans) session.getAttribute("memberLoginInfo")).getMemberMail();
        Map<String, List<ProductBeans>> purchaseMap = purchaseService.checkExistsStock(memberMail);
        logger.info("purchaseMap={}", purchaseMap);

        String errorMessage = "";
        if (purchaseMap.get("deleteList").size() != 0) {
            List<ProductBeans> deleteList = purchaseMap.get("deleteList");

            StringBuilder stringBuilder = new StringBuilder();
            for (ProductBeans productBeans : deleteList) {
                purchaseService.deleteCart(memberMail, productBeans.getProductId());
                stringBuilder.append(productBeans.getProductName()).append("\n");
            }
            stringBuilder.append("が既に販売済みであったためカートから削除しました");
            errorMessage = stringBuilder.toString();
        }

        request.setAttribute("errorMessage", errorMessage);
        session.setAttribute("productList", purchaseMap.get("purchaseList"));
        logger.trace("{} End", ErrorCheckService.getMethodName());
        request.getRequestDispatcher("WEB-INF/jsp/user/purchase_check.jsp").forward(request, response);
    }
}
