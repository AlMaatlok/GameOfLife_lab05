package Logic;

import Models.Configuration;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class FileHandler {
    private String fileName;

    public FileHandler(String fileName) {
        if(fileName == null || fileName.isEmpty()) {
            System.out.println("File name cannot be null");
        }
        else{
            this.setFileName(fileName);
        }
    }

    public Configuration getConfiguration() {
        try{
            File file = new File(this.getFileName());
            Scanner scanner = new Scanner(file);
            Configuration config = new Configuration();

            {
                int i = 1;
                int lineCount = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().strip();
                    switch (i) {
                        case 1 -> config.setxSize(Integer.parseInt(line));
                        case 2 -> config.setySize(Integer.parseInt(line));
                        case 3 -> config.setIterations(Integer.parseInt(line));
                        case 4 -> config.setLiveCellsCount(Integer.parseInt(line));
                        default -> config.addAliveCells(line);
                    }
                    lineCount++;
                    i++;
                }
                if (lineCount < 4) {
                    throw new InvalidParameterException("Insufficient lines in configuration file.");
                }
                return config;
            }
        }catch (IOException e){
            System.out.println("Cannot read given config file with path : " + this.fileName);
        } catch (InvalidParameterException e){
            System.out.println("Error occurred during parsing config : " + e.getMessage());
        }
        return null;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
