import java.util.ArrayList;
import java.util.Arrays;

public class Q5 {
    static String message =
            "icdfgpvbrwefhyjuqwwvffnpzkgawrvyokopycsjnliszokpop" +
                    "jdbrdynboljglubiwlchgdsanwrqjelvgfvodpwuwcmyafajni" +
                    "xcqqalgdycevxmocjohauzjwyzhjpuxerrxautjwrsggzkicox" +
                    "sideghfnvykaxxfcafjftdegxvrajnvbrpkssiixwkkoorkqko" +
                    "pnerruwtimeikdjpsgmivynbtosbkopesnxyicnseikjxyfsqa" +
                    "yrhkvsjaaauvijgsafgtdeguklccnmmdqrwwnyihibpohmukmm" +
                    "chewcpxgxddzysecspickxytbnrhgaqqtfkxietfsxizqmfkws" +
                    "pbkgtipaebjxycinbmzvyvfkcasuluchkvicdfgpeajnjnpaoc" +
                    "sxtuhtfjbevikppfhhjpcmyicmuzttxosgteikrtrgirbypgag" +
                    "ogytyttrvnysdmlqkhyjuhqvwcbezejkbwothcnwnwatoegmtk" +
                    "ouanaoapqppidiqmbvqlolrhjyawkrdkifncxkqxujennnvppp" +
                    "ytpaophroihcmztimgcjhgbgwfteqdeggbadljyupbodmmdvpt" +
                    "nldzgbeirsklaqpotjtimgcjczgrqcwjcajnlnpykaepgvtole" +
                    "zozazekpevnnfuvmrinrctpytpaolmaicdmogqelctjczyelso" +
                    "ybrldoybczgsnvvjbjqtkqmykbrumgyxczgydujcoaoznnhkis" +
                    "kggdegmkrohajgxmqcjnjiqmfuvizahbiscmihxwtnryztimwb" +
                    "imijykhblltkrctgmeiewjuruxiixgxrtrwvfcvvcrkjelwqui" +
                    "ravxmwqtgoeyxbomimhjmyyswvmlkrkqsklaqpajhwionbrajn" +
                    "yfynjntkopyfznvctgnwwgyemmdvptnldvqhmboajndijervda" +
                    "owjcajnlnpykawnqcdigipfizdeyiwwcidpaoajnvbnocwyvms" +
                    "dmbgtiylwvffxcjmvykhsshsjpuwtxlftnbajnrjhnaqhqvdjp" +
                    "jqtajnvbrmgmwbcqkhlapolusdeajnicdfgpimfqtitjduxerr" +
                    "xtevsusltjontckwnryxtrcqmewacmybgnvethatsggzmjixxo" +
                    "wehqvtghbeijglcidfgdlxwcoxrkdvrtcidvbrdkzktegknwar" +
                    "hljumcnahjwaouazmjudaekgswvvxmcinsaubeibzfqmfhbfol" +
                    "umtgofyfpikumofdsdecspdqtbevsmvunnlzreyghnkakzckdv" +
                    "kmucanldesnfnmxtrcqmwalsshuhdhnxyebmeiflpihmtkoyst" +
                    "ybvovholkokpgothxvjvjmkybblfdzfgdjvjajnehwjaecidpk" +
                    "asjacspspagagnghfylvqhglcmhorajfbbosbexvwhoqphtayg" +
                    "fwsgcmmdcrzrexdenxglansjivgsawehomxnysosocafgcmlap" +
                    "lzxodwsmjhqprkdplntnbpao"; // Length 1524 = 3 * 4 * 127
    static int blockLength = 3;
    static int twentySixCubed = 17576;

    public static void main(String[] args) {
        ArrayList<int[]> cipherBlocks = cipherBlocks();
        ArrayList<Integer> possibleGenKeys = doYourThing(cipherBlocks);
        decypher(cipherBlocks, possibleGenKeys);
    }
    static void decypher(ArrayList<int[]> cipherBlocks, ArrayList<Integer> possibleGenKeys){
        int [][] keyInverseMatrix;
        StringBuilder result;
        int [] temp;
        int[][]copyKey;

        keyInverseMatrix  =  getKeyMatrix(possibleGenKeys);
        int [] index = new int[blockLength];
        for (int i = 0; i< blockLength; i++){
            index[i] = i;
        }
        ArrayList<int[]> permutations = new ArrayList<>();
        generatePermutations(blockLength, index, permutations);
        int numberOfDifferences;
        for (int[] permutation : permutations) {
            numberOfDifferences = 0;
            copyKey = copyMatrix(keyInverseMatrix);
            for (int i = 0; i < permutation.length; i++){
                if (permutation[i] != i) numberOfDifferences += 1;
            }
            if (numberOfDifferences == 2){
                if(permutation[0] == 0) swapColumns(copyKey, 1,2);
                else if (permutation[1] == 1) swapColumns(copyKey, 0,2);
                else if (permutation[2] == 2) swapColumns(copyKey, 0,1);
            } else if (numberOfDifferences == 3){
                swapColumns(copyKey, 0, permutation[0]);
                swapColumns(copyKey,permutation[0], permutation[1]);
            }
            result = new StringBuilder();
            for (int i = 0; i < cipherBlocks.size(); i++){
                temp = vectorMultMatrix(copyKey, cipherBlocks.get(i));
                for (int j = 0; j<blockLength; j++){
                    result.append((char)(temp[j] + 'a'));
                }
            }
            System.out.println(result.toString());
        }

    }
    static void swapColumns(int[][] matrix, int firstColumn, int secondsColumn){
        int temp;
        for (int i = 0; i < matrix.length; i++){
            temp = matrix[i][firstColumn];
            matrix[i][firstColumn] = matrix[i][secondsColumn];
            matrix[i][secondsColumn] = temp;
        }
    }
    static int[][] getKeyMatrix(ArrayList<Integer> possibleGenKeys){
        int [][] keyInverse = new int[blockLength][blockLength];
        int [] genKeys = {5548, 14425, 14516};
        //        int [] genKeys = {5548, 14425, 14516, 14854};


        int []temp;
        int count = 0;
        for (int genKey : genKeys) {
            temp = generateKey(genKey);
            for (int i = 0; i< blockLength; i++){
                keyInverse[i][count ] = temp[i];
            }
            count++;
        }
        return keyInverse;
    }
    static int[] vectorMultMatrix(int[][] array, int[] vector){
        int []result = new int[vector.length];
        Arrays.fill(result, 0);
        for (int i = 0; i<array.length; i++){
            for (int j = 0; j<vector.length; j++){
                result[i] += array[j][i] * vector[j];
                result[i] %= 26;
            }
            while (result[i] < 0) result[i] += 26;
        }
        return result;
    }

    static ArrayList<Integer> doYourThing(ArrayList<int[]> blocks) {
        ArrayList<Integer> possibleKeyGenerators = new ArrayList<>();
        int[] keyInverseColumn;
        String tempResult;
        int genKey;
        double[] frequencies;
        double sigmaf2;
        StringBuilder result;
        for (int j = 0; j < twentySixCubed; j++) {
            genKey = j;
            keyInverseColumn = generateKey(genKey);
            result = new StringBuilder();
            for (int i = 0; i < blocks.size(); i++) {
                result.append((char)(vectorMultiplication(blocks.get(i), keyInverseColumn) + 'a'));
            }

            tempResult = result.toString();
//            System.out.println(tempResult);
            frequencies = letterFrequencyCalculator(tempResult);
            sigmaf2 = sumOfSquares(frequencies);
//            System.out.println("sigmaF2: " + sigmaf2);
            if (condition(sigmaf2, frequencies)) {
                possibleKeyGenerators.add(genKey);
                System.out.print("sigmaF2: " + sigmaf2 + ". ");
                System.out.print("entropy " + entropy(frequencies) + ". ");
                System.out.println("GenKey: " + genKey);
                for (int i = 0; i < 26; i++) {
                    System.out.print((char) (i + 'a') + ": " + 100 * frequencies[i] + " ");
                }
                System.out.println();
            }
        }
        return possibleKeyGenerators;
    }

    static boolean condition(double sumoSquares, double []frequencies) {
        double difference = sumoSquares - 0.065;
        difference = difference < 0 ? -difference : difference;
        boolean myCondition;
        myCondition =
                difference < 0.01
                && frequencies['e' - 'a'] > 0.09
                && frequencies['t' - 'a'] > 0.05
                && frequencies['a' - 'a'] > 0.05
//                && frequencies['o' - 'a'] > 0.03
//                && frequencies['i' - 'a'] > 0.03
//                && frequencies['z' - 'a'] < 0.02
//                && frequencies['q' - 'a'] < 0.02
//                && frequencies['x' - 'a'] < 0.02
        ;
        return myCondition;
    }

    static double sumOfSquares(double[] frequencies) {
        double result = 0;
        for (double frequency : frequencies) {
            result += frequency * frequency;
        }
        return result;
    }
    static double entropy(double[] frequencies){
        double result = 0;
        double log2 = Math.log(2);
        for (double frequency : frequencies) {
            result -= frequency * Math.log(frequency) / log2;
        }
        return result;
    }

    static double[] letterFrequencyCalculator(String input) {
        double[] frequencies = new double[26];
        Arrays.fill(frequencies, 0);
        int temp;
        try {
            for (int i = 0; i < input.length(); i++) {
                temp = input.charAt(i) - 'a';
                frequencies[temp] += 1;
            }
            for (int i = 0; i < 26; i++) {
                frequencies[i] /= input.length();
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error");
            Arrays.fill(frequencies, 0);
        }
        return frequencies;
    }

    static int[] generateKey(int keyToGenerate) {
        int[] converted = new int[blockLength];
        convert(keyToGenerate, 26, converted, blockLength - 1);
        return converted;
    }

    public static void convert(int number, int base, int[] result, int count) {
        int quotient = number / base;
        int remainder = number % base;
        result[count] = remainder;
        if (quotient != 0) {
            convert(quotient, base, result, count - 1);
        }
    }

    static ArrayList<int[]> cipherBlocks() {
        ArrayList<int[]> answer = new ArrayList<>();
        int[] temp = new int[blockLength];
        String stringBlock;
        for (int i = 0; i < message.length(); i += blockLength) {
            stringBlock = message.substring(i, i + blockLength);
            for (int j = 0; j < blockLength; j++) {
                temp[j] = stringBlock.charAt(j) - 'a';
            }
            answer.add(copyArray(temp));
        }
        return answer;
    }



    static int vectorMultiplication(int[] cipher, int[] key) {
        int result = 0;
        for (int i = 0; i < cipher.length; i++) {
            result += cipher[i] * key[i];
            result %= 26;
        }
        while (result < 0) result += 26;
        return result;
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
    static int[][] copyMatrix(int [][]matrix){
        int [][]copy = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }
    public static void generatePermutations(int n, int[] elements, ArrayList<int[]> permutations) {
        if(n == 1) {
            permutations.add(copyArray(elements));
        } else {
            for(int i = 0; i < n-1; i++) {
                generatePermutations(n - 1, elements,permutations);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
            generatePermutations(n - 1, elements,permutations);
        }
    }


}
