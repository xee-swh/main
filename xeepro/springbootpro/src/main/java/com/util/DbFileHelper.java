package com.util;

import org.mybatis.spring.SqlSessionTemplate;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author KANYUN
 * @Description PostGreSql导入导出文件
 * @Date 10:16 2021/12/12
 * @Param
 * @return
 **/

@Repository
public class DbFileHelper {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * *通过copy命令向数据库导入指定表数据  适用PostGreSql
     * copy中参数的作用
     * DELIMITER 定界符（分割符）
     * HEADER 导入的数据有没有头信息（true|false）
     * QUOTE   分割数据时,数据内容有没有被指定引用符包含
     * ENCODING  导入文件的编码格式
     * FORMAT   导入文件的文件格式
     *
     * @param tableName
     * @param separtor
     * @param encoding
     * @param inputStream
     * @throws SQLException
     * @throws IOException
     * @throws Exception
     */
    public void copyFromFile(String tableName, String separtor, String encoding, InputStream inputStream)
            throws SQLException, IOException, Exception {
        CopyManager copyManager = getDataBaseCopyManager();
        copyManager.copyIn("COPY \"" + tableName + "\" FROM STDIN (DELIMITER '" + separtor + "', HEADER true,QUOTE '\"', ENCODING '" + encoding + "', FORMAT 'csv');", inputStream);
    }

    public static void main(String[] args) throws IOException, SQLException {
        String filePath = "E:\\HSM-OS\\test.sql";

        String tableOrQuery = "select * from sys.sys_config sc ";

        new DbFileHelper().copyToFile(filePath, tableOrQuery);
    }


    /**
     * @return void
     * @Author KANYUN
     * @Description 将指定表数据或查询语句导出到指定路径
     * @Date 10:15 2021/12/12
     * @Param [filePath, tableOrQuery]
     **/
    public void copyToFile(String filePath, String tableOrQuery)
            throws SQLException, IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            CopyManager copyManager = getDataBaseCopyManager();
            copyManager.copyOut("COPY " + tableOrQuery + " TO STDOUT", fileOutputStream);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将数据表通过copy方法导出成csv文件
     */
    public void copyToFile(String tableOrQuery, OutputStream outputStream)
            throws SQLException, IOException, ClassNotFoundException {
        CopyManager copyManager = getDataBaseCopyManager();
        copyManager.copyOut("COPY  \"" + tableOrQuery + "\" TO STDOUT DELIMITER ',' ENCODING 'UTF-8';", outputStream);
    }

    /**
     * 通过copy命令导出数据库指定表(或查询语句)数据  适用PostGreSql
     *
     * @param tableOrQuery 指定表或者查询语句
     * @param encoding     字符编码
     * @param outputStream 输出流
     * @throws SQLException
     * @throws IOException
     */
    public void copyToFile(String tableOrQuery, String encoding, OutputStream outputStream)
            throws SQLException, IOException, ClassNotFoundException {
        CopyManager copyManager = getDataBaseCopyManager();
        copyManager.copyOut("COPY  \"" + tableOrQuery + "\" TO STDOUT( DELIMITER ',',HEADER true,QUOTE '\"', ENCODING '" + encoding + "', FORMAT 'csv');", outputStream);

    }

    /**
     * @return void
     * @Author
     * @Description 通过copy命令导出数据库指定表(或查询语句)数据  适用PostGreSql 并指定导出字段
     * @Date 10:09 2021/12/12
     * @Param [tableOrQuery, fields, encoding, outputStream]
     **/
    public void copyToFile(String tableOrQuery, String fields, String encoding, OutputStream outputStream)
            throws SQLException, IOException, ClassNotFoundException {
        CopyManager copyManager = getDataBaseCopyManager();
        copyManager.copyOut("COPY  \"" + tableOrQuery + "(" + fields + ")" + "\" TO STDOUT( DELIMITER ',',HEADER true,QUOTE '\"', ENCODING '" + encoding + "', FORMAT 'csv');", outputStream);

    }

    /**
     * @return void
     * @Author KANYUN
     * @Description 将sql的执行结果导出到文件
     * @Date 10:07 2021/12/12
     * @Param [copySql, outputStream]
     **/
    public void copyToFileByQuery(String querySql, OutputStream outputStream) throws SQLException, IOException, ClassNotFoundException {
        CopyManager copyManager = getDataBaseCopyManager();
        copyManager.copyOut(querySql, outputStream);
    }

    /**
     * @return org.postgresql.copy.CopyManager
     * @Author KANYUN
     * @Description 获取CopyManager对象
     * @Date 10:08 2021/12/12
     * @Param []
     **/
    public CopyManager getDataBaseCopyManager() throws SQLException, ClassNotFoundException {
        Connection connection = getDataBaseConnection();
        CopyManager copyManager = new CopyManager((BaseConnection) connection);
        return copyManager;
    }

    /**
     * @return java.sql.Connection
     * @Author KANYUN
     * @Description 获取数据库连接
     * @Date 10:08 2021/12/12
     * @Param []
     **/
    public Connection getDataBaseConnection() throws SQLException, ClassNotFoundException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://172.21.69.35:3306", "root", "mysql#123");
        return conn.unwrap(BaseConnection.class);
    }
}