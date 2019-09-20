package edu.iastate.netid.pocketcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    /**
     * The instance of the calculation stream model for use by this controller.
     */
    private CalculationStream mCalculationStream = new CalculationStream();

    /*
     * The instance of the calculator display TextView. You can use this to update the calculator display.
     */
    private TextView mCalculatorDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO - uncomment below after layout has been made.
        // Locate the instance of the calculator display TextView.  Don't forget to set the ID in the layout file.
        mCalculatorDisplay = findViewById(R.id.CalculatorDisplay);

    }

    // TODO - add event listeners for your calculator's buttons. See the model's API to figure out
    // what methods should be called. The equals button event listener has been done for you. Once
    // you've created the layout, you'll need to add these methods as the onClick() listeners
    // for the corresponding buttons in the XML layout.

    public void onEqualClicked(View view) {
        try {
            mCalculationStream.calculateResult();
        } finally {
            updateCalculatorDisplay();
        }
    }

    public void onDigitClicked(View view) {
        try {

            String clicked = ((TextView) view).getText().toString();

            switch (clicked){
                case "1":
                    mCalculationStream.inputDigit(CalculationStream.Digit.ONE);
                    break;
                case "2":
                    mCalculationStream.inputDigit(CalculationStream.Digit.TWO);
                    break;
                case "3":
                    mCalculationStream.inputDigit(CalculationStream.Digit.THREE);
                    break;
                case "4":
                    mCalculationStream.inputDigit(CalculationStream.Digit.FOUR);
                    break;
                case "5":
                    mCalculationStream.inputDigit(CalculationStream.Digit.FIVE);
                    break;
                case "6":
                    mCalculationStream.inputDigit(CalculationStream.Digit.SIX);
                    break;
                case "7":
                    mCalculationStream.inputDigit(CalculationStream.Digit.SEVEN);
                    break;
                case "8":
                    mCalculationStream.inputDigit(CalculationStream.Digit.EIGHT);
                    break;
                case "9":
                    mCalculationStream.inputDigit(CalculationStream.Digit.NINE);
                    break;
                case "0":
                    mCalculationStream.inputDigit(CalculationStream.Digit.ZERO);
                    break;
                case ".":
                    mCalculationStream.inputDigit(CalculationStream.Digit.DECIMAL);
                    break;

            }
        } finally {
            updateCalculatorDisplay();
        }
    }

    public void onOperandClicked(View view){

        try{

            String operand = ((TextView) view).getText().toString();

            switch (operand){
                case "+":
                    mCalculationStream.inputOperation(CalculationStream.Operation.ADD);
                    break;
                case "/":
                    mCalculationStream.inputOperation(CalculationStream.Operation.DIVIDE);
                    break;
                case "-":
                    mCalculationStream.inputOperation(CalculationStream.Operation.SUBTRACT);
                    break;
                case "*":
                    mCalculationStream.inputOperation(CalculationStream.Operation.MULTIPLY);
                    break;

            }
        } finally {
            updateCalculatorDisplay();
        }
    }

    public void onClearClicked(View view){
        try{
            mCalculationStream.clear();
        } finally {
            updateCalculatorDisplay();
        }
    }


    /**
     * Call this method after every button press to update the text display of your calculator.
     */
    public void updateCalculatorDisplay() {
        String value = getString(R.string.zero);
        try {
            value = Double.toString(mCalculationStream.getCurrentOperand());
        } catch(NumberFormatException e) {
            value = getString(R.string.error);
        } finally {
            mCalculatorDisplay.setText(value);
        }
    }

    //TODO - any other methods you want to use related to the UI
}
