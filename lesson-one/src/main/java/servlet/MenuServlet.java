package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MenuServlet", urlPatterns = "/menu")
public class MenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sMenu = "<div><li><a href=\"" + req.getContextPath();
        String fMenu = "</a></li>";
        resp.getWriter().println("<ul>");
        resp.getWriter().println(sMenu + "/main/\">Главная страница" + fMenu);
        resp.getWriter().println(sMenu + "/catalog/\">Каталог товаров" + fMenu);
        resp.getWriter().println(sMenu + "/product/\">Товар" + fMenu);
        resp.getWriter().println(sMenu + "/cart/\">Корзина" + fMenu);
        resp.getWriter().println(sMenu + "/order/\">Оформить заказ" + fMenu);
        resp.getWriter().println("<ul><br></div>");
    }
}