package tbs.io;

import interfaces.OnTbsMiniError;
import tbs.mini.TbsMini;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TBSConnect {

    private OnTbsMiniError tbsMiniError = null;

    private String serverApp = "dbsrv17";
    private final TbsMini tbsMini;

    public TBSConnect() {
        tbsMini = new TbsMini();
    }

    public Connection getConnection(String dsn){

        try {
                return DriverManager.getConnection("jdbc:sqlanywhere:DSN="+dsn);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return null;
    }

    public Connection getConnection(){

        try {
            return DriverManager.getConnection("jdbc:sqlanywhere:UserID=dba;Password=sql;DBF=C:\\Tbswin\\Database\\Sybase\\BSWSC17\\bswgene.db; Startline=dbsrv -ti 0");
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return null;
    }
    public Connection getConnectionUsingODBC(String dsn, String pwd){
        try {
            return DriverManager.getConnection( "jdbc:sqlanywhere:DSN="+dsn+";Password="+pwd);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void startServer(String serverName){
        try {
            DriverManager.getConnection("jdbc:sqlanywhere:UserId=dba;Password:sql;START=dbsrv17 -c 500M -gd all -ti 0 -n "+serverName);
        } catch (SQLException e) {
            e.printStackTrace();
            setError(e.getMessage());
        }
    }


    public Connection getConnection(String startLine, String uid, String pwd){

        try {
            return DriverManager.getConnection( "jdbc:sqlanywhere:UserID="+ uid +";Password="+ pwd +";"+startLine);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * It connects to a running database on another computer.
     * @param serverName String TBS
     * @param host String Hanspet-PC
     * @param dbName String dswgene
     * @param port Int 2638
     * @return Connection
     */
    public Connection getConnection(String serverName, String host, String dbName, int port){
        try {
            return DriverManager.getConnection( "jdbc:sqlanywhere:UserID=dba;Password=sql;Host="+ host +":"+ port +";ServerName="+ serverName +";DatabaseName="+dbName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public void setTbsMiniError(OnTbsMiniError tbsMiniError) {
        this.tbsMiniError = tbsMiniError;
    }

    private void setError(String message){
        if(tbsMiniError != null) tbsMiniError.error(message);
    }

    public String startServerAndDb(String dbName){
        //"START=dbsrv17 -c 500M -gd all -ti 0 -n tbs C:\\Tbswin\\Database\\Sybase\\DESWC17\\tbs.db";
        return serverApp+ " -gd all -c 500m -ti 0 " +tbsMini.getDbFilePath("tbs");
    }

    public String  getStartLine(String dbName){
        return serverApp+ " -gd all -c 500m -ti 0 " +tbsMini.getDbFilePath(dbName);
    }

    public TbsMini getTbsMini() {
        return tbsMini;
    }
}
