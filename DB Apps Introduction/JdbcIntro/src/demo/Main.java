package demo;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username default (root):");
        String user = sc.nextLine().trim();

        user = user.equals("") ? "root" : user;

        System.out.println("Enter password default (root):");
        String password = sc.nextLine().trim();
        password = password.equals("") ? "root" : password;

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection =
                DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/soft_uni?useSSL=false",
                        props
                );

        PreparedStatement stmt =
                connection.prepareStatement(
                        "SELECT COUNT(*) AS nRows FROM employees WHERE first_name LIKE ?"
                );

        System.out.println("Enter first name (default Ken): ");
        String inputName = sc.nextLine().trim();
        inputName = inputName.equals("") ? "%Ken%" : inputName;

        stmt.setString(1, inputName);
        ResultSet rs = stmt.executeQuery();




        while (rs.next()){
            if (Integer.parseInt(rs.getString("nRows")) == 0){
                System.out.println("No such username!");
            }else {
                System.out.printf("Number of appearances: %s\n",
                        rs.getString("nRows")
                );
            }


           // System.out.printf("%s", rs.getString("nRows"));
        }


        connection.close();
    }
}