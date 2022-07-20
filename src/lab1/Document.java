package lab1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {
    public static void loadDocument(String name, Scanner scan) {

        while (scan.hasNextLine()) {

            String line = scan.nextLine();
            String[] seperatedLine = line.split(" ");

            for (String s : seperatedLine) {

                if (correctLink(s)) {
                    String[] print = s.split("=");
                    System.out.println(print[1].toLowerCase());

                }

                if (correctEod(s)) {
                    return;

                }

            }

        }

    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the
    // begin)
    public static boolean correctLink(String link) {
        String linkPattern = "link=[a-z[A-Z]]\\w*";
        Pattern pLink = Pattern.compile(linkPattern, Pattern.CASE_INSENSITIVE);
        Matcher linkMatcher = pLink.matcher(link);
        if (linkMatcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean correctEod(String eod) {
        String endPattern = "eod";
        Pattern pEnd = Pattern.compile(endPattern);
        Matcher endMatcher = pEnd.matcher(eod);
        if (endMatcher.matches()) {
            return true;
        }
        return false;
    }

}
