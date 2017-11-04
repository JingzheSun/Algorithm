import java.io.*;
import java.util.*;

public class BudgetAllocation {
	private final InputReader reader;
	private final OutputWriter writer;

	public BudgetAllocation(InputReader reader, OutputWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public static void main(String[] args) {
		InputReader reader = new InputReader(System.in);
		OutputWriter writer = new OutputWriter(System.out);
		new BudgetAllocation(reader, writer).run();
		writer.writer.flush();
	}

	public void run() {
		int n = reader.nextInt();
		int m = reader.nextInt();

		Integer[] b = new Integer[n];
		Integer[][] A = new Integer[n][m];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				A[i][j] = reader.nextInt();
			}
		}
		for (int i = 0; i < n; ++i) {
			b[i] = reader.nextInt();
		}

		ConvertILPToSat converter = new ConvertILPToSat(n, m, A, b);

		if (converter.clauses.size() == 0) {
			writer.printf("1 1\n");
			writer.printf("1 -1 0\n");
		} else {
			writer.printf("%d %d\n", converter.clauses.size(), m);
			for (int i = 0; i < converter.clauses.size(); i++) {
				ArrayList<Integer> clause = converter.clauses.get(i);
				for (int j = 0; j < clause.size(); j++) {
					writer.printf("%d ", clause.get(j));
				}
				writer.printf("%d\n", 0);

			}
		}
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

class ConvertILPToSat {

	ArrayList<ArrayList<Integer>> clauses;
	private int variablesCount;
	private int clausesCount;
	Integer[] variables;
	Integer[] b;
	Integer[][] A;

	ConvertILPToSat(int n, int m, Integer[][] A, Integer[] b) {
		variablesCount = m;
		clausesCount = n;
		variables = new Integer[variablesCount];
		this.b = b;
		this.A = A;
		initVariables();
		clauses = new ArrayList<>();
		computeSat();
	}

	private void initVariables() {
		int counter = 1;
		for (int vCount = 0; vCount < variablesCount; vCount++)
			variables[vCount] = counter++;
	}

	private void addEachRow(int rowIndex) {
		ArrayList<Integer> var = new ArrayList<Integer>();
		for (int i = 0; i < variablesCount; i++)
			if (A[rowIndex][variables[i] - 1] != 0)
				var.add(variables[i]);

		switch (var.size()) {
		case 1:
			for (int i = 0; i < 2; i++)
				if (i * A[rowIndex][var.get(0) - 1] > b[rowIndex]) {
					ArrayList<Integer> clause = new ArrayList<Integer>();
					clause.add(var.get(0) * (int) Math.pow(-1, i));
					clauses.add(clause);
				}
			break;
		case 2:
			for (int i = 0; i < 2; i++)
				for (int j = 0; j < 2; j++)
					if (i * A[rowIndex][var.get(0) - 1] + j * A[rowIndex][var.get(1) - 1] > b[rowIndex]) {
						ArrayList<Integer> clause = new ArrayList<Integer>();
						clause.add(var.get(0) * (int) Math.pow(-1, i));
						clause.add(var.get(1) * (int) Math.pow(-1, j));
						clauses.add(clause);
					}
			break;
		case 3:
			for (int i = 0; i < 2; i++)
				for (int j = 0; j < 2; j++)
					for (int k = 0; k < 2; k++) {
						int x = i * A[rowIndex][var.get(0) - 1];
						int y = j * A[rowIndex][var.get(1) - 1];
						int z = k * A[rowIndex][var.get(2) - 1];
						if (x + y + z > b[rowIndex]) {
							ArrayList<Integer> clause = new ArrayList<Integer>();
							clause.add(var.get(0) * (int) Math.pow(-1, i));
							clause.add(var.get(1) * (int) Math.pow(-1, j));
							clause.add(var.get(2) * (int) Math.pow(-1, k));
							clauses.add(clause);
						}
					}
			break;
		default:
			return;
		}

	}

	private void addInequalityConstraint() {
		for (int i = 0; i < clausesCount; i++) {
			addEachRow(i);
		}
	}

	private void computeSat() {
		addInequalityConstraint();
	}
}