package step.learning.android_spd_111;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.function.BiFunction;

public class CalcActivity extends AppCompatActivity {
    private TextView tvHistory;
    private TextView tvResult;
    private Calculator calculator;
    private boolean isOperation = false; //чи проводиться арифмитична операція на даний момент
    private String operationString = "";

    @SuppressLint("DiscouragedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvHistory = findViewById( R.id.calc_tv_history );
        tvResult = findViewById( R.id.calc_tv_result );
        if( savedInstanceState == null ) {
            tvResult.setText("0");
        }
        for (int i = 0; i < 10; i++) {
            findViewById(
                    getResources().getIdentifier(
                            "calc_btn_" + i,
                            "id",
                            getPackageName()
                    )
            ).setOnClickListener(this::onDigitButtonClick);
        }


        // Мат. операції
        calculator = new Calculator();

        findViewById(R.id.calc_tv_result).setOnTouchListener( new onSwipeListener(this, 10, 20) {
            @Override
            public void onSwipeRight() {
                Backspace();
            }

        } );

        Button btnInverse = (Button)findViewById( R.id.calc_btn_inverse );
        if( btnInverse != null ) btnInverse.setOnClickListener(this::onInverseClick);


        Button btnBackspace = (Button) findViewById(R.id.calc_btn_backspase);
        if( btnBackspace != null ) btnBackspace.setOnClickListener(this::BackspaceViewClick);

        Button btnAdd = (Button) findViewById(R.id.calc_btn_add);
        if( btnAdd != null ) btnAdd.setOnClickListener(this::onAddClick);

        Button btnSubtract = (Button) findViewById(R.id.calc_btn_minus);
        if( btnSubtract != null ) btnSubtract.setOnClickListener(this::onSubtractClick);

        Button btnDivision = (Button) findViewById(R.id.calc_btn_division);
        if( btnDivision != null ) btnDivision.setOnClickListener(this::onDivisionClick);

        Button btnMultiplication = (Button) findViewById(R.id.calc_btn_multiplication);
        if( btnMultiplication != null ) btnMultiplication.setOnClickListener(this::onMultiplicationClick);

        Button btnResult = (Button) findViewById(R.id.calc_btn_result);
        if( btnResult != null ) btnResult.setOnClickListener(this::onResultClick);

        Button btnChangeSign = (Button) findViewById(R.id.calc_btn_plus_minus);
        if( btnChangeSign != null ) btnChangeSign.setOnClickListener(this::onChangeSignClick);


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence( "tvResult", tvResult.getText() );
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvResult.setText( savedInstanceState.getCharSequence( "tvResult" ) );

    }

    private  void onDigitButtonClick(View view) {
        if ( isOperation ) {
            tvHistory.setText( calculator.getCurrentValueString() + " " + operationString + " ___ " );
            tvResult.setText("0");
            isOperation = false;
        }

        String result = tvResult.getText().toString();
        String buttonString = ((Button) view).getText().toString();
        if(result.length() >= 50) {
            Toast.makeText(this, R.string.calc_limit_exceeded, Toast.LENGTH_SHORT).show();
            return;
        }

        int buttonNumber = Integer.parseInt(buttonString);
        double resultNumber = result.isEmpty() ? 0 : Double.parseDouble(result);

        if (buttonNumber != 0 && resultNumber == 0) tvResult.setText(buttonString);
        else if (buttonNumber == 0 && resultNumber == 0) tvResult.setText("0");
        else tvResult.setText(result + buttonString);

    }

    private void onInverseClick(View view) {

        String result = tvResult.getText().toString();
        calculator.setCurrentValueString(result);
        try {
            calculator.inverse();
        } catch (ArithmeticException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        tvHistory.setText(result);
        tvResult.setText( calculator.getCurrentValueString() );
        isOperation = true;

    }

    private void onAddClick( View view ) {
        CalculatorOperationView("+", (currentNum, inputNum) -> currentNum + " + ___ "  );
    }
    private void onSubtractClick( View view ) {
        CalculatorOperationView("-", (currentNum, inputNum) -> currentNum + " - ___ "  );
    }
    private void onDivisionClick( View view ) {
        CalculatorOperationView("/", (currentNum, inputNum) -> currentNum + " / ___ "  );
    }
    private void onMultiplicationClick( View view ) {
        CalculatorOperationView("*", (currentNum, inputNum) -> currentNum + " * ___ "  );
    }
    private void onResultClick( View view ) {
        String operation = operationString;
        CalculatorOperationView("=", (currentNum, inputNum) -> currentNum + " " + operation + " " + inputNum );
    }
    private void onChangeSignClick( View view ) {
        CalculatorOperationView("+/-", (currentNum, inputNum) -> inputNum );
    }
    private void BackspaceViewClick(View view) {
        Backspace();
    }
    private void Backspace() {
        String result = tvResult.getText().toString();
        String str = "0";
        if(result.length() > 1) {
            String numberStr = result;
            if (numberStr != null && numberStr.length() > 0) {
                numberStr = numberStr.substring(0, numberStr.length() - 1);
            }
            str = numberStr;
        }
        tvResult.setText(str);
    }


    private void CalculatorOperation ( String operation, String value ) {
        String operationTemp = operation;
        switch ( operation.trim() ) {
            case "+": case "-": case "/": case "*":

                if( isOperation ) {
                    operationString = operation;
                    tvHistory.setText( calculator.getCurrentValueString() + " " + operationString + " ___ " );
                    return;
                }

                isOperation = true;

                if( operationString.isEmpty() ) {
                    operationString = operation;
                    calculator.setCurrentValueString( value );
                    return;
                }
                operationTemp = operationString;
                operationString = operation;
                break;
            case "+/-":
                calculator.setCurrentValue( Double.parseDouble(value) );
                operationTemp = "+/-";
                operationString = "";
                break;
            case "=":
                operationTemp = operationString;
                operationString = "";
                break;


        }


        switch ( operationTemp.trim() ) {
            case "+":
                calculator.add( Double.parseDouble( value ) );
                break;
            case "-":
                calculator.subtract( Double.parseDouble( value ) );
                break;
            case "/":
                calculator.divide( Double.parseDouble( value ) );
                break;
            case "*":
                calculator.multiply( Double.parseDouble( value ) );
                break;
            case "+/-":
                calculator.changeSign();
                break;

        }
    }

    private void CalculatorOperationView (String operation, BiFunction<String, String, String> historySting ) {

        String result = tvResult.getText().toString();
        try {
            CalculatorOperation(operation, result);
            tvHistory.setText( historySting.apply( calculator.getCurrentValueString(), result) );
        } catch (ArithmeticException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        tvResult.setText( calculator.getCurrentValueString() );
    }

}