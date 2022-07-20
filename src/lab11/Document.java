package lab11;

import java.util.Scanner;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document implements IWithName {
    public String name;

    public SortedMap<String, Link> link;

    public Document(String name) {
        this.name = name.toLowerCase();
        link = new TreeMap<String, Link>();
    }

    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new TreeMap<String, Link>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNextLine()) {

            String line = scan.nextLine();
            String[] seperatedLine = line.split(" ");

            for (String s : seperatedLine) {

                if (correctLink(s) || correctLinkWithWeight(s)) {

                    Link link1 = createLinkReg(s);


                    //assert link1 != null;
                    link.put(link1.ref, link1);

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

        regex = "([a-zA-Z][a-zA-Z_0-9]*)";
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(link);

        if (matcher.matches()) {
            return new Link(matcher.group(1));
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
        String retStr = "Document: " + name + "\n";
        //TODO?
        retStr += link;
        return retStr;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String getName() {
        return name;
    }
}

