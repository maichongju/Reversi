package reversi;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class reversi {



    public static void main(String[] args) {
        final String START_LINE =
                "command:\n" +
                "new <size> (size must >= 4 and even integer)\n" +
                "play <row> <col>\n" +
                        "quit\n";
        System.out.println(START_LINE);
        Scanner scanner = new Scanner(System.in);
        String command;
        Grid grid = new Grid();
        GraphicDisplay gd = new GraphicDisplay(grid);
        grid.setGraphicDisplay(gd);
        grid.init(4);
        System.out.print(grid.toString());
        javax.swing.SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Reversi");

            frame.setPreferredSize(gd.getSize());
            frame.getContentPane().add(gd);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null); // Need to be at the end. Center the program
        });
        Colour currentColor = Colour.Black;
        while (scanner.hasNext()) {
            try {
                command = scanner.next();
                if (command.compareTo("new") == 0) {
                    int size = scanner.nextInt();
                    // if and only if the size is greater than 4 and is even number.
                    if (size >= 4 && size % 2 ==0 && size <=30){
                        grid.init(size);
                        System.out.print(grid.toString());
                    }
                } else if (command.compareTo("play") == 0) {
                    int r = scanner.nextInt();
                    int c = scanner.nextInt();
                    try {
                        grid.place(r, c);

                        System.out.print(grid.toString());
                        if (grid.isGameOver()){
                            Colour result = grid.whoWon();
                            switch (result) {
                                case Black: {
                                    System.out.println("Black Win!");
                                    break;
                                }
                                case NoColor: {
                                    System.out.println("Tie!");
                                    break;
                                }
                                case White: {
                                    System.out.println("White Win!");
                                }
                            }
                            System.out.println("Type new <size> to start a new game");
                        }
                    } catch (ReversiException ignored) {
                    }
                }
                else if (command.compareTo("quit") == 0){ // Exit the program if user type quit
                    break;
                }
            } catch (InputMismatchException e) { // If there is some input error, ignore it
            }
        }
        scanner.close();
    }
}
