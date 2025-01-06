import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input1 = reader.readLine();
            if (input1.equals("0")) {
                writer.flush();
                writer.close();
                break;
            }

            String[] input2 = input1.split(" ");

            int N = Integer.parseInt(input2[0]);

            int[] number = new int[N];
            for (int i = 0; i < N; i++) {
                number[i] = Integer.parseInt(input2[i + 1]);
            }

            Arrays.sort(number);
            int[] pickNumber = new int[6];
            recur(number, pickNumber,0, 0);
            writer.write("\n");
        }
    }

    private static void recur(int[] number, int[] pickNumber, int depth, int start)
            throws IOException {
        if (depth == 6) {
            Arrays.sort(pickNumber);
            for (int i : pickNumber) {
                writer.write(String.valueOf(i));
                writer.write(" ");
            }
            writer.write("\n");
            return;
        }

        for (int i = start; i < number.length; i++) {
            pickNumber[depth] = number[i];
            recur(number, pickNumber, depth + 1, i + 1);
        }

    }
}
