import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    //                우   좌  상  하
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(reader.readLine());
        String[][] generalBoard = new String[N][N];
        boolean[][] generalVisited = new boolean[N][N];

        String[][] patientBoard = new String[N][N];
        boolean[][] patientVisited = new boolean[N][N];

        for (int y = 0; y < N; y++) {
            String[] input = reader.readLine().split("");
            for (int x = 0; x < N; x++) {
                generalBoard[y][x] = input[x];
                if (input[x].equals("G") || input[x].equals("R")) {
                    patientBoard[y][x] = "R";
                } else {
                    patientBoard[y][x] = input[x];
                }
            }
        }

        solution(N, generalBoard, generalVisited, patientBoard, patientVisited);
    }

    private static void solution(
            int n,
            String[][] generalBoard, boolean[][] generalVisited,
            String[][] patientBoard, boolean[][] patientVisited
    ) {
        Queue<Node> generalQueue = new LinkedList<>();
        int generalAnswer = 0;
        for (int y = 0; y < generalBoard.length; y++) {
            for (int x = 0; x < generalBoard[0].length; x++) {
                if (!generalVisited[y][x]) {
                    generalVisited[y][x] = true;
                    generalQueue.add(new Node(y, x));
                    generalBfs(generalQueue, generalBoard, generalVisited, y, x);
                    generalAnswer++;
                }
            }
        }

        Queue<Node> patientQueue = new LinkedList<>();
        int patientAnswer = 0;
        for (int y = 0; y < patientBoard.length; y++) {
            for (int x = 0; x < patientBoard[0].length; x++) {
                if (!patientVisited[y][x]) {
                    patientVisited[y][x] = true;
                    patientQueue.add(new Node(y, x));
                    patientBfs(patientQueue, patientBoard, patientVisited, y, x);
                    patientAnswer++;
                }
            }
        }

        System.out.print(generalAnswer + " " + patientAnswer);

    }

    private static void patientBfs(
            Queue<Node> queue, String[][] patientBoard,
            boolean[][] patientVisited, int y, int x
    ) {
        String currentColor = patientBoard[y][x];
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = currentNode.getX() + dx[i];
                int ny = currentNode.getY() + dy[i];

                if (ny < 0 || nx < 0 || nx >= patientBoard[0].length || ny >= patientBoard.length) {
                    continue;
                }

                if (patientVisited[ny][nx]) {
                    continue;
                }

                if (!currentColor.equals(patientBoard[ny][nx])) {
                    continue;
                }

                patientVisited[ny][nx] = true;
                queue.add(new Node(ny, nx));
            }
        }
    }

    private static void generalBfs(
            Queue<Node> queue, String[][] generalBoard,
            boolean[][] generalVisited, int y, int x
    ) {
        String currentColor = generalBoard[y][x];
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = currentNode.getX() + dx[i];
                int ny = currentNode.getY() + dy[i];

                if (ny < 0 || nx < 0 || nx >= generalBoard[0].length || ny >= generalBoard.length) {
                    continue;
                }

                if (generalVisited[ny][nx]) {
                    continue;
                }

                if (!currentColor.equals(generalBoard[ny][nx])) {
                    continue;
                }

                generalVisited[ny][nx] = true;
                queue.add(new Node(ny, nx));
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

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
