import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;
/**
   >> Program must read definition of the machine from a file (first cmd line arg)
   >> Must read the string (input to automata) to simulate the machine running on (second cmd line arg)
   >> Must read maximum number of transitions to simulate (third cmd line arg)
   >> Output (written to std output) will consist of the list of states that automata transitions thruogh while
      reading the input string (separated by "->"), followed by either accept or reject
 */ 

public class aguilar_p1{
	private String fileName;
	private String input;
	private int max;
	private List<State> states = new ArrayList<>();
	public class State{
		public int name;
		public char type; // s = start, a = accept, r = reject, 0 = null
		public List<Transition> trans = new ArrayList<>();
		public State(int n, char t){
			name = n;
			type = t;
		}
	}
	public class Transition{
		public State q;
		public State r;
		public char a; //{0, 1, ... 9} OR {a, b, ..., z} OR {$, #, _, $}
		public char b;
		public char x; // {L, R}
		public Transition(int qc, int rc, char ac, char bc, char xc){
			a = ac;
			b = bc;
			x = xc;
			for (State s : states){
				if (qc == s.name){
					q = s;
				}
				if (rc == s.name){
					r = s;
				}
			}
		}
	}
	public aguilar_p1(String file, String in, int m){
		fileName = file;
		input = in;
		max = m;
	}
	public void setup() throws Exception{
		File file = new File(fileName);
		Scanner parser = new Scanner(file);
		while (parser.hasNextLine()){
			String line = parser.nextLine();
			if (line.charAt(0) == 's'){ //state
				String[] words = line.split("\t");
				char t;
				if (words[2].equals("start")) t = 's';
				else if (words[2].equals("accept")) t = 'a';
				else if (words[2].equals("reject")) t = 'r';
				else t = 0;
				int n = Integer.parseInt(words[1]);
				State s = new State(n, t);
				states.add(s);
			}
			else{ //transition
				Strings[] words = line.split("\t");
				int q = Integer.parseInt(words[1]);
				int r = Integer.parseInt(words[3]);
				Transition
			}
		}
	}
	public void run(){
		for (State s : states){
			System.out.println(s.name + " " + s.type);
		}
	}
	public static void main(String[] args) throws Exception{
		String file = args[0];
		String in = args[1];
		int m = Integer.parseInt(args[2]);
		aguilar_p1 Turing = new aguilar_p1(file, in, m);
		Turing.setup();
		Turing.run();
	}
}
