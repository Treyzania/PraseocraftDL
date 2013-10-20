package com.treyzania.praseocraft.ftb.downloader.resouces;

import java.util.Arrays;

public enum Domain {

	GENERIC,
	CILENT,
	SERVER;
	
	public static class Calc {
		
		public static String[] domains = new String[] {"generic", "client", "server"};
		
		public static boolean isClient(Domain dom) {
			
			if (dom == Domain.GENERIC || dom == Domain.CILENT) {
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
				d = Domain.CILENT;
			}
			
			return d;
			
		}
		
	}
	
}
