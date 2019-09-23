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
	private char[] input;
	private int max;
	private int pointer = 0;
	private State start;
	private List<State> states = new ArrayList<>();
	private List<State> accepts = new ArrayList<>();
	private List<State> rejects = new ArrayList<>();
	private List<State> path = new ArrayList<>();

	public class State{
		public int name;
		public char type; // s = start, a = accept, r = reject, n = normal, 0 = null
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
		public Transition(State qc, char ac, State rc, char bc, char xc){
			q = qc;
			a = ac;
			r = rc;
			b = bc;
			x = xc;
		}
	}
	
	public aguilar_p1(String file, char[] in, int m){
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
				if (t == 's') start = s;
				if (t == 'a') accepts.add(s);
				if (t == 'r') rejects.add(s);
			}
			else{ //transition
				String[] words = line.split("\t");
				State q = findState(Integer.parseInt(words[1]));
				char a = words[2].charAt(0);
				State r = findState(Integer.parseInt(words[3]));
				char b = words[4].charAt(0);
				char x = words[5].charAt(0);
				Transition t = new Transition(q, a, r, b, x);
				q.trans.add(t);
			}
		}
	}
	
	private State findState(int n){
		for (State s : states){
			if (n == s.name){
				return s;
			}
		}
		State s = new State(n, 'n');
		states.add(s);
		return s;
	}
		
	public void run(){
		State s = null;
		path.add(start);
		for (int i = 0; i < max; i++){
			if (i == 0){
				s = read(start, input[pointer]);
			}
			else{
				s = read(s, input[pointer]); 
			}
			if (s == null) return;
			path.add(s);
		}
	}

	public State read(State q, char a){
		int old = pointer;
		for (Transition t : q.trans){
			if (t.a == a){
				input[pointer] = t.b;
				if (t.x == 'L')	pointer--;
				else pointer++;
				if (pointer == old || pointer < 0 || pointer >= input.length) return null;
				return t.r;
			}
		}
		return null;
	}

	public void printPath(){
		for (int i = 0; i < path.size() - 1; i++){
			System.out.print(path.get(i).name + "->");
		}
		State last = path.get(path.size()-1);
		System.out.print(last.name);
		if (accepts.contains(last)) System.out.print(" accept");
		else if (rejects.contains(last)) System.out.print(" reject");
		else System.out.print(" quit");
		System.out.println();
	}

	public static void main(String[] args) throws Exception{
		String file = args[0];
		char[] in = args[1].toCharArray();
		int m = Integer.parseInt(args[2]);
		aguilar_p1 Turing = new aguilar_p1(file, in, m);
		Turing.setup();
		Turing.run();
		Turing.printPath();
	}
}
