package org.android.mbat;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

public class Lotto6CurrentAsync extends AsyncTask<String, Void, String[]>{
	TextView dateLabel = null;
	EditText lottoNumbers = null;
	EditText lastNumber = null;
	EditText jokerNumber = null;
	Context context = null;
	
	Elements numbersElements = null;
	Elements date = null;
	String numbers = null;
	String[] number = new String[20];
	String jokerText = null;
	String[] joker = new String[20];
	
	ProgressDialog prbarDialog;
	
	
	public Lotto6CurrentAsync(Context context, TextView dateLabel, EditText lottoNumbers, EditText lastNumber, EditText jokerNumber) {
		this.dateLabel = dateLabel;
		this.lottoNumbers = lottoNumbers;
		this.lastNumber = lastNumber;
		this.jokerNumber = jokerNumber;
		this.context = context;
	}
	
	@Override
	protected String[] doInBackground(String... urls) {
    	
    	for(String url : urls){
    		
    		try {
				Document doc = Jsoup.connect(url).get();
				Element element = doc.getElementById("winnings-info");
				numbersElements = element.getElementsByTag("li");
				
				numbers = numbersElements.text();
				//get lotto numbers and dopunski broj
				number = numbers.split(" ");
				
				Element dateElement = doc.getElementById("last-round-report-content");
				//get date
				date = dateElement.getElementsByTag("span");
				
				jokerText = element.getElementById("jocker-number").text();
				//joker number
				joker = jokerText.split(":");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return number;
	}
	
	@Override
	protected void onPreExecute() {

		prbarDialog = new ProgressDialog(context);
		prbarDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		prbarDialog.setMessage("Loading...");
		prbarDialog.setCancelable(false);
		prbarDialog.show();
	}
	
	@Override
	protected void onPostExecute(String[] result) {
		//set date
		dateLabel.setText(date.text());
		
		//set lotto numbers
		for(int i = 0; i < 6; i++){
        	lottoNumbers.append(result[i] + " ");
        }
		
		//set dopunski broj
		lastNumber.setText(result[6]);
		
		//set joker number
		jokerNumber.setText(joker[1]);

		prbarDialog.dismiss();
	}
	
}
