/**
 *		Om Namah Shivaay
 *
 * Edu On is a CLI based school management system with complete solutions, it is for both the teachers as well as the students.
 * @author: arun-first
 */

package edu.alpha; // package

import java.util.Scanner; // scanner class
import java.io.FileWriter; // file writer
import java.io.FileReader; // file reader
import java.io.BufferedReader; // buffered reader
import java.io.IOException; // io exception

public class EduOn {
	// necessary variables
	int option = 0;
	
	// necessary instantiation
	static Scanner stdInput = new Scanner(System.in);
	static EduOn edu = new EduOn();
	static Teacher tch = new Teacher(); // teacher class object

	// main menu
	static String[] mainMenu = {"","TEACHER","STUDENT","ADMINISTRATOR","EXIT"};

	// main method
	public static void main(String[] args) {
		edu.menuPrint();
		edu.executeMenu();
	}

	// printing the menu
	public void menuPrint () {
		edu.startScreen(); // start screen

		// printing the menu
		for (int i = 1, n = mainMenu.length; i < n; i++)
			System.out.println("\t\t\t\t[ "+i+" ] "+this.mainMenu[i]);

		// getting the input
		do {
			System.out.print("\n\t\t\t\tEnter [1 - 4]: ");
			edu.option = stdInput.nextInt();
		} while (edu.option < 1 || edu.option > 4);
		edu.endScreen();
	}

	// executing the menu
	public void executeMenu () {
		edu.startScreen();

		// executing accordingly
		switch (this.option) {
			case 1: tch.teachMain();
					break;
			case 2: break;
			case 3: edu.endScreen();
					System.exit(0);
					break;
		}
	}

	// start screen
	public void startScreen () {
		System.out.println("\t\t\t#################################################################");
		System.out.println("\t\t\t\t\t\tHOLY FAMILY SCHOOL");
		System.out.println("\t\t\t\t\t\t******************\n");
	}

	// end screen
	public void endScreen () {
		System.out.println("\t\t\t#################################################################\n");
	}
}
