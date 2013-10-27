package com.treyzania.zanidl;

import java.util.Arrays;

public enum Domain {

	GENERIC,
	CLIENT,
	SERVER;
	
	public static class Calc {
		
		public static String[] domains = new String[] {"generic", "client", "server"};
		
		public static boolean isClient(Domain dom) {
			
			if (dom == Domain.GENERIC || dom == Domain.CLIENT) {
				return true;
			} else {
				return false;
			}
			
		}
		
		public static boolean isServer(Domain dom) {
			
			if (dom == Domain.GENERIC || dom == Domain.SERVER) {
				return true;
			} else {
				return false;
			}
			
		}
		
		public static boolean isCompatible(Domain model, Domain test) {
			
			boolean result = false;
			
			if (model == null || test == null) {
				throw new NullPointerException("One of the domains in the test was null.");
			}
			
			if (model == Domain.GENERIC || test == Domain.GENERIC) {
				result = true;
			}
			
			if (model == Domain.CLIENT && test == Domain.CLIENT) {
				result = true;
			}
			
			if (model == Domain.SERVER && test == Domain.SERVER) {
				result = true;
			}
			
			return result;
			
		}
		
		public static boolean isValid(String s) {
			
			return Arrays.asList(domains).contains(s);
			
		}
		
		public static Domain parseDomain(String s) {
			
			Domain d = null;
			String sl = s.toLowerCase();
			
			if (sl.contains("generic")) {
				d = Domain.GENERIC;
			}
			
			if (sl.contains("server")) {
				d = Domain.SERVER;
			}
			
			if (sl.contains("client")) {
				d = Domain.CLIENT;
			}
			
			return d;
			
		}
		
	}
	
}
