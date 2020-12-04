import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;

public class Q2 {
    static String message =
            "tusiptriielurpstomocmatelsttvtendelohnacdoetiybr" +
            "hrnsrgafhieashioownarrydirateasatnpormlietucluss" +
            "sotsiesofwansacahnitdhaegkaablsoughoaiponoshscsf" +
            "lkerliomedhamebyittarnyerysdnbeidrigttdaicmawmfu" +
            "oreniklnrearndyeohyatdabstwngdeaaimhgosininpnstp" +
            "inleafttteepnetegelsbiihlahgsrelhatihnesdkttaeis" +
            "hefhlseirlapenboetdienraowmxswftrtygowncumeemnjo" +
            "lgtalwnebbeainfevinalgaltihaedrtfqyeplthiqlaasrt" +
            "boatonittrtneetdarbiehidfrsyessnipemledreetyldei" +
            "elehlsaehhcgebefwopigdaellogafaoiefeadrtaxhrscee" +
            "lcsrlrposeremeuvptchaoehbttaatatrlslrrniohiealeu" +
            "ngaretoarkrigpeiostinilnstqelrooygdigllrshrnhuvn" +
            "srahdhosrtaemhrenahesyroufnlinmcicoceearweaeauan" +
            "finhilitcafoamphvetapitfosaohohlesgiylenrgntengn" +
            "hhwlbbessotpiuonauhituofdicyewnhfgtsoehooryererx" +
            "nonaoeaedeavkafodhimpahcotnldysiipwaaeearerrhsro" +
            "rslslefbsdfbvsegnemonxotdlehriahoeteaohtattnsons" +
            "deitteeoyosedaiitvhsinethgteilrfeeninnhhlcegnoat" +
            "kgixoaifxoamfttnidhxosshnddsdhhmestasenowhfvtahe" +
            "eiagfhpsdhoaasnpeddpriksstkthiemfoseeiountokwtwk" +
            "esmttnenniawaenrheehhydmneiotdsagtnasssitrtoioym" +
            "wniaetuamnumwfdnfeiwuoewrrohaohuteiietidieeecohi" +
            "nsovnceeiadrtnslenrttantehtefuvanierctdicitndesbst";
    public static void main(String[] args) {

        letterFrequencies();
        int assumedKeyLength = 7;
        int []array=new int[assumedKeyLength];
        for (int i = 0; i<assumedKeyLength; i++) array[i] = i;
        ArrayList<int[]> permutations = new ArrayList<>();
        printAllRecursive(7, array,permutations);
        String permutationResult;
        int numberOfResults=0;
        for(int i = 0; i < permutations.size(); i++){
            permutationResult = permuteMessage(permutations.get(i), message);
            if(numberOfOcurrences("the", permutationResult) >= 15) {
                numberOfResults++;
                System.out.print(i + ": ");
                for (int j = 0; j<permutations.get(i).length; j++){
                    System.out.print(permutations.get(i)[j]);
                }
                System.out.println(": " + permutationResult);
            }
        }
        System.out.println(numberOfResults);
    }
    static void letterFrequencies(){
        int []letterFrequency = new int[26];
        Arrays.fill(letterFrequency, 0);

        System.out.println(message.length());
        for (int i = 0; i<message.length(); i++){
            letterFrequency[message.charAt(i) - 'a']++;
        }
        for (int i =0; i<26; i++){
            double freq = 100 * (double)letterFrequency[i] / message.length();
            System.out.println((char)(i + 'a') + ": " + freq );
        }
    }

    static int numberOfOcurrences(String textToSearchFor, String textToSearchIn){
        int occurences = 0;
        // Create a pattern to be searched
        Pattern pattern = Pattern.compile(textToSearchFor);
        // Search above pattern in "geeksforgeeks.org"
        Matcher m = pattern.matcher(textToSearchIn);
        // Print starting and ending indexes of the pattern
        // in text
        while (m.find())
            occurences++;
        return occurences;
    }
    static String permuteMessage(int[] order, String message){
        int length = order.length;
        int stepLength = message.length() / length;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<stepLength; i++){
            for(int j = 0;j<length; j++){
                result.append(message.charAt(i + j * stepLength));
            }
        }
        String columnarDepermuted = result.toString();
        result = new StringBuilder();
        for (int i = 0;i<message.length();i+=length){
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j<length; j++){
                temp.append(columnarDepermuted.charAt(i + order[j]));
            }
            result.append(temp.toString());
        }

        return result.toString();
    }
    public static void printAllRecursive(int n, int[] elements, ArrayList<int[]> permutations) {
        if(n == 1) {
            permutations.add(copyArray(elements));
        } else {
            for(int i = 0; i < n-1; i++) {
                printAllRecursive(n - 1, elements,permutations);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
            printAllRecursive(n - 1, elements,permutations);
        }
    }
    private static void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
    static int[] copyArray(int a[]){
        int []copy = new int[a.length];
        for (int i = 0;i<a.length;i++) copy[i] = a[i];
        return copy;
    }
}
