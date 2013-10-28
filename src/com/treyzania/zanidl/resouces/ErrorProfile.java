package com.treyzania.zanidl.resouces;

import java.util.ArrayList;

public class ErrorProfile {
	
	public ArrayList<String> errors;
	public ArrayList<StackTrace> traces;
	
	public ErrorProfile() {
		
		this.errors = new ArrayList<String>();
		this.traces = new ArrayList<StackTrace>();
		
	}
	
	public void addMessage(String message) {
		errors.add(message);
	}
	
	public void addException(Exception e) {
		traces.add(new StackTrace(e));
	}
	
	public static class StackTrace {
		
		public final String message;
		public final StackTraceElement[] trace;
		
		public StackTrace(String msg, StackTraceElement[] trace) {
			
			this.message = msg;
			this.trace = trace;
			
		}
		
		public StackTrace(Exception e) {
			this(e.getMessage(), e.getStackTrace());
		}
		
	}
	
}
