package tbs.io;

import com.google.gson.Gson;
import sql.HPSQL;
import sql.Insert;
import sql.Select;
import tbs.mini.TbsMini;
import tbs.models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TBSTables {
    private Connection con = null;
    private String dsn, pDsn;
    private HPSQL hpsql;
    private TbsMini mini;
    private String agencyName;

    public TBSTables(String dsn) {
        this.dsn = this.pDsn = dsn;
        common();
    }

    public TBSTables() {
        common();
        this.dsn = this.pDsn = mini.getCurrentDsn();
    }

    private void common(){
        this.hpsql = new HPSQL();
        mini = new TbsMini();
        agencyName = mini.getAgencyName().toLowerCase();
    }

    private void useGENE(){
        dsn = mini.getGeneDsn();

    }

    private void useMain(){
        dsn = pDsn;
    }


    public int addCustomer(Object bean) throws  SQLException {

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

    public <T> List<T> getCustomerComplaints(Class<T> bean, Map<String, Object> where) throws SQLException {
        return new Select()
                .setTableName("customer_complaints")
                .setColumns(hpsql.getColumnFromClass(bean))
                .setWhere(where)
                .setExclude("agency")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getCustomerCategories(Class<T> bean) throws SQLException {
        return getTableData(bean, "customer_category");
    }

    public <T> List<T> getCustomerTypes(Class<T> bean) throws SQLException {
        return getTableData(bean, "customer_types");
    }

    public CustomerConstants getCustomerConstants() throws SQLException {
        List<ComplaintCodes> complaintCodes = getTableData(ComplaintCodes.class);
        List<CustomerCategory> categories = getTableData(CustomerCategory.class);
        List<CustomerTypes> types = getTableData(CustomerTypes.class);
        List<CustomerServiceAreas> serviceAreas = getTableData(CustomerServiceAreas.class);
        List<SubZones> subZones = getTableData(SubZones.class, "customer_cc");
        List<CustomerStreets> streets = getTableData(CustomerStreets.class);
        List<CustomerSubacctCategories> subacctCategories = getTableData(CustomerSubacctCategories.class);

        return new CustomerConstants(complaintCodes, categories, types, serviceAreas, subZones, streets, subacctCategories);
    }

    public String getCustomerConstantsJson() throws SQLException {
        return objToJson(getCustomerConstants());
    }
    public Tariffs getTariffs() throws SQLException {
        List<BoreholeLicenseTarrifs> boreholeLicenseTariffs = getTableData(BoreholeLicenseTarrifs.class);
        List<MeteredWaterTarrifs> meteredWaterTarrifs = getTableData(MeteredWaterTarrifs.class);
        List<UnmeteredWaterTarrifs> unmeteredWaterTarrifs = getTableData(UnmeteredWaterTarrifs.class);
        List<SewerageTarrifs> sewerageTarrifs = getTableData(SewerageTarrifs.class);

        return new Tariffs(boreholeLicenseTariffs, sewerageTarrifs, meteredWaterTarrifs, unmeteredWaterTarrifs);

    }

    public String getTariffsJson() throws SQLException {
        return objToJson(getTariffs());
    }

    public <T> List <T> getEmployees(Class<T> bean, Map<String, Object> where) throws SQLException {
        return new Select()
                .setTableName("hr_master")
                .setColumns(hpsql.getColumnFromClass(bean))
                .setWhere(where)
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List <T> getEmployees(Class<T> bean) throws SQLException {
        return new Select()
                .setTableName("hr_master")
                .setColumns(hpsql.getColumnFromClass(bean))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getEmployeesJson(Class<T> bean, Map<String, Object> where) throws SQLException {
        return objToJson(getEmployees(bean, where));
    }

    public <T> String getEmployeesJson(Class<T> bean) throws SQLException {
        return objToJson(getEmployees(bean));
    }

    public <T> List<T> getComplaintsFileMovement(Class<T> bean, Map<String, Object> where) throws SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("agency")
                .setWhere(where)
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getComplaintsFileMovementJson(Class<T> bean, Map<String, Object> where) throws SQLException {
        return objToJson(getComplaintsFileMovement(bean, where));
    }

    public <T> List<T> getZonalCustomers(Class<T> bean, String zone, Map<String, Object> includes) throws  SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("id", "seen", "agency")
                .setIncludes(includes)
                .setTableName("customers")
                .setWhere(Map.of("zone", zone))
                .resultSetObjISQL(getConnection(), bean);

    }

    public <T> String getZonalCustomersJson(Class<T> bean, String zone, Map<String, Object> includes) throws  SQLException {
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
        return getTableData(bean, "users");
    }

    public <T> String getUsersJson(Class<T> bean) throws SQLException {
        return objToJson(getUsers(bean));
    }

    public <T> List<T> getArparm(Class<T> bean) throws SQLException {
        useMain();
        return getTableData(bean, "arparm");
    }

    public <T> String getArparmJson(Class<T> bean) throws SQLException {
        return objToJson(getArparm(bean));
    }

    public <T> List<T> getCustomers(Class<T> bean) throws  SQLException {
        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("id", "seen", "agency")
                .setTableName("customers")
                .resultSetObjISQL(getConnection(), bean);

    }
    /*
    Schemes
     */
    public List<Object[]> getSchemes(String [] columns) throws  SQLException {
        return getTable(columns, "schemes");
    }

    /*
    Enumeration
     */
    public int addCustomerEnumeration(Object bean) throws SQLException {

        return new Insert()
                .setBean(bean)
                .setTableName("customers_enumeration")
                .prepareBean(getConnection());

    }

    public <T> List<T> getZonalCustomersEnumeration(Class<T> bean, Object zone) throws SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName("customers_enumeration")
                .setWhere(Map.of("zone", zone.toString()))
                .resultSetObjISQL(getConnection(), bean);

    }

    public int addTableData(Object bean, String tableName) throws SQLException {
        return new Insert()
                .setTableName(tableName)
                .setBean(bean)
                .setExcludes("agency", "id", "seen", "code")
                .prepareBean(getConnection());
    }

    public int addTableData(Object bean) throws SQLException {
        return new Insert()
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setBean(bean)
                .setExcludes("agency", "id", "seen", "code")
                .prepareBean(getConnection());
    }

    public <T> List<T> getTableData(Class<T> bean) throws SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setExclude("agency", "id")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, Map<String, Object> where) throws SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setWhere(where)
                .setExclude("agency", "id")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName) throws SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setExclude("agency", "id")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName,  Map<String, Object> where) throws SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setWhere(where)
                .setExclude("agency", "id")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getTableDataJson(Class<T> bean) throws SQLException {

        return objToJson(getTableData(bean));
    }

    public <T> String getTableDataJson(Class<T> bean, String tableName) throws SQLException {

        return objToJson(getTableData(bean, tableName));
    }

    public <T> String getTableDataJson(Class<T> bean, Map<String, Object> where) throws SQLException {

        return objToJson(getTableData(bean, where));
    }

    public <T> String getTableDataJson(Class<T> bean, String tableName, Map<String, Object> where) throws SQLException {

        return objToJson(getTableData(bean, tableName, where));
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName, String [] excludes) throws  SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName, Map<String, Object> include, String [] excludes) throws  SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setIncludes(include)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, Map<String, Object> include, String [] excludes) throws  SQLException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setIncludes(include)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    private List<Object[]> getTable(String [] columns, String table) throws  SQLException {

        return new Select()
                .setColumns(columns)
                .setTableName(table)
                .resultSetISQL(getConnection());
    }

    private List<Object[]> getTable(String [] columns, String table, int limit) throws  SQLException {

        return new Select()
                .setColumns(columns)
                .setTableName(table)
                .setLimit(limit)
                .resultSetISQL(getConnection());
    }

    public void updateCustomerSno() throws  SQLException {
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

    public  <T> String objToJson(List<T> T){
        return new Gson().toJson(T);
    }

    public  <T> String objToJson(T bean){
        return new Gson().toJson(bean);
    }
}
