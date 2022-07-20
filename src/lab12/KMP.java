package lab12;


import java.util.LinkedList;

public class KMP implements IStringMatcher {

    @Override
    public LinkedList<Integer> validShifts(String pattern, String text) {

        LinkedList<Integer> shifts = new LinkedList<>();
        int m = pattern.length();
        int n = text.length();
        int pIndex = 0;
        int tIndex = 0;
        int[] pi = pi(pattern, m);


        while (tIndex < n) {

            if (pattern.charAt(pIndex) == text.charAt(tIndex)) {
                pIndex++;
                tIndex++;
            }
            if (pIndex == m) {
                shifts.add(tIndex - pIndex);
                pIndex = pi[pIndex - 1];
            } else if (tIndex < n && pattern.charAt(pIndex) != text.charAt(tIndex)) {

                if (pIndex != 0) {
                    pIndex = pi[pIndex - 1];
                } else {
                    tIndex = tIndex + 1;
                }

            }

        }
        return shifts;
    }


    public int[] pi(String pattern, int m) {

        int pi[] = new int[m];
        int q = 1;
        int k = 0;
        pi[0] = 0;

        while (q < m) {
            if (pattern.charAt(q) == pattern.charAt(k)) {
                k++;
                pi[q] = k;
                q++;
            } else {
                if (k != 0) {
                    k = pi[k - 1];
                } else {
                    pi[q] = k;
                    q++;
                }
            }
        }
        return pi;
    }


}


