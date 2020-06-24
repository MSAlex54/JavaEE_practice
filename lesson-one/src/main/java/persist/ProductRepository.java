package persist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final Connection conn;

    public ProductRepository(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExists(conn);
    }

    public void insert (Product prod) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "insert into products(title, description, price) values (?,?,?);")){
            stmt.setString(1, prod.getTitle());
            stmt.setString(2, prod.getDescription());
            stmt.setBigDecimal(3, prod.getPrice());
            stmt.execute();
        }
    }

    public void update(Product prod) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement(
                "update products set title = ?, description = ?, price = ? where id =?;")){
            stmt.setString(1, prod.getTitle());
            stmt.setString(2, prod.getDescription());
            stmt.setBigDecimal(3, prod.getPrice());
            stmt.setLong(4, prod.getId());
            stmt.execute();
        }
    }

    public void delete(Long id) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from products where id = ?")){
            stmt.setLong(1,id);
            stmt.execute();
        }
    }

    public Product findById(Long id) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id,title,description,price from products where id = ?")){
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Product(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBigDecimal(4));
            }
        }
        return new Product(-1L,"","",null);
    }

    public List<Product> findAll() throws SQLException{
        List<Product> res = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id,title,description,price from products")){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                res.add(new Product(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBigDecimal(4))
                );
            }
        }
        return res;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException{
        try (Statement stmt = conn.createStatement()){
            stmt.execute(
                    "create table if not exists products (\n" +
                    "\tid int auto_increment primary key,\n" +
                            "title VARCHAR(25),\n" +
                            "description VARCHAR(25),\n" +
                            "price DECIMAL(10,2) \n" +
                            ");");
        }
    }
}

