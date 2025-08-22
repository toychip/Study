import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        List<Meeting> meetings = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] input = reader.readLine().split(" ");
            int start = Integer.parseInt(input[0]);
            int end = Integer.parseInt(input[1]);
            meetings.add(new Meeting(start, end));
        }

        Collections.sort(meetings);

        int count = 0;
        int lastEndTime = 0;

        for (Meeting current : meetings) {
            if (lastEndTime <= current.getStart()) {
                count++;
                lastEndTime = current.getEnd();
            }
        }
        
        System.out.print(count);
    }
}

class Meeting implements Comparable<Meeting>{
    private final int start;
    private final int end;

    public Meeting(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public int compareTo(final Meeting meet) {
        if (this.end != meet.end) {
            return Integer.compare(this.end, meet.end);
        }
        return Integer.compare(this.start, meet.start);
    }
}