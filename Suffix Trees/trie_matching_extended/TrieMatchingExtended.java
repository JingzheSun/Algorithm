import java.io.*;
import java.util.*;

public class TrieMatchingExtended implements Runnable {
	List<Integer> solve(String text, int n, String[] patterns) {
		List<Map<Character, Integer>> trie = buildTrie(patterns);
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < text.length(); i++) {
			if (PrefixTrieMatching(text.substring(i), trie)) {
				result.add(i);
			}
		}
		return result;
	}

	public boolean PrefixTrieMatching(String text, List<Map<Character, Integer>> trie) {

		int letter = 0;
		char c = text.charAt(letter++);
		int index = 0;
		while (true) {
			if (trie.get(index).isEmpty() || bool.get(index).equals(true)) {
				return true;
			} else if (trie.get(index).containsKey(c)) {
				index = trie.get(index).get(c);
				if (letter < text.length()) {
					c = text.charAt(letter++);
				} else {
					c = '$';
				}
			} else {
				return false;
			}
		}
	}

	ArrayList<Boolean> bool = new ArrayList<Boolean>();

	List<Map<Character, Integer>> buildTrie(String[] patterns) {
		List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
		trie.add(new HashMap<Character, Integer>());
		bool.add(false);
		int sum = 1;
		for (int i = 0; i < patterns.length; i++) {
			String pattern = patterns[i];
			int index = 0;
			for (int j = 0; j < pattern.length(); j++) {
				if (trie.size() > index) {
					if (trie.get(index).containsKey(pattern.charAt(j))) {
						index = trie.get(index).get(pattern.charAt(j));
						if (j == pattern.length() - 1)
							bool.set(index, true);
					} else {
						trie.get(index).put(pattern.charAt(j), sum);
						sum++;
						trie.add(new HashMap<Character, Integer>());
						index = trie.size();
						bool.add(j == pattern.length() - 1 ? true : false);
					}
				} else {
					trie.get(trie.size() - 1).put(pattern.charAt(j), sum);
					trie.add(new HashMap<Character, Integer>());
					sum++;
					index = trie.size();
					bool.add(j == pattern.length() - 1 ? true : false);
				}
			}
		}
		return trie;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String text = in.readLine();
			int n = Integer.parseInt(in.readLine());
			String[] patterns = new String[n];
			for (int i = 0; i < n; i++) {
				patterns[i] = in.readLine();
			}

			List<Integer> ans = solve(text, n, patterns);

			for (int j = 0; j < ans.size(); j++) {
				System.out.print("" + ans.get(j));
				System.out.print(j + 1 < ans.size() ? " " : "\n");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatchingExtended ()).start ();
	}
}
