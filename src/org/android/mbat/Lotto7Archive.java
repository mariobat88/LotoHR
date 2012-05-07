package org.android.mbat;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Lotto7Archive extends ListActivity{

	ArrayList<String> kolo = new ArrayList<String>();
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> numbers = new ArrayList<String>();
	ArrayList<String> super7 = new ArrayList<String>();
	
	ProgressDialog prbarDialog;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lotto7archive_list);

		Lotto7ArchiveAsync async = new Lotto7ArchiveAsync(this);
		async.execute("https://www.lutrija.hr/cms/Loto7Arhiva");
	}
	
	public class Lotto7ArchiveAsync extends AsyncTask<String, Void, Void> {

		
		Context context;
		String[] message = null;
		
		
		public Lotto7ArchiveAsync(Context context) {
			this.context = context;
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
		protected Void doInBackground(String... urls) {
			int i = 0;
			
			for(String url : urls){
				try {
					Document doc = Jsoup.connect(url).get();
					Elements table = doc.getElementsByTag("table");
					Element myTable = table.get(1);
					Elements tags = myTable.getElementsByTag("td");
					
					while(i < tags.size()){
						kolo.add(tags.get(i).text());
						date.add(tags.get(i+1).text());
						numbers.add(tags.get(i+2).text());
						super7.add(tags.get(i+3).text());
						i+=8;
					}
					
					message = new String[kolo.size()];
					for(int j = 0; j < kolo.size(); j++){
						message[j] = kolo.get(j) + " kolo odigrano " + date.get(j) + "2012.";
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.lotto7archive_row, message);
			setListAdapter(adapter);
			prbarDialog.dismiss();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, Lotto7.class);
		
		String[] number = new String[20];
		String[] lottoNumbers = new String[7];
		String[] dopunski = new String[5];
		number = numbers.get(position).split(" ");
		
		for(int j = 0; j < 7; j++){
			lottoNumbers[j] = number[j];
		}
		
		dopunski = number[7].split("\\(");
		dopunski = dopunski[1].split("\\)");
		
		i.putExtra("kolo", kolo.get(position));
		i.putExtra("date", date.get(position));
		i.putExtra("numbers", lottoNumbers);
		i.putExtra("dopunski", dopunski[0]);
		i.putExtra("super7", super7.get(position)); 
		
		startActivity(i);
	}
	
}
