import java.io.*;
import java.util.*;

class Bracket {

    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']') {
            return true;
        }
        if (this.type == '{' && c == '}') {
            return true;
        }
        if (this.type == '(' && c == ')') {
            return true;
        }
        return false;
    }

    char type;
    int position;
}

public class check_brackets {

    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> brackets = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                brackets.push(new Bracket(next, position));
            }

            if (next == ')') {
                if (brackets.empty()) {
                    System.out.print(position + 1);
					return;
                } else if (next - brackets.peek().type == 1) {
                    brackets.pop();
                } else {
                    System.out.print(position + 1);
                    return;
                }

            } else if (next == ']' || next == '}') {
                if (brackets.empty()) {
                    System.out.print(position + 1);
					return;
                } else if (next - brackets.peek().type == 2) {
                    brackets.pop();
                } else {
                    System.out.print(position + 1);
                    return;
                }
            }
        }
		if(brackets.empty()){
			System.out.print("Success");
		}else{
			System.out.print(brackets.peek().position +1);
		}
    }
}