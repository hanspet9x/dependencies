package sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Select extends HPSQL{
    private String[] columns;
    private String[] exclude = null;
    private Map<String, Object> includes = null;
    private String tableName = null;
    private Map<String, Object> where = null;
    private Map<String, String> changeColumns = null;
    private boolean isDistinct = false;
    private HPSQL.Order order = HPSQL.Order.ASC;
    private HPSQL.Operands whereOperand = HPSQL.Operands.AND;
    private String orderColumn = null;
    private int limit = 0;
    private int offset = 0;

    public Select() {
    }

    public Select setColumns(String ...columns) {
        this.columns = columns;
        return this;
    }

    /**
     * It accepts a Map of snaked bean property and a corresponding table column name.
     * E.g If bean property is userLog but tableColumn is userlog, parameters should be,
     * user_log and userlog.
     * @param snakedNameColumnName String
     * @return Select
     */
    public Select setChangeColumn(Map<String, String> snakedNameColumnName){
        changeColumns = snakedNameColumnName;
        return this;
    }

    public Select setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Select setWhere(Map<String, Object> where, HPSQL.Operands whereOperand) {
        this.where = where;
        this.whereOperand = whereOperand;
        return this;
    }

    public Select setWhere(Map<String, Object> where) {
        this.where = where;
        return this;
    }

    public Select setDistinct(boolean distinct) {
        isDistinct = distinct;
        return this;
    }

    public Select setOrder(HPSQL.Order order) {
        this.order = order;
        return this;
    }

    public Select setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
        return this;
    }

    public Select setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public Select setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public Select setExclude(String ...exclude) {
        this.exclude = exclude;
        return this;
    }

    public Select setIncludes(Map<String, Object> includes) {
        this.includes = includes;
        return this;
    }

    private void processExclude(){
        if(exclude != null){
            List<String> excList = List.of(exclude);
            setColumns(Arrays.stream(columns)
                    .filter(data -> !excList.contains(data))
                    .toArray(String[]::new));
        }
    }

    private String[] processColumnsChange(String [] snakedColumns){
        if (changeColumns == null) return snakedColumns;
        return Arrays.stream(snakedColumns)
                .map(column -> {
                    if(changeColumns.containsKey(column)){
                        return changeColumns.get(column);
                    }
                    return column;
                }).toArray(String[]::new);
    }

    private String getChangedColumn(String snakeCase){
        if(changeColumns == null) return snakeCase;
        return changeColumns.getOrDefault(snakeCase, snakeCase);
    }

    public String buildISQL(){
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");

        if(isDistinct){
            sb.append("DISTINCT ");
        }

        if(limit > 0){
            sb.append("TOP ");
            sb.append(limit);
            sb.append(" ");
        }

        if(offset > 0){
            sb.append(" START AT ");
            sb.append(offset);
        }

        processExclude();

        String [] newColumns = processColumnsChange(getSnakeCaseColumnFromCamel(columns));
        //set columns
        sb.append(getCommaSeparated(newColumns));
        sb.append(" FROM ");
        sb.append(tableName);

        if(where != null){
            sb.append(" WHERE ");
            sb.append(keyEqualValue(where, parseOperand(whereOperand)));
        }

        if(orderColumn != null){
            sb.append(" ORDER BY ");
            sb.append(orderColumn);
            sb.append(" ");
            sb.append(parseOrder(order));
            sb.append(" ");
        }
        return sb.toString();
    }

    private String build(){
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");

        if(isDistinct){
            sb.append("DISTINCT");
            sb.append(" ");
        }

        processExclude();

        //set columns
        sb.append(getCommaSeparated(processColumnsChange(getSnakeCaseColumnFromCamel(columns))));
        sb.append(" FROM ");
        sb.append(tableName);
        if(where != null){
            sb.append(" WHERE ");
            sb.append(keyEqualValue(where, parseOperand(whereOperand)));

        }
        if(orderColumn != null){
            sb.append(" ORDER BY ");
            sb.append(orderColumn);
            sb.append(" ");
            sb.append(parseOrder(order));
            sb.append(" ");
        }

        if(limit > 0){
            sb.append(" LIMIT ");
            sb.append(limit);
        }

        if(offset > 0){
            sb.append(" OFFSET ");
            sb.append(offset);
        }

        return sb.toString();
    }

    public List<Object[]> resultSet(Connection con) throws SQLException {
        return resultSetProcess(con, build());
    }

    public List<Object[]> resultSetISQL(Connection con) throws SQLException {
        return resultSetProcess(con, buildISQL());
    }

    /*
    to be developed..
     */
    public <T> List<T> resultSetObjISQL(Connection con, Class<T> bean) throws SQLException {
        return resultSetObj(con, bean, buildISQL());
    }

    private List<Object[]> resultSetProcess(Connection con, String sql) throws SQLException {

        ResultSet rs = con.createStatement().executeQuery(sql);
        List<Object[]> rsList = new ArrayList<>();

        while(rs.next()){

            rsList.add(Arrays.stream(columns).map(column -> {
                try {
                    return rs.getObject(column);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                return new Object[]{};
            }).toArray());
        }
        return rsList;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> resultSetObj(Connection con, Class<T> beanClass, String sql) throws SQLException {

        ResultSet rs = con.createStatement().executeQuery(sql);
        List<T> rsList = new ArrayList<>();
        while (rs.next()){

            try {
                T beanObj = (T) Class.forName(beanClass.getName()).getConstructor().newInstance();
                Arrays.stream(columns)
                        .forEach(column -> {
                            try {

                                String cData = fromSnakeCase(column);

                                Object rowValue = rs.getObject(getChangedColumn(fromCamelCase(column)));
                                Class<?> fieldClass = beanObj.getClass().getDeclaredField(cData).getType();

                                rowValue = rowValue == null ? getDataTypeEmptyValue(fieldClass.getTypeName()) : rowValue;

                                beanObj.getClass()
                                        .getDeclaredMethod(getSetters(cData),fieldClass)
                                        .invoke(beanObj, fieldClass.getTypeName()
                                                .equals(rowValue.getClass().getTypeName())
                                                ? rowValue :
                                                getConvertedData(fieldClass.getTypeName(), rowValue));


                            } catch (NoSuchMethodException | NoSuchFieldException | SQLException | InvocationTargetException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                insertConstantColumnValue(beanObj);
                rsList.add(beanObj);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        con.close();
        return rsList;
    }

    private <T> void insertConstantColumnValue(T beanObj) {
        if(includes != null){
            includes.forEach((column, value) -> {
                try {
                    beanObj.getClass().getDeclaredMethod(getSetters(column),
                            beanObj.getClass().getDeclaredField(column).getType())
                            .invoke(beanObj, value);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
