package sql;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HPSQL contains methods to help construct or complete SQL statement painlessly.
 */
public class HPSQL {


    public String getBeanName(Object bean){
        String className = bean.getClass().getName();
        return className.substring(className.lastIndexOf(".")+1);
    }

    public String getBeanName(Class<?> bean){
        String className = bean.getName();
        return className.substring(className.lastIndexOf(".")+1);
    }

    public String getTableNameFromBean(Class<?> bean){
        return fromCamelCase(getBeanName(bean));
    }

    public String getTableNameFromBean(Object bean){
        return fromCamelCase(getBeanName(bean));
    }

    public Object getDataTypeEmptyValue(String fieldType){
        Object [] obj = new Object[1];
        switch (fieldType){
            case "java.lang.String", "string" -> obj[0] = "";
            case "java.lang.String[]" -> obj[0] = new String[0];
            case "java.lang.Integer", "int" -> obj[0] = 0;
            case "java.lang.Long", "long" -> obj[0] = Long.valueOf("0");
            case "java.lang.Integer[]" -> obj[0] = new Integer[0];
            case "java.lang.Character", "char" -> obj[0] = (char)0;
            case "char[]" -> obj[0] = new char[0];
            case "java.lang.Double", "double" -> obj[0] = (double) 0;
            case "java.math.BigDecimal" -> obj[0] = BigDecimal.valueOf(0);
            case "java.lang.Float", "float" -> obj[0] = (float)0;
            case "byte[]", "byte" -> obj[0] = new byte[0];
        }
        return obj[0];
    }

    public Object getConvertedData(String fieldType, Object rowValue){
        Object [] obj = new Object[1];
        switch (fieldType){
            case "java.lang.String", "string" -> obj[0] = String.valueOf(rowValue);
            case "java.lang.String[]" -> obj[0] = List.of(rowValue).toArray(new String[0]);
            case "java.lang.Integer", "int" -> obj[0] = Integer.valueOf(rowValue.toString());
            case "java.lang.Long", "long" -> obj[0] = Long.valueOf(rowValue.toString());
            case "java.lang.Integer[]" -> obj[0] = List.of(rowValue).toArray(new Integer[0]);
            case "java.lang.Character", "char" -> obj[0] = (char)rowValue;
            case "char[]" -> obj[0] = List.of(rowValue).toArray(new Character[0]);
            case "java.lang.Double", "double" -> obj[0] = Double.valueOf(rowValue.toString());
            case "java.math.BigDecimal" -> obj[0] = BigDecimal.valueOf( Double.parseDouble(rowValue.toString()));
            case "java.lang.Float", "float" -> obj[0] = Float.valueOf(rowValue.toString());
            case "byte[]", "byte" -> obj[0] = List.of(rowValue).toArray(new Byte[0]);
        }
        return obj[0];
    }

    public String getColumnDataType(String tableName, String column, Connection connection) throws SQLException {
        String sql = "SELECT TOP 1 "+column+" FROM "+tableName+"";
        ResultSet rs = connection.createStatement().executeQuery(sql);

            return rs.getObject(column).getClass().getTypeName();

    }

    public String getColumnDataType(Class<?> bean, String column, Connection connection) throws SQLException {
        String sql = "SELECT TOP 1 "+column+" FROM "+getTableNameFromBean(bean)+" WHERE sno = 11";
        ResultSet rs = connection.createStatement().executeQuery(sql);
        rs.next();
        return rs.getObject(column).getClass().getTypeName();

    }

    String parseOperand(Operands operand){
        if(operand == Operands.AND){
            return "AND";
        }else{
            return "OR";
        }
    }

    public String parseOrder(Order order){
        if(order == Order.ASC){
            return "ASC";
        }else{
            return "DESC";
        }
    }

    public enum Order{
        ASC, DESC
    }

    public enum Operands{
        OR, AND
    }

    public Object getMethodValue(Object bean, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return bean.getClass().getDeclaredMethod(getGetters(methodName)).invoke(bean);
    }

    public String[] getColumnFromJson(JSONObject obj){
        List<String> keys = new ArrayList<>();
        obj.keys().forEachRemaining(keys::add);
        return keys.toArray(new String[0]);
    }

    public String[] getColumnFromObject(Object obj){
        return getColumnFromJson(new JSONObject(obj));
    }

    public String[] getColumnFromClass(Class<?> classOfT){
        return Arrays.stream(classOfT.getDeclaredFields())
                .map(Field::getName).toArray(String[]::new);
    }

    public String[] getSnakeColumnFromClass(Class<?> classOfT){
        return Arrays.stream(classOfT.getDeclaredFields())
                .map( field -> fromCamelCase(field.getName())).toArray(String[]::new);
    }

    public String getSetters(String name){
        return (Character.isUpperCase(name.charAt(1)))? "set"+name:"set"+capFirstChar(name);

    }

    public String getGetters(String name){

        return (Character.isUpperCase(name.charAt(1)))? "get"+name:"get"+capFirstChar(name);
    }

    public String capFirstChar(String name){
        return Character.toUpperCase(name.charAt(0))+name.substring(1);
    }

    public String removeSetters(String name){
        String data = name.substring(3);
        return Character.toLowerCase(data.charAt(0))+data.substring(1);
    }
    /**
     * It presents structure used for an insert statement e.g name, school  for index 0
     * and 'ade', 'louis' as index 1.
     * @param jsonObject JSONObject POJO Object with getters and setters.
     * @return String Array
     */
    private Object [] columnsAndValues(JSONObject jsonObject){

        Map<String, Object> map = jsonObject.toMap();
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        map.forEach((key, value) -> {
            keyBuilder.append(key);
            keyBuilder.append(",");

            valueBuilder.append(getDataTypeValue(value));
            valueBuilder.append(",");
        });
        keyBuilder.deleteCharAt(keyBuilder.length()-1);
        valueBuilder.deleteCharAt(valueBuilder.length()-1);

        return new Object[]{keyBuilder.toString(), valueBuilder};
    }

    private Object[] columnAndValues(Object bean){

        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        Arrays.stream(bean.getClass().getDeclaredMethods()).forEach(method -> {
            String data = method.getName();
            if(data.startsWith("get")){
                try {
                    valueBuilder.append(method.invoke(bean));
                    valueBuilder.append(",");
                    keyBuilder.append(removeSetters(method.getName()));
                    keyBuilder.append(",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        keyBuilder.deleteCharAt(keyBuilder.length()-1);
        valueBuilder.deleteCharAt(valueBuilder.length()-1);

        return new Object[]{keyBuilder.toString(), valueBuilder};
    }

    private Object[] columnAndValuesSnakeCase(Object bean){

        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        Arrays.stream(bean.getClass().getDeclaredMethods()).forEach(method -> {
            String data = method.getName();
            if(data.startsWith("get")){
                try {
                    valueBuilder.append(method.invoke(bean));
                    valueBuilder.append(",");
                    keyBuilder.append(fromCamelCase(removeSetters(method.getName())));
                    keyBuilder.append(",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        keyBuilder.deleteCharAt(keyBuilder.length()-1);
        valueBuilder.deleteCharAt(valueBuilder.length()-1);

        return new Object[]{keyBuilder.toString(), valueBuilder};
    }

    public Object[] columnAndValuesSnakeCasePreparedStatement(Object bean){
        List<Object> listValues = new ArrayList<>();
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        Arrays.stream(bean.getClass().getDeclaredMethods()).forEach(method -> {
            String data = method.getName();
            if(data.startsWith("get") ){

                    valueBuilder.append("?");
                    valueBuilder.append(",");
                    keyBuilder.append(fromCamelCase(removeSetters(method.getName())));
                    keyBuilder.append(",");
                try {
                    listValues.add(method.invoke(bean));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        keyBuilder.deleteCharAt(keyBuilder.length()-1);
        valueBuilder.deleteCharAt(valueBuilder.length()-1);

        return new Object[]{keyBuilder.toString(), valueBuilder, listValues.toArray()};
    }

    public Object[] columnAndValuesSnakeCasePreparedStatement(Object bean, String[] excludes){
        List<String> excludesList = List.of(excludes);
        List<Object> listValues = new ArrayList<>();

        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        Arrays.stream(bean.getClass().getDeclaredMethods()).forEach(method -> {
            String data = method.getName();
            String strippedOfSetters = removeSetters(method.getName());
//            Console.log("strip", strippedOfSetters);
            if(data.startsWith("get") && !excludesList.contains(strippedOfSetters)){

                valueBuilder.append("?");
                valueBuilder.append(",");
                keyBuilder.append(fromCamelCase(strippedOfSetters));
                keyBuilder.append(",");

                try {
                    listValues.add(method.invoke(bean));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        keyBuilder.deleteCharAt(keyBuilder.length()-1);
        valueBuilder.deleteCharAt(valueBuilder.length()-1);

        return new Object[]{keyBuilder.toString(), valueBuilder, listValues.toArray()};
    }

    public Object[] columnAndValuesSnakeCasePreparedStatement(Object bean, Map<String, String> keySwapping){
        List<Object> listValues = new ArrayList<>();
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        Arrays.stream(bean.getClass().getDeclaredMethods()).forEach(method -> {
            String data = method.getName();

            if(data.startsWith("get")){
                String strippedOfSetters = removeSetters(method.getName());
                valueBuilder.append("?");
                valueBuilder.append(",");
                keyBuilder.append(fromCamelCase(keySwapping.getOrDefault(strippedOfSetters, strippedOfSetters)));
                keyBuilder.append(",");
                try {
                    listValues.add(method.invoke(bean));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        });
        keyBuilder.deleteCharAt(keyBuilder.length()-1);
        valueBuilder.deleteCharAt(valueBuilder.length()-1);

        return new Object[]{keyBuilder.toString(), valueBuilder, listValues.toArray()};
    }

    public Object[] columnAndValuesSnakeCasePreparedStatement(Object bean, String[] excludes, Map<String, String> keySwapping){
        List<String> excludesList = List.of(excludes);
        List<Object> listValues = new ArrayList<>();

        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        Arrays.stream(bean.getClass().getDeclaredMethods()).forEach(method -> {
            String data = method.getName();
            String strippedOfSetters = removeSetters(method.getName());
            if(data.startsWith("get") && excludesList.contains(strippedOfSetters)){

                valueBuilder.append("?");
                valueBuilder.append(",");
                keyBuilder.append(fromCamelCase(keySwapping.getOrDefault(strippedOfSetters, strippedOfSetters)));
                keyBuilder.append(",");
                try {
                    listValues.add(method.invoke(bean));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        });
        keyBuilder.deleteCharAt(keyBuilder.length()-1);
        valueBuilder.deleteCharAt(valueBuilder.length()-1);

        return new Object[]{keyBuilder.toString(), valueBuilder, listValues.toArray()};
    }

    private Object[] getBeanValues(Object bean){

        List<Object> list = new ArrayList<>();
        Arrays.stream(bean.getClass().getDeclaredMethods()).forEach(method -> {
            String data = method.getName();
            if(data.startsWith("get")){
                try {

                   list.add(method.invoke(bean));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        });
        return list.toArray();
    }

    public Object [] columnsAndValuesSnakeCase(JSONObject jsonObject){

        Map<String, Object> map = jsonObject.toMap();
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        map.forEach((key, value) -> {
            keyBuilder.append(fromCamelCase(key));
            keyBuilder.append(",");

            valueBuilder.append(getDataTypeValue(value));
            valueBuilder.append(",");
        });
        keyBuilder.deleteCharAt(keyBuilder.length()-1);
        valueBuilder.deleteCharAt(valueBuilder.length()-1);

        return new Object[]{keyBuilder.toString(), valueBuilder};
    }

    private String [] columnsAndValuesPreparedStatement(JSONObject jsonObject){
        Map<String, Object> map = jsonObject.toMap();

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        map.forEach((key, value) -> {
            sb.append(key);
            sb.append(",");
            sb2.append("?");
            sb2.append(",");
        });

        String keys = sb.deleteCharAt(sb.length()-1).toString();
        String values = sb2.deleteCharAt(sb2.length()-1).toString();

        return new String[]{keys, values};
    }

    public String [] columnsAndValuesPreparedStatementSnakeCamel(JSONObject jsonObject){
        Map<String, Object> map = jsonObject.toMap();

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        map.forEach((key, value) -> {
            sb.append(fromCamelCase(key));
            sb.append(",");
            sb2.append("?");
            sb2.append(",");
        });

        String keys = sb.deleteCharAt(sb.length()-1).toString();
        String values = sb2.deleteCharAt(sb2.length()-1).toString();

        return new String[]{keys, values};
    }

    /**
     * It filters the data via row removal by using a list of keys to be removed.
     * @param jsonObject JSONObject data
     * @param keys the arrays of keys to be removed.
     * @return JSONObject
     */
    public JSONObject excludeRow(JSONObject jsonObject, String[] keys){

        Map<String, Object> map = jsonObject.toMap();
        JSONObject newJSON = new JSONObject();
        List<String> lists = Arrays.asList(keys);
        map.forEach((key, value) -> {
            if(!lists.contains(key)){
                newJSON.put(key, value);
            }
        });

        return newJSON;
    }

    /**
     * It filters the data via row removal by using a list of keys to be removed.
     * @param jsonObject JSONObject data
     * @param newList - Map: contains column names and values to add to the data.
     * @return JSONObject
     */
    public JSONObject addRow(JSONObject jsonObject, Map<String, String> newList){



        newList.forEach((key, value) -> {
            if(!jsonObject.has(key)){
                jsonObject.put(key, value);
            }
        });

        return jsonObject;
    }

    /**
     * It swaps the key currently being executed with the value part of the mapping.
     * @param jsonObject JSONObject - the data
     * @param keySwapping Map<String, String> - object holds the string to be replaced in key-value.
     * @return
     */
    public JSONObject replaceColumn(JSONObject jsonObject, Map<String, String> keySwapping){

        Map<String, Object> map = jsonObject.toMap();
        JSONObject newJSON = new JSONObject();
        map.forEach((key, value) -> {
            if(keySwapping.containsKey(key)){
                key = keySwapping.get(key);
            }

            newJSON.put(key, value);
        });

        return newJSON;
    }

    public JSONObject camelCaseColumnToSnake(JSONObject jsonObject){
        Map<String, Object> map = jsonObject.toMap();
        JSONObject newJSON = new JSONObject();
        map.forEach((key, value) -> {
            newJSON.put(fromCamelCase(key), value);
        });

        return newJSON;
    }

    public String[] getSnakeCaseColumnFromCamel(String [] columns){
       return  Arrays.stream(columns)
                .map(this::fromCamelCase).toArray(String[]::new);
    }

    public JSONObject snakeCaseColumnToCamel(JSONObject jsonObject){
        Map<String, Object> map = jsonObject.toMap();
        JSONObject newJSON = new JSONObject();
        map.forEach((key, value) -> {
            newJSON.put(fromSnakeCase(key), value);
        });

        return newJSON;
    }

    /**
     * It presents structure used for a sql where clause e.g name = 'ade',
     * @param jsonObject JSONObject POJO Object with getters and setters.
     * @return String
     */

    public Object keyEqualValue(JSONObject jsonObject, String delimiter){

        Map<String, Object> map = jsonObject.toMap();
        return keyEqualValue(map, delimiter);
    }

    public Object keyEqualValue(Map<String, Object> map, String delimiter){

        StringBuilder builder = new StringBuilder();

        map.forEach((key, value) -> {

            builder.append(key);
            builder.append("=");
            builder.append(getDataTypeValue(value));
            builder.append(" ");
            builder.append(delimiter);
            builder.append(" ");

        });

        int len = builder.length();

        return builder.delete(len - (delimiter.length()+1), len);
    }

    public Object keyEqualValuePreparedStatement(JSONObject jsonObject, String delimiter){

        Map<String, Object> map = jsonObject.toMap();
        StringBuilder builder = new StringBuilder();
        map.forEach((key, value) -> {
            builder.append(key);
            builder.append("=");
            builder.append("?");
            builder.append(delimiter);
            builder.append(" ");
        });
        return builder.substring(0, builder.length() - (delimiter.length()+1));

    }

    /**
     * It turns red to 'red'
     * @param data - String to quote
     * @return Object
     */

    public Object quote(String data){
        return "'"+data+"'";
    }

    /**
     *  It takes a camel cased character sequence and returns a dashed sequence.
     *  noMeter turns no_meter.
     * @param camelCasedData String data
     * @return String
     */
    public String fromCamelCase(String camelCasedData){
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]");
        String [] data = camelCasedData.split("");
        for(String d: data){
            Matcher mat = pattern.matcher(d);
            if(mat.find()){
                sb.append("_");
                sb.append(d.toLowerCase());
                continue;
            }
            sb.append(d);
        }

        if(sb.toString().startsWith("_"))sb.deleteCharAt(0);
        return sb.toString();
    }

    public String fromSnakeCase(String snakeCaseData){
        StringBuilder sb = new StringBuilder();

        Arrays.stream(snakeCaseData.split("_")).forEach(word -> {
            Object c = word.charAt(0)+"";
            if(c.getClass().getTypeName().equals("java.lang.String")){
                sb.append(c.toString().toUpperCase());
                sb.append(word.substring(1));
            }
        });
        if(sb.toString().length() > 0)sb.setCharAt(0, Character.toLowerCase(sb.toString().charAt(0)));
        return sb.toString();
    }

    /**
     * changes array to comma separated string.
     * @param data data array
     * @return String
     */
    public String getCommaSeparated(String [] data){
        StringBuilder sb = new StringBuilder();

        Arrays.asList(data).forEach(column -> {
            sb.append(column);
            sb.append(",");
        });

        return sb.deleteCharAt(sb.length()-1).toString();
    }

    private Object getDataTypeValue(Object data){
        String type = "java.lang.String";
        if(type.equals(data.getClass().getTypeName())){
            return quote(data.toString());
        }
        return data;
    }
}
