package Game;

import java.io.File;

public class GameSaveDeleter {
    public static void doIt(String path){
        File file = new File(Constants.BASESAVEPATH.concat("/").concat(path));
        deleteDirectory(file);
    }

    public static void deleteDirectory(File file) {
        File[] list = file.listFiles();
        if (list != null) {
            for (File temp : list) {
                //recursive delete
                System.out.println("Visit " + temp);
                deleteDirectory(temp);
            }
        }

        if (file.delete()) {
            System.out.printf("Delete : %s%n", file);
        } else {
            System.err.printf("Unable to delete file or directory : %s%n", file);
        }

    }
}
