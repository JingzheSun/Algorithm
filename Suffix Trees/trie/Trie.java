import java.io.*;
import java.util.*;

public class Trie {
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

	List<Map<Character, Integer>> buildTrie(String[] patterns) {
		List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
		int sum = 1;
		for (int i = 0; i < patterns.length; i++) {
			String pattern = patterns[i];
			int index = 0;
			for (int j = 0; j < pattern.length(); j++) {
				if (trie.size() > index) {
					if (trie.get(index).containsKey(pattern.charAt(j))) {
						index = trie.get(index).get(pattern.charAt(j));
					} else {
						trie.get(index).put(pattern.charAt(j), sum);
						sum++;
						trie.add(new HashMap<Character, Integer>());
						index = trie.size();
					}
				} else {
					trie.add(new HashMap<Character, Integer>());
					trie.get(trie.size() - 1).put(pattern.charAt(j), sum);
					sum++;
					index = trie.size();
				}
			}
		}
		return trie;
	}

	static public void main(String[] args) throws IOException {
		new Trie().run();
	}

	public void print(List<Map<Character, Integer>> trie) {
		for (int i = 0; i < trie.size(); i++) {
			Map<Character, Integer> node = trie.get(i);
			for (Map.Entry<Character, Integer> entry : node.entrySet()) {
				System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
			}
		}
	}

	public void run() throws IOException {
		FastScanner scanner = new FastScanner();
		int patternsCount = scanner.nextInt();
		String[] patterns = new String[patternsCount];
		for (int i = 0; i < patternsCount; i++) {
			patterns[i] = scanner.next();
		}
		List<Map<Character, Integer>> trie = buildTrie(patterns);
		print(trie);
	}
}
