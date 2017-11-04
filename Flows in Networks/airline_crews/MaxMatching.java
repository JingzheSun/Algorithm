import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class MaxMatching {
	private FastScanner in;
	private PrintWriter out;

	public static void main(String[] args) throws IOException {
		new MaxMatching().solve();
	}

	public void solve() throws IOException {
		in = new FastScanner();
		out = new PrintWriter(new BufferedOutputStream(System.out));
		boolean[][] bipartiteGraph = readData();
		int[] matching = findMatching(bipartiteGraph);
		writeResponse(matching);
		out.close();
	}

	boolean[][] readData() throws IOException {
		int numLeft = in.nextInt();
		int numRight = in.nextInt();
		boolean[][] adjMatrix = new boolean[numLeft][numRight];
		for (int i = 0; i < numLeft; ++i)
			for (int j = 0; j < numRight; ++j)
				adjMatrix[i][j] = (in.nextInt() == 1);
		return adjMatrix;
	}

	private int[] findMatching(boolean[][] bipartiteGraph) {

		int numLeft = bipartiteGraph.length;
		int numRight = bipartiteGraph[0].length;
		int from = 0, to = numLeft + numRight + 1;
		FlowGraph graph = new FlowGraph(to + 1);
		for (int i = 0; i < numLeft; i++) {
			for (int j = 0; j < numRight; j++) {
				if (bipartiteGraph[i][j]) {
					graph.addEdge(i + 1, numLeft + j + 1, 1);
				}
			}
		}
		for (int i = 0; i < numLeft; i++) {
			graph.addEdge(from, i + 1, 1);
		}
		for (int j = 0; j < numRight; j++) {
			graph.addEdge(numLeft + j + 1, to, 1);
		}

		int[] parent = new int[graph.graph.length];
		while (true) {
			Arrays.fill(parent, -1);
			LinkedBlockingQueue<List<Integer>> queue = new LinkedBlockingQueue<List<Integer>>();
			queue.add(graph.graph[from]);
			while (!queue.isEmpty()) {
				List<Integer> node = queue.poll();
				for (int index : node) {
					Edge edge = graph.edges.get(index);
					if (parent[edge.to] == -1 && edge.to != 0 && edge.capacity > edge.flow) {
						parent[edge.to] = index;
						queue.add(graph.graph[edge.to]);
					}
				}
			}
			if (parent[to] == -1)
				break;

			int minFlow = Integer.MAX_VALUE;
			for (int i = parent[to];; i = parent[graph.edges.get(i).from]) {
				Edge e = graph.edges.get(i);
				int remain = e.capacity - e.flow;
				minFlow = minFlow < remain ? minFlow : remain;
				if (graph.edges.get(i).from == 0)
					break;
			}
			for (int i = parent[to];; i = parent[graph.edges.get(i).from]) {
				graph.addFlow(i, minFlow);
				if (graph.edges.get(i).from == 0)
					break;
			}
		}

		int[] matching = new int[numLeft];
		Arrays.fill(matching, -1);
		int cnt = -2;
		for (int i = 0; i < numLeft; i++) {
			for (int j = 0; j < numRight; j++) {
				if (bipartiteGraph[i][j]) {
					cnt += 2;
					if (graph.edges.get(cnt).flow == 1) {
						matching[i] = j + 1;

					}
				}
			}
		}

		return matching;
	}

	static class Edge {
		int from, to, capacity, flow;

		public Edge(int from, int to, int capacity, int flow) {
			this.from = from;
			this.to = to;
			this.capacity = capacity;
			this.flow = flow;
		}
	}

	static class FlowGraph {
		private List<Edge> edges;
		private List<Integer>[] graph;

		public FlowGraph(int n) {
			this.graph = (ArrayList<Integer>[]) new ArrayList[n];
			for (int i = 0; i < n; ++i)
				this.graph[i] = new ArrayList<>();
			this.edges = new ArrayList<>();
		}

		public void addEdge(int from, int to, int capacity) {

			Edge forwardEdge = new Edge(from, to, capacity, 0);
			Edge backwardEdge = new Edge(to, from, capacity, capacity);
			graph[from].add(edges.size());
			edges.add(forwardEdge);
			graph[to].add(edges.size());
			edges.add(backwardEdge);
		}

		public void addFlow(int id, int flow) {
			edges.get(id).flow += flow;
			edges.get(id ^ 1).flow -= flow;
		}
	}

	private void writeResponse(int[] matching) {
		for (int i = 0; i < matching.length; ++i) {
			if (i > 0) {
				out.print(" ");
			}
			out.print(matching[i]);
		}
		out.println();
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
