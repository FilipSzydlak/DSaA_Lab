package lab12;


import java.util.LinkedList;

public class Automaton implements IStringMatcher {
    private static int sigma = 255;

    @Override
    public LinkedList<Integer> validShifts(String pattern, String text) {

        LinkedList<Integer> shifts = new LinkedList<>();
        int m = pattern.length();
        int n = text.length();
        int[][] delta = delta(pattern, m);
        int q = 0;

        for (int i = 0; i < n; i++) {
            q = delta[q][text.charAt(i)];
            if (q == m)

                shifts.add(i - m + 1);
        }

        return shifts;
    }

    static int[][] delta(String pattern, int m) {
        int[][] delta = new int[m + 1][sigma];
        int q;
        int a;

        for (q = 0; q <= m; q++)
            for (a = 0; a < sigma; a++)
                delta[q][a] = newQ(pattern, m, q, a);

        return delta;
    }

    static int newQ(String pattern, int m, int q, int a) {

        if (q < m && a == pattern.charAt(q))
            return q + 1;


        int new_q;
        int i = 0;


        for (new_q = q; new_q > 0; new_q--) {
            if (pattern.charAt(new_q - 1) == a) {
                for (i = 0; i < new_q - 1; i++) {

                    if (pattern.charAt(i) != pattern.charAt(q - new_q + 1 + i)) {
                        break;
                    }
                }
                if (i == new_q - 1)
                    return new_q;
            }
        }

        return 0;
    }


}
