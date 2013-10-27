package com.treyzania.zanidl.jobbing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import argo.format.JsonFormatter;
import argo.format.JsonFormatter.FieldSorter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonField;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilders;
import argo.jdom.JsonNodeFactories;
import argo.jdom.JsonObjectNodeBuilder;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

import com.treyzania.zanidl.PackFile;
import com.treyzania.zanidl.ZaniDL;
import com.treyzania.zanidl.Util;

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
	
	@SuppressWarnings({ "resource", "rawtypes", "unchecked" })
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
			FileInputStream fis = new FileInputStream(lpjsonFile);
			lpjsonContent = new char[(int) lpjsonFile.length()];
			
			for (int i = 0; i < lpjsonContent.length; i++) {
				char in = (char) fis.read();
				lpjsonContent[i] = in;
			}
			
			fis = null;
			
		} catch (Exception e) {

			ZaniDL.log.severe(e.getMessage());
			out = false;

		}

		lpJson = new String(lpjsonContent); // Actually make it into a String.
		
		
		
		// Actually parse it!
		String profName = this.name;
		String profGameDir = Util.fs_sysPath(pf.generatePackPath());
		String profLastVersionId = pf.generateLauncherVersionName();
		String profJavaArgs = "-Dfml.ignoreInvalidMinecraftCertificates\u003dtrue";
		
		JdomParser JDOM_PARSER = new JdomParser();
		JsonRootNode json = null;
		
		try {
			json = JDOM_PARSER.parse(lpJson);
		} catch (InvalidSyntaxException e) {
			System.out.println("Error!  " + e);
			e.printStackTrace();
			out = false;
		}
		
		Map<JsonStringNode, JsonNode> profilesObj = json.getObjectNode("profiles");
		
		JsonField[] profFields = new JsonField[] {
				field("name", string(profName)),
				field("gameDir", string(profGameDir)),
				field("lastVersionId", string(profLastVersionId)),
				field("javaArgs", string(profJavaArgs))
				};
		
		// Just some copypasta... (Fufufufufuuuu...)
		HashMap profileCopy = new HashMap(json.getNode(new Object[] { "profiles" }).getFields());
		HashMap rootCopy = new HashMap(json.getFields());
		profileCopy.put(JsonNodeFactories.string(profName),JsonNodeFactories.object(profFields));
		JsonRootNode profileJsonCopy = JsonNodeFactories.object(profileCopy);
		
		rootCopy.put(string("profiles"), profileJsonCopy);
		
		JsonRootNode jsonProfileData = object(rootCopy);
		JsonFormatter jsonFormatter = PrettyJsonFormatter.fieldOrderPreservingPrettyJsonFormatter();
		String output = jsonFormatter.format(jsonProfileData);
		
		// Finally output it to file.
		try {
			FileOutputStream fos = new FileOutputStream(lpjsonFile);
			fos.write(output.getBytes());
		} catch (Exception e) {
			ZaniDL.log.warning(e.getMessage());
			out = false;
		}
		
		String newJsonContent = "";
		
		// Finally freaking return the value...
		return out;
		
	}
}
