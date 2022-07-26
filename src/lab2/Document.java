package lab2;

import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {
    public String name;
    public OneWayLinkedList<Link> links = new OneWayLinkedList<Link>();

    public Document(String name, Scanner scan) {
        this.name = name;

        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNextLine()) {

            String line = scan.nextLine();
            String[] seperatedLine = line.split(" ");

            for (String s : seperatedLine) {

                if (correctLink(s)) {
                    String[] print = s.split("=");
                    String linkS = new String(print[1].toLowerCase());
                    Link link = new Link(linkS.trim());
                    links.add(link);

                }

                if (correctEod(s)) {
                    return;

                }

            }

        }
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the
    // begin)

    @Override
    public String toString() {
        StringBuilder linksD = new StringBuilder();
        Iterator<Link> iter = links.iterator();

        while (iter.hasNext()) {
            linksD.append("\n" + iter.next().getRef().trim());
        }

        return "Document: " + this.name + linksD;
    }

    private static boolean correctLink(String link) {
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
