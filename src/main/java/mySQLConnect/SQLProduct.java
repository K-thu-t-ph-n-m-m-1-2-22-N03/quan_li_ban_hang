package mySQLConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Product;

import java.sql.*;
import java.time.LocalDate;

public class SQLProduct {
    public static Connection DBConnect(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/manager","root", "096270");
        }catch (ClassNotFoundException | SQLException e){
            e.fillInStackTrace();
        }
        return connection;
    }

    public static ObservableList<Product> getDatabase(){
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Product> list = FXCollections.observableArrayList();
        try{
            connection = DBConnect();
            String sql = "select * from product";
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                String id = result.getString("product_id");
                String name = result.getString("product_name");
                int price = Integer.parseInt(result.getString("product_price"));
                int quantity = Integer.parseInt(result.getString("product_quantity"));
                String note = result.getString("product_note");
                String image = result.getString("product_image");
                LocalDate date = result.getDate("product_date").toLocalDate();
                list.add(new Product(id,name,price,quantity,note,image,date));
            }
        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement != null) statement.close();

            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
        return list;
    }
    public static void insertProduct(Product product){
        Connection connection = null;
        PreparedStatement statement1 = null;
        try{
            connection = DBConnect();
            String sql = "insert into product values(?,?,?,?,?,?,?);";
            statement1 = connection.prepareStatement(sql);
            statement1.setString(1,product.getProductID());
            statement1.setString(2,product.getProductName());
            statement1.setInt(3,product.getProductPrice());
            statement1.setInt(4,product.getProductQuantity());
            statement1.setString(5,product.getProductNote());
            statement1.setString(6,product.getProductImage());
            statement1.setDate(7, java.sql.Date.valueOf(product.getDate()));
            statement1.executeUpdate();

        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(statement1 != null) statement1.close();
                if(connection != null) connection.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }

    public static void deteteProduct(Product product){
        Connection connection = null;
        PreparedStatement statement1 = null;

        try{
            connection = DBConnect();

            String sql = "delete from product where product_id = ?";
            statement1 = connection.prepareStatement(sql);
            statement1.setString(1,product.getProductID());
            statement1.executeUpdate();


        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement1 != null) connection.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }
    public static void UpdateProduct(Product product){
        Connection connection = null;
        PreparedStatement statement1 = null;
        try{
            connection = DBConnect();
            String sql = "update product set product_name = ?, " +
                    "product_price = ?, product_quantity = ?, product_note = ?, product_image = ?, product_date = ?  " +
                    "where product_id = ?";

            statement1 = connection.prepareStatement(sql);
            statement1.setString(1, product.getProductName());
            statement1.setInt(2,product.getProductPrice());
            statement1.setInt(3,product.getProductQuantity());
            statement1.setString(4,product.getProductNote());
            statement1.setString(5,product.getProductImage());
            statement1.setDate(6,java.sql.Date.valueOf(product.getDate()));
            statement1.setString(7,product.getProductID());
            statement1.executeUpdate();

        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement1 != null) statement1.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }

    }
    public static int TotalQuantityInStock(){
        Connection connection = null;
        PreparedStatement statement = null;
        int sum = 0;
        try{
            connection = DBConnect();
            String sql = "select sum(product_quantity) as 'quantity' from product";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                sum = resultSet.getInt("quantity");
            }
        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement != null) statement.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
        return sum;
    }
}
