import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int[][] board;
    static int[][][] dist;
    static final int NOT_VISITED = -1;
    static final int NOT_BROKEN = 0;

    static Queue<Node> queue;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        queue = new LinkedList<>();
        queue.add(new Node(0, 0, false));

        String[] input1 = reader.readLine().split(" ");
        int y = Integer.parseInt(input1[0]);
        int x = Integer.parseInt(input1[1]);

        board = new int[y][x];

        for (int i = 0; i < y; i++) {
            String[] input2 = reader.readLine().split("");
            for (int j = 0; j < x; j++) {
                board[i][j] = Integer.parseInt(input2[j]);
            }
        }

        dist = new int[y][x][2];
        for (int[][] ints : dist) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, -1);
            }
        }

        dist[0][0][NOT_BROKEN] = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int cy = currentNode.getY();
            int cx = currentNode.getX();
            int broken = currentNode.isBroken();

            for (int i = 0; i < 4; i++) {
                int ny = dy[i] + cy;
                int nx = dx[i] + cx;

                if (ny < 0 || nx < 0 || ny >= y || nx >= x) {
                    continue;
                }

                // 길이 맞다면
                if (board[ny][nx] == 0) {
                    if (dist[ny][nx][broken] != NOT_VISITED) {
                        continue;
                    }
                    dist[ny][nx][broken] = dist[cy][cx][broken] + 1;
                    queue.add(new Node(ny, nx, broken));
                } else {
                    if (broken == 1) {
                        continue;
                    }

                    if (dist[ny][nx][1] != NOT_VISITED) {
                        continue;
                    }

                    dist[ny][nx][1] = dist[cy][cx][broken] + 1;
                    queue.add(new Node(ny, nx, true));
                }
            }
        }

        int answer = getAnswer(y, x);
        writer.write(String.valueOf(answer));

        writer.flush();
        writer.close();
    }

    private static int getAnswer(int y, int x) {
        int a0 = dist[y-1][x-1][0];
        int a1 = dist[y-1][x-1][1];
        if (a0 == NOT_VISITED && a1 == NOT_VISITED) {
            return -1;
        }
        if (a0 == NOT_VISITED) {
            return a1 + 1;
        }
        if (a1 == NOT_VISITED) {
            return a0 + 1;
        }

        return Math.min(a0, a1) + 1;
    }
}

class Node {
    private final int y;
    private final int x;
    private final boolean broken;

    public Node(int y, int x, boolean broken) {
        this.y = y;
        this.x = x;
        this.broken = broken;
    }

    public Node(int y, int x, int broken) {
        this.y = y;
        this.x = x;
        if (broken == 1) {
            this.broken = true;
        } else {
            this.broken = false;
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int isBroken() {
        if (broken) {
            return 1;
        }

        return 0;
    }
}