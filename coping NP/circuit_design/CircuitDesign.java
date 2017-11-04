import java.util.*;
import java.io.*;

public class CircuitDesign {
	private final InputReader reader;

	public CircuitDesign(InputReader reader) {
		this.reader = reader;
	}

	public static void main(String[] args) {
		InputReader reader = new InputReader(System.in);
		new CircuitDesign(reader).run();
	}

	public void run() {
		int n = reader.nextInt();
		int m = reader.nextInt();
		Clause[] clauses = new Clause[m];
		for (int i = 0; i < m; ++i) {
			clauses[i] = new Clause();
			clauses[i].firstVar = reader.nextInt();
			clauses[i].secondVar = reader.nextInt();
		}

		TwoSat twoSat = new TwoSat(n, clauses);
		twoSat.printAsssignments();
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

}

class Clause {
	int firstVar;
	int secondVar;
}

class TwoSat {
	private int numVars;
	DirectedGraph graph;
	DirectedGraph reverseGraph;
	KosaRajuSCC scc;

	TwoSat(int n, Clause[] clauses) {
		numVars = n;
		buildImplicationGraph(clauses);
		scc = new KosaRajuSCC(graph, reverseGraph);
	}

	public void printAsssignments() {
		if (scc.isSat()) {
			System.out.println("SATISFIABLE");
			for (int i = 0; i < graph.V(); i += 2) {
				int val = scc.vertexAssignment(i);
				if (val == 1) {
					System.out.print(i / 2 + 1 + " ");
				} else {
					System.out.print(-1 * (i / 2 + 1) + " ");
				}
			}
		} else {
			System.out.println("UNSATISFIABLE");
		}
	}

	private void buildImplicationGraph(Clause[] clauses) {
		graph = new DirectedGraph(numVars * 2);
		reverseGraph = new DirectedGraph(numVars * 2);
		for (Clause clause : clauses)
			createEdges(clause);
	}

	private void createEdges(Clause clause) {
		int firstVar = clause.firstVar;
		int secondVar = clause.secondVar;
		int fVertexNegfVar = getVertex(-1 * firstVar);
		int sVertexsVar = getVertex(secondVar);
		int sVertexNegsVar = getVertex(-1 * secondVar);
		int fVertexfVar = getVertex(firstVar);
		graph.addEdge(fVertexNegfVar, sVertexsVar);
		graph.addEdge(sVertexNegsVar, fVertexfVar);
		reverseGraph.addEdge(sVertexsVar, fVertexNegfVar);
		reverseGraph.addEdge(fVertexfVar, sVertexNegsVar);
	}

	private int getVertex(int variable) {
		return Math.abs(variable) * 2 - (variable < 0 ? 1 : 2);
	}
}

class DirectedGraph {
	private Bag<Integer>[] adj;

	DirectedGraph(int vertexCount) {
		adj = (Bag<Integer>[]) new Bag[vertexCount];
		for (int v = 0; v < vertexCount; v++) {
			adj[v] = new Bag<Integer>();
		}
	}

	void addEdge(int start, int end) {
		adj[start].add(end);
	}

	int V() {
		return adj.length;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}
}

class KosaRajuSCC {

	private DirectedGraph graph;
	private DirectedGraph reverseGraph;
	private boolean[] visited;
	private int[] fstimes; // DFS on reverse graph for postorder(topo)
	private int fsTime = 0; // global finishing time required for first run
	private int scc = 0; // required for 2nd run of DFS on orig graph
	private int[] vertexcc; // labels of cluster of scc
	private int[] vertexAssignments; // assignments of vertex
	private boolean sat = true;

	KosaRajuSCC(DirectedGraph graph, DirectedGraph reverseGraph) {
		this.graph = graph;
		this.reverseGraph = reverseGraph;
		run();
	}

	public boolean isSat() {
		return sat;
	}

	private void run() {
		visited = new boolean[graph.V()];
		fstimes = new int[graph.V()];
		// run DFS on reverseGraph
		Stack<Integer> stack = new Stack<Integer>();
		Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[reverseGraph.V()];
		for (int v = 0; v < reverseGraph.V(); v++)
			adj[v] = reverseGraph.adj(v).iterator();
		for (int vertex = reverseGraph.V() - 1; vertex >= 0; vertex--) {
			if (!visited[vertex]) {
				iterative_dfs_first(vertex, adj, stack);
			}
		}
		// run DFS on original graph in order of reverse topo
		visited = new boolean[graph.V()];
		vertexAssignments = new int[graph.V()];
		for (int i = 0; i < vertexAssignments.length; i++) {
			vertexAssignments[i] = -1;
		}
		vertexcc = new int[graph.V()];
		for (int i = 0; i < vertexcc.length; i++) {
			vertexcc[i] = -1;
		}
		for (int fstime = fstimes.length - 1; fstime >= 0; fstime--) {
			int vertex = fstimes[fstime];
			if (!visited[vertex]) {
				iterative_dfs_second(vertex, graph, stack);
				if (!sat) {
					break;
				}
				scc++;
			}
		}
	}

	private void iterative_dfs_first(int vertex, Iterator<Integer>[] adj, Stack<Integer> stack) {
		visited[vertex] = true;
		stack.push(vertex);
		while (!stack.isEmpty()) {
			int v = stack.peek();
			if (adj[v].hasNext()) {
				int w = adj[v].next();
				if (!visited[w]) {
					visited[w] = true;
					stack.push(w);
				}
			} else {
				fstimes[fsTime++] = stack.pop();
			}
		}
	}

	private void iterative_dfs_second(int vertex, DirectedGraph graph, Stack<Integer> stack) {
		stack.push(vertex);
		visited[vertex] = true;
		while (!stack.isEmpty()) {
			vertex = stack.pop();
			vertexcc[vertex] = scc;
			if (!checkAndMakeAssignment(vertex)) {
				sat = false;
				break;
			}
			Iterator<Integer> adjList = graph.adj(vertex).iterator();
			while (adjList.hasNext()) {
				int neighbor = adjList.next();
				if (!visited[neighbor]) {
					visited[neighbor] = true;
					stack.push(neighbor);
				}
			}
		}
	}

	public boolean strongComponent(int v, int w) {
		return vertexcc[v] == vertexcc[w];
	}

	public int vertexAssignment(int v) {
		return vertexAssignments[v];
	}

	private boolean checkAndMakeAssignment(int literal) {
		int negation = literal ^ 1;
		if (strongComponent(literal, negation)) {
			// vertex and negation belong to same component
			return false;
		} else if (vertexAssignment(negation) == -1) {
			vertexAssignments[literal] = 1;
			vertexAssignments[negation] = 0; // assign negative value
			return true;
		}
		return true;
	}

}

/**
 * Bag is a node list storing the positivized index of variables of its neighbor
 * in a stacking order
 */
class Bag<Item> implements Iterable<Item> {
	private Node<Item> first; // beginning of bag, first neighbor
	private int n; // number of elements in bag/ # of neighbor

	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}

	public Bag() {
		first = null;
		n = 0; // Initializes an empty bag.
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return n;
	}

	// adding a new node at head/top
	public void add(Item item) {
		Node<Item> oldfirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldfirst;
		n++;
	}

	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		// with action of deleting the first node / pop
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}
