import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    static int[][] board;
    static int x;
    static int y;
    static int[] dx = {+1, -1, 0, 0};
    static int[] dy = {0, 0, -1, +1};
    static Queue<Node> queue;
    static int answer = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input1 = reader.readLine().split(" ");
        x = Integer.parseInt(input1[0]);
        y = Integer.parseInt(input1[1]);
        board = new int[y][x];
        queue = new LinkedList<>();
        for (int i = 0; i < y; i++) {
            String[] input2 = reader.readLine().split(" ");
            for (int j = 0; j < x; j++) {
                board[i][j] = Integer.parseInt(input2[j]);
            }
        }
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (board[i][j] == 1) {
                    queue.add(new Node(i, j));
                }
            }
        }

        boolean[][] visited = new boolean[y][x];
        while (!queue.isEmpty()) {
            int todaySize = queue.size();
            boolean todayChange = false;

            for (int i = 0; i < todaySize; i++) {
                Node currentNode = queue.poll();
                boolean isChanged = bfs(currentNode, visited);
                if (isChanged) {
                    todayChange = true;
                }

            }
            if (todayChange) {
                answer++;
            }
        }
        boolean result = hasZero();
        if (result) {
            System.out.print("-1");
        } else {
            System.out.print(answer);
        }
    }

    private static boolean hasZero() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean bfs(Node node, boolean[][] visited) {
        boolean isChanged = false;
        for (int i = 0; i < 4; i++) {
            int ny = node.getY() + dy[i];
            int nx = node.getX() + dx[i];

            boolean inTheBoard = ny >= 0 && nx >= 0 && y > ny && x > nx;
            if (inTheBoard) {
                if (board[ny][nx] == -1) continue;

                boolean notRipeTomatoes = board[ny][nx] == 0;
                if (notRipeTomatoes && !visited[ny][nx]) {
                    visited[ny][nx] = true;
                    board[ny][nx] = 1;
                    queue.add(new Node(ny, nx));
                    isChanged = true;
                }
            }
        }
        return isChanged;
    }
}

class Node {
    private final int y;
    private final int x;

    public Node(int y, int x) {
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