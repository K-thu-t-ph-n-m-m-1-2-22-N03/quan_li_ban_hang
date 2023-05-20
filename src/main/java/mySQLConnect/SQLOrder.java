package mySQLConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Order;
import model.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class SQLOrder {
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
    public static ObservableList<Product> getListProductOrder(){
        ObservableList<Product> list = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DBConnect();
            String sql = "select orderdetails.*, product.* from orderdetails " +
                    " inner join product on orderdetails.product_id = product.product_id";
            statement = connection.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                  String id = res.getString("product.product_id");
                  String name = res.getString("product.product_name");
                  String note = res.getString("product.product_note");
                  String image = res.getString("product.product_image");
                  LocalDate date = res.getDate("product_date").toLocalDate();
                  int price = Integer.parseInt(res.getString("detail_price"));
                  int quantity = Integer.parseInt(res.getString("detail_quantity"));
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
    public static void insertProductOrder(Product product){
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;

        try{
            connection = DBConnect();
            String sql1 = "set foreign_key_checks = 0 ";
            statement1 = connection.prepareStatement(sql1);
            statement1.executeUpdate();

            String sql2 = "insert into orderdetails( product_id, detail_quantity, detail_price) " +
                    "values(?,?,?) ";

            statement2 = connection.prepareStatement(sql2);
            statement2.setString(1,product.getProductID());
            statement2.setInt(2,product.getProductQuantity());
            statement2.setInt(3,product.getProductPrice());
            statement2.executeUpdate();

            String sql3 = "set foreign_key_checks = 1 ";
            statement3 = connection.prepareStatement(sql3);
            statement3.executeUpdate();
        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement1 != null) statement1.close();
                if(statement2 != null) statement2.close();
                if(statement3 != null) statement3.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }

    public static void insertOrder(Order order){
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = DBConnect();
            String sql = "insert into orders( orders_id, order_date, total_quantity, total_amount, orders_note) " +
                    "values(?,?,?,?,?) ";

            statement = connection.prepareStatement(sql);
            statement.setString(1,order.getOrderID());
            statement.setDate(2, java.sql.Date.valueOf(order.getOrderDate()));
            statement.setInt(3,order.getTotal_quantity());
            statement.setInt(4,order.getTotal_amount());
            statement.setString(5,order.getNote());
            statement.executeUpdate();

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
    }
    public static void deleteOrderDetail(String id){
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = DBConnect();
            String sql = "delete from orderdetails where product_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,id);
            statement.executeUpdate();

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
    }

    public static Map<Integer,Double> getRevenue(int year){
        Connection connection = null;
        PreparedStatement statement = null;
        Map<Integer,Double> map = new LinkedHashMap<>();
        map.put(1,0.0);
        map.put(2,0.0);
        map.put(3,0.0);
        map.put(4,0.0);
        map.put(5,0.0);
        map.put(6,0.0);
        map.put(7,0.0);
        map.put(8,0.0);
        map.put(9,0.0);
        map.put(10,0.0);
        map.put(11,0.0);
        map.put(12,0.0);

        try{
            connection = DBConnect();
            String sql = "SELECT MONTH(order_date) AS month, SUM(total_amount) AS revenue FROM orders " +
                    "WHERE YEAR(order_date) = ? AND MONTH(order_date) BETWEEN 1 AND 12 " +
                    "GROUP BY MONTH(order_date)";

            statement = connection.prepareStatement(sql);
            statement.setInt(1,year);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int month = resultSet.getInt("month");
                double revenue = resultSet.getDouble("revenue");
                revenue = revenue / 1000000;
                map.put(month,revenue);
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
        return map;
    }

    public static int getAmount(){
        Connection connection = null;
        PreparedStatement statement = null;
        int sum = 0;
        try{
            connection = DBConnect();
            String sql = "select sum(total_amount) as 'amount' from orders";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                sum = resultSet.getInt("amount");

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
