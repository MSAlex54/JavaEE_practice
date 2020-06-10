package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        if(response.getStatus()==404){
            ((HttpServletResponse) resp).sendRedirect(req.getServletContext().getContextPath() +"/error/");
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}