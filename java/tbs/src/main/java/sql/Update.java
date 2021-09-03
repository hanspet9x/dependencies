package sql;

import io.Console;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Update extends HPSQL{

    //UPDATE School SET(name = 'ade', school = 'louis') WHERE id = 1 AND name= 'school' ;

    private Map<String, Object> columnsAndValue;
    private Map<String, Object> where = null;
    private String tableName;
    private String [] exclude = null;
    private Map<String, String> keySwapping = null;
    private Object whereValue = null;
    private Object[] objects = null;
    private HPSQL.Operands operands = HPSQL.Operands.AND;


    public Update setColumnsAndValue(Map<String, Object> columnsAndValue) {
        this.columnsAndValue = columnsAndValue;
        return this;
    }

    public Update setColumnsAndValue(Object bean){
        columnsAndValue = new HashMap<>();
        Map<String, Object> map = new JSONObject(bean).toMap();
        map.forEach((s, o) -> columnsAndValue.put(fromCamelCase(s), o));
        return this;
    }

    public Update setWhere(Map<String, Object> where) {
        this.where = where;
        return this;
    }

    public Update setWhere(Object bean, String ...where){
        this.where = new HashMap<>();
        Arrays.stream(where).forEach(getterName -> {
            try {
                this.where.putIfAbsent(fromCamelCase(getterName), getMethodValue(bean, getterName));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return this;
    }

    public Update setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Update setOperands(HPSQL.Operands operands) {
        this.operands = operands;
        return this;
    }

    public Update setExclude(String ...exclude) {
        this.exclude = exclude;
        return this;
    }

    public Update setKeySwapping(Map<String, String> keySwapping) {
        this.keySwapping = keySwapping;
        return this;
    }



    public String build(){
        StringBuilder sb = new StringBuilder();
        JSONObject data = setUp(sb);
        Object cnv = keyEqualValue(data, ",");

        sb.append(cnv);
        sb.append(" ) ");

        if(whereValue != null){
            sb.append("WHERE ");
            sb.append(whereValue);

        }
        return sb.toString();
    }

    public int prepare(Connection con) throws SQLException {
        StringBuilder sb = new StringBuilder();
        JSONObject data = setUp(sb);

        sb.append(keyEqualValuePreparedStatement(data, ","));
        if(whereValue != null){
            sb.append(" WHERE ");
            sb.append(whereValue);
        }
        System.out.println(sb);
        PreparedStatement prs = con.prepareStatement(sb.toString());
        AtomicInteger atomicInteger = new AtomicInteger(1);
        data.toMap().forEach((key, value) -> {
            try {
                prs.setObject(atomicInteger.getAndIncrement(), value);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        int i = prs.executeUpdate();
        prs.close();
        return i;
    }

    private JSONObject setUp(StringBuilder sb){

        sb.append("UPDATE ");

        sb.append(tableName);

        sb.append(" SET ");

        JSONObject data = new JSONObject(columnsAndValue);

        if(exclude != null){
            Arrays.asList(exclude).forEach(Console::log);
            data = excludeRow(data, exclude);
            Console.log("CLEANSED", data);
        }

        if(keySwapping != null){
            data = replaceColumn(data, keySwapping);
        }


        if(where != null){
            whereValue = keyEqualValue(where, parseOperand(operands));
        }

        return data;

    }
}

