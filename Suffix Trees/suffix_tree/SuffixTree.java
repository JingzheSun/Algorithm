import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.*;
import java.util.zip.CheckedInputStream;

class Node {
	public int start;
	public int len;
	public ArrayList<Node> list;
	public Node parrent;

	Node(int s, int e, ArrayList<Node> l, Node p) {
		this.start = s;
		this.len = e;
		this.list = l;
		this.parrent = p;
	}
}

public class SuffixTree {
	class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}

	public List<String> computeSuffixTreeEdges(String text) {
		List<String> result = new ArrayList<String>();
		Node root = new Node(0, 0, new ArrayList<Node>(), null);
		root.list.add(new Node(0, text.length(), new ArrayList<Node>(), root));
		for (int i = 1; i < text.length(); i++) {
			newBranch(root, i, text);
		}

		LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<Node>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node temp = queue.poll();
			result.add(text.substring(temp.start, temp.start + temp.len));
			for (Node nodes : temp.list) {
				queue.add(nodes);
			}
		}

		return result;
	}

	ArrayList<Character> order = new ArrayList<Character>();

	public void newBranch(Node root, int start, String text) {
		Node currentNode = root;
		while (true) {
			boolean found = false;
			for (int j = 0; j < currentNode.list.size(); j++) {
				Node node = currentNode.list.get(j);
				if (text.charAt(start) == text.charAt(node.start)) {
					start++;
					found = true;
					for (int i = 1; i < node.len; i++) {
						char a = text.charAt(i + node.start);
						char b = text.charAt(start);
						if (a != b) {
							Node newRoot = new Node(node.start, i, new ArrayList<Node>(), node.parrent);
							node.parrent.list.set(j, newRoot);
							Node newNode = new Node(start, text.length() - start, new ArrayList<Node>(), newRoot);
							newRoot.list.add(newNode);
							newRoot.list.add(node);
							node.parrent = newRoot;
							node.start = newRoot.start + newRoot.len;
							node.len = node.len - newRoot.len;
							return;
						}
						start++;
					}
					currentNode = node;
					break;
				}
			}
			if (!found) {
				Node newNode = new Node(start, text.length() - start, new ArrayList<Node>(), currentNode);
				currentNode.list.add(newNode);
				return;
			}
		}
	}

	static public void main(String[] args) throws IOException {
		new SuffixTree().run();
	}

	public void print(List<String> x) {
		for (String a : x) {
			System.out.println(a);
		}
	}

	public void run() throws IOException {
		FastScanner scanner = new FastScanner();
		String text = scanner.next();
		List<String> edges = computeSuffixTreeEdges(text);
		print(edges);
	}
}
