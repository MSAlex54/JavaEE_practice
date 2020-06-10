package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "error_403_servlet", urlPatterns = "/error403")
public class Error403Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("headerText", "Доступ запрещён");
        getServletContext().getRequestDispatcher("/header").include(req, resp);
        getServletContext().getRequestDispatcher("/menu").include(req, resp);
        resp.getWriter().println("Ошибка 403");
    }
}