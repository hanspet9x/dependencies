package sql;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Insert extends HPSQL{
    //INSERT INTO School(name, school) VALUES('ade', 'louis');
    private Map<String, Object> columnsAndValue = null;
    private Object bean = null;
    private Object[] beans = null;
    private Map<String, String> keySwapping = null;
    private String [] excludes = null;
    private String tableName = null;
    AtomicInteger atomicInteger = new AtomicInteger(1);

    public Insert setColumnsAndValue(Map<String, Object> columnsAndValue) {
        this.columnsAndValue = columnsAndValue;
        return this;
    }

    public Insert setKeySwapping(Map<String, String> keySwapping) {
        this.keySwapping = keySwapping;
        return this;
    }

    public Insert setExcludes(String ... excludes) {
        this.excludes = excludes;
        return this;
    }

    public Insert setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Insert setBean(Object bean) {
        this.bean = bean;
        return this;
    }


    public PreparedStatement prepare(Connection con) throws SQLException {
        StringBuilder sb = new StringBuilder();

        JSONObject data = setUp(sb);
        String [] cvprs = columnsAndValuesPreparedStatementSnakeCamel(data);

        sb.append(cvprs[0]);
        sb.append(") VALUES( ");
        sb.append(cvprs[1]);
        sb.append(" )");

        PreparedStatement preparedStatement = con.prepareStatement(sb.toString());

        Map<String, Object> map = data.toMap();

        map.forEach((key, value) -> {
            try {
                preparedStatement.setObject(atomicInteger.getAndIncrement(), value);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return preparedStatement;
    }

    public String build(){
        StringBuilder sb = new StringBuilder();

        Object [] object = columnsAndValuesSnakeCase(setUp(sb));

        sb.append(object[0]);
        sb.append(") VALUES( ");
        sb.append(object[1]);
        sb.append(" )");

        return sb.toString();
    }

    public int prepareBean(Connection con) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName == null ? getBeanName(bean) : tableName);
        sb.append(" ( ");

        Object [] keyValue;

        if(excludes != null && keySwapping != null){
            keyValue = columnAndValuesSnakeCasePreparedStatement(bean, excludes, keySwapping);
        }else if(excludes != null) {
            keyValue = columnAndValuesSnakeCasePreparedStatement(bean, excludes);
        }else if(keySwapping != null) {
            keyValue = columnAndValuesSnakeCasePreparedStatement(bean, keySwapping);
        }else{
            keyValue = columnAndValuesSnakeCasePreparedStatement(bean);
        }


        sb.append(keyValue[0]);
        sb.append(")");
        sb.append(" VALUES (");

        sb.append(keyValue[1]);
        sb.append(")");

        PreparedStatement prs = con.prepareStatement(sb.toString());
        Arrays.stream((Object[])keyValue[2]).forEach(data -> {
            System.out.println(data);
            try {
                prs.setObject(atomicInteger.getAndIncrement(), data);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        int i = prs.executeUpdate();
        prs.close();
        con.close();
        return i;
    }

    private JSONObject setUp(StringBuilder sb){

        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" (");

        JSONObject data = new JSONObject(columnsAndValue);

        if (excludes != null){
            data = excludeRow(data, excludes);
        }

        if(keySwapping != null){
            data = replaceColumn(data, keySwapping);
        }
        return data;
    }
}