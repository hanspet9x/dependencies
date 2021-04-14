import io.CSV;
import io.Console;
import io.FS;
import org.apache.pdfbox.pdmodel.PDDocument;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class Test {


    private void testCSVDownload() throws IOException {
        CSV csv = CSV.create(Paths.get("C:\\Users\\payod\\OneDrive\\Documents\\test.csv"));
        csv.addHeaders("id", "name");
        csv.addRow("1, Ade");
        csv.addRow("2, Bisi");
        csv.addColumn("3");
        csv.addColumn("Tola");
        csv.nextLine();
        csv.addRows("4, David", "5, Bimpe");
        csv.addColumns("3", "bnam");
        csv.nextLine();

        csv.addHeaderValue("name", "Timi");
        csv.nextLine();
        csv.download();
    }


    private void testPDFCount() {
        String path = JOptionPane.showInputDialog("Root Directory");

        JOptionPane.showMessageDialog(null, "Scanning..");

        int res = 0;
        try {
            res = FS.countTotalPDFPages(Paths.get(path));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Total PDF pages ="+res);
    }

    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.testPDFCount();
    }
}
