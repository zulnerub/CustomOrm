import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Main {


    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_STRING = "minions_db";

    private static Connection connection;
    private static String query;
    private static PreparedStatement stmt;
    private static BufferedReader rd;


    public static void main(String[] args) throws SQLException, IOException {
        rd =
                new BufferedReader(
                        new InputStreamReader(
                                System.in
                        )
                );

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        connection = DriverManager
                .getConnection(CONNECTION_STRING + DATABASE_STRING, props);
        ////
       // getVillainsNamesAndCountMinions();
        /////

      //  getMinionNamesAgeByVillainId();
        ////
        
       // addMinion();


       // ChangeTownNamesCasing();

       // deleteVillainAndReleaseMinions();

      // printMinionNamesScrambled();

       // updateMinionAgeAndNames();

        increaseMinionAgeWithProcedure();

    }

    private static void increaseMinionAgeWithProcedure() throws IOException, SQLException {
        System.out.println("Enter minion id:");
        int id = Integer.parseInt(rd.readLine());

        query = "CALL usp_get_older(?)";

        CallableStatement cStmt = connection.prepareCall(query);
        cStmt.setInt(1, id);
        cStmt.execute();

        getMinionById(id);
    }

    private static void getMinionById(int id) throws SQLException {
        query = "SELECT * FROM minions WHERE id = ? ";

        stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("age"));
    }

    private static void updateMinionAgeAndNames() throws IOException, SQLException {
        System.out.println("Enter minion ids to update:");
        int[] ids = Arrays.stream(rd.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int id : ids) {
            query = "UPDATE minions SET name = lcase(name), age = age + 1 WHERE id = ? ";

            stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }


        listAllMinions();


    }

    private static void listAllMinions() throws SQLException {
        query = "SELECT name, age FROM minions";
         stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();

         while (rs.next()){
             System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("age"));
         }

    }

    private static void printMinionNamesScrambled() throws SQLException {
        query = "SELECT name FROM minions";

        stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<String> result = new ArrayList<>();

        while (rs.next()){
            result.add(rs.getString(1));
        }

        for (int i = 0; i < result.size() / 2; i++) {
            System.out.println(result.get(i));
            System.out.println(result.get(result.size() - 1 - i));
        }


    }

    private static void deleteVillainAndReleaseMinions() throws SQLException, IOException {
        System.out.println("Enter villain id:");
        int id = Integer.parseInt(rd.readLine());

        try {
            connection.setAutoCommit(false);

            String villainName = getEntityNameById(id, "villains");

            int releasedMinions = releaseMinionsByVillainId(id);

            deleteVillainById(id);


            System.out.printf("%s was deleted%n", villainName);
            System.out.printf("%d minions released%n", releasedMinions);

            connection.commit();
        }catch (SQLException ex){
            System.out.println("No such villain was found");
            connection.rollback();
        }

    }

    private static int releaseMinionsByVillainId(int id) throws SQLException {
        query = "DELETE FROM minions_villains WHERE villain_id = ?";
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        return stmt.executeUpdate();
    }

    private static void deleteVillainById(int id) throws SQLException {
        query = "DELETE FROM villains WHERE `id` = ? ";
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.execute();
    }

    private static void ChangeTownNamesCasing() throws IOException, SQLException {
        System.out.println("Enter country name:");
        String countryName = rd.readLine();

        int updateRows = updateTownNameToUppercaseByCountryName(countryName);
        System.out.printf("%d town names were affected.%n", updateRows);

        getEntityNameByOtherStringValueColumn(countryName);
    }

    private static void getEntityNameByOtherStringValueColumn(String columnValue) throws SQLException {
        query = "SELECT name FROM towns WHERE country = ?";

        stmt = connection.prepareStatement(query);
        stmt.setString(1, columnValue);

        ResultSet rs = stmt.executeQuery();

        List<String> affectedValues = new ArrayList<>();

        while (rs.next()){
            affectedValues.add(rs.getString(1));
        }

        String[] result = new String[affectedValues.size()];
        result = affectedValues.toArray(result);
        System.out.println(Arrays.toString(result));
    }

    private static int updateTownNameToUppercaseByCountryName(String countryName) throws SQLException {
        query = "UPDATE towns SET name = ucase(name) WHERE country = ?";

        stmt = connection.prepareStatement(query);
        stmt.setString(1, countryName);
        return stmt.executeUpdate();
    }

    private static void addMinion() throws IOException, SQLException {
        System.out.println("Enter minion params:");
        String[] minionData = rd.readLine().split("\\s+");

        System.out.println("Enter villain name:");
        String villainName = rd.readLine();

        int townId;
        int minionId;
        int villainId;

        if(!checkIfEntityExistsByValue(minionData[2], "towns")){
            insertEntityInTowns(minionData[2]);
            System.out.printf("Town %s was added to the database.%n", minionData[2]);
        }
        townId = getEntityIdByName(minionData[2], "towns");

        insertEntityInMinions(minionData[0], Integer.parseInt(minionData[1]), townId);
        minionId = getEntityIdByName(minionData[0], "minions");

        if (!checkIfEntityExistsByValue(villainName, "villains")){
            insertEntityInVillains(villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }
        villainId = getEntityIdByName(villainName, "villains");

        insertEntityInMinionsVillains(minionId, villainId);
        System.out.printf("Successfully added %s to be minion of %s%n", minionData[0], villainName);
    }

    private static void insertEntityInMinionsVillains(int minionId, int villainId) throws SQLException {
        query = "INSERT INTO minions_villains (minion_id, villain_id) value (?, ?)";

        stmt = connection.prepareStatement(query);
        stmt.setInt(1, minionId);
        stmt.setInt(2, villainId);
        stmt.execute();
    }

    private static void insertEntityInVillains(String name) throws SQLException {
        query = "INSERT INTO villains (`name`, evilness_factor) value (?, 'evil')";

        stmt = connection.prepareStatement(query);
        stmt.setString(1, name);
        stmt.execute();
    }

    private static void insertEntityInMinions(String name, int age, int town_id) throws SQLException {
        query = "INSERT INTO minions (`name`, age, town_id) value (?, ?, ?)";

        stmt = connection.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setInt(3, town_id);
        stmt.execute();
    }

    private static void insertEntityInTowns(String entityData) throws SQLException {
        query = "INSERT INTO towns (`name`, country) value (?, null)";

        stmt = connection.prepareStatement(query);
        stmt.setString(1, entityData);
        stmt.execute();
    }

    private static int getEntityIdByName(String name, String tableName) throws SQLException {
        query = "SELECT id FROM " + tableName + " WHERE name LIKE ?";

        stmt = connection.prepareStatement(query);
        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        return rs.getInt(1);
    }

    private static void getMinionsByVillainId(int entityId) throws SQLException {
        query = "SELECT m.`name`, m.`age` " +
                "FROM minions AS m " +
                "JOIN minions_villains AS mv on m.id = mv.minion_id " +
                "WHERE mv.villain_id = ?";

        stmt = connection.prepareStatement(query);
        stmt.setInt(1, entityId);

        ResultSet rs = stmt.executeQuery();
        int count = 1;
        while (rs.next()) {
            System.out.printf(
                    "%d. %s %d%n",
                    count,
                    rs.getString("name"),
                    Integer.parseInt(rs.getString("age"))
            );
            count++;
        }
        rs.close();
    }

    private static void getMinionNamesAgeByVillainId() throws IOException, SQLException {
        System.out.println("Enter villain id:");
        int villain_id = Integer.parseInt(rd.readLine());

        if (!checkIfEntityExistsByID(villain_id, "villains")){
            System.out.printf("No villain with ID %d exists in the database", villain_id);
            return;
        }


        System.out.printf(
                "Villain: %s%n", getEntityNameById(villain_id, "villains"
                ));

        getMinionsByVillainId(villain_id);

    }

    private static String getEntityNameById(int entityId, String tableName) throws SQLException {
        query = "SELECT `name` FROM " + tableName + " WHERE id = ?";

        stmt = connection.prepareStatement(query);
        stmt.setInt(1, entityId);

        ResultSet rs = stmt.executeQuery();

        return rs.next() ? rs.getString("name") : null;
    }

    private static boolean checkIfEntityExistsByID(int entityId, String tableName) throws SQLException {
        query = "SELECT * FROM " + tableName + " WHERE id = ?";

        stmt = connection.prepareStatement(query);
        stmt.setInt(1, entityId);

        ResultSet rs = stmt.executeQuery();

        return rs.next() ;
    }

    private static boolean checkIfEntityExistsByValue(String entityValue, String tableName) throws SQLException {
        query = "SELECT * FROM " + tableName + " WHERE name = ?";

        stmt = connection.prepareStatement(query);
        stmt.setString(1, entityValue);

        ResultSet rs = stmt.executeQuery();


        return rs.next();
    }

    private static void getVillainsNamesAndCountMinions() throws SQLException {
        query = "SELECT v.name, COUNT(mv.minion_id) AS `count` FROM villains AS v JOIN minions_villains AS mv ON  mv.villain_id = v.id GROUP BY v.name HAVING `count` > 15 ORDER BY `count` DESC";

        stmt = connection.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.printf("%s %s %n", rs.getString(1), rs.getString(2));
        }

        rs.close();
    }
}
