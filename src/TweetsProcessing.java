import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class TweetsProcessing {
    
	public static void main(String[] args) {
        System.out.println("Start processing.");

        
        Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        Map<String, Tweet> tweetMap = instance.getMap("tweetMap");

        final File folder = new File("./data/");
        loadTweetsFromFolder(tweetMap, folder);
 
        System.out.println("Map Size:" + tweetMap.size());
        System.out.println("End processing.");
    }
	
	public static void loadTweetsFromFolder(Map<String, Tweet> tweetMap, final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            System.out.println("skipping folder " + fileEntry.getName());
	        } else {
	        	String file = fileEntry.getPath();
	            System.out.println("loadind tweet from file " + file);
	            loadTweets(tweetMap, file);
	            try {
	            	Files.move(Paths.get(file), Paths.get("./data/done/"+fileEntry.getName()));
	            }catch(IOException e){
	            	System.out.println("Unable to move file "  + e);
	            }
	        }
	    }
	}


	
    public static void loadTweets(Map<String, Tweet> tweetMap, String filename){

    	JSONParser parser = new JSONParser();
    	 
        try {
 
            Object obj = parser.parse(new FileReader(filename));
 
            JSONObject jsonObject = (JSONObject) obj;
 
            String idString = (String) jsonObject.get("id_str");
            String text = (String) jsonObject.get("text");
 
            System.out.println("idString: " + idString);
            System.out.println("text: " + text);

            Tweet tweet = new Tweet(idString, text, jsonObject);
            
            tweetMap.put(idString, tweet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
