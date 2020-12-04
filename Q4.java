import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Q4 {
    static int dimension = 51;
    static int maxPowerOf2LowerThanBinaryStringLength = 11 + 1;
    static int[][][] preComputationPowerMatrix = new int[maxPowerOf2LowerThanBinaryStringLength][dimension][dimension];
    static int[][] identityMatrix = new int[dimension][dimension];
    static {
        for (int i = 0; i < dimension; i++) {
            Arrays.fill(identityMatrix[i], 0);
            identityMatrix[i][i] = 1;
        }
        Arrays.fill(preComputationPowerMatrix[0][0], 0);
        for (int i = 1; i < dimension; i++) {
            Arrays.fill(preComputationPowerMatrix[0][i], 0);
            preComputationPowerMatrix[0][i][i - 1] = 1;
        }
        preComputationPowerMatrix[0][0][50] = 1;
        preComputationPowerMatrix[0][9][50] = 1;
        for (int i = 1; i < maxPowerOf2LowerThanBinaryStringLength; i++) {
            preComputationPowerMatrix[i] = matrixMultiplication(preComputationPowerMatrix[i - 1]);
        }
    }

    public static void main(String[] args) {
        String messageHex =
                "6d2a42943f64e701286b8d0cb90f6855673b48eb5b8e056742cb29007c1dcf1220a0ddc6c33096e26cf9d1fa17812c5e7245" +
                        "6676c0225b4773c70963431d224d616779ca6427f1203e783c896f82be39f15917a21f41b03d74abe256e8c2474cae0b55df" +
                        "296647b070724466d0ac61078b691975359bd19ce1bbb3060ce5e54c52f83325d32933a57581ec189c4b12f8fc9459ad3f51" +
                        "7ee171204164165922340a9f875bbf7d16a618ffb37bfee8ab4cae305aa76256dde144b92083ba3858af5a8ae2b5a75bd30a" +
                        "053b9287d876ac0721e543975f8cc430b8327351ca7c6ac8592b7f7463b062256316c0bf3de55d989527cd0f7e1007b482a" +
                        "599f1167ab23d2e8db7be9763f5a956519e6256b4e196a31cb288da335e69496cb3fa5beb55d899d6c5b142c7169c35d1504" +
                        "04481710218dd1da824927f8514e5eddc95d1efd22d2ef9b88af2596d00077899eb4cfdaeb8e9bbee0c273243142e3ef6a3" +
                        "f43a138d2d";
        String binaryMessage = hexToBinary(messageHex);
        int binaryMessageLength = binaryMessage.length();

        int[] permutation;
        int[] eVector = new int[dimension];
        Arrays.fill(eVector, 0);
        eVector[0] = 1;
        int[][] matrixToFindItsInverse = new int[dimension][dimension];
        int[][] poweredMatrix;
        int[] tempVector;
        int[][] inverse;
        while (true) {
            try {
//                permutation = randomPermutation(binaryMessageLength);
//                Arrays.sort(permutation);
                permutation = orderedIndices(0);
                for (int i = 0; i < dimension; i++) {
                    poweredMatrix = matrixPower(permutation[i]);
//                    System.out.println("The coefficient matrix to the power " + permutation[i]);
//                    printMatrix(poweredMatrix);
                    tempVector = matrixTimesVector(poweredMatrix, eVector);
                    for (int j = 0; j < dimension; j++) {
                        matrixToFindItsInverse[j][i] = tempVector[j];
                    }
                }
//                printMatrix(matrixToFindItsInverse);
                inverse = matrixInverse(matrixToFindItsInverse);
                break;
            } catch (Exception e) {
//                System.out.println(e);
            }
        }
        printVector(permutation);
        int[] xVector = new int[dimension];
        for (int i = 0; i < dimension; i++) {
//            xVector[i] = binaryMessage.charAt(binaryMessageLength - 1 - permutation[i]) - '0';
            xVector[i] = binaryMessage.charAt(permutation[i]) - '0';
        }
        System.out.println("the X Vector:");
        printVector(xVector);
        int[] initialState;
        initialState = vectorTimesMatrix(inverse, xVector);
        System.out.println("The initial state:");
        printVector(initialState);
        ArrayList<Integer> state = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            state.add(initialState[i]);
        }
        int[] lfsrOutput = LFSR(state, binaryMessageLength);
        decode(lfsrOutput, binaryMessage);


    }

    static void decode(int[] lfsrOutput, String binaryMessage) {
        int[] decodedBinary = new int[binaryMessage.length()];
        Arrays.fill(decodedBinary, 0);
        for (int i = 0; i < binaryMessage.length(); i++) {
//            decodedBinary[i] = lfsrOutput[i] ^ (binaryMessage.charAt(binaryMessage.length() - i - 1) - '0');
            decodedBinary[i] = lfsrOutput[i] ^ (binaryMessage.charAt(i) - '0');
        }
        System.out.println(binaryMessage);
        printVector(decodedBinary);
        int count = 0;
        for (int i = 0; i < binaryMessage.length(); i += 8) {
            count++;
            int currentAsciiValue = 0;
            int multiplyBy = 1;
            for (int j = 7; j >= 0; j--) {
                if (decodedBinary[i + j] != 0) {
                    currentAsciiValue += multiplyBy;
                }
                multiplyBy *= 2;
            }
            System.out.print((char)(currentAsciiValue));
            if (count == 100) {
                System.out.print("\n");
                count = 0;
            }
        }
    }

    static int[] orderedIndices(int count) {
        int[] result = new int[dimension];
        for (int i = 0; i < dimension; i++) {
            result[i] = (i + count + 1) * 16;
        }
        return result;
    }

    static void printMatrix(int[][] matrix) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    static void printVector(int[] vector) {
        for (int i = 0; i < dimension; i++)
            System.out.print(vector[i] + " ");
        System.out.print("\n");
    }

    static int[][] matrixInverse(int[][] inputMatrix) {
        int[][] result = copyMatrix(identityMatrix);
        int[][] matrix = copyMatrix(inputMatrix);
        int count = 0;
        for (int i = 0; i < dimension; i++) {
//            printMatrix(result);
            int j = count;
            for (; matrix[j][i] == 0; j++) {
                if (j == dimension - 1) {
                    System.out.println(i + ". Error. Matrix is singular");
                }
            }
            if (j != count) {
                swapRows(matrix, j, count);
                swapRows(result, j, count);
            }
            for (int k = 0; k < dimension; k++) {
                if (k == count || matrix[k][i] == 0) continue;
                addVector(matrix, matrix[count], k);
                addVector(result, result[count], k);
            }
            count++;
        }
        return result;
    }


    static int[][] matrixPower(int desiredPower) {
        StringBuilder temp;
        String binaryString;
        temp = new StringBuilder(Integer.toBinaryString(desiredPower));
        while (temp.length() < maxPowerOf2LowerThanBinaryStringLength) {
            temp.insert(0, "0");
        }
        binaryString = temp.toString();
        int[][] result = copyMatrix(identityMatrix);
        for (int i = 0; i < maxPowerOf2LowerThanBinaryStringLength; i++) {
            if (binaryString.charAt(i) == '1') {
                result = matrixMultiplication(result, preComputationPowerMatrix[maxPowerOf2LowerThanBinaryStringLength - 1 - i]);
            }
        }
        return result;
    }

    static int[][] copyMatrix(int[][] a) {
        int[][] copied = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                copied[i][j] = a[i][j];
            }
        }
        return copied;
    }

    static void swapRows(int[][] matrix, int firstRow, int secondRow) {
        int temp;
        for (int i = 0; i < dimension; i++) {
            temp = matrix[firstRow][i];
            matrix[firstRow][i] = matrix[secondRow][i];
            matrix[secondRow][i] = temp;
        }
    }

    static void addVector(int[][] matrix, int[] vector, int rowToAdd) {
        for (int i = 0; i < dimension; i++) {
            matrix[rowToAdd][i] = matrix[rowToAdd][i] ^ vector[i];
        }
    }

    static int[] matrixTimesVector(int[][] matrix, int[] vector) {
        int[] result = new int[dimension];
        for (int i = 0; i < dimension; i++) {
            result[i] = 0;
            for (int j = 0; j < dimension; j++) {
                result[i] = result[i] ^ (matrix[i][j] * vector[j]);
            }
        }

        return result;
    }

    static int[] vectorTimesMatrix(int[][] matrix, int[] vector) {
        int[] result = new int[dimension];
        for (int i = 0; i < dimension; i++) {
            result[i] = 0;
            for (int j = 0; j < dimension; j++) {
                result[i] = result[i] ^ (vector[j] * matrix[j][i]);
            }
        }

        return result;
    }

    static int[][] matrixMultiplication(int[][] a) {
        int[][] result = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < a.length; k++) {
                    result[i][j] = result[i][j] ^ (a[i][k] * a[k][j]);
                }
            }
        }
        return result;
    }

    static int[][] matrixMultiplication(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < b.length; k++) {
                    result[i][j] = result[i][j] ^ (a[i][k] * b[k][j]);
                }
            }
        }
        return result;
    }

    static int[] randomPermutation(int binaryMessageLength) {
        ArrayList<Integer> possibleIndices = new ArrayList<>();
        for (int i = 1; 8 * i < binaryMessageLength; i++) {
            possibleIndices.add(8 * i);
        }
        int[] permutation = new int[dimension];
        Random random = new Random(System.nanoTime());
        int count = 0;
        while (count < dimension) {
            permutation[count] = possibleIndices.remove(random.nextInt(possibleIndices.size()));
            count++;
        }
        return permutation;
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

    private static String hexToBinary(String hex) {
        int decimalValue;
        StringBuilder temp;
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < hex.length(); i += 2) {
            decimalValue = fromHex(hex.charAt(i)) * 16 + fromHex(hex.charAt(i + 1));
            temp = new StringBuilder(Integer.toBinaryString(decimalValue));
            while (temp.length() < 8) {
                temp.insert(0, "0");
            }
            result.append(temp);
        }

        return result.toString();
    }

    private static int LFSR(ArrayList<Integer> State) {
        State.add(State.get(0) ^ State.get(9));
        return State.remove(0);
    }

    private static int[] LFSR(ArrayList<Integer> State, int outputLength) {
        int[] lfsrOutput = new int[outputLength];
        for (int i = 0; i < outputLength; i++) {
            lfsrOutput[i] = LFSR(State);
        }
        return lfsrOutput;
    }
}
