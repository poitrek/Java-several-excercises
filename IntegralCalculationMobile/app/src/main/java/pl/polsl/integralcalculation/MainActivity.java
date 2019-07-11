package pl.polsl.integralcalculation;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.polsl.model.IntegralModel;
import pl.polsl.exceptions.PointArrayException;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {


    private IntegralModel model;
    private EditText nodeEditText;
    private EditText valueEditText;
    private Button calculateButton;

    private MainActivity getActivity () {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        model = new IntegralModel();

        nodeEditText = (EditText) findViewById(R.id.xCoords);
        valueEditText = (EditText) findViewById(R.id.yCoords);

        calculateButton = (Button)findViewById(R.id.calcButton);
        calculateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                try {
                    String nodeString = nodeEditText.getText().toString();
                    String valueString = valueEditText.getText().toString();

                    // Split both strings by spaces to arrays
                    String[] nodesStringSplit = nodeString.split("\\s");
                    String[] valuesStringSplit = valueString.split("\\s");
                    nodesStringSplit = nodeString.trim().split("\\s+");
                    valuesStringSplit = valueString.trim().split("\\s+");

                    if (nodesStringSplit.length != valuesStringSplit.length) {
                        throw new PointArrayException("X-coords and Y-coords aren't the same size!");
                    }

                    if (nodesStringSplit.length == 0) {
                        throw new PointArrayException("No values has been entered!");
                    }

                    // Parse arrays to double and set to model
                    Vector<Double> nodes = new Vector<>();
                    Vector<Double> values = new Vector<>();

                    for (int i = 0; i < nodesStringSplit.length; i++) {
                        nodes.add(Double.parseDouble(nodesStringSplit[i]));
                        values.add(Double.parseDouble(valuesStringSplit[i]));
                    }

                    model.setPoints(nodes, values);

                    model.calculateIntegral();

                    String toastResultMessage = "The integral equals " + model.getResult();
                    Toast.makeText(getActivity(), toastResultMessage, Toast.LENGTH_LONG).show();

                }
                catch (PointArrayException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Wrong input data - should be floating point numbers!", Toast.LENGTH_LONG).show();
                }

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
