package com.treyzania.praseocraft.ftb.downloader.jobbing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import argo.format.JsonFormatter;
import argo.format.JsonFormatter.FieldSorter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeFactories;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;

import com.treyzania.praseocraft.ftb.downloader.Util;
import com.treyzania.praseocraft.ftb.downloader.Pcdl;
import com.treyzania.praseocraft.ftb.downloader.PackFile;

import static argo.jdom.JsonNodeFactories.*;

@SuppressWarnings("unused")
public class JobAddLauncherProfile extends Job {

	public String name;
	public PackFile pf;
	
	public JobAddLauncherProfile(Joblist jl, String name, PackFile pf) {
		
		super(jl);
		
		this.name = name;
		this.pf = pf;
		
	}

	@SuppressWarnings("resource")
	@Override
	public boolean runJob() {
		
		boolean out = true;
		
		String lpjsonLoc = Util.getMinecraftDir() + "/launcher_profiles.json";
		
		File lpjsonFile = null;
		char[] lpjsonContent = "nope!".toCharArray();
		String lpJson;
		
		// Read the bytes!
		try {
			
			lpjsonFile = new File(lpjsonLoc);
			DataInputStream dis = new DataInputStream(new FileInputStream(lpjsonFile));
			lpjsonContent = new char[(int) lpjsonFile.length()];
			
			for (int i = 0; i < lpjsonContent.length; i++) {
				lpjsonContent[i] = dis.readChar();
			}
			
			dis = null;
			
		} catch (Exception e) {
			
			Pcdl.log.severe(e.getMessage());
			out = false;
			
		} finally {
			
			lpJson = new String(lpjsonContent); // Actually make it into a String.
			
		}
		
		// Actually parse it!
		String profName = pf.metadata.access("PackName") + "-" + pf.packVer;
		String profGameDir = Util.getPCDLDir() + "/" + profName;
		String profLastVersionId = pf.generateVersionName();
		int profResX = 1280;
		int profResY = 720;
		
		JdomParser JDOM_PARSER = new JdomParser();
		JsonRootNode json = null;
		
		try {
			json = JDOM_PARSER.parse(lpJson);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
			out = false;
		}
		
		List<JsonNode> totalJson = json.getElements();
		
		JsonNode newNode = object(
				field("name", string(profName)),
				field("gameDir", string(profGameDir)),
				field("lastVersionId", string(profLastVersionId)),
				field("resolution", object(
						field("width", number(profResX)),
						field("height", number(profResY)))),
				field("launcherVisibilityOnGameClose", string("keep the launcher open"))
				);
		
		totalJson.add(newNode); // FINALLY!
		
		JsonRootNode newJRN = JsonNodeFactories.array((JsonNode[]) totalJson.toArray());
		JsonFormatter jsonFormatter = new PrettyJsonFormatter();
		String output = jsonFormatter.format(newJRN);
		
		// Finally output it to file. 
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(lpjsonFile));
			dos.writeUTF(output);
		} catch (Exception e) {
			Pcdl.log.warning(e.getMessage());
			out = false;
		}
		
		String newJsonContent = "";
		
		// Finally freaking return the value...
		return out;
		
	}
	
}
