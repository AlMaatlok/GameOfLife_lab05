package Logic;

import Models.Configuration;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class FileHandler {
    private String fileName;

    public FileHandler(String fileName) {
        if(fileName.equals(""))
            this.setFileName(System.getProperty("user.dir") + File.separator + "default-config.txt");
        else
            this.setFileName(fileName);
    }

    public Configuration getConfiguration() {
        try{
            File file = new File(this.getFileName());
            Scanner scanner = new Scanner(file);
            Configuration config = new Configuration();

            int i = 1;

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine().strip();
                switch (i){
                    case 1 -> config.setxSize(Integer.parseInt(line));
                    case 2 -> config.setySize(Integer.parseInt(line));
                    case 3 -> config.setIterations(Integer.parseInt(line));
                    case 4 -> config.setLiveCellsCount(Integer.parseInt(line));
                    default ->
                        config.addAliveCells(line);

                }
                i++;
            }
            return config;
        }catch (IOException e){
            System.out.println("Cannot read given config file with path : " + this.fileName);
        } catch (InvalidParameterException e){
            System.out.println("Error occured during parsing config : " + e.getMessage());
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
