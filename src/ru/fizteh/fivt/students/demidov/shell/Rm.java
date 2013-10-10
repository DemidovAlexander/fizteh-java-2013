package ru.fizteh.fivt.students.demidov.shell;

import java.io.File;
import java.io.IOException;

public class Rm implements BasicCommand {
	public void executeCommand(String[] arguments) throws IOException {		
		File source = Utils.getFile(arguments[0]);	

		if (source.exists()) {
			Utils.deleteFileOrDirectory(source);
		} else {
			throw new IOException(arguments[0] + " doesn't exist");
		}
	}
}