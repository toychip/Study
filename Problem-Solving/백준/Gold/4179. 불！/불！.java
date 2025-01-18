import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    static String[][] board;
    static int[][] fireDist;
    static int[][] jihoonDist;
                      //좌 우  하  상
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input1 = reader.readLine().split(" ");
        int R = Integer.parseInt(input1[0]);
        int C = Integer.parseInt(input1[1]);

        board = new String[R][C];
        fireDist = new int[R][C];
        jihoonDist = new int[R][C];
        for (int[] ints : fireDist) {
            Arrays.fill(ints, -1);
        }

        for (int[] ints : jihoonDist) {
            Arrays.fill(ints, -1);
        }
        Queue<Node> fireQueue = new LinkedList<>();
        Queue<Node> jihoonQueue = new LinkedList<>();

        for (int y = 0; y < R; y++) {
            String[] input2 = reader.readLine().split("");
            for (int x = 0; x < C; x++) {
                if (input2[x].equals("F")) {
                    fireQueue.add(new Node(y, x));
                    fireDist[y][x] = 0;
                } else if (input2[x].equals("J")) {
                    jihoonQueue.add(new Node(y, x));
                    jihoonDist[y][x] = 0;
                }

                board[y][x] = input2[x];
            }
        }

        initFire(fireQueue, R, C);
        if (solution(jihoonQueue, R, C)) {
            return;
        }

        System.out.print("IMPOSSIBLE");
    }

    private static boolean solution(final Queue<Node> jihoonQueue, final int R, final int C) {
        while (!jihoonQueue.isEmpty()) {
            Node currentNode = jihoonQueue.poll();
            int cx = currentNode.getX();
            int cy = currentNode.getY();
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + cx;
                int ny = dy[i] + cy;

                if (ny >= R || ny < 0 || nx >= C || nx < 0) {
                    System.out.print(jihoonDist[cy][cx] + 1);
                    return true;
                }

                if (board[ny][nx].equals("#")) {
                    continue;
                }

                if (jihoonDist[ny][nx] != -1) {
                    continue;
                }

                if (fireDist[ny][nx] != -1 && fireDist[ny][nx] <= jihoonDist[cy][cx] + 1) {
                    continue;
                }

                jihoonDist[ny][nx] = jihoonDist[cy][cx] + 1;
                jihoonQueue.add(new Node(ny, nx));
            }
        }
        return false;
    }

    private static void initFire(Queue<Node> fireQueue, int R, int C) {
        while (!fireQueue.isEmpty()) {
            Node currentNode = fireQueue.poll();
            int cx = currentNode.getX();
            int cy = currentNode.getY();
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + cx;
                int ny = dy[i] + cy;

                if (ny >= R || ny < 0 || nx >= C || nx < 0) {
                    continue;
                }

                if (board[ny][nx].equals("#")) {
                    continue;
                }

                if (fireDist[ny][nx] != -1) {
                    continue;
                }

                fireDist[ny][nx] = fireDist[cy][cx] + 1;
                fireQueue.add(new Node(ny, nx));
            }
        }
    }

    static class Node {
        private final int y;
        private final int x;

        public Node(final int y, final int x) {
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
}
