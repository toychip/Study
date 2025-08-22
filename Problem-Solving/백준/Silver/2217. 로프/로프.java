import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(reader.readLine());
        int[] values = new int[N];

        for (int i = 0; i < N; i++) {
            int value = Integer.parseInt(reader.readLine());
            values[i] = value;
        }

        Arrays.sort(values);

        int max = 0;
        for (int i = 0; i < N; i++) {
            int currentWeight = values[i] * (N - i);
            max = Math.max(max, currentWeight);
        }
        System.out.print(max);
    }

}
