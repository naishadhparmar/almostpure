import java.io.*;
import java.util.*;

public class MainEngine {

	public static File file = null;

	private Node 		mainNode 	= null;
	private BufferedReader 	reader 		= null;	

	public static void main(String[] args) {
		file = new File("Script.txt");
		MainEngine obj = new MainEngine();
		try {
			obj.init();
			obj.listen();		
		}
		catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		finally{}
		
	}
	
	public void init() throws IOException{
		reader = new BufferedReader(new FileReader("Script.txt"));
		mainNode	=	new Node();
		mainNode.setMessage(reader.readLine());
		traverse(mainNode, 1);
	}

	public void traverse(Node node, int level) throws IOException{
		Node possibleNext = null;
		reader.mark((int)file.length());
		String s = reader.readLine();
		while(s != null) {
			int numOfSpaces = 0;
			for(int i = 0 ; i < s.length() ; i++) {
				if(s.charAt(i) == ' ') numOfSpaces++;
				else break;
			}
			if(numOfSpaces == level) {
				possibleNext = new Node();
				possibleNext.setMessage(reader.readLine().trim());
				node.putPossibility(s.substring(numOfSpaces+1), possibleNext);
			}
			else if(numOfSpaces > level) {
				reader.reset();
				traverse(possibleNext, level+1);
			}
			else {
				reader.reset();
				break;
			}
			reader.mark((int)file.length());
			s = reader.readLine();
		}
	}

	public void	listen() {
		Node	currNode	=	mainNode;
		Scanner sc		=	new Scanner(System.in);
		do{
			System.out.println(currNode.getMessage());
			List<String> list = currNode.getAllPossibilities();
			for(int i = 0 ; i < list.size() ; i++) {
				System.out.println((i+1) + ". " + list.get(i));
			}
			System.out.print("Enter the number of your choice: ");
			int choice = sc.nextInt();
			currNode = currNode.getPossibility(list.get(choice-1));
		}
		while(!currNode.isEnding());
	}
}

class Node {
	
	private String 			message = null;
	private Map<String, Node>	possibilities = null;	

	public Node() {
		possibilities = new HashMap<String, Node>();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void putPossibility(String option, Node possibility) {
		possibilities.put(option, possibility);
	}
	
	public Node getPossibility(String option) {
		return possibilities.get(option);
	}
	
	public List<String> getAllPossibilities() {
		Iterator<String> iterator =	possibilities.keySet().iterator();
		List<String> list = new ArrayList<String>();
		while(iterator.hasNext()) list.add(iterator.next());
		return list;
	}	

	public boolean isEnding() {
		return possibilities.size() == 0;
	}
}
