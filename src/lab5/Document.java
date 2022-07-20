package lab5;


import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document{
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TwoWayCycledOrderedListWithSentinel<Link>();
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


        return "Document: " + this.name + link.toString();
    }


    public String toStringReverse() {



        return "Document: " + name + link.reversetoString();
    }
    public int[] getWeights() {
        int [] weightArr = new int[link.size()];
        for(int i = 0; i < link.size(); i++){
            weightArr[i] = link.get(i).weight;
        }
        return weightArr;
    }

    public static void showArray(int[] arr) {
        for(int k = 0; k<arr.length; k++){
            if (k != arr.length - 1){
                System.out.print(arr[k] + " ");
            }else {
                System.out.print(arr[k]);
            }
        }
    }


    void bubbleSort(int[] arr) {
        int counter= 0;
        int tempElem;

        for (int i = 0 ; i < arr.length; i++){
            showArray(arr);
            System.out.println();

            for (int k = arr.length - 1; k > counter;k--){
                if ( arr[k] < arr[k - 1]){
                    tempElem = arr[k];
                    arr[k] = arr[k - 1];
                    arr[k - 1] = tempElem;
                }
            }

            counter++;
        }
    }

    public void insertSort(int[] arr) {
        int counter = 0;
        int currElem;
        int temp;

        for (int i = 0 ; i < arr.length; i++){
            currElem = arr[arr.length - counter - 1];

            for (int j = arr.length - counter; j < arr.length; j++){
                if (currElem < arr[j]){
                    break;

                }else {
                    temp = arr[j-1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }

            counter++;

            showArray(arr);
            System.out.println();
        }
    }
    public void selectSort(int[] arr) {
        int counter = 0;
        int maxValue = Integer.MIN_VALUE;;
        int maxIndex = Integer.MIN_VALUE;
        int tempElem;

        for (int i = 0 ; i < arr.length; i++){
            showArray(arr);
            System.out.println();
            maxValue = Integer.MIN_VALUE;

            for (int j = 0; j < arr.length - counter; j++){
                if (arr[j] > maxValue ){
                    maxValue= arr[j];
                    maxIndex = j;
                }
            }

            tempElem = arr[arr.length - counter - 1];
            arr[arr.length - counter - 1] = maxValue;
            arr[maxIndex] = tempElem;

            counter++;


        }
    }

}
