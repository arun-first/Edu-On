/**
 *								Om Namah Shivaay
 * Compilation: javac Student.java
 * Execution: java Student
 *
 * Handles the student end.
 */

package edu.alpha; // package

// necessary imports
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Student extends EduOn {
	// necessary variables
	private int studAdmn = 0;
	int option = 0, numMsg = 0;
	String msg = " ";

	// necessary arrays
	private String[] menu = {"","CHECK MESSAGES","WRITE MESSAGE","LIST TEACHERS","EXIT"};
	private String[] studDetails = new String[10];
	private String[] messages = new String[10];
	private String[] teacherDetails = new String[10];

	// necessary instantiation
	private static EduOn edu = new EduOn();
	private static Student stud = new Student();

	// necesary objects reference
	private static FileWriter fw = null;
	private static BufferedReader read = null;

	// method to display the main screen and login
	public void mainScreen () {
		stud.loginScreen();
		edu.startScreen();
		stud.display();
		edu.endScreen();
	}

	// method to handle the input
	public void display () {
		// displaying the options
		stud.mainMenu();
		
		switch(this.option) {
			case 1: stud.checkMsg();
					stud.printMsg();
					break;
			case 2: boolean is = stud.writeToTeacher();
					if (is)
						System.out.println("\t\t\t\tMessage Sent");
					else
						System.out.println("\t\t\t\tMessage Not Sent (teacher not found)");
					break;
			case 3: stud.listTeachers();
					break;
			case 4: stud.endScreen();
					System.exit(0);
					break;
		}
	}
	
	// method to display the main menu
	public void mainMenu () {
		// printing
		for (int i = 1, n = this.menu.length; i < n; i++)
			System.out.println("\t\t\t\t[ "+i+" ] "+this.menu[i]);

		do {
			try {
				System.out.println("\n\t\t\t\tEnter [1 - 4]: ");
				stud.option = stdInput.nextInt();
			} catch (Exception e) {
				System.out.println("ERROR: invalid input");
			}
		} while (stud.option > 4 || stud.option < 1);
	}

	// method to make the login
	private void loginScreen () {
		// getting credentials
		edu.startScreen();
		System.out.print("\t\t\tAdmn No.: ");
		stud.studAdmn = stdInput.nextInt();
		stdInput.nextLine(); // eating the extra '\n'
		System.out.print("\t\t\tPassword: ");
		String pswd = stdInput.nextLine();

		// checking credentials
		boolean is = stud.checkDetails(stud.studAdmn,pswd);
		if(!is) {
			System.out.println("ERROR: invalid credentials");
			edu.endScreen();
			System.exit(0);
		} else
			edu.endScreen();
	}

	// checking & getting details
	private boolean checkDetails(int admn, String pswd) {
		boolean is = false;

		// reading from the file
		try {
			read = new BufferedReader(new FileReader("students.csv"));
			String line = " ";

			while((line = read.readLine()) != null) {
				this.studDetails = line.split(",");

				if(Integer.parseInt(this.studDetails[0]) == admn) {
					// checking pswd
					if (this.studDetails[1].equals(pswd))
						is = true;
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
		
		return is;
	}

	// method to check messages
	public void checkMsg () {
		// reading the message
		try {
			read = new BufferedReader(new FileReader("students.csv")); // reading

			// necessary variable
			String line = " ";

			while((line = read.readLine()) != null) {
				studDetails = line.split(","); // getting the student information
				// getting the message
				for (int i = 7, n = studDetails.length; i < n; i++) {
					messages[stud.numMsg] = studDetails[i];
					stud.numMsg++;
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
	}

	// printing the messages
	public void printMsg () {
		if (stud.numMsg != 0) {
			for (int i = 0; i < stud.numMsg; i++)
				System.out.println("\t\t\t\t[ "+i+" ] "+this.messages[i]);
		} else 
			System.out.println("\t\t\t\tNo message found");
	}

	// method to list the teachers
	public void listTeachers () {
		System.out.println("\n\t\t\t\t\t\t  TEACHER LIST");
		System.out.println("\t\t\t\t\t\t  ************");
		// reading the teacher list
		try {
			read = new BufferedReader(new FileReader("teacher.csv"));
			String line = " ";
			String[] teacher = new String[10]; // temporary to hold the teacher
			int i = 1;

			while((line = read.readLine()) != null) {
				teacher = line.split(",");
				System.out.println("\t\t\t\t[ "+i+" ] "+teacher[0]+" "+teacher[1]);
				i++;
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
	}

	// get teacher detail
	public int getTeacher () {
		// getting the teacher's unique ID		
		System.out.print("\n\t\t\t\tUnique ID: ");
		int uniq = stdInput.nextInt();

		// getting the message
		stdInput.nextLine(); // eating the '\n'
		System.out.print("\t\t\t\tMessage: ");
		this.msg = stdInput.nextLine();
		
		return uniq;
	}

	// writting the message
	public boolean writeToTeacher () {
		int uniq = stud.getTeacher();
		boolean is = false; // confirmation variable

		try {
			// opening the files
			File oldFile = new File("teacher.csv");
			File newFile = new File("temp.csv");
			
			fw = new FileWriter(newFile,true);
			read = new BufferedReader(new FileReader("teacher.csv")); // reader
			String line = " ";
			String[] teacher = new String[10];

			while ((line = read.readLine()) != null) {
				teacher = line.split(","); // writting to teacher

				if(Integer.parseInt(teacher[0]) == uniq) {
					fw.append(line+this.msg+",\n");
					is = true;
				} else 
					fw.append(line+"\n");
			}

			// renaming the file
			oldFile.delete();
			File dump = new File("teacher.csv");
			newFile.renameTo(dump);
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
		return is;
	}
}
