import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        String[] input1 = reader.readLine().split(" ");
        String[] input2 = reader.readLine().split(" ");

        for (int i = 0; i < n; i++) {
            a.add(Integer.parseInt(input1[i]));
            b.add(Integer.parseInt(input2[i]));
        }

        Collections.sort(a);
        Collections.sort(b, Collections.reverseOrder());

        int answer = 0;
        for (int i = 0; i < n; i++) {
            int times = a.get(i) * b.get(i);
            answer += times;
        }

        System.out.print(answer);
    }
}
