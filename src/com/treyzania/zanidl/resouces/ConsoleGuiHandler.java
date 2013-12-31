package com.treyzania.zanidl.resouces;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ConsoleGuiHandler extends Handler {

	public final ConsolePanel consoleContainer;
	
	public ConsoleGuiHandler(ConsolePanel console) {
		this.consoleContainer = console;
	}
	
	@Override
	public void close() throws SecurityException {
		// Nope.
	}

	@Override
	public void flush() {
		// Nope.
	}

	@Override
	public void publish(LogRecord lr) {
		
		consoleContainer.console.append(lr.getMessage() + "\n");
		
	}

}
