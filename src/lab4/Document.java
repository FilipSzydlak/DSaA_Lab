package lab4;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;

    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNextLine()) {

            String line = scan.nextLine();
            String[] seperatedLine = line.split(" ");

            for (String s : seperatedLine) {

                if (correctLink(s) || correctLinkWithWeight(s)) {

                    Link link1 = createLink(s);


                    link.add(link1);

                }

                if (correctEod(s)) {
                    return;

                }

            }

        }
    }

    public static boolean isCorrectId(String id) {
        String linkPattern;
        linkPattern = "[a-zA-Z][a-zA-Z_0-9]*";
        Pattern pLink = Pattern.compile(linkPattern, Pattern.CASE_INSENSITIVE);
        Matcher linkMatcher = pLink.matcher(id);
        if (linkMatcher.matches()) {
            return true;
        }
        return false;
    }


    public static boolean correctLinkWithWeight(String link) {
        String linkPattern;
        linkPattern = "link=[a-zA-Z]\\w*\\([1-9]\\d*\\)";
        Pattern pLink = Pattern.compile(linkPattern, Pattern.CASE_INSENSITIVE);
        Matcher linkMatcher = pLink.matcher(link);
        if (linkMatcher.matches()) {
            return true;
        }
        return false;
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    private static Link createLink(String link) {
        if (correctLinkWithWeight(link)) {
            String[] add = link.split("=");
            String[] add2 = add[1].split("\\(");
            String ref = add2[0].toLowerCase().trim();
            String[] add3 = add2[1].split("\\)");
            String weight = add3[0].trim();
            int weightInt = Integer.parseInt(weight);


            Link link1 = new Link(ref, weightInt);
            return link1;


        }
        if (correctLink(link)) {
            String[] add = link.split("=");
            String ref = add[1].toLowerCase().trim();
            Link link1 = new Link(ref);
            return link1;
        }
        return null;
    }

    private static boolean correctLink(String link) {
        String linkPattern = "link=[a-zA-Z]\\w*";
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


    @Override
    public String toString() {


        return "Document: " + this.name + link.toString();
    }


    public String toStringReverse() {



        return "Document: " + name + link.reversetoString();
    }
}

