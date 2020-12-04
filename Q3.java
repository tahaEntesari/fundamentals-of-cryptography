import java.util.ArrayList;
import java.util.Arrays;

public class Q3 {
    static String message =
            "ALLHESHCQAEXSMTGRPPAQXSUSFEEUOFATXLJMDQUHEYPLAZPAUL" +
            "XSQTJYPYESRTZGGJTFSUVPQDOISALVXSQSWXCGTZWEABWWPXFWZ" +
            "TPEFXETALEWXMWRLDEUVPMTWHPCUSPTTANILPRWEXFHSXZZEVEJ" +
            "ANLLPDEVLTXLKSQSEGVRUALLPEOFWZRFGVXQRKPLHEKEYPTZIDA" +
            "NKSQROJQPDSDEGQOORPDSOMWXBWEMXELSDUTVSHZTGKPFHWVLFT" +
            "ZIEMBDIZRBJSETEJLZAD";

    public static void main(String[] args) {
//        letterFrequencies(message);
        System.out.println(message.length());
        int windowLength = 5;
        ArrayList<String> segregated = segregateString(windowLength);
        double [][]letterFreq = new double[windowLength][26];
        for (int i = 0; i<windowLength; i++) {
            letterFreq[i] = letterFrequencies(segregated.get(i));
            System.out.println(sumOfSquares(letterFreq[i]) + "\n");
        }
        int[] key = {'w' - 'e', 'e' - 'a', 'l' - 'a', 'm' - 'a', 'a' - 'a'};
//        int[] key = {'w' - 'e', 'e' - 'a', 'l' - 'a', 'e' - 'a', 'a' - 'a', 'a' - 'a'};
        // 1:
        // 2:
        // 3: p-a;e-a;l-a
        // 4: m-a;q-a;e-a

        // 's' - a
        // 2nd is OK
        // 3rd is OK
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < message.length(); i+= windowLength){
            for (int j = 0; j<windowLength; j++){
                if (i + j >= message.length()) break;
                result.append(decypher(message.charAt(i + j), key[j]));
            }
        }
        System.out.println(result.toString());
        letterFrequencies(result.toString());
        System.out.println(decypher('A',1));
    }
    static char decypher(char input, int key){
        char result;
        result = (char)(input - key);
        int temp;
        if(result < 65){
            temp = 65 - result;
            System.out.println(temp);
            result = (char)(91 - temp);
        }
        if (result > 90){
            temp = result - 90;
            result = (char)(64 + temp);
        }
        return result;
    }
    static double sumOfSquares(double []freq){
        double result = 0;
        for (double v : freq) {
            result += v*v;
        }
        return result;
    }
    static ArrayList<String> segregateString(int stepLength){
        ArrayList<String> result = new ArrayList<>();
        StringBuilder temp;
        for (int i = 0; i < stepLength; i++){
            temp = new StringBuilder();
            for (int j = 0; i + j < message.length(); j+= stepLength){
                temp.append(message.charAt(i + j));
            }
            result.add(temp.toString());
        }
        return result;
    }


    static double[] letterFrequencies(String message){
        double []letterFrequency = new double[26];
        Arrays.fill(letterFrequency, 0);
        for (int i = 0; i<message.length(); i++){
            letterFrequency[message.charAt(i) - 'A']++;
        }
        for (int i =0; i<26; i++){
            letterFrequency[i] /= message.length();

            System.out.println((char)(i + 'A') + ": " + 100 * letterFrequency[i] );
        }
        return letterFrequency;
    }
}
