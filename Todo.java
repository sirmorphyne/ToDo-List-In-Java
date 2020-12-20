import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.time.LocalDate; 
public class Todo {
	static ArrayList<String> a = new ArrayList<>();
	static ArrayList<String> areset = new ArrayList<>();

	private static void TodoLoop() {
		BufferedReader read = null;
		try {
			read = new BufferedReader(new InputStreamReader(new FileInputStream("todo.txt"), StandardCharsets.UTF_8));
			String printreader;
			while ((printreader = read.readLine()) != null) {
				a.add(printreader);
			}
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void TodoLoopDone() {
		BufferedReader read = null;
		try {
			read = new BufferedReader(new InputStreamReader(new FileInputStream("done.txt"), StandardCharsets.UTF_8));
			String printreader;
			while ((printreader = read.readLine()) != null) {
				areset.add(printreader);
			}
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length == 0 || args[0].equals("help")) {
			System.out.print("Usage :-\n" + "$ ./todo add \"todo item\"  # Add a new todo\n"
					+ "$ ./todo ls               # Show remaining todos\n"
					+ "$ ./todo del NUMBER       # Delete a todo\n" + "$ ./todo done NUMBER      # Complete a todo\n"
					+ "$ ./todo help             # Show usage\n" + "$ ./todo report           # Statistics\n");
		}

		
		else if (args[0].equals("ls")) {
			Integer i = 1;
			TodoLoop();
			if (a.size() == 0) {
				System.out.println("There are no pending todos!");
			} else {
				for (; i <= a.size(); i++) {
					System.out.println("[" + (a.size() - i + 1) + "] " + a.get(a.size() - i));
				}
			}
		}

		
		else if (args[0].equals("add")) {
			if (args.length == 1)
				System.out.println("Error: Missing todo string. Nothing added!");
			else {
				try {
					BufferedWriter bfWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("todo.txt", true), StandardCharsets.UTF_8));
					String temp = args[1];
					Charset.forName("UTF-8").encode(temp);
					bfWriter.write(temp);
					bfWriter.newLine();
					bfWriter.close();
					System.out.println("Added todo: \"" + args[1] + "\"");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		
		else if (args[0].equals("del")) {
			if (args.length == 1) {
				System.out.println("Error: Missing NUMBER for deleting todo.");
			} else {
				TodoLoop();
				try {
					int i = 1;
					BufferedWriter bfWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("todo.txt", false), StandardCharsets.UTF_8));
					if (Integer.parseInt(args[1]) > a.size() || args[1].equals("0")) {
						System.out.println(
								"Error: todo #" + Integer.parseInt(args[1]) + " does not exist. Nothing deleted.");
					} else {
						for (int j = 1; j <= a.size(); j++) {
							if (j == Integer.parseInt(args[1])) {
								i++;
								System.out.println("Deleted todo #" + (i - 1));
								continue;
							} else {
								bfWriter.write(a.get(i - 1));
								bfWriter.newLine();
								i++;
							}

						}
					}
					bfWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		
		else if (args[0].equals("done")) {
			if (args.length == 1) {
				System.out.println("Error: Missing NUMBER for marking todo as done.");
			} else {
			
				try {
					TodoLoop();
					BufferedWriter bfWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("done.txt", true), StandardCharsets.UTF_8));
					if (Integer.parseInt(args[1]) > a.size() || args[1].equals("0")) {
						System.out.println("Error: todo #" + Integer.parseInt(args[1]) + " does not exist.");
					} else {
						bfWriter.write(args[1]);
						bfWriter.newLine();
						bfWriter.close();
						System.out.println("Marked todo #" + args[1] + " as done.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					int i = 1;
					BufferedWriter bfWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("todo.txt", false), StandardCharsets.UTF_8));
					for (int j = 1; j <= a.size(); j++) {
						if (j == Integer.parseInt(args[1])) {
							i++;
							continue;
						} else {
							bfWriter.write(a.get(i - 1));
							bfWriter.newLine();
							i++;
						}

					}
					bfWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		else if (args[0].equals("report")) {
			TodoLoop();
			TodoLoopDone();
			LocalDate today = LocalDate.now();
			System.out.println(today + " Pending : " + a.size() + " Completed : " + areset.size());
		}
	}
}