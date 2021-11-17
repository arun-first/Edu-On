/**
 * Om Namah Shivaay
 *
 * Teacher class occupies the necessary variables as well as necessary functions to work out the teacher.
 * @author: Arunoday
 */

package edu.alpha; // package

import java.util.Scanner; // scanner
import java.io.FileReader; // file reader
import java.io.FileWriter; // file writer
import java.io.BufferedReader; // to read the files
import java.io.IOException; // exception for io of files
import java.io.File; // for handling files

public class Teacher {
	// necessary variables
	private int uniqueID = 0;
	int option = 0; // for menu
	private String msg = " ";
	boolean send = false; // for messaging

	// menus
	static String[] teachMenu = {"","SEND MESSAGE","SEARCH STUDENT","EXIT"};
	static String[] msgMenu = {"","ALL STUDENTS","ONE CLASS STUDENTS","ONE STUDENT","EXIT"};
	private static String[] studDetails = new String[10]; // for student
	private static String[] teachDetails = new String[10]; // for teacher
	private static String[] studField = {"Admn No.","Class","Name","DOB","Father's Name","Mother's Name","Password"};
	private static String[] teachField = {"Unique ID","Name","DOB","Mobile No.","Password","Subject","ClassTeacher"};

	// instantiation
	static Scanner stdInput = new Scanner(System.in);
	static Teacher teach = new Teacher();
	static FileWriter fw = null; // file writer
	static BufferedReader read = null; // reader

	public void teachMain () {
		// login credentials
		if (teach.teacherLogin()) {
			System.out.println("\n\t\t\tWelcome Mr./Mrs, "+teach.teachDetails[1]+"\n");
			teach.mainMenu();
		} else {
			System.out.println("ERROR: invalid login credentials!");
		}
	}

	// for main menu print
	public void mainMenu () {
		teach.menuPrint();
					
		switch (teach.option) {
			case 1: teach.sendMsg();
					break;
			case 2: System.out.print("\t\t\tAdmn No.: ");
					teach.srchStud(stdInput.nextInt());
					break;
			case 3: System.exit(0);	
					break;
		}
	}

	// methods
	public void menuPrint () {
		// printing the menu
		for (int i = 1, n = this.teachMenu.length; i < n; i++)
			System.out.println("\t\t\t\t["+i+"] "+this.teachMenu[i]);

		// taking input the choice
		do {
			try {
				System.out.print("\n\t\t\tEnter [1 - 3]: ");
				teach.option = stdInput.nextInt();
			} catch (Exception e) {
				System.out.println("\t\t\tERROR: invalid input");
			}
		} while (teach.option < 1 || teach.option > 3);
	}

	// message send
	public void sendMsg () {
		// printing the menu
		for (int i = 1, n = this.msgMenu.length; i < n; i++)
			System.out.println("\t\t\t\t["+i+"] "+this.msgMenu[i]);

		// getting the choice
		do {
			try {
				System.out.print("\n\t\t\tEnter [1 - 4]: ");
				teach.option = stdInput.nextInt();
			} catch (Exception e) {
				System.out.println("\t\t\tERROR: invalid input");
			}
		} while (teach.option < 1 || teach.option > 4);

		// doing according to the choice
		switch (teach.option) {
			case 1: teach.getMessage();
					teach.msgAll();
					break;
			case 2: System.out.print("\t\t\tClass: ");
					char a = stdInput.next().charAt(0);
					teach.getMessage();
					teach.msgClass(a);
					break;
			case 3: System.out.print("\t\t\tAdmn No: ");
					int b = stdInput.nextInt();
					teach.getMessage();
					teach.msgOne(b);
					break;
			case 4: System.exit(0);
					break;
		}
	}

	// get message
	public void getMessage () {
		// getting the message
		System.out.println("\t\t\tMessage: ");
		stdInput.nextLine(); // eating the \n
		teach.msg = stdInput.nextLine();
	}

	// send message to all
	public boolean msgAll() {
		System.out.println(teach.msg);
		// creating files
		File oldFile = new File("students.csv"); // older one file
		File newFile = new File("temp.csv"); // new file to append the message
		
		try {
			fw = new FileWriter("temp.csv",true); // writer
			read = new BufferedReader(new FileReader("students.csv")); // reader
			String line = " "; // to hold individual record

			while ((line = read.readLine()) != null) {
				String app = line +teach.msg+",";
				fw.append(app + "\n"); // appending the message
			}

			File dump = new File("students.csv");
			oldFile.delete();  // deleting the previous file
			send = newFile.renameTo(dump); // renaming the new file
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return send;
	}

	// messaging a whole class
	public void msgClass (char clas) {
		File oldFile = new File("students.csv"); // older file
		File newFile = new File("temp.csv"); // new file to append the message

		// appending message
		try {
			fw = new FileWriter("temp.csv",true); // writer
			read = new BufferedReader(new FileReader("students.csv")); // reader
			String line = " ", app = " "; // to hold individual record

			while ((line = read.readLine()) != null) {
				teach.studDetails = line.split(",");

				if (teach.studDetails[1].charAt(0) == clas){ // check for class
					app = line + teach.msg+","; // forming the message
					fw.append(app+"\n");
				}
				else
					fw.append(line+"\n");
			}

			File dump = new File("students.csv"); // for renaming the file
			oldFile.delete(); // deleting the old file
			newFile.renameTo(dump); // renaming the file
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void msgOne (int admn) {
		File oldFile = new File("students.csv"); // older file
		File newFile = new File("temp.csv"); // new file to append the message

		try {
			fw = new FileWriter("temp.csv",true); // writer
			read = new BufferedReader(new FileReader("students.csv")); // reader
			String line = " ", app = " "; // to hold individual record

			while ((line = read.readLine()) != null) {
				studDetails = line.split(",");

				// checking the class
				if (Integer.parseInt(studDetails[0]) == admn) {
					app = line + this.msg + ",\n"; // building the message
					fw.append(app); // writting
				} else {
					fw.append(line+"\n"); // writting
				}
			}

			File dump = new File("students.csv"); // garbage file
			oldFile.delete(); // deleting the older file
			newFile.renameTo(dump); // renaming the newly created file
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// search student
	public boolean srchStud (int admn) {
		boolean found = false;

		// searchin
		try {
			read = new BufferedReader(new FileReader("students.csv")); // getting ready to read the file

			// necessary variable
			String line = " ";

			while ((line = read.readLine()) != null) {
				studDetails = line.split(","); // generating student information

				// searchin
				if (Integer.parseInt(studDetails[0]) == admn) {
					found = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// displaying the details
		if (found) {
			System.out.println("\n\t\t\tStudent Found,\n");
			for (int i = 0; i < 6; i++) {
				System.out.print("\t\t\t"+this.studField[i]+" : ");
				System.out.println(this.studDetails[i]);
			}
		} else {
			System.out.println("\n\t\t\tStudent not found!!");
		}
		
		return found;
	}

	// login teacher
	private boolean teacherLogin () {
		// getting the necessary input
		System.out.println("\t\t\t\t\t\tTEACHER LOGIN");
		System.out.println("\t\t\t\t\t\t*************\n");
		String pswd = " "; // for holding password
		boolean isTeacher = false; // for check
		try {
			System.out.print("\t\t\tUnique ID: ");
			this.uniqueID = stdInput.nextInt();
			stdInput.nextLine(); // eating the \n
			System.out.print("\t\t\tPassword: ");
			pswd = stdInput.nextLine();
		} catch (Exception e) {
			System.out.println("ERROR: invalid credentials!");
		}
		System.out.println(); // new line
		// reading from the file
		try {
			read = new BufferedReader(new FileReader("teacher.csv")); // getting the file
			String line = " "; // necessary variable

			// reading
			while((line = read.readLine()) != null) {
				teachDetails = line.split(",");

				// checking the credentials
				if (Integer.parseInt(teachDetails[0]) == this.uniqueID && teachDetails[4].equals(pswd)) {
					isTeacher = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isTeacher;
	}
	
}
