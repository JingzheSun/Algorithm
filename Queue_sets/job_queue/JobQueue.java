import java.io.*;
import java.util.*;

public class JobQueue {

    private int numWorkers;
    private int[] jobs;
    private int[] assignedWorker;
    private long[] startTime;
    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        ArrayList<jobProcessing> queue = new ArrayList<jobProcessing>();
        ArrayList<Integer> idleCores = new ArrayList<Integer>();
        for (int j = 0; j < numWorkers; j++) {
            idleCores.add(j);
        }
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long lastfinishT = 0;
        int i = 0;
        while (i < jobs.length) {
            if (queue.size() < numWorkers) {
                while (queue.size() < numWorkers && i < jobs.length) {
                    long duration = (long) jobs[i];
                    int core = idleCores.remove(0);
                    int k = findPosition(queue, 0, queue.size(), lastfinishT + duration);
                    queue.add(k, new jobProcessing(core, lastfinishT + duration));
                    assignedWorker[i] = core;
                    startTime[i] = lastfinishT;
                    i++;
                }
            } else {
                jobProcessing job = queue.remove(0);
                idleCores.add(job.getCore());
                lastfinishT = job.getFinishTime();
                while (!queue.isEmpty() && lastfinishT == queue.get(0).getFinishTime()) {
                    idleCores.add(queue.remove(0).getCore());
                }
                Collections.sort(idleCores);
            }
        }
    }

    private int findPosition(ArrayList<jobProcessing> queue, int l, int r, long t) {
        int k = (l + r) / 2;
        while (r >= l) {
            k = (l + r) / 2;
            if (r == l) {
                return k;
            }
            if (t > queue.get(k).finishTime) {
                l = k + 1;
            } else {
                r = k;
            }
        }
        return k;
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    private static class jobProcessing implements Comparable<jobProcessing> {

        int core;
        long finishTime;

        jobProcessing(int core, long finishTime) {
            this.core = core;
            this.finishTime = finishTime;
        }

        public int getCore() {
            return core;
        }

        public long getFinishTime() {
            return finishTime;
        }

        @Override
        public int compareTo(jobProcessing o) {
            return (int) (this.getFinishTime() - o.getFinishTime());
        }
    }

    static class FastScanner {

        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
