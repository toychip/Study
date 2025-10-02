import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    static int y;
    static int x;

    static int[][] board;
    static boolean[][] visited;
    static int[][] dist;

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input1 = reader.readLine().split(" ");
        y = Integer.parseInt(input1[0]);
        x = Integer.parseInt(input1[1]);
        board = new int[y][x];
        visited = new boolean[y][x];
        dist = new int[y][x];

        for (int i = 0; i < y; i++) {
            String[] input2 = reader.readLine().split("");
            for (int j = 0; j < x; j++) {
                board[i][j] = Integer.parseInt(input2[j]);
            }
        }

        int answer = bfs();
        System.out.print(answer);
    }

    private static int bfs() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(0, 0));
        visited[0][0] = true;
        dist[0][0] = 1;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int cx = currentNode.getX();
            int cy = currentNode.getY();

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx >= 0 && ny >= 0 && x > nx && y > ny && !visited[ny][nx] && board[ny][nx] == 1) {
                    queue.add(new Node(ny, nx));
                    visited[ny][nx] = true;
                    dist[ny][nx] = dist[cy][cx] + 1;
                    if ((ny == y - 1) && (nx == x - 1)) {
                        return dist[ny][nx];
                    }
                }
            }
        }
        throw new RuntimeException();
    }
}

class Node {
    private final int y;
    private final int x;

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}