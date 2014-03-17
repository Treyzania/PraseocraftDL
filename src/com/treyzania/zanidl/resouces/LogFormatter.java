package com.treyzania.zanidl.resouces;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

	@Override
	public String format(LogRecord r) {
		
		Date date = new Date(r.getMillis());
		String[] srcPkg = r.getSourceClassName().split("\\."); // Won't work properly for sub-classes.  Who the hell cares?
		
		return (date.toString() + "[" + r.getLoggerName() + "][" + srcPkg[srcPkg.length - 1] + ":" + r.getSourceMethodName() + "][" + r.getLevel() + "]" + r.getMessage() + "\n");
		
	}

}
