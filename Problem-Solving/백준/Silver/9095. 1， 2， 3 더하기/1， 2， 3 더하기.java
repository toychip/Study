import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(reader.readLine());
        for (int i = 0; i < N; i++) {
            int n = Integer.parseInt(reader.readLine());
            int[] dp = new int[n + 1];
            Arrays.fill(dp, -1);
            int answer = recur(0, n, dp);
            writer.write(String.valueOf(answer));
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    private static int recur(final int level, final int goal, final int[] dp) {
        if (level == goal) {
            return 1;
        }

        if (level > goal) {
            return 0;
        }

        if (dp[level] != -1) {
            return dp[level];
        }

        dp[level] = recur(level + 1, goal, dp) +
                recur(level + 2, goal, dp) +
                recur(level + 3, goal, dp);

        return dp[level];
    }
}