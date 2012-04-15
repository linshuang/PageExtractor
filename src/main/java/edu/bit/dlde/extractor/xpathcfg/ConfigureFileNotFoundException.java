package edu.bit.dlde.extractor.xpathcfg;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ConfigureFileNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4018871715258390511L;
	String _file;
	final String stackTrace;

	public ConfigureFileNotFoundException(String file) {
		_file = file;
		StringWriter sw = new StringWriter();
		super.printStackTrace(new PrintWriter(sw));
		stackTrace = sw.toString();
	}

	public void printStackTrace(PrintStream ps) {
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("user.dir")).append(File.separator)
				.append(_file).append(" for configuration not found. \n");
		synchronized (ps) {
			ps.print("\n" + sb.toString());
			ps.print(stackTrace);
		}
	}

	public void printStackTrace() {
		printStackTrace(System.out);
	}
}
