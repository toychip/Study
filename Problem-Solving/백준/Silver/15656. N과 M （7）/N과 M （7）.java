import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {

    static int[] number;
    static int[] array;
    static int N;
    static int M;
    static BufferedWriter writer;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] input1 = reader.readLine().split(" ");
        String[] input2 = reader.readLine().split(" ");

        N = Integer.parseInt(input1[0]);
        M = Integer.parseInt(input1[1]);

        number = new int[N];
        array = new int[M];

        for (int i = 0; i < N; i++) {
            number[i] = Integer.parseInt(input2[i]);
        }

        Arrays.sort(number);

        backtrack(0);
        writer.flush();
        writer.close();
    }

    private static void backtrack(final int depth) throws IOException {
        if (depth == M) {
            for (int i = 0; i < M; i++) {
                writer.write(String.valueOf(array[i]));
                writer.write(" ");
            }
            writer.write("\n");
            return;
        }

        for (int i = 0; i < N; i++) {
            array[depth] = number[i];
            backtrack(depth + 1);
        }
    }
}
