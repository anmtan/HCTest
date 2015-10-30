import java.io.Serializable;

import org.json.simple.JSONObject;

@SuppressWarnings("serial")
public class Tweet implements Serializable{
	private String idString;
	private String text;
	private JSONObject object;
	

	public Tweet(String idString, String text, JSONObject object){
		this.idString = idString;
		this.text = text;
		this.object = object;
	}
	
	public String getIdString() {
		return idString;
	}
	
	public void setIdString(String idString) {
		this.idString = idString;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public JSONObject getObject() {
		return object;
	}

	public void setObject(JSONObject object) {
		this.object = object;
	}


	@Override
	public String toString() {
		return "Tweet [idString=" + idString + ", text=" + text + "]";
	}
}
