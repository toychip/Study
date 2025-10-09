import java.io.*;
import java.util.Arrays;

public class Main {

    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(reader.readLine());

        for (int i = 0; i < n; i++) {
            dp = new int[20];
            Arrays.fill(dp, -1);
            int newOne = Integer.parseInt(reader.readLine());
            int answer = solve(0, newOne);
            writer.write(String.valueOf(answer));
            writer.write("\n");
        }

        writer.flush();
        writer.close();
    }

    private static int solve(int level, int goal) {
        if (level == goal) {
            return 1;
        }

        if (level > goal) {
            return 0;
        }

        if (dp[level] != -1) {
            return dp[level];
        }

        dp[level] = solve(level + 3, goal) + solve(level + 2, goal) + solve(level + 1, goal);
        return dp[level];
    }
}