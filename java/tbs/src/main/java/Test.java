import hp.io.Console;
import sql.HPSQL;
import tbs.io.TBSConnect;
import tbs.io.TBSTables;
import tbs.io.TbsMini;
import tbs.models.Customer;
import tbs.models.Testi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

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
    void hello(Consumer<Integer> consumer) throws InterruptedException {
        int i = 0;
        while(i++ < 10){
            consumer.accept(i);
            Thread.sleep(1000);
        }
    }
    void connectTable() throws SQLException, IOException, InterruptedException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//        TBSTables tb = new TBSTables("BSW012021_17");
        TBSTables tb = new TBSTables("DSW012020_17");
        Testi testi = new Testi("ola", "biodun");
        HPSQL hpsql = new HPSQL();
        String da = "[]";
        Console.log(da.substring(0, 1000));
        Console.log(tb.getZonalCustomers(Customer.class, "BOMADI"));
//        List<Testi> testis = List.of(new Testi("hello", "2021-01-20"), new Testi("hell2", "2021-01-20"));
////        Console.log(hpsql.getMethodValue(testi, "datem"));
//        tb.updateMany(testis, "datem");
//        Console.log(sql);
//        Console.log(tb.getArparmJson(Arparm.class, "bswsc"));

//        Console.log(tb.getTariffsJson());
//        Console.log(tb.getTableDataJson(Schemes.class));
//        Console.log(tb.getCustomerConstantsJson());
//        Console.log(tb.getTableDataJson(HrMaster.class));
//        Console.log(tb.getCommercialEmployeesJson(HrMaster.class));
//            tb.getStreamedData(HrMaster.class, 5, "sno", Console::log);
//        Console.log(tb.getEmployeesJson(HrMaster.class));
//          Console.log(tb.getUsersJson(Users.class));
/*
        Console.log(tb.getZonalCustomersJson(Customer.class, "birshi", Map.of("agency", "bswsc")));
            tb.getStreamedZonalCustomers(Customer.class, "BIRSHI", 20, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    Console.log(s);
                    Console.log("fetchingggggggggggggggggggggggg");
                }
            });
        Gson g = new Gson();
        String d = "[{'datem': '2020-05-20'}]";
        Type type = new TypeToken<ArrayList<Testi>>(){}.getType();
        List<Testi> t = g.fromJson(d, type);
        Console.log(t.get(0).getDatem());
        Testi testi = new Testi();
        testi.setDatem("2020-05-20");
//        testi.setPhoto("".getBytes());
        tb.addTableData(testi);
        t.forEach(test -> {
            try {
                tb.addTableData(test);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        tbs.models.Test test = new tbs.models.Test(123, "hello".getBytes(), 123.45);
        tb.addTableData(test);
*/
    }

    public static void main(String[] args) {
        Test t = new Test();

        Executors.newCachedThreadPool().execute(() -> {
            try {
                t.connectTable();
            } catch (SQLException | IOException | InterruptedException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
    }
}
