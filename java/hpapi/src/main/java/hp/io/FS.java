package hp.io;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class FS {

    public static String getExt(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static boolean isImage(String fileName){
        switch (getExt(fileName)){
            case ".jpg", ".png", ".gif", ".jpeg", ".tiff" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public static File zipFolder() throws IOException {
        return null;
    }

    public static int countTotalPDFPages(Path path) throws IOException {
        AtomicInteger total = new AtomicInteger(0);

        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(file.toString().contains(".pdf") && getExt(file.getFileName().toString()).equals(".pdf")){
                    File f = new File(file.toString());
                    PDDocument document = PDDocument.load(f);
                    total.getAndAdd(document.getNumberOfPages());
                    document.close();
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
        return total.get();
    }

    public static void copyAsync(Path from, Path to, Consumer<Boolean> consumer){
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()-> {
            try {
                Files.walkFileTree(from, new DirectoryWalker(from, to, WalkerType.Copy));
                consumer.accept(true);
                service.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
                Console.error(e.getMessage());
                consumer.accept(false);
                service.shutdown();
            }
        });
    }

    public static void moveAsync(Path from, Path to, Consumer<Boolean> consumer){
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()-> {
            try {
                Files.walkFileTree(from, new DirectoryWalker(from, to, WalkerType.Move));
                consumer.accept(true);
                service.shutdown();
            } catch (IOException e) {
                consumer.accept(false);
                service.shutdown();
            }
        });
    }

    public static class DirectoryWalker implements FileVisitor<Path>{

        private Path from;
        private Path to;
        private WalkerType type;

        public DirectoryWalker(Path from, Path to, WalkerType type) throws IOException {

            this.from = from;
            this.to = to;
            this.type = type;

            //create target
            if(!to.toFile().exists()){
                Files.createDirectory(to);
            }
        }

        public DirectoryWalker() {}

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

            Path target = to.resolve(from.relativize(dir));
            try {
                if(type == WalkerType.Copy){
                    Files.copy(dir, target);
                }
//                Files.mov
            } catch (FileAlreadyExistsException e) {
                if (!Files.isDirectory(target))
                    throw e;
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
           Path target = to.resolve(from.relativize(file));
            Files.copy(file, target);
            return FileVisitResult.CONTINUE;
        }


        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.TERMINATE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

            return FileVisitResult.CONTINUE;
        }
    }

    public static enum WalkerType{
        Copy, Move, Delete;
    }

}