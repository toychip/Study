import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static int K;
    static List<Bag> bags;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        String[] input1 = reader.readLine().split(" ");
        N = Integer.parseInt(input1[0]);
        K = Integer.parseInt(input1[1]);
        bags = new ArrayList<>();

        dp = new int[N][100_001];
        for (int i = 0; i < N; i++) {
            // 모든 값을 -1로 초기화 (아직 계산되지 않음)
            Arrays.fill(dp[i], -1);
        }

        for (int i = 0; i < N; i++) {
            String[] input2 = reader.readLine().split(" ");
            int weight = Integer.parseInt(input2[0]);
            bags.add(new Bag(weight, Integer.parseInt(input2[1])));
        }

        writer.write(String.valueOf(recur(0, 0)));
        writer.flush();
        writer.close();
    }

    private static int recur(final int index, int weight) {
        // 무게가 들 수 있는 무게를 초과하면 들 수 없음
        if (weight > K) {
            return Integer.MIN_VALUE;
        }

        // 모든 물건을 다 확인하면 더이상 물건을 담을 수 없음
        if (index == N) {
            return 0;
        }

        if (dp[index][weight] != -1) {
            return dp[index][weight];
        }

        Bag bag = bags.get(index);

        dp[index][weight] = Math.max(
                recur(index + 1, weight + bag.getWeight()) + bag.getValue(),
                recur(index + 1, weight)
        );

        return dp[index][weight];
    }
}

class Bag {
    private final int weight;
    private final int value;

    public Bag(final int weight, final int value) {
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }
}
