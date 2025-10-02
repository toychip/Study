import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int[] dx = {-1, 1, 0, 0, 0, 0};
    static int[] dy = {0, 0, -1, 1, 0, 0};
    static int[] dh = {0, 0, 0, 0, -1, 1};
    static int[][][] box;

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input1 = reader.readLine().split("\\s+");
        int x = Integer.parseInt(input1[0]);
        int y = Integer.parseInt(input1[1]);
        int h = Integer.parseInt(input1[2]);
        box = new int[h][y][x];
        Queue<Node> queue = new LinkedList<>();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < y; j++) {
                String[] input2 = reader.readLine().split("\\s+");
                for (int k = 0; k < x; k++) {
                    box[i][j][k] = Integer.parseInt(input2[k]);
                }
            }
        }
        
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < x; k++) {
                    if (box[i][j][k] == 1) {
                        queue.add(new Node(i, j, k));
                    }
                }
            }
        }

        boolean[][][] visited = new boolean[h][y][x];
        int answer = 0;
        while (!queue.isEmpty()) {
            int todaySize = queue.size();
            boolean todayChanged = false;

            for (int i = 0; i < todaySize; i++) {
                Node currentNode = queue.poll();
                boolean result = bfs(currentNode, h, y, x, visited, queue);
                if (result) {
                    todayChanged = true;
                }
            }

            if (todayChanged) {
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
        for (int[][] ints : box) {
            for (int[] anInt : ints) {
                for (int i : anInt) {
                    if(i == 0) return true;
                }
            }
        }
        return false;
    }

    private static boolean bfs(Node currentNode, int h, int y, int x, boolean[][][] visited, Queue<Node> queue) {
        boolean isChanged = false;
        for (int i = 0; i < 6; i++) {
            int nh = currentNode.getH() + dh[i];
            int ny = currentNode.getY() + dy[i];
            int nx = currentNode.getX() + dx[i];

            if (nh >= h || ny >= y || nx >= x || nh < 0 || ny < 0 || nx < 0) {
                continue;
            }

            if (box[nh][ny][nx] != 0) {
                continue;
            }

            if (visited[nh][ny][nx]) {
                continue;
            }

            box[nh][ny][nx] = 1;
            queue.add(new Node(nh, ny, nx));
            visited[nh][ny][nx] = true;
            isChanged = true;
        }
        return isChanged;
    }
}

class Node {
    private final int h;
    private final int y;
    private final int x;

    public Node(int h, int y, int x) {
        this.h = h;
        this.y = y;
        this.x = x;
    }

    public int getH() {
        return h;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}