package me.toolkit.java.util.db;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import me.toolkit.java.entity.db.DBConnectionResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;

/**
 * c3p0 datasoutce management
 *
 *
 * @author wangdi0410@gmail.com
 */
public class C3p0Util {

    private static Log LOG = LogFactory.getLog(C3p0Util.class);

    private static ComboPooledDataSource dataSource = null;


    public static String driverClassName = "com.mysql.jdbc.Driver";
    public static String dbJDBCUrl = "jdbc:mysql://127.0.0.1:3806/test";
    public static String characterEncoding = "UTF-8";
    public static String username = "test";
    public static String password = "test";
    public static int maxActive = 30;
    public static int maxIdle = 10;
    public static int maxWait = 10000;

    public C3p0Util() {
    }

    public C3p0Util(String driverClassName, String dbJDBCUrl, String characterEncoding, String username, String password, int maxActive, int maxIdle, int maxWait) {
        C3p0Util.driverClassName = driverClassName;
        C3p0Util.dbJDBCUrl = dbJDBCUrl;
        C3p0Util.characterEncoding = characterEncoding;
        C3p0Util.username = username;
        C3p0Util.password = password;
        C3p0Util.maxActive = maxActive;
        C3p0Util.maxIdle = maxIdle;
        C3p0Util.maxWait = maxWait;
    }


    /**
     * init datasource pool
     */
    private static void init() throws Exception {

        /** shut down datasource if startup the datasource   */
        shutdownDataSource();
        try {

            dataSource = new ComboPooledDataSource();
            dataSource.setUser(C3p0Util.username);
            dataSource.setPassword(C3p0Util.password);
            dataSource.setJdbcUrl(C3p0Util.dbJDBCUrl);
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            // set init pool size
            dataSource.setInitialPoolSize(2);
            // set min pool size
            dataSource.setMinPoolSize(1);
            // set max pool size
            dataSource.setMaxPoolSize(10);
            // set max statement size
            dataSource.setMaxStatements(50);
            // set max idle time
            dataSource.setMaxIdleTime(60);
            LOG.warn("Start init datasource[driverName:" +
                    driverClassName + ", url: " + C3p0Util.dbJDBCUrl + ", username: [" + username + "], password: [" + password + "]");
        } catch (Exception e) {
            throw new Exception("create datasource fail " + e.getMessage(), e.getCause());
        }
    }

    /**
     * shut down datasource
     */
    public static void shutdownDataSource() {
        if (null != C3p0Util.dataSource) {
            try {
                C3p0Util.dataSource.close();
            } catch (Exception e) {
                // ignore
            }
            C3p0Util.dataSource = null;
        }
    }

    /**
     * get connection from datasource
     */
    private static synchronized Connection getConnection() throws Exception {

        if (null == C3p0Util.dataSource) {
            init();
        }
        Connection conn = null;
        if (null != C3p0Util.dataSource) {
            try {
                conn = dataSource.getConnection();
            } catch (Throwable e) {
                throw new Exception("Can't create conncetion,please make sure if database is available" + e.getMessage(), e.getCause());
            }
        }
        return conn;
    }

    /**
     * close resultset
     */
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
            }// IGNORE}
    }

    /**
     * close resultset and statement
     */
    public static void closeResultSetAndStatement(ResultSet resultSet, Statement statement) {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
            }// IGNORE}
        if (null != statement)
            try {
                statement.close();
            } catch (SQLException e) {
            }// IGNORE
    }

    /**
     * close statement
     */
    public static void closeStatement(Statement statement) {
        if (null != statement)
            try {
                statement.close();
            } catch (SQLException e) {
            }// IGNORE
    }

    /**
     * return  connection to pool
     */
    public static void returnBackConnectionToPool(Connection conn) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
            }// IGNORE
    }

    /**
     *  * must execute list 1,2 when you
     * 1. DataSourceManager.closeResultSetAndStatement( resultSet, stmt )
     * 2. DataSourceManager.returnBackConnectionToPool( conn )
     *
     * @param querySql
     */
    public static DBConnectionResource executeQuery(String querySql) throws Exception {
        try {
            Connection conn = C3p0Util.getConnection();
            if (null == conn)
                throw new Exception("No available connection");
            Statement stmt = conn.createStatement();
            return new DBConnectionResource(conn, stmt, stmt.executeQuery(querySql));
        } catch (Exception e) {
            throw new Exception("执行数据库查询[" + querySql + "]出错: " + e.getMessage(), e.getCause());
        }
    }

    /**
     * execute insert
     * this function will release resources automatically
     */
    public static int executeInsert(String insertSql) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = C3p0Util.getConnection();
            if (null == conn)
                throw new Exception("No available connection");
            stmt = conn.createStatement();
            return stmt.executeUpdate(insertSql);
        } catch (Exception e) {
            throw new Exception("Error when execute insert [" + insertSql + "],error: " + e.getMessage(), e.getCause());
        } finally {
            C3p0Util.closeStatement(stmt);
            C3p0Util.returnBackConnectionToPool(conn);
        }
    }

    /**
     * execute insert
     * return  auto increasemennt primarykley
     * this function will release resources automatically
     */
    public static int executeInsertAndReturnGeneratedKeys(String insertSql) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = C3p0Util.getConnection();
            if (null == conn)
                throw new Exception("No available connection");
            stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (null != rs && rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (Exception e) {
            throw new Exception("execute sql [" + insertSql + "] occur an error " + e.getMessage(), e.getCause());
        } finally {
            C3p0Util.closeResultSetAndStatement(rs, stmt);
            C3p0Util.returnBackConnectionToPool(conn);
        }
    }

    /**
     * execute update statement
     * this function will release resources automatically
     */
    public static int executeUpdate(String updateSql) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = C3p0Util.getConnection();
            if (null == conn)
                throw new Exception("No available connection");
            stmt = conn.createStatement();
            return stmt.executeUpdate(updateSql);
        } catch (Exception e) {
            throw new Exception("execute sql [" + updateSql + "] occur an error " + e.getMessage(), e.getCause());
        } finally {
            C3p0Util.closeStatement(stmt);
            C3p0Util.returnBackConnectionToPool(conn);
        }
    }

    /**
     * execute delete statement
     * this function will release resources automatically
     */
    public static int executeDelete(String deleteSql) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = C3p0Util.getConnection();
            if (null == conn)
                throw new Exception("No available connection");
            stmt = conn.createStatement();
            return stmt.executeUpdate(deleteSql);
        } catch (Exception e) {
            throw new Exception("execute sql [" + deleteSql + "] occur an error " + e.getMessage(), e.getCause());
        } finally {
            C3p0Util.closeStatement(stmt);
            C3p0Util.returnBackConnectionToPool(conn);
        }
    }

    public static void main(String[] args) {

        DBConnectionResource myResultSet = null;
        ResultSet resultSet = null;
        String querySql = "select * from test";
        try {
            myResultSet = C3p0Util.executeQuery(querySql);
            resultSet = myResultSet.resultSet;
            System.out.println("Results:");
            int numcols = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= numcols; i++) {
                    System.out.print("\t" + resultSet.getString(i) + "\t");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != myResultSet)
                C3p0Util.closeResultSetAndStatement(resultSet, myResultSet.statement);
            C3p0Util.returnBackConnectionToPool(myResultSet.connection);
        }
    }

    public void setDriverClassName(String driverClassName) {
        C3p0Util.driverClassName = driverClassName;
    }

    public void setDbJDBCUrl(String dbJDBCUrl) {
        C3p0Util.dbJDBCUrl = dbJDBCUrl;
    }

    public void setUsername(String username) {
        C3p0Util.username = username;
    }

    public void setPassword(String password) {
        C3p0Util.password = password;
    }

    public void setMaxActive(int maxActive) {
        C3p0Util.maxActive = maxActive;
    }

    public void setMaxIdle(int maxIdle) {
        C3p0Util.maxIdle = maxIdle;
    }

    public void setMaxWait(int maxWait) {
        C3p0Util.maxWait = maxWait;
    }

    public void setCharacterEncoding(String characterEncoding) {
        C3p0Util.characterEncoding = characterEncoding;
    }

}
