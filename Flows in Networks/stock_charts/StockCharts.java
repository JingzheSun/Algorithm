import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingQueue;

public class StockCharts {
	private FastScanner in;
	private PrintWriter out;

	public static void main(String[] args) throws IOException {
		new StockCharts().solve();
	}

	public void solve() throws IOException {
		in = new FastScanner();
		out = new PrintWriter(new BufferedOutputStream(System.out));
		boolean[][] bipartiteGraph = readData();
		int result = minCharts(bipartiteGraph);
		writeResponse(result);
		out.close();
	}

	boolean[][] readData() throws IOException {
		int numStocks = in.nextInt();
		int numPoints = in.nextInt();
		int[][] stockData = new int[numStocks][numPoints];
		for (int i = 0; i < numStocks; ++i)
			for (int j = 0; j < numPoints; ++j)
				stockData[i][j] = in.nextInt();

		boolean[][] bipartiteGraph = new boolean[numStocks][numStocks];
		for (int i = 0; i < numStocks; ++i)
			for (int j = 0; j < numStocks; ++j)
				if (i == j) {
					bipartiteGraph[i][j] = false;
				} else {
					bipartiteGraph[i][j] = compare(stockData[i], stockData[j]);// sj>>si
				}

		return bipartiteGraph;
	}

	private int minCharts(boolean[][] bipartiteGraph) {
		int numLeft = bipartiteGraph.length;
		int numRight = bipartiteGraph[0].length;
		int from = 0, to = numLeft + numRight + 1;
		FlowGraph graph = new FlowGraph(to + 1);
		for (int i = 0; i < numLeft; i++)
			for (int j = 0; j < numRight; j++)
				if (bipartiteGraph[j][i])
					graph.addEdge(i + 1, numLeft + j + 1, 1);

		for (int i = 0; i < numLeft; i++)
			graph.addEdge(from, i + 1, 1);

		for (int j = 0; j < numRight; j++)
			graph.addEdge(numLeft + j + 1, to, 1);

		int flow = 0;
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
				return numLeft - flow;

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
			flow += minFlow;
		}
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

	boolean compare(int[] stock1, int[] stock2) {
		for (int i = 0; i < stock1.length; ++i)
			if (stock1[i] >= stock2[i])
				return false;
		return true;
	}

	private void writeResponse(int result) {
		out.println(result);
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
