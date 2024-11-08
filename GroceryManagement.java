package Project;

//package src.GroceryManage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class GroceryManagement {
    private Connection conn;

    public GroceryManagement() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Speedygrocer", "root", "1234");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void browseItems() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM category");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("item_id") + ", Name: " + rs.getString("item_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error browsing items: " + e.getMessage());
        }
    }

    public void searchItems(String query) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM groceries where item_id=?");
            pstmt.setString(1, query);
            pstmt.setString(2, query);
            pstmt.setString(3, query);
            pstmt.setString(4, query);
            pstmt.setString(5, query);
            pstmt.setString(6, query);
            pstmt.setString(7, query);
            pstmt.setString(8, query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("prod_id") + ", Name: " + rs.getString("prod_name") +
                                   ", Price: " + rs.getDouble("price") + ", Item id: " + rs.getInt("item_id") +
                                   ", Stock Quantity: " + rs.getInt("stock_qty"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching items: " + e.getMessage());
        }
    }

    public void addItemToCart(int prod_id, int stock_qty) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO cart (prod_id, quantity) VALUES (?, ?)");
            pstmt.setInt(1, prod_id);
            pstmt.setInt(2, stock_qty);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding item to cart: " + e.getMessage());
        }
    }

    public void viewCart() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT cart.prod_id, cart.quantity, groceries.price, groceries.prod_name " +
                                             "FROM cart JOIN groceries ON cart.prod_id = groceries.prod_id");
            double totalCost = 0;
            while (rs.next()) {
                int prodId = rs.getInt("prod_id");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String prodName = rs.getString("prod_name");
                totalCost += price * quantity;
                System.out.println("ID: " + prodId + ", Name: " + prodName + ", Price: " + price + ", Quantity: " + quantity);
            }
            System.out.println("Total Cost: " + totalCost);
        } catch (SQLException e) {
            System.out.println("Error viewing cart: " + e.getMessage());
        }
    }

    public void removeItemFromCart(int prodId) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM cart WHERE prod_id = ?");
            pstmt.setInt(1, prodId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error removing item from cart: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GroceryManagement gm = new GroceryManagement();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Browse Items");
            System.out.println("2. Search Items");
            System.out.println("3. Add Item to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Remove Item from Cart");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    gm.browseItems();
                    break;
                case 2:
                    System.out.print("Enter search query: ");
                    String query = scanner.next();
                    gm.searchItems(query);
                    break;
                case 3:
                    System.out.print("Enter product ID: ");
                    int prodId = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    gm.addItemToCart(prodId, quantity);
                    break;
                case 4:
                    gm.viewCart();
                    break;
                case 5:
                    System.out.print("Enter product ID: ");
                    prodId = scanner.nextInt();
                    gm.removeItemFromCart(prodId);
                    break;
                case 6:
                    try {
                        if (gm.conn != null && !gm.conn.isClosed()) {
                            gm.conn.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Error closing connection: " + e.getMessage());
                    }
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}

