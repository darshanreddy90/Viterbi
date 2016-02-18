import java.util.Scanner;
/*
* This class computes the Viterbi Sequence for given input, the HMM model for this program is hardcoded from the question
*
* */
public class Viterbi {
    public static double[][] HMM;

    public static void main(String[] args) {
	    //initialize a 2X3 matrix for holding probability of each of 3 observations fir the 2 states available
        //let HMM[1] be hot and HMM[2] be cold
        HMM = new double[3][4];
        HMM[1] = new double[]{.8, .2, .4, .4};
        HMM[2] = new double[]{.2, .5, .4, .1};
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.next();
        computeViterbi(inputString);
    }
    /*
    * Given the input sequence of observation and HMM, it computes and pritns the state sequence
    * */
    private static void computeViterbi(String inputString) {
        double[][] computationMatrix = new double[3][inputString.length()+1];
        int[][] stateTrackingMatrix = new int[3][inputString.length()+1];
        computationMatrix[1][0] = HMM[1][0];
        computationMatrix[2][0] = HMM[2][0];

        for (int i = 1; i <= inputString.length(); i++) {
            int currentObservation = Integer.parseInt(String.valueOf(inputString.charAt(i-1)));
            // Calculating and storing the argmax
            double valueAt1From1 = computationMatrix[1][i-1]*HMM[1][currentObservation];
            double valueAt1From2 = computationMatrix[2][i-1]*HMM[1][currentObservation];
            double valueAt2From1 = computationMatrix[1][i-1]*HMM[2][currentObservation];
            double valueAt2From2 = computationMatrix[2][i-1]*HMM[2][currentObservation];

            if(valueAt1From1 > valueAt1From2) {
                computationMatrix[1][i] = valueAt1From1;
                stateTrackingMatrix[1][i] = 1;
            } else {
                computationMatrix[1][i] = valueAt1From2;
                stateTrackingMatrix[1][i] = 2;
            }
            if(valueAt2From1 > valueAt2From2) {
                computationMatrix[2][i] = valueAt2From1;
                stateTrackingMatrix[2][i] = 1;
            } else {
                computationMatrix[2][i] = valueAt2From2;
                stateTrackingMatrix[2][i] = 2;
            }
        }

        // This code is to Print the computed state sequence in the order
        int maxState = (computationMatrix[1][inputString.length()] > computationMatrix[2][inputString.length()]) ? 1 : 2;
        int[] outPutSates = new int[inputString.length()+1];
        for (int i = inputString.length(); i > 0; i--) {
            outPutSates[i] = maxState;
            maxState = stateTrackingMatrix[maxState][i];
        }

        for (int i = 1; i < inputString.length() + 1; i++) {
            if(outPutSates[i]==1){
                System.out.print(" HOT ");
            } else {
                System.out.print(" COLD ");
            }
        }
    }
}
