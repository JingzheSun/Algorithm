import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

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
		List<Map<Character, Integer>> trie = buildTrie(text);
		result = compress(trie, text);
		return result;
	}

	public ArrayList<String> compress(List<Map<Character, Integer>> trie, String text) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 1; i < trie.size(); i++) {
			int size = trie.get(i).size();
			if (size != 1) {
				result.add(order.get(i).toString());
			} else {
				StringBuilder temp = new StringBuilder();
				while (size == 1) {
					temp.append(order.get(i));
					i++;
					size = trie.get(i).size();
				}
				temp.append(order.get(i));
				result.add(temp.toString());
			}
		}
		return result;
	}

	ArrayList<Character> order = new ArrayList<Character>();

	List<Map<Character, Integer>> buildTrie(String pattern) {
		List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
		int sum = 1;
		order.add('#');
		trie.add(new HashMap<Character, Integer>());
		for (int i = 0; i < pattern.length(); i++) {
			int index = 0;
			for (int j = i; j < pattern.length(); j++) {
				if (trie.size() > index) {
					if (trie.get(index).containsKey(pattern.charAt(j))) {
						index = trie.get(index).get(pattern.charAt(j));
					} else {
						trie.get(index).put(pattern.charAt(j), sum);
						sum++;
						order.add(pattern.charAt(j));
						trie.add(new HashMap<Character, Integer>());
						index = trie.size();
					}
				} else {
					trie.get(trie.size() - 1).put(pattern.charAt(j), sum);
					trie.add(new HashMap<Character, Integer>());
					order.add(pattern.charAt(j));
					sum++;
					index = trie.size();
				}
			}
		}
		return trie;
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
