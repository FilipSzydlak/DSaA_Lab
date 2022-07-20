package lab3;

import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {
    public String name;
    public TwoWayUnorderedListWithHeadAndTail<Link> link;

    public Document(String name, Scanner scan) {
        this.name = name;
        link = new TwoWayUnorderedListWithHeadAndTail<Link>();
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
                    Link linkElem = new Link(linkS.trim());
                    link.add(linkElem);

                }

                if (correctEod(s)) {
                    return;

                }

            }

        }
    }

    @Override
    public String toString() {
        StringBuilder linksD = new StringBuilder();
        Iterator<Link> iter = link.iterator();

        while (iter.hasNext()) {
            linksD.append("\n" + iter.next().toString().trim());
        }

        return "Document: " + this.name + linksD;
    }

    public String toStringReverse() {
        String retStr = "Document: " + name;
        return retStr + link.toStringReverse();
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