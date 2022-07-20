package lab7;

import java.util.ListIterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document implements IWithName {
    private static final int MODVALUE = 100000000;
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;

    public Document(String name) {

        this.name = name;

    }



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

                    Link link1 = createLinkReg(s);


                    link.add(link1);

                }

                if (correctEod(s)) {
                    return;

                }

            }

        }
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)


    public static boolean isCorrectId(String id) {
        String linkPattern;
        linkPattern = "[a-z]*";
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

    static Link createLink(String link) {
        String regex = "([a-zA-Z][a-zA-Z_0-9]*)\\((.*)\\)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);

        if (matcher.matches()) {

            return new Link(matcher.group(1), Integer.parseInt(matcher.group(2)));
        }
        return null;
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static Link createLinkReg(String link) {
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
        return "Document: " + name + link.toString().toLowerCase();
    }

    public String toStringReverse() {
        return "Document: " + name + link.reversetoString().toLowerCase();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Document document = (Document) o;

        return Objects.equals(name, document.name);
    }

    @Override
    public int hashCode() {

        int[] sequence = {7,11,13,17,19};
        String name = this.getName();
        int hashCode = 0;
        hashCode = name.charAt(0);

        for(int i = 1; i< name.length(); i++) {

            hashCode = (hashCode* sequence[(i-1)%5] + name.charAt(i))%MODVALUE;

        }
        return hashCode;
    }


}
