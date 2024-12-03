import Logic.FileHandler;
import Logic.LogicHandler;
import Models.Board;
import Models.Configuration;

import java.util.Scanner;

public class MainGameOfLife {
    public static void main(String[] args) {
        System.out.println("GAME OF LIFE");
        System.out.println();
        System.out.println("please provide absolute path to config file: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        System.out.println("provide number of threads you want to run: ");
        Scanner scanner2 = new Scanner(System.in);
        int numberOfThreads = scanner2.nextInt();

        FileHandler Fh = new FileHandler(path);
        Configuration config = Fh.getConfiguration();

        Board board = new Board(config.getxSize(), config.getySize(), config);
        LogicHandler logic = new LogicHandler(config);
        logic.goingThroughIterations(config, board);
        logic.printWorkingThreads(numberOfThreads, config);

    }
}