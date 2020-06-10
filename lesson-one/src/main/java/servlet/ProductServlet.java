package servlet;

import persist.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static listener.AppListener.PROD_REPO;

@WebServlet(name = "productServlet", urlPatterns = {"","/"})
public class ProductServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ProductServlet.class);

    private ProductRepository repo;

    @Override
    public void init() throws ServletException {
        repo = (ProductRepository) getServletContext().getAttribute(PROD_REPO);
        if (repo == null){
            throw new ServletException("Product repository not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Index Product page");
        if (req.getServletPath().equals("/")){
            try {
                req.setAttribute("products",repo.findAll());
                getServletContext().getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(req, resp);
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        } else if (req.getServletPath().equals("/new")){
            req.setAttribute("product", new Product());
            getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
        } else if (req.getServletPath().equals("/edit")){
            try {
                Product pr = repo.findById(Long.valueOf(req.getParameter("id")));
                req.setAttribute("product", pr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
        } else if (req.getServletPath().equals("/delete")){
            try {
                repo.delete(Long.valueOf(req.getParameter("id")));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resp.sendRedirect(getServletContext().getContextPath());
        } else {
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if (req.getParameter("id")!=null){
                Product pr = repo.findById(Long.valueOf(req.getParameter("id")));
                pr.setTitle(req.getParameter("title"));
                pr.setTitle(req.getParameter("description"));
                pr.setPrice(new BigDecimal(req.getParameter("price")));
                repo.update(pr);
            } else {
                repo.insert(new Product(
                        -1L,
                        req.getParameter("title"),
                        req.getParameter("description"),
                        new BigDecimal(req.getParameter("price"))));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
        resp.sendRedirect(getServletContext().getContextPath());
    }
}
