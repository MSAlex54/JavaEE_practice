package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/catalog/*")
public class Catalog extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("headerText", "Каталог товаров" );
        getServletContext().getRequestDispatcher("/header").include(req,resp);
        getServletContext().getRequestDispatcher("/menu").include(req, resp);
        resp.getWriter().println("Тут будет каталог товаров");
    }
}