/**
 * @author Michael Barbour and Robert Newkirk
 *
 * Android Screen for Formula Balancing.
 *
 */

package com.chemwise.startup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import FormulaBalancer.FormulaBalancer;

public class FormulaBalancerScreen extends AppCompatActivity {

    // Create an activity and add a listener to the Calculate button.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_balancer_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button calcButton = (Button) findViewById(R.id.calcButton);

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit = (EditText) findViewById(R.id.formulaInput);
                String formula = edit.getText().toString();

                FormulaBalancer a = new FormulaBalancer();

                String outFormula = a.balance(formula);

                TextView formOut = (TextView) findViewById(R.id.formulaOutput);

                System.out.println(outFormula);
                formOut.setText(outFormula);
            }
        });

    }

    @Override
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
}