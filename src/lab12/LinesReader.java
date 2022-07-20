package lab12;


import java.util.Scanner;

public class LinesReader {
    String concatLines(int howMany, Scanner scanner) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < howMany; i++) {
            str.append(scanner.nextLine());
        }


        return str.toString();
    }

}
