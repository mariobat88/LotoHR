package org.android.mbat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class Lotto7 extends Activity {
    String[] lottoNumber = new String[20];
    Bundle extras = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_info);
        
        
        TextView dateLabel = (TextView) findViewById(R.id.lastLottoDate);
        EditText lottoNumbers = (EditText) findViewById(R.id.lottoOfToday);
        EditText lastNumber = (EditText) findViewById(R.id.editLastNumber);
        TextView lblJoker = (TextView) findViewById(R.id.lblJoker);
        EditText jokerNumber = (EditText) findViewById(R.id.editJokerNumber);
        EditText super7Number = (EditText) findViewById(R.id.editSuper7Number);

        
        Bundle extras = getIntent().getExtras();
    	if(extras != null){
    		String kolo = null;
    		String date = null;
    		String[] numbers = new String[6];
    		String dopunski = null;
    		String super7 = null;
    		
    		kolo = extras.get("kolo").toString();
    		date = extras.getString("date").toString();
    		numbers = extras.getStringArray("numbers");
    		dopunski = extras.getString("dopunski").toString();
    		super7 = extras.getString("super7").toString();
    		
    		dateLabel.setText(kolo + " kolo odigrano " + date + "2012.");
    		
    		for(int i = 0; i < numbers.length; i++){
    			lottoNumbers.append(numbers[i] + " ");
    		}
    		
    		lastNumber.setText(dopunski);
    		super7Number.setText(super7);
    		lblJoker.setVisibility(TextView.GONE);
    		jokerNumber.setVisibility(EditText.GONE);
    		
    	}else{
	        Lotto7CurrentAsync task = new Lotto7CurrentAsync(this, dateLabel, lottoNumbers, lastNumber, jokerNumber, super7Number);
	        task.execute("https://www.lutrija.hr/cms/loto7od39");
    	}
                
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater mi = getMenuInflater();
    	mi.inflate(R.menu.lotto_7_options_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch(item.getItemId()){
    	
    	case R.id.lotto_7_archive:
    		Intent i = new Intent(Lotto7.this, Lotto7Archive.class);
			startActivity(i);
    	}
    	return true;
    	
    }
}