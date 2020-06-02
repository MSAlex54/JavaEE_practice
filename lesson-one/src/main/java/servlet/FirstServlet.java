package servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.Serializable;

public class FirstServlet implements Servlet, Serializable {
    private transient ServletConfig config;
    private final Logger logger = LoggerFactory.getLogger(FirstServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Servlet created");
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        logger.info("Servlet request");
        req.setAttribute("headerText", "FirstServlet");
        config.getServletContext().getRequestDispatcher("/header").include(req, resp);
//        resp.getWriter().println("<h1>Hello from the first servlet!!!</h1>");
    }

    @Override
    public String getServletInfo() {
        return "our first servlet";
    }

    @Override
    public void destroy() {
        logger.info("Servlet destroyed");
    }
}
