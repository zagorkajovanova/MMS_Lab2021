/* example: Enter sequence for coding: MULTI
            Enter probability for each letter from the sentence: 0.1 0.3 0.3 0.2 0.1
            Result: 0.81602 0.8162
*/

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Arithmetic_Coding {

    static double high = 1;
    static double low = 0.0;
    static double[][] letter_prob;
    static ArrayList<Pairs> pairs = new ArrayList<>();


    public static void main(String[] args)
    {
        // Input Word and Letter probability
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter sequence for coding: ");
        String input = scanner.nextLine();
        char[] letter = input.toCharArray();


        System.out.println("Enter probability for each letter from the sequence: ");
        Double[] probability = new Double[letter.length];
        for(int k=0; k<letter.length; k++) probability[k] = scanner.nextDouble();

        scanner.close();

        letter_prob = new double[letter.length][letter.length];

        for(int i=0; i<letter.length; i++)
        {
            letter_prob[0][i] = letter[i];
            letter_prob[1][i] = probability[i];
        }

        bubble_srt(letter.length);

        for(int i=0; i<letter.length; i++)
        {
            pairs.add(new Pairs(low,letter_prob[1][i],(char)letter_prob[0][i]));
            low = pairs.get(i).getHigh();
        }

        high = 1;
        low  = 0;

        for(int i=0; i<letter.length; i++)
        {
            int j=0;
            while(j<letter.length)
            {
                if(letter[i] == (char)letter_prob[0][j])
                {
                    encoding(pairs.get(j).getHigh(),pairs.get(j).getLow());
                    break;
                }
                else j++;
            }

        }
        System.out.println(low + " " + high);

    }

    private static void encoding(double high_bukva, double low_bukva)
    {
        double range = calcSub(high,low);
        high = calcSum(low,calcMult(range,high_bukva));
        low = calcSum(low,calcMult(range,low_bukva));
    }
    private static double calcSum(double a1, double a2)
    {
        BigDecimal bd = new BigDecimal(Double.toString(a1));
        BigDecimal bd2 = new BigDecimal(Double.toString(a2));
        return bd.add(bd2).doubleValue();
    }
    private static double calcSub(double a1, double a2)
    {
        BigDecimal bd = new BigDecimal(Double.toString(a1));
        BigDecimal bd2 = new BigDecimal(Double.toString(a2));
        return bd.subtract(bd2).doubleValue();
    }
    private static double calcMult(double a1, double a2)
    {
        BigDecimal bd = new BigDecimal(Double.toString(a1));
        BigDecimal bd2 = new BigDecimal(Double.toString(a2));
        return bd.multiply(bd2).doubleValue();
    }

    public static void bubble_srt(int length)
    {
        int k;
        for (int m = length; m >= 0; m--)
        {
            for (int i = 0; i < length - 1; i++)
            {
                k = i + 1;
                if (letter_prob[1][i] < letter_prob[1][k])
                {
                    double temp,temp2;
                    temp = letter_prob[0][i];
                    temp2 = letter_prob[1][i];
                    letter_prob[0][i] = letter_prob[0][k];
                    letter_prob[1][i] = letter_prob[1][k];
                    letter_prob[0][k] = temp;
                    letter_prob[1][k] = temp2;
                }
            }
        }
    }
    public static class Pairs {

        double low = 0;
        double prob = 0;
        char letter;

        Pairs(double low, double prob, char bukva)
        {
            this.low = low;
            this.prob = prob;
            this.letter = bukva;
        }
        private double calcHigh()
        {
            BigDecimal bd = new BigDecimal(Double.toString(prob));
            BigDecimal bd2 = new BigDecimal(Double.toString(low));
            return bd.add(bd2).doubleValue();
        }

        public double getLow() { return low; }
        public double getHigh() { return calcHigh();}
        public double getProb() { return prob;}
    }
}
