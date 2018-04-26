package blueground_challenge;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;

import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class json_getter {
	public static 			  String[][] arr=new String[5][2];

	
		  
		  public static void main(String[] args) throws IOException, JSONException {
			  table_init();
		    JSONObject obj = JsonFromUrl("http://api.wunderground.com/api/e5283805958e1379/history_20171030/q/NY/New_York.json");
		    File file = new File("raw_file.txt");
		    try (FileWriter filew = new FileWriter(file)) {
				filew.write(obj.toString());
			}
		    JSONObject response  = obj.getJSONObject("history");
		    JSONArray array  = response.getJSONArray("dailysummary");
		    JSONObject first = array.getJSONObject(0);
		    String maxtemptm = first.getString("maxtempm");
		    String maxhumidity = first.getString("maxhumidity");
		    String mintempm = first.getString("mintempm");
		    String precipm = first.getString("precipm");
		    arr[1][1]=maxhumidity.toString();
			arr[2][1]=maxtemptm.toString();
			arr[3][1]=mintempm.toString();
			arr[4][1]=precipm.toString();
		    //System.out.println(maxtemptm+"\n"+ maxhumidity +"\n"+ mintempm+"\n"+precipm+"\n");	
		    //p_printer();
		    TextFileWrite();
		  }
		  private static String readAll(Reader rd) throws IOException {
			    StringBuilder sb = new StringBuilder();
			    int cp;
			    while ((cp = rd.read()) != -1) {
			      sb.append((char) cp);
			    }
			    return sb.toString();
			  }

			  public static JSONObject JsonFromUrl(String url) throws IOException, JSONException {
			    InputStream is = new URL(url).openStream();
			    try {
			      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			      String jsonText = readAll(rd);
			      JSONObject json = new JSONObject(jsonText);
			      return json;
			      
			    } finally {
			      is.close();
			    }
			  }
			  public static void table_init() {
				  arr[0][0]="Metric";
				  arr[0][1]="Value";
				  arr[1][0]="Max percentage Humidity";
				  arr[2][0]="Max Temp in C";
				  arr[3][0]="Min Temp in C";
				  arr[4][0]="Precipitation in mm";
			  }
			  			  
			  public static void TextFileWrite() {

				    try {
				        FileWriter writer = new FileWriter("output_table.txt", false);
				        for (int i = 0; i < arr.length; i++) {
				            for (int j = 0; j < arr[i].length; j++) {
				                  writer.write(arr[i][j]+" \t | \t");
				            }
				            writer.write("\n"); 
				        }
				        writer.close();
				        System.out.println("Output file created!");
				        Component dialogw = null;
						JOptionPane.showMessageDialog(dialogw, "Output file with data for 30/10/2017 weather in New York, created!","Blueground Challenge",JOptionPane.PLAIN_MESSAGE);
				    } catch (IOException e) {
				        e.printStackTrace();
				    }

				}
}
