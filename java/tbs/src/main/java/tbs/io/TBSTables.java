package tbs.io;

import com.google.gson.Gson;
import sql.HPSQL;
import sql.Insert;
import sql.Select;
import tbs.mini.TbsMini;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TBSTables {
    private Connection con = null;
    private String dsn, pDsn;
    private final HPSQL hpsql;
    private final TbsMini mini;
    private String agencyName;

    public TBSTables(String dsn) {
        this.hpsql = new HPSQL();
        mini = new TbsMini();
        this.dsn = this.pDsn = dsn;
        agencyName = mini.getAgencyName();
    }

    public TBSTables() {
        this.hpsql = new HPSQL();
        mini = new TbsMini();
        this.dsn = this.pDsn = mini.getCurrentDsn();
        agencyName = mini.getAgencyName();
    }

    private void useGENE(){
        dsn = mini.getGeneDsn();

    }

    private void useMain(){
        dsn = pDsn;
    }


    public int addCustomer(Object bean) throws IOException, SQLException {

        return new Insert()
                .setBean(bean)
                .setTableName("customers")
                .setExcludes("id", "seen", "agency")
                .prepareBean(getConnection());

    }

    public int addCustomerComplaints(Object bean) throws SQLException {
        return new Insert()
                .setBean(bean)
                .setExcludes("agency")
                .setTableName("customer_complaints")
                .prepareBean(getConnection());
    }


    public <T> List<T> getComplaintsFileMovement(Class<T> bean, String agency) throws SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("agency")
                .setIncludes(Map.of("agency", agency))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getComplaintsFileMovementJson(Class<T> bean, String agency) throws SQLException {
        return objToJson(getComplaintsFileMovement(bean, agency));
    }

    public <T> List<T> getZonalCustomers(Class<T> bean, String zone, Map<String, Object> includes) throws IOException, SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("id", "seen", "agency")
                .setIncludes(includes)
                .setTableName("customers")
                .setWhere(Map.of("zone", zone))
                .resultSetObjISQL(getConnection(), bean);

    }

    public <T> String getZonalCustomersJson(Class<T> bean, String zone, Map<String, Object> includes) throws IOException, SQLException {
        return objToJson(getZonalCustomers(bean, zone, includes));
    }

    public <T> List<T> getZones(Class<T> bean) throws SQLException {
        useMain();
        return new Select()
                .setTableName("customer_zones")
                .setColumns(hpsql.getColumnFromClass(bean))
                .setIncludes(Map.of("agency",agencyName.toLowerCase()))
                .setExclude("agency")
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getZonesJson(Class<T> bean) throws SQLException {
        return new Gson().toJson(getZones(bean));
    }

    public <T> List<T> getUsers(Class<T> bean) throws SQLException {
        useGENE();
        return new Select()
                .setTableName("users")
                .setColumns(hpsql.getColumnFromClass(bean))
                .setIncludes(Map.of("agency",agencyName.toLowerCase()))
                .setExclude("agency")
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getUsersJson(Class<T> bean) throws SQLException {
        return objToJson(getUsers(bean));
    }

    public <T> List<T> getArparm(Class<T> bean, String agency) throws SQLException {
        return new Select()
                .setTableName("arparm")
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("agency")
                .setIncludes(Map.of("agency", agency))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getArparmJson(Class<T> bean, String agency) throws SQLException {
        return objToJson(getArparm(bean, agency));
    }

    public <T> List<T> getCustomers(Class<T> bean) throws IOException, SQLException {
        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("id", "seen", "agency")
                .setTableName("customers")
                .resultSetObjISQL(getConnection(), bean);

    }
    /*
    Schemes
     */
    public List<Object[]> getSchemes(String [] columns) throws IOException, SQLException {
        return getTable(columns, "schemes");
    }

    /*
    Enumeration
     */
    public int addCustomerEnumeration(Object bean) throws IOException, SQLException {

        return new Insert()
                .setBean(bean)
                .setTableName("customers_enumeration")
                .prepareBean(getConnection());

    }

    public <T> List<T> getZonalCustomersEnumeration(Class<T> bean, Object zone) throws IOException, SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName("customers_enumeration")
                .setWhere(Map.of("zone", zone.toString()))
                .resultSetObjISQL(getConnection(), bean);

    }

    public <T> List<T> getTableData(Class<T> bean, String tableName, String [] excludes) throws IOException, SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName, String [] excludes, Map<String, Object> include) throws IOException, SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setIncludes(include)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, String [] excludes, Map<String, Object> include) throws IOException, SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setIncludes(include)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    private List<Object[]> getTable(String [] columns, String table) throws IOException, SQLException {

        return new Select()
                .setColumns(columns)
                .setTableName(table)
                .resultSetISQL(getConnection());
    }

    private List<Object[]> getTable(String [] columns, String table, int limit) throws IOException, SQLException {

        return new Select()
                .setColumns(columns)
                .setTableName(table)
                .setLimit(limit)
                .resultSetISQL(getConnection());
    }

    public void updateCustomerSno() throws IOException, SQLException {
        String sql = "SELECT custno FROM customers";
        Connection con = getConnection();
        ResultSet rs = con.createStatement().executeQuery(sql);
        int i = 1;
        while (rs.next()){
            String sql2 = "UPDATE customers SET sno = "+i+" WHERE custno = '"+rs.getString("custno")+"'";
            con.createStatement().executeUpdate(sql2);
            ++i;
            System.out.println(i);
        }
    }

    private Connection getConnection(){
        if(con == null){
            TBSConnect tbsConnect = new TBSConnect();
            return tbsConnect.getConnection(dsn);
        }
        return con;
    }

    private <T> String objToJson(List<T> T){
        return new Gson().toJson(T);
    }
}
