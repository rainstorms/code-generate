package rain.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import rain.dbconverts.*;
import rain.domain.TableField;
import rain.domain.TableInfo;
import rain.utils.CommonUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Data
@NoArgsConstructor
public class DataSourceConfig {
    private DbEnum dbEnum;
    private String schemaName = "public";
    private IDbConvert typeConvert;
    private String url;
    private String driverName;
    private String username;
    private String password;
    private Connection conn = null;

    public DbEnum getDbType() {
        if (null == this.dbEnum) {
            if (this.driverName.contains("mysql")) {
                this.dbEnum = DbEnum.MYSQL;
            } else if (this.driverName.contains("oracle")) {
                this.dbEnum = DbEnum.ORACLE;
            } else {
                if (!this.driverName.contains("postgresql")) {
                    throw new RuntimeException("Unknown type of database!");
                }

                this.dbEnum = DbEnum.POSTGRE_SQL;
            }
        }

        return this.dbEnum;
    }

    public IDbConvert getTypeConvert() {
        if (null == this.typeConvert) {
            switch (this.getDbType()) {
                case ORACLE:
                    this.typeConvert = new OracleDbConvert();
                    break;
                case POSTGRE_SQL:
                    this.typeConvert = new PostgreSqlDbConvert();
                    break;
                default:
                    this.typeConvert = new MySqlDbConvert();
            }
        }

        return this.typeConvert;
    }

    public Connection getConn() {
        if (conn != null) return conn;

        try {
            Class.forName(this.driverName);
            Properties props = new Properties();
            props.setProperty("user", this.username);
            props.setProperty("password", this.password);
            props.setProperty("remarks", "true");
            props.setProperty("useInformationSchema", "true");//mysql设置可以获取tables remarks信息
            conn = DriverManager.getConnection(this.url, props);
        } catch (SQLException | ClassNotFoundException var2) {
            var2.printStackTrace();
        }

        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("close connection failure", e);
        }
    }

    /**
     * 获取生成的表信息
     *
     * @param tables
     * @return
     */
    public List<TableInfo> getTablesInfo(String[] tables) {
        List<TableInfo> tableList = new ArrayList<>();
        for (String table : tables) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setName(table);
            List<TableField> fieldList = this.getTableFields(tableInfo.getName());
            tableInfo.setFields(fieldList);
            tableList.add(tableInfo);
        }
        closeConnection();
        return tableList;
    }

    /**
     * 根据表名获取该表的所有字段
     *
     * @param tableName 表名称
     * @return
     */
    public List<TableField> getTableFields(String tableName) {
        List<TableField> tableFields = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConn();
        try (ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn), tableName, "%")) {

            while (rs.next()) {
                TableField field = new TableField();
                String fieldNm = rs.getString("COLUMN_NAME").toLowerCase();
                field.setName(fieldNm);//表字段名
                field.setPropertyName(CommonUtils.getNoUnderlineStr(fieldNm));//字段名
                field.setComment(rs.getString("REMARKS"));//字段注释
                field.setType(rs.getString("TYPE_NAME"));//字段类型
                field.setColumnType(this.getTypeConvert().processTypeConvert(field.getType()));

                tableFields.add(field);
            }
        } catch (SQLException e) {
            throw new RuntimeException("getColumnNames failure", e);
        } catch (Exception e) {
            throw new RuntimeException("Exception rs failure", e);
        }

        return tableFields;
    }

    //其他数据库不需要这个方法 oracle需要
    private String getSchema(Connection conn) throws Exception {
        if (!(DbEnum.ORACLE == this.getDbType())) return null;//不是oracle数据库

        String schema = conn.getMetaData().getUserName();
        if (StringUtils.isBlank(schema)) throw new Exception("ORACLE数据库模式不允许为空");

        return schema.toUpperCase();
    }
}