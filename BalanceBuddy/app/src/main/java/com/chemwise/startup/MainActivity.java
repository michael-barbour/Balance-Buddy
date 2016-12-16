/**
 *
 *Robert Newkirk 4/22/16
 *Most of the code was auto-generated
 */


package com.chemwise.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Robert Newkirk
     * 4/22/2016
     * passed here when a button is selected from the content_main.xml using android:onClick"?".
     */
    public void RedoxReactionScreen(View view) {

        Intent RedoxReaction = new Intent(this, Redox.class);  // Redox here is the name of the second page
        startActivity(RedoxReaction);
    }
    /**
     * Robert Newkirk
     * 4/22/2016
     * passed here when a button is selected from the content_main.xml using android:onClick"?".
     */
    public void GasLawScreen(View view) {

        Intent gasLaw = new Intent(this, GasLaw.class);  // Redox here is the name of the second page
        startActivity(gasLaw);
    }
    /**
     * Robert Newkirk
     * 4/22/2016
     * passed here when a button is selected from the content_main.xml using android:onClick"?".
     */
    public void ConversionScreen(View view) {

        Intent conversion = new Intent(this, Conversion.class);  // Redox here is the name of the second page
        startActivity(conversion);
    }
    /**
     * Robert Newkirk
     * 4/22/2016
     * passed here when a button is selected from the content_main.xml using android:onClick"?".
     */
    public void BalancerScreen(View view) {

        Intent balancer = new Intent(this, FormulaBalancerScreen.class);  // Redox here is the name of the second page
        startActivity(balancer);
    }
    /**
     * Robert Newkirk
     * 4/22/2016
     * passed here when a button is selected from the content_main.xml using android:onClick"?".
     */
    public void PeriodicImageScreen(View view) {

        Intent balancer = new Intent(this, PeriodicTable.class);  // Redox here is the name of the second page
        startActivity(balancer);
    }

}
