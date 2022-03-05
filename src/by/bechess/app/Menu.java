package by.bechess.app;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    static Scanner menuInput = new Scanner(System.in);

    public static Color getSideColor() {
        Scanner colorInput = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Choose a side you wish to lead:\n1 - white, 2 - black:");
                switch (colorInput.nextInt()) {
                    case 1 -> { return Color.WHITE; }
                    case 2 -> { return Color.BLACK; }
                }
            }
            catch(Exception ex) { colorInput.next(); }
        }
    }

    public static String getPlayerMove() {
        //Scanner menuInput = new Scanner(System.in);
        String fromPoint, toPoint;

        System.out.println("Select piece to move (ex. e2):");
        fromPoint = menuInput.nextLine();
        System.out.println("Enter cell to move on (ex. e4):");
        toPoint = menuInput.nextLine();

        //menuInput.close();
        return fromPoint + toPoint;
    }

    public static String getOpponentMove(AI aiPlayer) {
        return aiPlayer.getMove().getNotation().replace("-","").substring(1);
    }
}
