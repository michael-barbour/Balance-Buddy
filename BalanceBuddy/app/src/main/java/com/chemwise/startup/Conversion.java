/**
 * Robert Newkirk and Michael Barbour
 * 4/22/2016
 * java class for the conversion screen
 *
 */

package com.chemwise.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import UnitConverter.*;


public class Conversion extends AppCompatActivity {


    private Spinner BaseUnitSpinner;
    private Spinner ConvertedUnitSpinner;
    private Spinner ConversionSelectorTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //creates an array of the conversion types form values folder: file name Conversions_Selector_Types
        //and passes it to the setConversionTypes function
        setConversionTypes(R.array.Conversions_Selector_Types);
        addListenerToConversionSpinnerType();

        //sets some initial values in the BaseUnit_Convert_Spinner spinner on page open. creates an array of the conversion types form values folder: file name Mass_Conversion_Types
        //and passes it to the addItemToTempSpinner function
        addItemToTempSpinner(R.array.Mass_Conversion_Types);  // passes the xml conversion types the the spinner
        addListenerToTempSpinner();

        //sets some initial values in the CovertTO_Unit_Spinner spinner on page open. creates an array of the conversion types form values folder: file name Mass_Conversion_Types
        //and passes it to the addItemToTempSpinner function
        addItemToConvertedUnitSpinner(R.array.Mass_Conversion_Types);  //
        addListenerToConvertedUnitSpinner();

        onCreateCalculateButton();

    }

    /**
     * @param ConversionSelector
     * Robert Newkirk
     * 4/22/2016
     * populates the spinner from the Conversions_Selector_Types array passed in, and set an adapter for it.
     */
    public void setConversionTypes(int ConversionSelector) {

        ConversionSelectorTypeSpinner = (Spinner) findViewById(R.id.Spinner_Pick_Types);  // gets the conversion types the user chooses

        ArrayAdapter<CharSequence> Conversion_Adapter =
                ArrayAdapter.createFromResource(this,
                        ConversionSelector,
                        android.R.layout.simple_spinner_item);  // creates a spinner array

        Conversion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  // sets it as a dropdown

        ConversionSelectorTypeSpinner.setAdapter(Conversion_Adapter);

    }


    /**
     * Robert Newkirk and Michael Barbour
     * 4/22/2016
     * listeners for the spinners,  grabs the text form the Spinner_Pick_Types spinner and populates the
     * BaseUnit_Convert_Spinner and the CovertTO_Unit_Spinner with the correct values for the type.
     */
    public void addListenerToConversionSpinnerType() {

        ConversionSelectorTypeSpinner = (Spinner) findViewById(R.id.Spinner_Pick_Types);

        ConversionSelectorTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected_In_Conversion_Spinner = parent.getItemAtPosition(position).toString();

                if(itemSelected_In_Conversion_Spinner.equals("Temp")){
                    addItemToTempSpinner(R.array.Temp_Conversion_Types);  // passes the xml conversion types the the spinner
                    addListenerToTempSpinner();

                    addItemToConvertedUnitSpinner(R.array.Temp_Conversion_Types);  //
                    addListenerToConvertedUnitSpinner();


                }else if(itemSelected_In_Conversion_Spinner.equals("Mass")) {

                    addItemToTempSpinner(R.array.Mass_Conversion_Types);  // passes the xml conversion types the the spinner
                    addListenerToTempSpinner();

                    addItemToConvertedUnitSpinner(R.array.Mass_Conversion_Types);  //
                    addListenerToConvertedUnitSpinner();

                }else if(itemSelected_In_Conversion_Spinner.equals("Length")) {

                    addItemToTempSpinner(R.array.Length_Conversion_Types);  // passes the xml conversion types the the spinner
                    addListenerToTempSpinner();

                    addItemToConvertedUnitSpinner(R.array.Length_Conversion_Types);  //
                    addListenerToConvertedUnitSpinner();

                }else if(itemSelected_In_Conversion_Spinner.equals("Volume")) {

                    addItemToTempSpinner(R.array.Volume_Conversion_Types);  // passes the xml conversion types the the spinner
                    addListenerToTempSpinner();

                    addItemToConvertedUnitSpinner(R.array.Volume_Conversion_Types);  //
                    addListenerToConvertedUnitSpinner();
                }

                //checkIfConvertingFrom(itemSelected_In_temp_Spinner);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing
            }
        });
    }

    /**
     * Robert Newkirk
     * 4/22/2016
     * listener for the BaseUnit_Convert_Spinner spinners
     */
    public void addListenerToTempSpinner() {

        BaseUnitSpinner = (Spinner) findViewById(R.id.BaseUnit_Convert_Spinner);

        BaseUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            // auto generated code
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected_In_temp_Spinner = parent.getItemAtPosition(position).toString();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing
            }
        });
    }

    /**
     * Robert Newkirk
     * 4/22/2016
     * listener for the CovertTO_Unit_Spinner spinners
     */
    public void addListenerToConvertedUnitSpinner(){
        ConvertedUnitSpinner = (Spinner) findViewById(R.id.CovertTO_Unit_Spinner);

        ConvertedUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected_In_temp_Spinner = parent.getItemAtPosition(position).toString();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing
            }
        });
    }

    /**
     * Robert Newkirk
     * 4/22/2016
     * Gets the user input and gets the Type form the Spinner_Pick_Types spinner. Then passing the values form
     * the spinners to the correct class for calculation.
     */

    public void onCreateCalculateButton(){
        Button ConversionCalculateButton = (Button) findViewById(R.id.ConversionCalculateButton);

        ConversionCalculateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String outputText;

                EditText baseUnitTB = (EditText) findViewById(R.id.Data_Convert_TextBox);  // gets the user input to be converted
                String baseUnitString = baseUnitTB.getText().toString();
                double baseUnit = Double.parseDouble(baseUnitString);

                Spinner baseUnitTypeSpinner = (Spinner) findViewById(R.id.BaseUnit_Convert_Spinner);  // this is the unit of the input before convert
                String baseUnitType = baseUnitTypeSpinner.getSelectedItem().toString();

                Spinner convUnitTypeSpinner = (Spinner) findViewById(R.id.CovertTO_Unit_Spinner);     // the unit to be converted to
                String convertedUnitType = convUnitTypeSpinner.getSelectedItem().toString();

                Spinner spinner = (Spinner)findViewById(R.id.Spinner_Pick_Types);
                String spinnerText = spinner.getSelectedItem().toString();

                // if Mass is in the Spinner_Pick_Types spinner pass the values to the MassConverter class for calculation
                if(spinnerText.equals("Mass")) {
                    double convertedUnit = MassConverter.calculate(baseUnit, baseUnitType, convertedUnitType); // sends the data to massConvert
                    outputText = String.valueOf(convertedUnit);
                    EditText text = (EditText) findViewById(R.id.Conversion_Menu_Title);
                    text.setText(outputText);
                }else if(spinnerText.equals("Temp")) {
                    double convertedUnit = TempConverter.calculate(baseUnit, baseUnitType, convertedUnitType); // sends the data to TempConverter
                    outputText = String.valueOf(convertedUnit);
                    EditText text = (EditText) findViewById(R.id.Conversion_Menu_Title);
                    text.setText(outputText);
                }else if(spinnerText.equals("Volume")) {
                    double convertedUnit = VolumeConverter.calculate(baseUnit, baseUnitType, convertedUnitType); // sends the data to VolumeConverter
                    outputText = String.valueOf(convertedUnit);
                    EditText text = (EditText) findViewById(R.id.Conversion_Menu_Title);
                    text.setText(outputText);
                }else if(spinnerText.equals("Length")) {
                    double convertedUnit = LengthConverter.calculate(baseUnit, baseUnitType, convertedUnitType); // sends the data to LengthConverter
                    outputText = String.valueOf(convertedUnit);
                    EditText text = (EditText) findViewById(R.id.Conversion_Menu_Title);
                    text.setText(outputText);
                }
            }
        });
    }

    /**
     * Robert Newkirk
     * 4/22/2016
     * populates the spinner from the BaseUnit_Convert_Spinner array passed in, and set an adapter for it.
     */
    public void addItemToTempSpinner(int baseUnitSpinnerArray) {

        BaseUnitSpinner = (Spinner)
                findViewById(R.id.BaseUnit_Convert_Spinner);  // gets the id of the base unit spinner on the page

        ArrayAdapter<CharSequence> Temp_Type_Conversion_Adapter =
                ArrayAdapter.createFromResource(this,
                        baseUnitSpinnerArray,
                        android.R.layout.simple_spinner_item);  // creates a spinner array

        Temp_Type_Conversion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  // sets it as a dropdown

        BaseUnitSpinner.setAdapter(Temp_Type_Conversion_Adapter); // populates


    }

    /**
     * Robert Newkirk
     * 4/22/2016
     * the convert types are passed in form onClick and populated the convertTO spinner with that xml file
     */

    public void addItemToConvertedUnitSpinner(int convertedUnitSpinnerArray){
        ConvertedUnitSpinner = (Spinner)
                findViewById(R.id.CovertTO_Unit_Spinner);

        ArrayAdapter<CharSequence> Temp_Type_Conversion_Adapter =
                ArrayAdapter.createFromResource(this,
                        convertedUnitSpinnerArray,
                        android.R.layout.simple_spinner_item);

        Temp_Type_Conversion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ConvertedUnitSpinner.setAdapter(Temp_Type_Conversion_Adapter);

    }


    /**
     * @param view
     * returns the use the the main screen if selected
     */
    public void returnToHome(View view) {

        Intent returnHome = new Intent(this, com.chemwise.startup.MainActivity.class);
        startActivity(returnHome);
    }
}