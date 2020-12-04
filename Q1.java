import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Q1 {
    static class Messages {
        public static final String m1 = "315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778c" +
                "df2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee" +
                "59418ce7dc6bc41556bdb36bbca3e8774301fbcaa3b83b220809560987815f65286764703de0f3d524400a19b159610b11ef" +
                "3e";
        public static final String m2 = "234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543b" +
                "edb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb74151f6d551f4480c82b2cb24cc5b028aa76eb7b4ab24171ab3cdadb8" +
                "356f";
        public static final String m3 = "32510ba9a7b2bba9b8005d43a304b5714cc0bb0c8a34884dd91304b8ad40b62b07df44ba6e9d8a2368e51d04e0e7b207b70b9b8261112b" +
                "acb6c866a232dfe257527dc29398f5f3251a0d47e503c66e935de81230b59b7afb5f41afa8d661cb";
        public static final String m4 = "32510ba9aab2a8a4fd06414fb517b5605cc0aa0dc91a8908c2064ba8ad5ea06a029056f47a8ad3306ef5021eafe1ac01a81197847a5c68" +
                "a1b78769a37bc8f4575432c198ccb4ef63590256e305cd3a9544ee4160ead45aef520489e7da7d835402bca670bda8eb775200b8dabbba" +
                "246b130f040d8ec6447e2c767f3d30ed81ea2e4c1404e1315a1010e7229be6636aaa";
        public static final String m5 = "3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68" +
                "bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c65b40aaa065f2a5e33a5a0bb5dcaba4" +
                "3722130f042f8ec85b7c2070";
        public static final String m6 = "3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68" +
                "bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c65b40aaa065f2a5e33a5a0bb5dcaba4" +
                "3722130f042f8ec85b7c2070";
        public static final String m7 = "3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68" +
                "bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c65b40aaa065f2a5e33a5a0bb5dcaba4" +
                "3722130f042f8ec85b7c2070";
        public static final String m8 = "315c4eeaa8b5f8bffd11155ea506b56041c6a00c8a08854dd21a4bbde54ce56801d943ba708b8a3574f40c00fff9e00fa1439fd0654327" +
                "a3bfc860b92f89ee04132ecb9298f5fd2d5e4b45e40ecc3b9d59e9417df7c95bba410e9aa2ca24c5474da2f276baa3ac325918b2daada4" +
                "3d6712150441c2e04f6565517f317da9d3";
        public static final String m9 = "271946f9bbb2aeadec111841a81abc300ecaa01bd8069d5cc91005e9fe4aad6e04d513e96d99de2569bc5e50eeeca709b50a8a987f4264" +
                "edb6896fb537d0a716132ddc938fb0f836480e06ed0fcd6e9759f40462f9cf57f4564186a2c1778f1543efa270bda5e933421cbe88a4a5" +
                "2222190f471e9bd15f652b653b7071aec59a2705081ffe72651d08f822c9ed6d76e48b63ab15d0208573a7eef027";
        public static final String m10 = "466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503" +
                "bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d66f1d559ba520e89a2cb2a83";
        public static final String toDecode = "32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e" +
                "77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904";
        public static final String[] allMessages = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10};

    }

    public static String xorHex(String a, String b) {
        // TODO: Validation
        char[] chars = new char[max(a.length(), b.length())];
        for (int i = 0; i < chars.length; i++) {
            if (i < min(a.length(), b.length()))
                chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
            else if (a.length() > b.length()) {
                chars[i] = a.charAt(i);
            } else {
                chars[i] = b.charAt(i);
            }
        }
        return new String(chars);
    }

    private static int fromHex(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        throw new IllegalArgumentException();
    }

    private static char toHex(int nybble) {
        if (nybble < 0 || nybble > 15) {
            throw new IllegalArgumentException();
        }
        return "0123456789ABCDEF".charAt(nybble);
    }

    private static ArrayList<String> draggingXOR(String message, String text) {
        ArrayList<String> answers = new ArrayList<>();
        int textLength = text.length();
        for (int i = 0; i < message.length() - textLength + 1; i += 2) {
            answers.add(xorHex(message.substring(i, i + textLength), text));
        }
        return answers;
    }

    private static ArrayList<Integer> xorByCharacterResults(String hexEncodedCharacter){
        boolean firstRun = true;
        ArrayList<Integer> characterLocations = new ArrayList<>();
        ArrayList<Integer> tempIntersection = new ArrayList<>();

        ArrayList<String> answers;
        int xorResult;
        String charToSearchForHex = hexEncodedCharacter;
        int decimalAmountOfTheAboveHex = fromHex(charToSearchForHex.charAt(0)) * 16
                + fromHex(charToSearchForHex.charAt(1));
        for (String message : Messages.allMessages) {
            answers = draggingXOR(xorHex(message, Messages.toDecode), charToSearchForHex);
            for (int i = 0; i < min(answers.size(), Messages.toDecode.length() / 2); i++) {
                xorResult = fromHex(answers.get(i).charAt(0)) * 16 + fromHex(answers.get(i).charAt(1));
                if (
                        xorResult == decimalAmountOfTheAboveHex ||
                        (xorResult >= 65 && xorResult <= 90) || (xorResult >= 97 && xorResult <= 122)) {
                    if (firstRun) {
                        characterLocations.add(i);
                    } else {
                        if (characterLocations.contains(i)) {
                            tempIntersection.add(i);
                        }
                    }
                }
            }
            if (!firstRun) {
                characterLocations.clear();
                characterLocations.addAll(tempIntersection);
                tempIntersection.clear();
            }
            System.out.println(characterLocations);
            firstRun = false;
        }
        return characterLocations;
    }
    private static String convertHexStringToString(String hexencodedString){
        StringBuffer stringBuffer = new StringBuffer();
        char []result = new char[hexencodedString.length()/2];
        for (int i = 0; i<hexencodedString.length(); i+=2){
            stringBuffer.append((char)(fromHex(hexencodedString.charAt(i)) * 16 + fromHex(hexencodedString.charAt(i + 1))));
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(xorByCharacterResults("20"));
        StringBuffer buffer = new StringBuffer();
        System.out.println("Enter the word to search for in the text:");
        String wordToSearch = " from ";
        wordToSearch = "we can factor the number ";
        String firstString = Messages.m1;
        String secondString = Messages.toDecode;  // Decode can only be this string
        char []tempArray = wordToSearch.toCharArray();
        ArrayList<String> results;
        for (char c : tempArray) {
            buffer.append(Integer.toHexString((int)c));
        }
        wordToSearch = buffer.toString();
        ArrayList<Integer> occurences;
        char []decoded = new char[secondString.length()/2];
        Arrays.fill(decoded, '*');
        results = draggingXOR(xorHex(firstString, secondString), wordToSearch);

        int count = -1;
        ArrayList<Integer> foundIndices = new ArrayList<>();
//        foundIndices.add(0);foundIndices.add(1);foundIndices.add(2);
        foundIndices.add(11);foundIndices.add(12);foundIndices.add(13);foundIndices.add(14);
        foundIndices.add(15);foundIndices.add(16);foundIndices.add(17);
        foundIndices.add(26);foundIndices.add(27);foundIndices.add(28);
        foundIndices.add(29);foundIndices.add(30);foundIndices.add(31);
        foundIndices.add(32);foundIndices.add(33);foundIndices.add(34);
        foundIndices.add(55);foundIndices.add(56);foundIndices.add(63);
        foundIndices.add(57);foundIndices.add(58);foundIndices.add(59);
        foundIndices.add(60);foundIndices.add(61);foundIndices.add(62);
        foundIndices.add(69);foundIndices.add(77);foundIndices.add(70);
        foundIndices.add(71);foundIndices.add(72);foundIndices.add(73);
        foundIndices.add(74);foundIndices.add(75);foundIndices.add(76);

//        foundIndices.add(35);foundIndices.add(36);foundIndices.add(37);
//        foundIndices.add(38);foundIndices.add(39);foundIndices.add(40);
//        foundIndices.add(41);foundIndices.add(42);foundIndices.add(43);
//        foundIndices.add(44);foundIndices.add(45);foundIndices.add(46);
//        foundIndices.add(47);foundIndices.add(48);

//        foundIndices.add(0);foundIndices.add(1);foundIndices.add(2);
//        foundIndices.add(61);foundIndices.add(62);foundIndices.add(63);
//        foundIndices.add(11);foundIndices.add(12);foundIndices.add(13);foundIndices.add(14);
//        foundIndices.add(26);foundIndices.add(28);foundIndices.add(29);foundIndices.add(30);
//
//        foundIndices.add(69);foundIndices.add(70);foundIndices.add(71);foundIndices.add(72);
//        foundIndices.add(55);foundIndices.add(57);foundIndices.add(58);foundIndices.add(59);
//        foundIndices.add(74);foundIndices.add(75);foundIndices.add(76);foundIndices.add(77);
//        foundIndices.add(31);foundIndices.add(32);
//        foundIndices.add(33);foundIndices.add(34);foundIndices.add(35);
//        //        foundIndices.add();foundIndices.add();foundIndices.add();foundIndices.add();
//
//        // Space Locations:
//        foundIndices.add(10);
//        foundIndices.add(27);
        //foundIndices.add(18);foundIndices.add(22);foundIndices.add(60);foundIndices.add(68);foundIndices.add(78);

        for (String string : results) {
            count++;
            if (count % 10 == 1) System.out.print("\n");
            if (count>Messages.toDecode.length()/2) break;
            if (foundIndices.contains(count)) continue;
            System.out.print(count + ": " + convertHexStringToString(string) + "\t\t");

        }

        char[] DecodedString = new char[Messages.toDecode.length()/2];
        for (int i = 0; i<DecodedString.length; i++) DecodedString[i] = '*';
        DecodedString[0] = 't';
        DecodedString[1] = 'h';
        DecodedString[2] = 'e';
        DecodedString[3] = ' ';

        DecodedString[61] = 't';
        DecodedString[62] = 'h';
        DecodedString[63] = 'e';
        DecodedString[64] = ' ';

        DecodedString[10] = ' ';
        DecodedString[11] = 'm';
        DecodedString[12] = 'e';
        DecodedString[13] = 's';
        DecodedString[14] = 's';

        DecodedString[26] = 'n';
        DecodedString[27] = ' ';
        DecodedString[28] = 'u';
        DecodedString[29] = 's';
        DecodedString[30] = 'i';

        DecodedString[69] = 'm';
        DecodedString[70] = 'o';
        DecodedString[71] = 'r';
        DecodedString[72] = 'e';
        DecodedString[73] = ' ';

        DecodedString[55] = 'r';
        DecodedString[56] = ' ';
        DecodedString[57] = 'u';
        DecodedString[58] = 's';
        DecodedString[59] = 'e';

        DecodedString[73] = ' ';
        DecodedString[74] = 't';
        DecodedString[75] = 'h';
        DecodedString[76] = 'a';
        DecodedString[77] = 'n';
        DecodedString[78] = ' ';

        DecodedString[31] = 'n';
        DecodedString[32] = 'g';
        DecodedString[33] = ' ';

        DecodedString[34] = 'a';
        DecodedString[35] = ' ';

        DecodedString[15] = 'a';
        DecodedString[16] = 'g';
        DecodedString[17] = 'e';

        // Below found using searching "from" in m8 then used m2 to get "never"
        DecodedString[35] = ' ';
        DecodedString[36] = 's';
        DecodedString[37] = 't';
        DecodedString[38] = 'r';
        DecodedString[39] = 'e';
        DecodedString[40] = 'a';
        DecodedString[41] = 'm';
        DecodedString[42] = ' ';
        DecodedString[43] = 'c';
        DecodedString[44] = 'i';
        DecodedString[45] = 'p';
        DecodedString[46] = 'h';
        DecodedString[47] = 'e';
        DecodedString[48] = 'r';
        DecodedString[49] = ',';
        DecodedString[50] = ' ';

        DecodedString[51] = 'n';
        DecodedString[52] = 'e';
        DecodedString[53] = 'v';
        DecodedString[54] = 'e';
        DecodedString[55] = 'r';
        // I can deduce that the next word is "key". Be sure by testing on m1
        DecodedString[65] = 'k';
        DecodedString[66] = 'e';
        DecodedString[67] = 'y';

        DecodedString[79] = 'o';
        DecodedString[80] = 'n';
        DecodedString[81] = 'c';
        DecodedString[82] = 'e';
        //
        DecodedString[4] = 's';
        DecodedString[5] = 'e';
        // test on m5 and complete
        DecodedString[6] = 'c';
        DecodedString[7] = 'r';
        DecodedString[8] = 'e';
        DecodedString[9] = 't';
        // try: "the secret message " on m1. results in:
        DecodedString[19] = 'i';
        DecodedString[20] = 's';
        DecodedString[21] = ':';
        DecodedString[23] = 'W';
        //
        DecodedString[24] = 'h';
        DecodedString[25] = 'e';

        // Space Locations:
        DecodedString[18] = ' ';
        DecodedString[22] = ' ';
        DecodedString[60] = ' ';
        DecodedString[68] = ' ';
        DecodedString[78] = ' ';






        System.out.print("\nThe Resultant String So Far: ");
        System.out.print(DecodedString);
    }


}
