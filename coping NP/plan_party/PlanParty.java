import java.io.*;
import java.util.*;

class Vertex {
	Vertex() {
		this.weight = 0;
		this.children = new ArrayList<Integer>();
	}

	int weight;
	ArrayList<Integer> children;
}

public class PlanParty {
	static Vertex[] ReadTree() throws IOException {
		InputStreamReader input_stream = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input_stream);
		StreamTokenizer tokenizer = new StreamTokenizer(reader);

		tokenizer.nextToken();
		int vertices_count = (int) tokenizer.nval;

		Vertex[] tree = new Vertex[vertices_count];

		for (int i = 0; i < vertices_count; ++i) {
			tree[i] = new Vertex();
			tokenizer.nextToken();
			tree[i].weight = (int) tokenizer.nval;
		}

		for (int i = 1; i < vertices_count; ++i) {
			tokenizer.nextToken();
			int from = (int) tokenizer.nval;
			tokenizer.nextToken();
			int to = (int) tokenizer.nval;
			tree[from - 1].children.add(to - 1);
			tree[to - 1].children.add(from - 1);
		}

		return tree;
	}

	static void dfs(Vertex[] tree, int vertex, int parent, int[] funfactor, int root) {
		for (int child : tree[vertex].children)
			if (child != parent)
				dfs(tree, child, vertex, funfactor, root);
		// if (funfactor[vertex] == Integer.MAX_VALUE) {
		if (tree[vertex].children.size() == 1 && vertex != root) {
			funfactor[vertex] = tree[vertex].weight;
		} else {
			int m1 = tree[vertex].weight;
			for (int child : tree[vertex].children) {
				if (child != parent) {
					for (int grandchild : tree[child].children) {
						if (grandchild != vertex) {
							m1 = m1 + funfactor[grandchild];
						}
					}
				}
			}
			int m0 = 0;
			for (int child : tree[vertex].children) {
				if (child != parent) {
					m0 = m0 + funfactor[child];
				}
			}
			funfactor[vertex] = Integer.max(m1, m0);

		}
		// }
	}

	static int MaxWeightIndependentTreeSubset(Vertex[] tree) {
		int size = tree.length;
		int[] funfactor = new int[tree.length];
		for (int i = 0; i < funfactor.length; i++) {
			funfactor[i] = Integer.MAX_VALUE;
		}
		if (size == 0)
			return 0;
		int root = 0;
		dfs(tree, 0, -1, funfactor, root);
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < funfactor.length; i++) {
			if (funfactor[i] > max) {
				max = funfactor[i];
			}
		}
		return max;
	}

	public static void main(String[] args) throws IOException {
		// This is to avoid stack overflow issues
		new Thread(null, new Runnable() {
			public void run() {
				try {
					new PlanParty().run();
				} catch (IOException e) {
				}
			}
		}, "1", 1 << 26).start();
	}

	public void run() throws IOException {
		Vertex[] tree = ReadTree();
		int weight = MaxWeightIndependentTreeSubset(tree);
		System.out.println(weight);
	}
}
