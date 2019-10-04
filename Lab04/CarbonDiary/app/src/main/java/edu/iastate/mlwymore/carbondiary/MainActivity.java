package edu.iastate.mlwymore.carbondiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements Observer<CarbonDiary>, AdapterView.OnItemSelectedListener {

    final String TAG = "MainActivity";

    private CarbonDiaryViewModel carbonDiaryViewModel;
    private String newEntryType = "";
    private double newEntryAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carbonDiaryViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(CarbonDiaryViewModel.class);
        carbonDiaryViewModel.carbonDiary.observe(this, this);

        Spinner typeSpinner = findViewById(R.id.new_entry_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                CarbonDiaryEntry.getEntryTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);

        EditText amountEdit = findViewById(R.id.new_entry_amount);
        amountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    newEntryAmount = Double.parseDouble(charSequence.toString());
                } catch(Exception e) {
                    newEntryAmount = 0.0;
                }
                Log.d(TAG, "onTextChanged called");
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    public void addEntry(View view) {
        carbonDiaryViewModel.addEntry(newEntryType, newEntryAmount);
    }

    @Override
    public void onChanged(CarbonDiary carbonDiary) {
        TableLayout table = findViewById(R.id.diary_table);
        table.removeAllViews();

        for(CarbonDiaryEntry entry : carbonDiary.getEntries()) {
            TableRow row = new TableRow(this);
            TextView name = new TextView(this);
            name.setText(getString(R.string.entry_name_display, entry.getType(),
                    entry.getAmount(), CarbonDiaryEntry.getUnitForType(entry.getType())));
            name.setTextSize(20);
            name.setPadding(0, 0, 30, 0);
            TextView carbon = new TextView(this);
            carbon.setText(getString(R.string.entry_carbon_display, entry.getCarbon()));
            carbon.setTextSize(20);
            carbon.setPadding(0, 0, 30, 0);
            Button removeButton = new Button(this);
            removeButton.setText(R.string.remove_button);
            removeButton.setTag(entry);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    carbonDiaryViewModel.removeEntry((CarbonDiaryEntry)view.getTag());
                }
            });
            row.addView(name);
            row.addView(carbon);
            row.addView(removeButton);

            table.addView(row);
        }

        TextView todayCarbon = findViewById(R.id.today_carbon);
        todayCarbon.setText(getString(R.string.today_carbon, carbonDiary.getTotalCarbon()));
        TextView averageCarbon = findViewById(R.id.average_carbon);
        averageCarbon.setText(getString(R.string.average_carbon, 0.0));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        newEntryType = (String)adapterView.getItemAtPosition(i);
        TextView unit = findViewById(R.id.new_entry_unit);
        unit.setText(CarbonDiaryEntry.getUnitForType(newEntryType));
        Log.d(TAG, "onItemSelected called");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        newEntryType = "";
    }
}
