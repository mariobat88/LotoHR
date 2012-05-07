package org.android.mbat;

import android.app.ListActivity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ConnectivityManager connection = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		
		if(connection != null && (connection.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) || (connection.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){
			
			setContentView(R.layout.main);
			String[] rows = new String[]{"Loto 7/39","Loto 6/45"};	
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.main_row, rows);
			setListAdapter(adapter);
			
		}else if(connection.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED || connection.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED){
			Toast.makeText(this, "Morate biti spojeni na internet", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = null;
		switch(position){
		case 0:
			i = new Intent(this, Lotto7.class);
			startActivity(i);
			break;
		case 1:
			i = new Intent(this, Lotto6.class);
			startActivity(i);
			break;
		}
	}
}
