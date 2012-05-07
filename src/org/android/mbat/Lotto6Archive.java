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

public class Lotto6Archive extends ListActivity{

	ArrayList<String> kolo = new ArrayList<String>();
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> numbers = new ArrayList<String>();
	
	ProgressDialog prbarDialog;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lotto6archive_list);

		Lotto6ArchiveAsync async = new Lotto6ArchiveAsync(this);
		async.execute("https://www.lutrija.hr/cms/Loto6Arhiva");
	}
	
	public class Lotto6ArchiveAsync extends AsyncTask<String, Void, Void> {

		
		Context context;
		String[] message = null;
		
		
		public Lotto6ArchiveAsync(Context context) {
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
						i+=5;
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
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.lotto6archive_row, message);
			setListAdapter(adapter);
			prbarDialog.dismiss();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, Lotto6.class);
		
		String[] number = new String[20];
		String[] lottoNumbers = new String[6];
		String[] dopunski = new String[5];
		number = numbers.get(position).split(" ");
		
		for(int j = 0; j < 6; j++){
			lottoNumbers[j] = number[j];
		}
		
		dopunski = number[6].split("\\(");
		dopunski = dopunski[1].split("\\)");
		
		i.putExtra("kolo", kolo.get(position));
		i.putExtra("date", date.get(position));
		i.putExtra("numbers", lottoNumbers);
		i.putExtra("dopunski", dopunski[0]);
		
		startActivity(i);
	}
	
}