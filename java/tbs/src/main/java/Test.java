import io.Console;
import tbsio.TBSConnect;
import tbsmini.TbsMini;

import java.sql.Connection;
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

        Console.log(mini.getDbFilePath("dswgene"));
//        Connection connection = connect.getConnection();
//        Connection connection = connect.getConnection("tbs", "hanspet-pc", "dswgene", 2638);
        connect.getConnection("", "dba", "sql");
        connect.getConnection();

    }
    public static void main(String[] args) {
        Test t = new Test();
//        t.testTbsMini();

        Executors.newCachedThreadPool().execute(t::testTbsConnect);
    }
}
