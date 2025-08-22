import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(reader.readLine());
        String[] A = reader.readLine().split(" ");
        int[] intA = new int[A.length];

        int M = Integer.parseInt(reader.readLine());
        String[] inputTarget = reader.readLine().split(" ");
        int[] target = new int[inputTarget.length];

        for (int i = 0; i < N; i++) {
            intA[i] = Integer.parseInt(A[i]);
        }

        for (int i = 0; i < M; i++) {
            target[i] = Integer.parseInt(inputTarget[i]);
        }

        Arrays.sort(intA);

        for (int i = 0; i < M; i++) {
            int currentTarget = target[i];

            int start = 0;
            int end = A.length - 1;
            boolean inAnswer = false;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (intA[mid] < currentTarget) {
                    start = mid + 1;
                } else if (intA[mid] > currentTarget) {
                    end = mid - 1;
                } else if (intA[mid] == currentTarget) {
                    inAnswer = true;
                    break;
                }
            }
            if (inAnswer) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }

    }
}
