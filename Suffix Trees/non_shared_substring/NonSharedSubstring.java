import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.*;

class Node {
	public int start;
	public int len;
	public ArrayList<Node> list;
	public Node parrent;
	public boolean old;

	Node(int s, int e, ArrayList<Node> l, Node p, boolean o) {
		this.start = s;
		this.len = e;
		this.list = l;
		this.parrent = p;
		this.old = o;
	}
}

public class NonSharedSubstring {
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

	public List<String> computeSuffixTreeEdges(String text, String text1) {
		List<String> result = new ArrayList<String>();
		Node root = new Node(0, 0, new ArrayList<Node>(), null, false);
		root.list.add(new Node(0, text.length(), new ArrayList<Node>(), root, true));
		for (int i = 1; i < text.length(); i++) {
			newBranch(root, i, text, true);
		}
		for (int i = text.length(); i < text1.length(); i++) {
			newBranch(root, i, text1, false);
		}

		LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<Node>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node temp = queue.poll();
			for (Node nodes : temp.list) {
				queue.add(nodes);
			}
			if (temp.old && temp.start != text.length() - 1) {
				String s = "";
				Node p = temp.parrent;
				while (p != null) {
					s = text.substring(p.start, p.start + p.len) + s;
					p = p.parrent;
				}
				s = s + text.charAt(temp.start);
				result.add(s);
			}
		}
		return result;
	}

	ArrayList<Character> order = new ArrayList<Character>();

	public void newBranch(Node root, int start, String text, boolean old) {
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
							Node newRoot = new Node(node.start, i, new ArrayList<Node>(), node.parrent, old);
							node.parrent.list.set(j, newRoot);
							Node newNode = new Node(start, text.length() - start, new ArrayList<Node>(), newRoot, old);
							newRoot.list.add(newNode);
							newRoot.list.add(node);
							node.parrent = newRoot;
							node.start = newRoot.start + newRoot.len;
							node.len = node.len - newRoot.len;
							return;
						}
						start++;
					}
					node.old = old;
					currentNode = node;
					break;
				}
			}
			if (!found) {
				Node newNode = new Node(start, text.length() - start, new ArrayList<Node>(), currentNode, old);
				currentNode.list.add(newNode);
				return;
			}
		}
	}

	static public void main(String[] args) throws IOException {
		new NonSharedSubstring().run();
	}

	public void print(List<String> x) {
		String o = x.get(0);
		for (String a : x) {
			if (o.length() > a.length())
				o = a;
		}
		System.out.println(o);
	}

	public void run() throws IOException {
		FastScanner scanner = new FastScanner();
		String text = scanner.next() + "#";
		String text1 = text + scanner.next() + "$";
		List<String> edges = computeSuffixTreeEdges(text, text1);
		print(edges);
	}

}
