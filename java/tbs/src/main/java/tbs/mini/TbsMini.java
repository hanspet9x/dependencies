package tbs.mini;

import interfaces.OnTbsMiniError;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class TbsMini {

    private Properties properties = null;
    private final String tbsMiniPath = "C:\\Tbswin\\Tbsmini.ini";
    private String appMiniPath = null;
    private String dbPath = null;
    private String appPath = null;
    private String appDirName = null;
    private String dbDirName = null;
    private String dbPrefix = null;
    private String serverName = null;
    private String computerName = null;
    private int currentFiscalYear = 0;

    public TbsMini() {
        properties = getProps();
        init();
    }


    /**
     * It loads App path from tbsmini inside tbswin and instantiates the properties object.
     * @return Properties
     * @throws IOException exception
     */
    private Properties getProps() {
        if(properties == null){
            properties = new Properties();
            FileReader reader = null;
            try {
                reader = new FileReader(tbsMiniPath);
                properties.load(reader);
                String appPathKey = "APP_PATH";
                appDirName = properties.get(appPathKey).toString().replaceAll("[' ]",
                        "");

                appPath = "C:\\"+appDirName;
                appMiniPath = appPath+"\\Tbsmini17.ini";
                reader = new FileReader(appMiniPath);
                properties.load(reader);
                return properties;
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                setError(e.getMessage());
            }


        }
        return properties;
    }

    private void init() {
        dbDirName = appDirName.replace("9", "").toLowerCase();
        dbPath = "C:\\tbswin\\database\\sybase\\"+dbDirName+"\\";
        dbPrefix = get("DATAPREFIX");
        currentFiscalYear = Integer.parseInt(get("CURRENT_FISCAL_YR").trim());
        serverName = get("ServerName");
        computerName = get("SERVER_COMPUTER_NAME");
    }

    public String get(String key) {
        return getProps().get(key).toString().replaceAll("'", "");
    }

    public void set(String key, String value) {
        Properties prop = getProps();
        prop.setProperty(key, value);
        store();
    }

    private void store(){
        try {
            Properties prop = getProps();
            FileWriter writer = new FileWriter(appPath);
            prop.store(writer, "Do not Touch");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            setError(e.getMessage());
        }
    }

    public String getDBPrefix(){
        return dbPrefix;
    }

    public List<String> getDBNames(){

        return List.of(prefixAndData("GENE"), prefixAndData(getCurrentMainDbSuffix()), getMainDbByYear(currentFiscalYear-1));
    }

//    public String getFull
    private String getCurrentMainDbSuffix(){
        return "01"+currentFiscalYear;
    }

    public String getMainDbByYear(int year){
        return dbPrefix+"01"+year;
    }

    private int getCurrentFiscalYear(){
        return currentFiscalYear;
//        return Calendar.getInstance().get(Calendar.YEAR);
    }


    private String prefixAndData(String data){
        return dbPrefix+data;
    }

    private OnTbsMiniError onTbsMiniError = null;

    private void setError(String error){
        if(onTbsMiniError != null){
            onTbsMiniError.error(error);
        }
    }

    public void setOnTbsMiniError(OnTbsMiniError onTbsMiniError) {
        this.onTbsMiniError = onTbsMiniError;
    }

    /**
     * It returns the system database path C:\tbswin\database\sybase\deswc17\
     * @return String
     */
    public String getDbPath() {
        return dbPath;
    }

    /**
     * It sends the full path of the specified db with a .db suffix.
     * @param dbName String - dsw012020 or dsw012020.db
     * @return String.
     */
    public String getDbFilePath(String dbName){
        if(dbName.contains(".db")){
            return dbPath+dbName;
        }
        return dbPath+dbName+".db";
    }


    public String getDSNFromDBName(String dbName){
        if(dbName.contains(".db")) dbName = dbName.replace(".db", "");

        if(dbName.contains("_17")) return dbName;

        return dbName+"_17";

    }

    public String getDbNameFromDSN(String dsn) {
        if (dsn.contains("_17")) {
            return dsn.replace("_17", "");
        }
        String error = "DSN is invalid. It should contain _17";
        setError(error);
        throw new IllegalArgumentException(error);

    }

    public String getAgencyName() {
        return get("COMPANY_ABBREVIATION");
    }

    public String getComputerName(){
        return computerName;
    }

    public String getServerName(){
        return serverName;
    }

    public String getAppDirName() {
        return appDirName;
    }

    public String getTbsMiniPath() {
        return tbsMiniPath;
    }

    public String getAppMiniPath() {
        return appMiniPath;
    }

    public String getGeneDsn(){
        return dbPrefix+"GENE_17";
    }

    public String getCurrentDsn(){
        return dbPrefix+"01"+ currentFiscalYear +"_17";
    }
}