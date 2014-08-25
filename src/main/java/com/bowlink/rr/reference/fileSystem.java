package com.bowlink.rr.reference;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class fileSystem {

    //Get the operating system
    String os = System.getProperty("os.name").toLowerCase();

    String dir = null;

    String macDirectoryPath = "/Applications/rapidRegistry/";
    String winDirectoryPath = "c:\\rapidRegistry\\";
    String unixDirectoryPath = "/home/rapidRegistry/";

    public String getDir() {
        return dir;
    }
   
    public void setDirByName(String dirName) {
        //Windows
        if (os.indexOf("win") >= 0) {
            this.dir = winDirectoryPath + dirName;
        } //Mac
        else if (os.indexOf("mac") >= 0) {
            this.dir = macDirectoryPath + dirName;
        } //Unix or Linux or Solarix
        else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0 || os.indexOf("sunos") >= 0) {
            this.dir = unixDirectoryPath + dirName;
        }
    }

    public static void delete(File file) throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                }
            }

        } else {
            //if file, then delete it
            file.delete();
        }
    }

    /**
     * The checkFileDelimiters function will check to make sure the file contains data separated by the delimiter chosen when uploading the file.
     *
     * @param fileName	The name of the file uploaded
     * @param delim	The delimiter chosen when uploading the file
     *
     * @returns The function will return 0 if no delimiter was found or the count of the delimiter
     */
    public Integer checkFileDelimiter(fileSystem dir, String fileName, String delim) {

        int delimCount = 0;

        FileInputStream fileInput = null;
        try {
            File file = new File(dir.getDir() + fileName);
            fileInput = new FileInputStream(file);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInput));
        
        try {
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    if (delim == "t") {
                        delimCount = line.split("\t", -1).length - 1;
                    } else {
                        delimCount = line.split("\\" + delim, -1).length - 1;
                    }
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(fileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return delimCount;
    }
    
    public boolean isWinMachine() {
    	
    	boolean winMachine = false;
        //Windows
        if (os.indexOf("win") >= 0) {
        	winMachine =  true;
        } 
        return winMachine;
    }
    
    public String setPath(String addOnPath) {
    	String path = "";
    	//Windows
        if (os.indexOf("win") >= 0) {
        	path = winDirectoryPath.replace("\\bowlink\\", "") + addOnPath.replace("", "").replace("/", "\\");  
        } //Mac
        else if (os.indexOf("mac") >= 0) {
        	path = macDirectoryPath.replace("/bowlink/", "") + addOnPath;
        } //Unix or Linux or Solarix
        else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0 || os.indexOf("sunos") >= 0) {
        	path = unixDirectoryPath.replace("/bowlink/", "") + addOnPath;        
        }
        return path;
    }

    
    public String setPathFromRoot(String addOnPath) {
    	
    	String path = "";
    	
    	//Windows
        if (os.indexOf("win") >= 0) {
        	path = addOnPath.replace("", "").replace("/", "\\");  
        } //Mac
        else if (os.indexOf("mac") >= 0) {
        	path = addOnPath;
        } //Unix or Linux or Solarix
        else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0 || os.indexOf("sunos") >= 0) {
        	path = addOnPath;        
        }
        return path;
    }
    
    public Integer checkFileDelimiter(File file, String delim) {

        int delimCount = 0;

        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInput));
        
        try {
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    if (delim == "t") {
                        delimCount = line.split("\t", -1).length - 1;
                    } else {
                        delimCount = line.split("\\" + delim, -1).length - 1;
                    }
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(fileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return delimCount;
    }

      
}