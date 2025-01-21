import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int[] dx = {1, 2, 2, 1, -1, -2, -2, -1};
    static int[] dy = {2, 1, -1, -2, -2, -1, 1, 2};

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(reader.readLine());
        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(reader.readLine());
            String[] input1 = reader.readLine().split(" ");
            int startY = Integer.parseInt(input1[0]);
            int startX = Integer.parseInt(input1[1]);
            Node start = new Node(startY, startX);

            String[] input2 = reader.readLine().split(" ");
            int endY = Integer.parseInt(input2[0]);
            int endX = Integer.parseInt(input2[1]);
            Node end = new Node(endY, endX);
            int answer = solution(N, start, end);
            writer.write(String.valueOf(answer));
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    private static int solution(int n, Node start, Node end) {
        if (start.getX() == end.getX() && start.getY() == end.getY()) {
            return 0;
        }

        int[][] dist = new int[n][n];
        for (int[] ints : dist) {
            Arrays.fill(ints, -1);
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        dist[start.getY()][start.getX()] = 0;
        return bfs(n, end, queue, dist);
    }

    private static int bfs(int n, Node end, Queue<Node> queue, int[][] dist) {
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int cx = currentNode.getX();
            int cy = currentNode.getY();
            if (cy == end.getY() && cx == end.getX()) {
                return dist[cy][cx];
            }
            for (int i = 0; i < 8; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (ny >= n || nx >= n || ny < 0 || nx < 0) {
                    continue;
                }

                if (dist[ny][nx] != -1) {
                    continue;
                }

                dist[ny][nx] = dist[cy][cx] + 1;
                queue.add(new Node(ny, nx));
            }
        }
        return -1;
    }

    static class Node {
        private final int y;
        private final int x;

        public Node(final int y, final int x) {
            this.y = y;
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
