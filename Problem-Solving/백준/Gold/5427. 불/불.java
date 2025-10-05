import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int best;

    static Queue<Node> queue;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int games = Integer.parseInt(reader.readLine());

        for (int i = 0; i < games; i++) {
            best = Integer.MAX_VALUE;
            String[] input1 = reader.readLine().split(" ");
            int x = Integer.parseInt(input1[0]);
            int y = Integer.parseInt(input1[1]);

            String[][] boards = new String[y][x];
            int[][] fireDist = new int[y][x];
            for (int[] ints : fireDist) {
                Arrays.fill(ints, -1);
            }

            int[][] sangeunDist = new int[y][x];
            for (int[] ints : sangeunDist) {
                Arrays.fill(ints, -1);
            }

            for (int j = 0; j < y; j++) {
                String[] input2 = reader.readLine().split("");
                for (int k = 0; k < x; k++) {
                    boards[j][k] = input2[k];
                }
            }

            queue = new LinkedList<>();
            distInit(y, x, boards, "*", fireDist);
            distBfs(y, x, fireDist, boards);

            queue = new LinkedList<>();
            distInit(y, x, boards, "@", sangeunDist);
            distBfsSangeun(y, x, sangeunDist, fireDist, boards);
            if (best == Integer.MAX_VALUE) {
                writer.write("IMPOSSIBLE");
            } else {
                writer.write(String.valueOf(best));
            }

            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    private static void distBfsSangeun(int y, int x, int[][] sangeunDist, int[][] fireDist, String[][] boards) {
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            for (int k = 0; k < 4; k++) {
                int cx = currentNode.getX();
                int nx = dx[k] + cx;
                int cy = currentNode.getY();
                int ny = dy[k] + cy;
                int currentTime = sangeunDist[cy][cx];

                if (cy == 0 || cx == 0 || cy == y - 1 || cx == x - 1) {
                    best = Math.min(currentTime + 1, best);
                }

                if (ny < 0 || nx < 0 || ny >= y || nx >= x) {
                    continue;
                }

                if (sangeunDist[ny][nx] != -1) {
                    continue;
                }

                if (boards[ny][nx].equals("#")) {
                    continue;
                }

                if (fireDist[ny][nx] != -1 && fireDist[ny][nx] <= currentTime + 1) {
                    continue;
                }

                sangeunDist[ny][nx] = currentTime + 1;
                queue.add(new Node(ny, nx));
            }
        }
    }

    private static void distInit(int y, int x, String[][] boards, String anObject, int[][] dist) {
        for (int j = 0; j < y; j++) {
            for (int k = 0; k < x; k++) {
                if (boards[j][k].equals(anObject)) {
                    queue.add(new Node(j, k));
                    dist[j][k] = 0;
                }
            }
        }
    }

    private static void distBfs(int y, int x, int[][] dist, String[][] boards) {
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            for (int k = 0; k < 4; k++) {
                int nx = dx[k] + currentNode.getX();
                int ny = dy[k] + currentNode.getY();

                if (ny < 0 || nx < 0 || ny >= y || nx >= x) {
                    continue;
                }

                if (dist[ny][nx] != -1) {
                    continue;
                }

                if (boards[ny][nx].equals("#")) {
                    continue;
                }

                dist[ny][nx] = dist[currentNode.getY()][currentNode.getX()] + 1;
                queue.add(new Node(ny, nx));
            }
        }
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