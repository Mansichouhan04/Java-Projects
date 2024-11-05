import java.sql.*;
import java.util.Scanner;
public class Employee2 {
    private static final String DB_URL="jdbc:mysql://localhost:3306/users";
    private static final String USER="root";
    private static final String PASSWORD="1234";

    private Connection connect()throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
    
     public void addUSer(String username,String email, int age)
     {
        String sql = "INSERT INTO users (username, email, age) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    // View all users
    public void viewAllUsers() {
        String sql = "SELECT * FROM users";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("ID\tUsername\tEmail\t\tAge");
            System.out.println("-------------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                                   rs.getString("username") + "\t" +
                                   rs.getString("email") + "\t" +
                                   rs.getInt("age"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
    }

    // Update user details
    public void updateUser(int id, String newUsername, String newEmail, int newAge) {
        String sql = "UPDATE users SET username = ?, email = ?, age = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, newEmail);
            pstmt.setInt(3, newAge);
            pstmt.setInt(4, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User details updated successfully!");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    // Delete a user by ID
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Employee2 app = new Employee2();

        while (true) {
            System.out.println("\n--- User CRUD Application ---");
            System.out.println("1. Add a new user");
            System.out.println("2. View all users");
            System.out.println("3. Update a user’s details");
            System.out.println("4. Delete a user by ID");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    app.addUSer(username, email, age);
                    break;

                case 2:
                    app.viewAllUsers();
                    break;

                case 3:
                    System.out.print("Enter user ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter new age: ");
                    int newAge = scanner.nextInt();
                    app.updateUser(idToUpdate, newUsername, newEmail, newAge);
                    break;

                case 4:
                    System.out.print("Enter user ID to delete: ");
                    int idToDelete = scanner.nextInt();
                    app.deleteUser(idToDelete);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
