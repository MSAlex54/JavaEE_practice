package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/*
    юрл-паттерны
    0) "/sometext"
    1) "/sometext/*"
    2) "*.html"
    3) "/"
    4) ""
 */
@WebServlet(name = "FirstHttpServlet", urlPatterns = "/first_http_servlet/*")
public class FirstHttpServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("headerText", "FirstHttpServlet" );
        getServletContext().getRequestDispatcher("/header").include(req,resp);
//        resp.getWriter().println("<h1>FirstHttpServlet123</h1>");
//        resp.getWriter().println("<h1>Привет!</h1>");
        resp.getWriter().println("<p>contextPath: " + req.getContextPath() + "</p>");
        resp.getWriter().println("<p>servletPath: " + req.getServletPath() + "</p>");
        resp.getWriter().println("<p>pathInfo: " + req.getPathInfo()+ "</p>");
        resp.getWriter().println("<p>getQueryString: " + req.getQueryString()+ "</p>");

        resp.setHeader("Hhheader", "value");

        Cookie ck = new Cookie("myOwnCookies", "theBest");
        ck.setMaxAge(5);
        resp.addCookie(ck);

        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            resp.getWriter().println("<p>" + name + ":"  + req.getParameter(name)+ "</p>");
        }


    }
}
