/* example: Enter sequence for encoding: /TAN/HAN/HAN/AN/
            Result: 1: /
                    2: T
                    3: A
                    4: N
                    5: H
                    6: /T
                    7: TA
                    8: AN
                    9: N/
                    10: /H
                    11: HA
                    12: AN/
                    13: /HA
                    14: AN/A
                    Encoded sequence: 1 2 3 4 1 5 8 10 12 12
 */
import java.util.ArrayList;
import java.util.Scanner;

public class LZW {

    static ArrayList<String> table = new ArrayList<>();
    static ArrayList<Integer> code = new ArrayList<>();

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter sequence for encoding : ");
        String input = scanner.nextLine();
        char[] sequence = input.toCharArray();
        scanner.close();

        encoding(sequence);

        for(String s : table) System.out.println(table.indexOf(s)+1 + ": " + s);

        System.out.print("Encoded sequence: ");
        for(Integer c : code) System.out.print(c+" ");

        /* Calculating compression percent */
        double ratio = ((double)sequence.length - (double)code.size())/(double)sequence.length;
        System.err.println("\n" + "Compression : " + (float)ratio*100 + " %");
    }


    private static void encoding(char[] s)
    {
        /* Initialize Starting Table */
        table.add(Character.toString(s[0]));

        for(int i=1; i<s.length; i++)
        {
            String toBeCompared = Character.toString(s[i]);
            if( !table.contains(toBeCompared)) table.add(toBeCompared);
        }

        /* ENCODING */
        int i=0;
        while(i<s.length)
        {
            StringBuilder sb = new StringBuilder();
            if(i+1<s.length)
            {
                // Add new letter to the current letter
                sb.append(Character.toString(s[i])).append(Character.toString(s[++i]));

                if( !table.contains(sb.toString()))
                {
                    code.add(table.indexOf(Character.toString(s[i-1]))+1);
                    table.add(sb.toString());
                }

                else
                {
                    String temp = "";
                    while(table.contains(sb.toString()))
                    {
                        temp = sb.toString();
                        i++;
                        if(i<s.length)sb.append(Character.toString(s[i]));
                        else break;
                    }

                    code.add(table.indexOf(temp)+1);
                    if( !table.contains(sb.toString()))
                    {
                        table.add(sb.toString());
                    }

                }

            }
            else break;

        }
    }
}