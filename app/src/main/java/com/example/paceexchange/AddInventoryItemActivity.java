package com.example.paceexchange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddInventoryItemActivity extends AppCompatActivity {

    private EditText mNewItemInput;
    private Button mSubmitItemButton;
    private RadioGroup mConditionRadioGroup;
    private RadioButton mSelectedRadioButton;
    private Spinner mSpinner;
    private ArrayAdapter<CharSequence> mItemAdapter;
    private String mNewItemName, mNewitemConditionm, mNewItemCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        mSpinner = findViewById(R.id.spinner);
        mNewItemInput = findViewById(R.id.itemNameInput);
        mConditionRadioGroup = findViewById(R.id.productConditionButtons);
        mSubmitItemButton = findViewById(R.id.submitNewItemButton);
        setOnItemMenuClickListener();
        setOnButtonClickListener();


    }

    public void getNewItemData() {

        int selectedConditionButtonID = mConditionRadioGroup.getCheckedRadioButtonId();
        mSelectedRadioButton = findViewById(selectedConditionButtonID);

        if (mNewItemInput.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.error_new_item_name, Toast.LENGTH_LONG).show();
        } else if (mSelectedRadioButton == null) {
            Toast.makeText(getApplicationContext(), R.string.error_new_item_radio_button, Toast.LENGTH_LONG).show();
        } else {
            mNewItemName = mNewItemInput.getText().toString().trim();
        }
    }

    public void setOnButtonClickListener() {

        mSubmitItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewItemData();
            }
        });
    }

    public void setOnItemMenuClickListener() {

        mItemAdapter = ArrayAdapter.createFromResource(this, R.array.item_array, android.R.layout.simple_spinner_item);
        mItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mItemAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNewItemCategory = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
