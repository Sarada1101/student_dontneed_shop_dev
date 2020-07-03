<%@ page import="shop.model.bean.ProductBeans" %>
<%@ page import="shop.model.service.ProductService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    ProductBeans productBeans = (ProductBeans) session.getAttribute("productBeans");

    ProductService productService = new ProductService();
    List<Map<String, Object>> genreInfoList = productService.fetchGenreInfo();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品情報削除確認</title>
    <%@include file="/WEB-INF/jsp/bootstrap.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/jsp/admin/admin_header.jsp" %>

<div>
    <table class="table table-striped">
        <tbody>
        <tr>
            <th scope="row">商品名</th>
            <td>
                <%=productBeans.getProductName()%>
            </td>
        </tr>
        <tr>
            <th scope="row">価格</th>
            <td>
                <%=productBeans.getPrice()%>
            </td>
        </tr>
        <tr>
            <th scope="row">画像</th>
            <td><img src="getImage" alt="商品画像"></td>
        </tr>
        <tr>
            <th scope="row">商品説明</th>
            <td>
                <%=productBeans.getProductExplanation()%>
            </td>
        </tr>
        <tr>
            <th scope="row">ジャンル</th>
            <td>
                <%
                    for (Map<String, Object> genreInfoMap : genreInfoList) {
                        if (productBeans.getGenreCode() == (int) genreInfoMap.get("genreCode")) {
                %>
                <%=genreInfoMap.get("genreName")%>
                <%
                        }
                    }
                %>
            </td>
        </tr>
        </tbody>
    </table>

    <form action="productDeleteComplete" method="post">
        <button type="submit" class="btn btn-primary">購入済みに変更する</button>
    </form>

    <form action="productTop" method="get">
        <button type="submit" class="btn btn-outline-dark">戻る</button>
    </form>
</div>

<%@include file="/WEB-INF/jsp/script.jsp" %>
</body>
</html>