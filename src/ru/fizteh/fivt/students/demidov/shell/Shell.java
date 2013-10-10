package ru.fizteh.fivt.students.demidov.shell;

import java.io.IOException;
import java.lang.Thread;
import java.util.Scanner;
import java.util.Arrays;

public class Shell {
	private static String currentDirectory = System.getProperty("user.dir");
	
	public static String getCurrentDirectory() {
			return currentDirectory;	
	}
	
	public static void changeCurrentDirectory(String newDirectory) {
		currentDirectory = newDirectory;		
	}

	public static void main(String[] args) {
		if (0 != args.length) {
			StringBuilder strBuilderOfInstructions = new StringBuilder();
			for (int i = 0; i < args.length; ++i) {
				strBuilderOfInstructions.append(args[i] + " ");
			}			
			String instructions = strBuilderOfInstructions.toString();

			try {
				doInstructions(instructions);
			} catch (IOException catchedException) {
				System.err.println(catchedException);
				System.exit(1);
			} catch (InterruptionException catchedException) {
				System.exit(0);
			}
		} else {
			Scanner inputScanner = new Scanner(System.in);

			while (!Thread.currentThread().isInterrupted()) {
				System.out.print(currentDirectory + "$ ");

				String instructions = inputScanner.nextLine();				
		
				try {
					doInstructions(instructions);
				} catch (IOException catchedException) {
					System.err.println(catchedException);
				} catch (InterruptionException catchedException) {
					System.exit(0);
				}
			} 
		}
	}

	private static void doInstructions(String instructions) throws IOException, InterruptionException {
		for (String instruction : instructions.trim().split("\\s*;\\s*", -1)) {
			executeInstruction(instruction.split("\\s+"));
		}
	}

	private static void executeInstruction(String[] exeInstruction) throws IOException, InterruptionException {
		if (0 == exeInstruction.length) {
			throw new IOException("empty instruction");
		}
		
		BasicCommand exeCommand = null;
		int numberOfArguments = 1;
		
		switch (exeInstruction[0]) {
			case "cd":
				exeCommand = new Cd();
				numberOfArguments = 2;
				break;
			case "pwd":
				exeCommand = new Pwd();
				break;
			case "dir":
				exeCommand = new Dir();
				break;
			case "mkdir":
				exeCommand = new Mkdir();
				numberOfArguments = 2;
				break;
			case "rm":
				exeCommand = new Rm();
				numberOfArguments = 2;
				break;
			case "cp":
				exeCommand = new Cp();
				numberOfArguments = 3;
				break;
			case "mv":
				exeCommand = new Mv();
				numberOfArguments = 3;
				break;
			case "exit":
				exeCommand = new Exit();
				break;
				
		}

		if (null == exeCommand) {
			throw new IOException("unknown instruction: " + exeInstruction[0]);
		} 
		
		if (exeInstruction.length > numberOfArguments) {
			throw new IOException("too many arguments for " + exeInstruction[0]);
		} else if (exeInstruction.length < numberOfArguments) {
			throw new IOException("too less arguments for " + exeInstruction[0]);
		}

		exeCommand.executeCommand(Arrays.copyOfRange(exeInstruction, 1, exeInstruction.length));
	}
}