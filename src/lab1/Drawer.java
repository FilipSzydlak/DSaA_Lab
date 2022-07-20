package lab1;

public class Drawer {
    private static void drawLine(int s, int x) {
        int spacesInTheRow = s;
        int xInTheRow = x;
        for (int i = 0; i < spacesInTheRow; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < xInTheRow; i++) {
            System.out.print("X");
        }
        System.out.println();
    }

    public static void drawPyramid(int n) {
        int spaces = n - 1;
        int x = 1;
        int noOfLine = 1;
        if (n <= 0) {
        }
        for (int i = 0; i < n; i++) {
            x = 2 * noOfLine - 1;
            drawLine(spaces, x);
            spaces--;
            noOfLine++;

        }
        /*
         * for (int i = 0; i < n; i++) { int xInTheRow = 2 * noOfLine - 1; for (int j =
         * spacesInTheRow; j > 0; j--) { System.out.print(" "); } for (int k = 1; k <=
         * xInTheRow; k++) { System.out.print("X"); }
         *
         * System.out.println(); spacesInTheRow--; noOfLine++; }
         */

    }

    public static void drawChristmassTree(int n) {
        int noOfPart = 1;

        for (int i = 0; i < n; i++) {
            int x = 1;
            int Spaces = n - 1;
            for (int j = 0; j < noOfPart; j++) {
                drawLine(Spaces, 2 * x - 1);
                x++;
                Spaces--;
            }

            noOfPart++;

        }

    }

}

