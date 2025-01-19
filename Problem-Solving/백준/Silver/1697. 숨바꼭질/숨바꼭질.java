import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int start;
    static int end;
    static boolean[] visited = new boolean[100001];
    static int[] dist = new int[100001];

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input = reader.readLine().split(" ");
        start = Integer.parseInt(input[0]);
        end = Integer.parseInt(input[1]);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            Integer current = queue.poll();

            if (current == end) {
                System.out.print(dist[current]);
                return;
            }
            if (current + 1 <= 100000 && !visited[current + 1]) {
                visited[current + 1] = true;
                dist[current + 1] = dist[current] + 1;
                queue.add(current + 1);
            }

            if (current - 1 >= 0 && !visited[current - 1]) {
                visited[current - 1] = true;
                dist[current - 1] = dist[current] + 1;
                queue.add(current - 1);
            }

            if (current * 2 <= 100000 && !visited[current * 2]) {
                visited[current * 2] = true;
                dist[current * 2] = dist[current] + 1;
                queue.add(current * 2);
            }

        }
        System.out.print(-1);
    }

}
