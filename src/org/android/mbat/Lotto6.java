package org.android.mbat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class Lotto6 extends Activity{
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_info);
        
        
        TextView dateLabel = (TextView) findViewById(R.id.lastLottoDate);
        EditText lottoNumbers = (EditText) findViewById(R.id.lottoOfToday);
        EditText lastNumber = (EditText) findViewById(R.id.editLastNumber);
        EditText jokerNumber = (EditText) findViewById(R.id.editJokerNumber);
        TextView lblJoker = (TextView) findViewById(R.id.lblJoker);
        
        EditText super7Number = (EditText) findViewById(R.id.editSuper7Number);
        super7Number.setVisibility(EditText.GONE);
        TextView lblsuper7 = (TextView) findViewById(R.id.lblsuper7);
        lblsuper7.setVisibility(TextView.GONE);
    	
        Bundle extras = getIntent().getExtras();
    	if(extras != null){
    		String kolo = null;
    		String date = null;
    		String[] numbers = new String[5];
    		String dopunski = null;
    		
    		kolo = extras.get("kolo").toString();
    		date = extras.getString("date").toString();
    		numbers = extras.getStringArray("numbers");
    		dopunski = extras.getString("dopunski").toString();
    		
    		dateLabel.setText(kolo + " kolo odigrano " + date + "2012.");
    		
    		for(int i = 0; i < numbers.length; i++){
    			lottoNumbers.append(numbers[i] + " ");
    		}
    		
    		lastNumber.setText(dopunski);
    		super7Number.setVisibility(EditText.GONE);
    		lblJoker.setVisibility(TextView.GONE);
    		jokerNumber.setVisibility(EditText.GONE);
    		
    	}else{
	        Lotto6CurrentAsync task = new Lotto6CurrentAsync(this, dateLabel, lottoNumbers, lastNumber, jokerNumber);
	        task.execute("https://www.lutrija.hr/cms/Loto6od45");
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater mi = getMenuInflater();
    	mi.inflate(R.menu.lotto_6_options_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch(item.getItemId()){
    	
    	case R.id.lotto_6_archive:
    		Intent i = new Intent(this, Lotto6Archive.class);
			startActivity(i);
    	}
    	return true;
    	
    }

}
