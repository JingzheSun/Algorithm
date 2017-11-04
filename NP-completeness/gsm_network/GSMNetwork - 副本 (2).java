
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingDeque;

public class GSMNetwork {
	private final InputReader reader;
	private final OutputWriter writer;

	public GSMNetwork(InputReader reader, OutputWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public static void main(String[] args) {
		InputReader reader = new InputReader(System.in);
		OutputWriter writer = new OutputWriter(System.out);
		new GSMNetwork(reader, writer).run();
		writer.writer.flush();
	}

	class Edge {
		int from;
		int to;
	}

	class ConvertGSMNetworkProblemToSat {
		int numVertices;
		ArrayList<Integer>[] adj;

		ConvertGSMNetworkProblemToSat(int n, int m) {
			numVertices = n;
			adj = (ArrayList<Integer>[]) new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				adj[i] = new ArrayList<Integer>();
			}
		}

		boolean tripartite(int s, int[] distance) {
			LinkedBlockingDeque<Integer> queue = new LinkedBlockingDeque<Integer>();
			distance[s] = 0;
			queue.add(s);

			while (!queue.isEmpty()) {
				int processing = (int) queue.poll();
				for (int i = 0; i < adj[processing].size(); i++) {
					int next = (int) adj[processing].get(i);
					if (distance[next] == -1) {
						distance[next] = distance[processing] + 1;
						queue.add(next);
					} else if (distance[next] % 3 == distance[processing] % 3) {
						return false;
					}
				}
			}
			return true;
		}

		void printEquisatisfiableSatFormula() {
			// This solution prints a simple satisfiable formula
			// and passes about half of the tests.
			// Change this function to solve the problem.
			// writer.printf("3 2\n");
			// writer.printf("1 2 0\n");
			// writer.printf("-1 -2 0\n");
			// writer.printf("1 -2 0\n");
			writer.printf("1 1\n");
			writer.printf("1 -1 0\n");
		}

		void printEquisatisfiableSatFormulaf() {
			// This solution prints a simple satisfiable formula
			// and passes about half of the tests.
			// Change this function to solve the problem.
			writer.printf("2 1\n");
			writer.printf("1 0\n");
			writer.printf("-1 0\n");
		}
	}

	public void run() {
		int n = reader.nextInt();
		int m = reader.nextInt();

		int[] distance = new int[n];
		for (int i = 0; i < n; i++) {
			distance[i] = -1;
		}
		ConvertGSMNetworkProblemToSat converter = new ConvertGSMNetworkProblemToSat(n, m);
		for (int i = 0; i < m; i++) {
			int x, y;
			x = reader.nextInt();
			y = reader.nextInt();
			converter.adj[x - 1].add(y - 1);
			converter.adj[y - 1].add(x - 1);
		}
		if (converter.tripartite(0, distance))
			converter.printEquisatisfiableSatFormula();
		else
			converter.printEquisatisfiableSatFormulaf();
	}

	static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer;

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
			tokenizer = null;
		}

		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

	static class OutputWriter {
		public PrintWriter writer;

		OutputWriter(OutputStream stream) {
			writer = new PrintWriter(stream);
		}

		public void printf(String format, Object... args) {
			writer.print(String.format(Locale.ENGLISH, format, args));
		}
	}
}
