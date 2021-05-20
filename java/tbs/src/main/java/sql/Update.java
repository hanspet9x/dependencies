package sql;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private HPSQL.Operands operands = HPSQL.Operands.AND;


    public void setColumnsAndValue(Map<String, Object> columnsAndValue) {
        this.columnsAndValue = columnsAndValue;
    }

    public void setWhere(Map<String, Object> where) {
        this.where = where;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setOperands(HPSQL.Operands operands) {
        this.operands = operands;
    }

    public void setExclude(String[] exclude) {
        this.exclude = exclude;
    }

    public void setKeySwapping(Map<String, String> keySwapping) {
        this.keySwapping = keySwapping;
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

    public PreparedStatement prepare(Connection con) throws SQLException {
        StringBuilder sb = new StringBuilder();
        JSONObject data = setUp(sb);

        sb.append(keyEqualValuePreparedStatement(data, ","));
        sb.append(" ) ");

        if(whereValue != null){
            sb.append("WHERE ");
            sb.append(whereValue);

        }

        PreparedStatement prs = con.prepareStatement(sb.toString());
        AtomicInteger atomicInteger = new AtomicInteger(1);
        data.toMap().forEach((key, value) -> {
            try {
                prs.setObject(atomicInteger.incrementAndGet(), value);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        return prs;
    }

    private JSONObject setUp(StringBuilder sb){
        sb.append("UPDATE ");
        sb.append(tableName);
        sb.append(" SET ( ");
        JSONObject data = new JSONObject(columnsAndValue);

        if(exclude != null){
            data = excludeRow(data, exclude);
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

