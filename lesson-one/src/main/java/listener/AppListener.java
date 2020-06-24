package listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persist.Product;
import persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class AppListener  implements ServletContextListener {
    public static final String DB_CONNECTION = "dbConnection";
    public static final String PROD_REPO = "productRepo";

    private static final Logger logger = LoggerFactory.getLogger(ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("initializing app");

        ServletContext sc = sce.getServletContext();
        String jdbcConnString = sc.getInitParameter("jdbcConnectionString");
        String user = sc.getInitParameter("username");
        String pass = sc.getInitParameter("password");

        try {
            Connection conn = DriverManager.getConnection(jdbcConnString,user,pass);
            sc.setAttribute(DB_CONNECTION,conn);

            ProductRepository repos = new ProductRepository(conn);
            sc.setAttribute(PROD_REPO,repos);

            if (repos.findAll().isEmpty()){
                for (int i = 0; i < 10 ; i++) {
                    Product pr = new Product(-1L, "Product" + i,"Produce Desc " + i ,new BigDecimal(100.24*(i+1)));
                    repos.insert(pr);
                }
            }

        } catch (SQLException ex){
            logger.error("",ex);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        Connection conn = (Connection) sc.getAttribute(DB_CONNECTION);
        try {
            if (conn!=null && conn.isClosed()){
                conn.close();
            }
        } catch (SQLException ex) {
            logger.error("",ex);
        }
    }
}

