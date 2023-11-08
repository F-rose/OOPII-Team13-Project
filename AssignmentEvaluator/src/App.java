import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class App {
    public static void extractZipFile(String zipFileName, String outputFolder) throws IOException {
        byte[] buffer = new byte[1024];

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName))) {
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String entryName = zipEntry.getName();
                String outputPath = outputFolder + File.separator + entryName;

                File entryFile = new File(outputPath);

                if (zipEntry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    new File(entryFile.getParent()).mkdirs();

                    try (FileOutputStream fos = new FileOutputStream(entryFile)) {
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Please input desired file name: ");
        String zipFileName = in.nextLine();
        String outputFolder = "extractedFiles";

        File zipFile = new File(zipFileName);

        if (!zipFile.exists() || !zipFile.isFile() || !zipFileName.endsWith(".zip")) {
            System.out.println("No Java zip file found.");
            return; // Exit the program if the zip file is not found
        }

        try {
            extractZipFile(zipFileName, outputFolder);
            System.out.println("Zip file extracted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

