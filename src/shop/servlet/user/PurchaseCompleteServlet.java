package shop.servlet.user;

import shop.model.bean.MemberBeans;
import shop.model.bean.ProductBeans;
import shop.model.bean.PurchaseDetailBeans;
import shop.model.service.ProductService;
import shop.model.service.PurchaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@WebServlet("/purchaseComplete")
public class PurchaseCompleteServlet extends HttpServlet {

    private PurchaseService purchaseService = new PurchaseService();
    private ProductService  productService  = new ProductService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession               session            = request.getSession();
        String                    memberMail         = ((MemberBeans) session.getAttribute("memberLoginInfo")).getMemberMail();
        List<ProductBeans>        purchaseList       = (List<ProductBeans>) session.getAttribute("productList");
        Calendar                  cal                = Calendar.getInstance();
        SimpleDateFormat          sdf                = new SimpleDateFormat("yyyy-MM-dd");
        String                    today              = sdf.format(cal.getTime());
        List<PurchaseDetailBeans> purchaseDetailList = new ArrayList<>();


        for (ProductBeans productBeans : purchaseList) {
            PurchaseDetailBeans purchaseDetailBeans = new PurchaseDetailBeans();
            purchaseDetailBeans.setMemberMail(memberMail);
            purchaseDetailBeans.setProductId(productBeans.getProductId());
            purchaseDetailBeans.setPurchaseDate(today);
            purchaseDetailList.add(purchaseDetailBeans);
        }

        //購入詳細登録
        purchaseService.insertPurchaseDetail(purchaseDetailList);

        //購入した商品を購入済みにしカートから削除
        for (ProductBeans productBeans : purchaseList) {
            productBeans.setIsSold(true);
            productService.updateProduct(productBeans);
            purchaseService.deleteCart(memberMail, productBeans.getProductId());
        }

        request.getRequestDispatcher("WEB-INF/jsp/user/purchase_complete.jsp").forward(request, response);
    }
}
