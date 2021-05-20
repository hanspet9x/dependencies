import hp.io.Console;
import tbs.io.TBSConnect;
import tbs.io.TBSTables;
import tbs.mini.TbsMini;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;

public class Test {

    void testTbsMini(){

        TbsMini tbsMini = new TbsMini();
        tbsMini.setOnTbsMiniError( e -> Console.error(e));
        tbsMini.getDBNames().forEach(Console::log);
        Console.log(tbsMini.getDbPath());
    }

    void testTbsConnect() {

        TBSConnect connect = new TBSConnect();
        TbsMini mini = connect.getTbsMini();

        Console.log(mini.getDbFilePath("bswgene"));
        connect.getConnection("Bsw012021_17");
//        Connection connection = connect.getConnection();
//        Connection connection = connect.getConnection("tbs", "hanspet-pc", "dswgene", 2638);
//        connect.getConnection("", "dba", "sql");
//        connect.startServer("tbs");
//        connect.getConnection();

    }

    void connectTable() throws SQLException, IOException {
        TBSTables tb = new TBSTables("BSW012021_17");
//        Console.log(tb.getArparmJson(Arparm.class, "bswsc"));
//        Console.log(tb.getZonalCustomersJson(Customer.class, "birshi", Map.of("agency", "bswsc")));

    }

    public static void main(String[] args) {
        Test t = new Test();

        Executors.newCachedThreadPool().execute(() -> {
            try {
                t.connectTable();
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
