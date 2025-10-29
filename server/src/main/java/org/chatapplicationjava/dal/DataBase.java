package org.chatapplicationjava.dal;


import java.sql.*;

public class DataBase {
    private static final Connection connection;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/chat_app_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    static {
        try {
            connection = DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException("\n une erreur à la connexion de la base des donnees \n" + e);
        }
    }

    public static ResultSet executeSelectSQL(String sql){
        try {
            PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean executeActionSQL(String sql)  {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            //statement.getGeneratedKeys()
            connection.commit();
            return true;
        }catch (SQLException e) {
            // s'il y a une erreur une exception de la base des donne le server sera arreté
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nune erreur au niveau de rollback !!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
            /// dans le cas de déclenchement de n'import quelles exceptions la method va retourner false
            return false;
    }

    public static ResultSet executeActionSQLAndReturnGeneratedKey(String sql)  {
        try {
            PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            connection.commit();
            return statement.getGeneratedKeys();
        }catch (SQLException e) {
            // s'il y a une erreur une exception de la base des donne le server sera arreté
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nune erreur au niveau de rollback !!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        /// dans le cas de déclenchement de n'import quelles exceptions la method va retourner false
        return null;
    }

    public static boolean executeActionSQL(String[] sqls)  {
        try {
            for (int i = 0 ; i < sqls.length; i++){
                System.out.println(sqls[i]);
                PreparedStatement statement = connection.prepareStatement(sqls[i]);
                statement.executeUpdate();
            }

            //statement.getGeneratedKeys()
            connection.commit();
            return true;
        }catch (SQLException e) {
            // s'il y a une erreur une exception de la base des donne le server sera arreté
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nune erreur au niveau de rollback !!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        /// dans le cas de déclenchement de n'import quelles exceptions la method va retourner false
        return false;
    }

    public static ResultSet executeActionSQLAndReturnGenerateKey(String sql)  {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            connection.commit();
            return statement.getGeneratedKeys();
        }catch (SQLException e) {
            // s'il y a une erreur une exception de la base des donne le server sera arreté
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nune erreur au niveau de rollback !!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                throw new RuntimeException(ex);
            }
            e.getStackTrace();
            //throw new RuntimeException(e);
        }
        /// dans le cas de déclenchement de n'import quelles exceptions la method va retourner false
        return null;
    }
}
