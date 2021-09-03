package tbs.io;

import com.google.gson.Gson;
import io.Console;
import sql.HPSQL;
import sql.Insert;
import sql.Select;
import sql.Update;
import tbs.models.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TBSTables {
    private Connection con = null;
    private String dsn;
    private final String pDsn;
    private HPSQL hpsql;
    private TbsMini mini;
    private String agencyName;
    public static final String COMMERCIAL_DEPARTMENT = "COMMERCIAL";
    private final String[] excludes = {"agency", "sno", "year", "seen", "leakPictureBase"};
    private Drive drive = Drive.C;

    public TBSTables(String dsn) throws IOException, SQLException {
        this.dsn = this.pDsn = dsn;
        common();
    }
    public TBSTables() throws IOException, SQLException {
        common();
        this.dsn = this.pDsn = mini.getCurrentDsn();
    }

    public TBSTables(Drive drive) throws IOException, SQLException {
        this.drive = drive;
        common();
        this.dsn = this.pDsn = mini.getCurrentDsn();
    }

    public TBSTables(String dsn, Drive drive) throws IOException, SQLException {
        this.drive = drive;
        this.dsn = this.pDsn = dsn;
        common();
    }

    private void common() throws IOException, SQLException {
        this.hpsql = new HPSQL();
        mini = new TbsMini(drive);
        agencyName = mini.getAgencyName().toLowerCase();
    }

    private void useGENE(){
        dsn = mini.getGeneDsn();

    }

    private void useMain(){
        dsn = pDsn;
    }


    public int addCustomer(Object bean) throws SQLException, IOException {
        //retrieve last id , increment it and update it, use incremented for new customer.

        return new Insert()
                .setBean(bean)
                .setTableName("customers")
                .setExcludes("id", "seen", "agency", "propertyPhotoBase", "ownerPhotoBase", "occupantPhotoBase")
                .prepareBean(getConnection());

    }

    public CustomerZones getCustomerDataInc(Object bean, String customerZone) throws SQLException, IOException {
        String tableName = hpsql.getTableNameFromBean(CustomerZones.class);
        List<CustomerZones> zones = new Select()
                .setTableName(tableName)
                .setColumns("lastCustomerNo", "shortName")
                .setWhere(Map.of("description", customerZone))
                .resultSetObjISQL(getConnection(), CustomerZones.class);
        int no = zones.get(0).getLastCustomerNo() + 1;

        new Update()
                .setTableName(tableName)
                .setColumnsAndValue(Map.of("last_customer_no", no))
                .setWhere(Map.of("description", customerZone))
                .prepare(getConnection());
        zones.get(0).setLastCustomerNo(no);
        return zones.get(0);
    }

    /**
     * Get last customer no added for this zone, increments it and update the table. It uses the incremented details
     * to create the customer no.
     * @param bean Customers
     * @return int
     * @throws SQLException exception
     */
    public Customers addCustomer(Customers bean) throws SQLException, IOException {
        //retrieve last id , increment it and update it, use incremented for new customer.
        CustomerZones zone = getCustomerDataInc(bean, bean.getZone());
        Console.log(zone);
        bean.setCustno(getCustomerNo(zone));

        new Insert()
                .setBean(bean)
                .setTableName("customers")
                .setExcludes("id", "seen", "agency", "propertyPhotoBase", "ownerPhotoBase", "occupantPhotoBase")
                .prepareBean(getConnection());
        return bean;
    }

    private String getCustomerNo(CustomerZones zones){
        return mini.getDBPrefix()+"/"+zones.getShortName()+"/"+prependZero(5, zones.getLastCustomerNo()+"");
    }

    public String prependZero(int length, String data){
        if(length > data.length()){
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length-data.length(); i++) {
                builder.append(0);
            }
            builder.append(data);
            return builder.toString();
        }
       return data;
    }

    public int addCustomerComplaints(Object bean) throws SQLException, IOException {
        return new Insert()
                .setBean(bean)
                .setExcludes("agency")
                .setTableName("customer_complaints")
                .prepareBean(getConnection());
    }

    public <T> List<T> getCustomerComplaints(Class<T> bean, Map<String, Object> where) throws SQLException, IOException {
        return new Select()
                .setTableName("customer_complaints")
                .setColumns(hpsql.getColumnFromClass(bean))
                .setWhere(where)
                .setExclude("agency")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getCustomerCategories(Class<T> bean) throws SQLException, IOException {
        return getTableData(bean, "customer_category");
    }

    public <T> List<T> getCustomerTypes(Class<T> bean) throws SQLException, IOException {
        return getTableData(bean, "customer_types");
    }

    public CustomerConstants getCustomerConstants() throws SQLException, IOException {
        useMain();
        List<ComplaintCodes> complaintCodes = getTableData(ComplaintCodes.class);
        List<CustomerCategory> categories = getTableData(CustomerCategory.class);
        List<CustomerTypes> types = getTableData(CustomerTypes.class);
        List<CustomerServiceAreas> serviceAreas = getTableData(CustomerServiceAreas.class);
        List<SubZones> subZones = getTableData(SubZones.class, "customer_cc");
        List<CustomerStreets> streets = getTableData(CustomerStreets.class);
        List<CustomerSubacctCategories> subacctCategories = getTableData(CustomerSubacctCategories.class);
        List<Schemes> schemes = getTableData(Schemes.class);

        return new CustomerConstants(complaintCodes, categories, types, serviceAreas, subZones, streets, subacctCategories, schemes);
    }

    public String getCustomerConstantsJson() throws SQLException, IOException {
        return objToJson(getCustomerConstants());
    }

    public Tariffs getTariffs() throws SQLException, IOException {
        useMain();
        List<BoreholeLicenseTarrifs> boreholeLicenseTariffs = getTableData(BoreholeLicenseTarrifs.class);
        List<MeteredWaterTarrifs> meteredWaterTarrifs = getTableData(MeteredWaterTarrifs.class);
        List<UnmeteredWaterTarrifs> unmeteredWaterTarrifs = getTableData(UnmeteredWaterTarrifs.class);
        List<SewerageTarrifs> sewerageTarrifs = getTableData(SewerageTarrifs.class);

        return new Tariffs(boreholeLicenseTariffs, sewerageTarrifs, meteredWaterTarrifs, unmeteredWaterTarrifs);

    }

    public String getTariffsJson() throws SQLException, IOException {
        return objToJson(getTariffs());
    }

    public EmployeeUsers getEmployeeAndUsers() throws SQLException, IOException {

        return new EmployeeUsers(getEmployees(HrMaster.class), getUsers(Users.class));
    }

    public <T> List <T> getCommercialEmployees(Class<T> bean) throws SQLException, IOException {
        return getEmployees(bean, Map.of("department", COMMERCIAL_DEPARTMENT));
    }

    public <T> String getCommercialEmployeesJson(Class<T> bean) throws SQLException, IOException {
        useMain();
        return objToJson(getCommercialEmployees(bean));
    }

    public <T> List <T> getEmployees(Class<T> bean, Map<String, Object> where) throws SQLException, IOException {
        useMain();
        return new Select()
                .setTableName("hr_master")
                .setColumns(hpsql.getColumnFromClass(bean))
                .setIncludes(Map.of("agency", agencyName))
                .setExclude("agency", "id")
                .setWhere(where)
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List <T> getEmployees(Class<T> bean) throws SQLException, IOException {
        useMain();
        return new Select()
                .setTableName("hr_master")
                .setIncludes(Map.of("agency", agencyName))
                .setExclude("agency", "id")
                .setColumns(hpsql.getColumnFromClass(bean))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getEmployeesJson(Class<T> bean, Map<String, Object> where) throws SQLException, IOException {
        return objToJson(getEmployees(bean, where));
    }

    public <T> String getEmployeesJson(Class<T> bean) throws SQLException, IOException {
        return objToJson(getEmployees(bean));
    }

    public <T> List<T> getComplaintsFileMovement(Class<T> bean, Map<String, Object> where) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("agency")
                .setWhere(where)
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getComplaintsFileMovementJson(Class<T> bean, Map<String, Object> where) throws SQLException, IOException {
        return objToJson(getComplaintsFileMovement(bean, where));
    }

    public <T> List<T> getZonalCustomers(Class<T> bean, String zone) throws SQLException, IOException {
        useMain();
        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("id", "seen", "agency", "propertyPhotoBase", "ownerPhotoBase", "occupantPhotoBase", "propertyPhoto", "ownerPhoto", "propertyPhoto")
                .setIncludes(Map.of("agency", agencyName))
                .setTableName("customers")
                .setWhere(Map.of("zone", zone))
                .resultSetObjISQL(getConnection(), bean);

    }

    public <T> void getStreamedData(Class<T> bean, int limit, String orderColumn, Consumer<String> consumer) throws SQLException, IOException {
        int offset = 1; boolean loop = true;
        while(loop){

            List<T> table = new Select()
                    .setTableName(hpsql.getTableNameFromBean(bean))
                    .setColumns(hpsql.getColumnFromClass(bean))
                    .setExclude("sno", "agency", "id", "year", "seen")
                    .setIncludes(Map.of("agency", agencyName))
                    .setLimit(limit)
                    .setOffset(offset)
                    .setOrder(HPSQL.Order.ASC)
                    .setOrderColumn(orderColumn)
                    .resultSetObjISQL(getConnection(), bean);
            offset += table.size();
            loop = table.size() > 0;
            if(loop){
                consumer.accept(objToJson(table));
            }
        }
    }

    public <T> void getStreamedData(Class<T> bean, int limit, String orderColumn, Map<String, Object> where, Consumer<String> consumer) throws SQLException, IOException {
        int offset = 1; boolean loop = true;
        while(loop){

            List<T> table = new Select()
                    .setTableName(hpsql.getTableNameFromBean(bean))
                    .setColumns(hpsql.getColumnFromClass(bean))
                    .setExclude("sno", "agency", "id", "year", "seen")
                    .setIncludes(Map.of("agency", agencyName))
                    .setLimit(limit)
                    .setWhere(where)
                    .setOffset(offset)
                    .setOrder(HPSQL.Order.ASC)
                    .setOrderColumn(orderColumn)
                    .resultSetObjISQL(getConnection(), bean);
            offset += table.size();
            loop = table.size() > 0;
            if(loop){
                consumer.accept(objToJson(table));
            }
        }
    }

    public <T> void getStreamedZonalCustomers(Class<T> bean, String zone, int limit, Consumer<String> consumer) throws SQLException, IOException {
        int offset = 1; boolean loop = true;
            while(loop){
                List<T> customers = szc(bean, zone, limit, offset);
                offset += customers.size();
                loop = customers.size() > 0;
                if(loop){
                    consumer.accept(objToJson(customers));
                }
            }
    }


    private <T> List<T> szc(Class<T> bean, String zone, int limit, int offset) throws SQLException, IOException {
        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("id", "seen", "agency")
                .setIncludes(Map.of("agency", agencyName))
                .setLimit(limit)
                .setOffset(offset)
                .setOrder(HPSQL.Order.ASC)
                .setOrderColumn("sno")
                .setTableName("customers")
                .setWhere(Map.of("zone", zone))
                .resultSetObjISQL(getConnection(), bean);
    }

    private Consumer<String> str (){
        String data;
        return new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        };
    }

    public <T> String getZonalCustomersJson(Class<T> bean, String zone) throws SQLException, IOException {

        return objToJson(getZonalCustomers(bean, zone));
    }

    public <T> List<T> getZones(Class<T> bean) throws SQLException, IOException {
        useMain();
        return new Select()
                .setTableName("customer_zones")
                .setColumns(hpsql.getColumnFromClass(bean))
                .setIncludes(Map.of("agency",agencyName.toLowerCase()))
                .setExclude("agency")
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getZonesJson(Class<T> bean) throws SQLException, IOException {
        return new Gson().toJson(getZones(bean));
    }

    public <T> List<T> getUsers(Class<T> bean) throws SQLException, IOException {
        useGENE();
        List<T> lists =  getTableData(bean, "users", Map.of("company_id", "1"));
        useMain();
        return lists;
    }

    public <T> String getUsersJson(Class<T> bean) throws SQLException, IOException {
        return objToJson(getUsers(bean));
    }

    public <T> List<T> getArparm(Class<T> bean) throws SQLException, IOException {
        useMain();
        return getTableData(bean, "arparm");
    }

    public <T> String getArparmJson(Class<T> bean) throws SQLException, IOException {
        return objToJson(getArparm(bean));
    }

    public <T> List<T> getCustomers(Class<T> bean) throws SQLException, IOException {
        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setExclude("id", "seen", "agency")
                .setTableName("customers")
                .resultSetObjISQL(getConnection(), bean);

    }
    /*
    Schemes
     */
    public List<Object[]> getSchemes(String [] columns) throws SQLException, IOException {
        return getTable(columns, "schemes");
    }

    /*
    Enumeration
     */
    public int addCustomerEnumeration(Object bean) throws SQLException, IOException {
        return new Insert()
                .setBean(bean)
                .setTableName("customers_enumeration")
                .prepareBean(getConnection());

    }

    public <T> List<T> getZonalCustomersEnumeration(Class<T> bean, Object zone) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName("customers_enumeration")
                .setWhere(Map.of("zone", zone.toString()))
                .resultSetObjISQL(getConnection(), bean);

    }






    public int addTableData(Object bean, String tableName) throws SQLException, IOException {
        return new Insert()
                .setTableName(tableName)
                .setBean(bean)
                .setExcludes("agency", "id", "seen")
                .prepareBean(getConnection());
    }

    public int addTableData(Object bean, String tableName, String ...excludes) throws SQLException, IOException {
        return new Insert()
                .setTableName(tableName)
                .setBean(bean)
                .setExcludes(excludes)
                .prepareBean(getConnection());
    }

    public int addTableData(Object bean) throws SQLException, IOException {
        return new Insert()
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setBean(bean)
                .setExcludes(excludes)
                .prepareBean(getConnection());
    }

    public <T> void addTableDataMany(List<T> beans) throws IOException, SQLException {
        Connection con = getConnection();
        beans.forEach(bean -> {
            try {
                new Insert()
                        .setTableName(hpsql.getTableNameFromBean(bean))
                        .setBean(bean)
                        .setExcludes(excludes)
                        .prepareBean(con);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public int addTableData(Object bean, String ...excludes) throws SQLException, IOException {

        return new Insert()
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setBean(bean)
                .setExcludes(excludes)
                .prepareBean(getConnection());
    }

    public <T> List<T> getTableData(Class<T> bean) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setExclude("agency", "id")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, Map<String, Object> where) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setWhere(where)
                .setExclude("agency", "id")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setExclude("agency", "id")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName,  Map<String, Object> where) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setWhere(where)
                .setExclude("agency", "id")
                .setIncludes(Map.of("agency", agencyName))
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> String getTableDataJson(Class<T> bean) throws SQLException, IOException {

        return objToJson(getTableData(bean));
    }

    public <T> String getTableDataJson(Class<T> bean, String tableName) throws SQLException, IOException {

        return objToJson(getTableData(bean, tableName));
    }

    public <T> String getTableDataJson(Class<T> bean, Map<String, Object> where) throws SQLException, IOException {

        return objToJson(getTableData(bean, where));
    }

    public <T> String getTableDataJson(Class<T> bean, String tableName, Map<String, Object> where) throws SQLException, IOException {

        return objToJson(getTableData(bean, tableName, where));
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName, String [] excludes) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, String tableName, Map<String, Object> include, String [] excludes) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(tableName)
                .setIncludes(include)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    public <T> List<T> getTableData(Class<T> bean, Map<String, Object> include, String [] excludes) throws SQLException, IOException {

        return new Select()
                .setColumns(hpsql.getColumnFromClass(bean))
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setIncludes(include)
                .setExclude(excludes)
                .resultSetObjISQL(getConnection(), bean);
    }

    private List<Object[]> getTable(String [] columns, String table) throws SQLException, IOException {

        return new Select()
                .setColumns(columns)
                .setTableName(table)
                .resultSetISQL(getConnection());
    }

    private List<Object[]> getTable(String [] columns, String table, int limit) throws SQLException, IOException {

        return new Select()
                .setColumns(columns)
                .setTableName(table)
                .setLimit(limit)
                .resultSetISQL(getConnection());
    }

    public int updateAll(Object bean) throws SQLException, IOException {
        return new Update()
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setColumnsAndValue(bean)
                .setExclude(excludes)
                .prepare(getConnection());

    }

    public int updateOne(Object bean, String ...beanProp) throws SQLException, IOException {
        return new Update()
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setColumnsAndValue(bean)
                .setExclude(excludes)
                .setWhere(bean, beanProp)
                .prepare(getConnection());

    }

    public int updateOne(Object bean, Map<String, Object> where) throws SQLException, IOException {
        return new Update()
                .setTableName(hpsql.getTableNameFromBean(bean))
                .setColumnsAndValue(bean)
                .setExclude(excludes)
                .setWhere(where)
                .prepare(getConnection());

    }

    public <T> void updateMany(List<T> beans, String ...beanProp) throws IOException, SQLException {
        Connection con = getConnection();
        beans.forEach(bean -> {
            try {
                new Update()
                        .setTableName(hpsql.getTableNameFromBean(bean))
                        .setColumnsAndValue(bean)
                        .setExclude(excludes)
                        .setWhere(bean, beanProp)
                        .prepare(con);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public <T> void updateMany(List<T> beans, Map<String, Object> where) throws SQLException, IOException {
        Connection con = getConnection();
        beans.forEach(bean -> {
            try {
                new Update()
                        .setTableName(hpsql.getTableNameFromBean(bean))
                        .setColumnsAndValue(bean)
                        .setExclude(excludes)
                        .setWhere(where)
                        .prepare(con);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
    public void updateCustomerSno() throws SQLException, IOException {
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

    public Connection getConnection() throws IOException, SQLException {

        if(con == null || con.isClosed()){
            TBSConnect tbsConnect = new TBSConnect(drive);
            con = tbsConnect.getConnection(dsn);
            return con;
        }
        return con;
    }

    public  <T> String objToJson(List<T> T){
        return new Gson().toJson(T);
    }


    public String objToJson(Object bean){
        return new Gson().toJson(bean);
    }
}
