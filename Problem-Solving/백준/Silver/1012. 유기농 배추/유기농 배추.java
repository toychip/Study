import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(reader.readLine());

        for (int i = 0; i < T; i++) {
            String[] input1 = reader.readLine().split(" ");
            // 세로
            int N = Integer.parseInt(input1[0]);
            // 가로
            int M = Integer.parseInt(input1[1]);

            int[][] board = new int[N][M];
            boolean[][] visited = new boolean[N][M];

            // 배추 개수
            int K = Integer.parseInt(input1[2]);
            for (int j = 0; j < K; j++) {
                String[] input2 = reader.readLine().split(" ");
                int targetM = Integer.parseInt(input2[0]);
                int targetN = Integer.parseInt(input2[1]);

                board[targetM][targetN] = 1;
            }

            int answer = solution(board, visited, N, M);
            writer.write(String.valueOf(answer));
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    static int solution(int[][] board, boolean[][] visited, int n, int m) {
        Queue<Node> queue = new LinkedList<>();
        int answer = 0;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[y][x] == 1 && !visited[y][x]) {
                    bfs(queue, board, visited, y, x, n, m);
                    answer++;
                }
            }
        }
        return answer;
    }

    private static void bfs(Queue<Node> queue, int[][] board, boolean[][] visited, int y, int x, int n, int m) {
        queue.add(new Node(x, y));
        visited[y][x] = true;
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int cx = currentNode.getX();
            int cy = currentNode.getY();
            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx < 0 || ny < 0 || nx >= m || ny >= n) {
                    continue;
                }

                if (visited[ny][nx]) {
                    continue;
                }

                if (board[ny][nx] == 1) {
                    queue.add(new Node(nx, ny));
                    visited[ny][nx] = true;
                }
            }
        }
    }

    //                우   좌  상  하
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static class Node {
        private final int x;
        private final int y;

        public Node(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
