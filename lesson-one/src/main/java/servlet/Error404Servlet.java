package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

@WebServlet(name = "error_404_servlet", urlPatterns = "/error404")
public class Error404Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("headerText", "Запрошенной страницы не существует");
        getServletContext().getRequestDispatcher("/header").include(req, resp);
        getServletContext().getRequestDispatcher("/menu").include(req, resp);
        if ( LocalDateTime.now().getMonth()== Month.APRIL && LocalDateTime.now().getDayOfMonth()==4){
            resp.getWriter().println("С днем веб-мастера!!!");
        } else {
            resp.getWriter().println("Ошибка 404");
        }

    }
}