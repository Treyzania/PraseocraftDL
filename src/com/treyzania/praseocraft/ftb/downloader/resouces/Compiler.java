package com.treyzania.praseocraft.ftb.downloader.resouces;

import com.treyzania.praseocraft.ftb.downloader.parsing.FileOp;
import com.treyzania.praseocraft.ftb.downloader.parsing.OpList;

@Deprecated
public class Compiler {

	public static OpList theFiles;
	
	public static int mode = -1; // 0 = Client, 1 = Server
	
	public static void compile() {
		
		//theFiles = Parser.parseXML(PCDL.address);
		
	}
	
	public static void download() {
		
		int nextPriorityLevel = 0;
		int filesInLastLoop = -1;
		
		while (true) {
			
			if (filesInLastLoop == 0) {
				break;
			}
			
			OpList files = new OpList(theFiles.getFilesWithPriority(nextPriorityLevel));
			nextPriorityLevel++;
			
			filesInLastLoop = files.files.size();
			
			for (FileOp file : files.files) {
				
				if (mode == 0 && file.isClient()) {
					
				} else if (mode == 1 && file.isServer()) {
					
				}
				
			}
			
		}
		
	}
	
	public static void install() {
		
	}
	
}
