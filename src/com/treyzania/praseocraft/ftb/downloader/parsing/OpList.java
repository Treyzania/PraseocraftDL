package com.treyzania.praseocraft.ftb.downloader.parsing;

import java.util.ArrayList;

public class OpList {

	public ArrayList<FileOp> files;
	
	public OpList() {
		
		this.files = new ArrayList<FileOp>();
		
	}
	
	public OpList(ArrayList<FileOp> files) {
		
		this.files = files;
		
	}

	public void addFile(FileOp file) {
		
		files.add(file);
		
	}
	
	public ArrayList<FileOp> getFilesWithPriority(int p) {
		
		ArrayList<FileOp> list = new ArrayList<FileOp>();
		
		for (FileOp f : files) {
			
			if (f.priority == p) {
				list.add(f);
			}
			
		}
		
		return list;
		
	}
	
}
