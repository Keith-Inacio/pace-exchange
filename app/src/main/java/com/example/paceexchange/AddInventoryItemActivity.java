package com.example.paceexchange;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddInventoryItemActivity extends AppCompatActivity {

    private EditText mNewItemInput;
    private Button mSubmitItemButton;
    private RadioGroup mItemConditionRadioGroup, mItemValueRadioGroup;
    private RadioButton mSelectedConditionRadioButton, mSelectedValueRadioButton;
    private Spinner mSpinner;
    private ArrayAdapter<CharSequence> mItemAdapter;
    private String mNewItemName, mNewitemCondition, mNewItemCategory;
    private DatabaseReference mDataBase;

    private static final String CHANNEL_ID = "com.example.keithinacio.NOTIFICATION";
    private static final String CHANNEL_NAME = "com.example.keithinacio.DICTIONARY_NOTIFICATION";
    private static final String CHANNEL_DESC = "com.example.keithinacio.NEW_WORD_NOTIFICATION";
    private static final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mSpinner = findViewById(R.id.spinner);
        mNewItemInput = findViewById(R.id.itemNameInput);
        mItemConditionRadioGroup = findViewById(R.id.productConditionButtons);
        mItemValueRadioGroup = findViewById(R.id.productValueButtons);
        mSubmitItemButton = findViewById(R.id.submitNewItemButton);
        setOnItemMenuClickListener();
        setOnButtonClickListener();

        //establish a notification channel
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    public void getNewItemData() {

        int selectedConditionButtonID = mItemConditionRadioGroup.getCheckedRadioButtonId();
        mSelectedConditionRadioButton = findViewById(selectedConditionButtonID);

        int selectedValueButtonID = mItemValueRadioGroup.getCheckedRadioButtonId();
        mSelectedValueRadioButton = findViewById(selectedValueButtonID);

        if (mNewItemInput.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.error_new_item_name, Toast.LENGTH_LONG).show();
        } else if (mSelectedConditionRadioButton == null) {
            Toast.makeText(getApplicationContext(), R.string.error_new_item_radio_button, Toast.LENGTH_LONG).show();
        }else if (mSelectedValueRadioButton == null) {
            Toast.makeText(getApplicationContext(), R.string.error_new_item_value, Toast.LENGTH_LONG).show();
        }
        else {
            mNewItemName = mNewItemInput.getText().toString().trim();
        }
    }

    public void addItemToFirebase() {

        mDataBase.child("Student").child("-LbdV3uBhsq7xfV4ZQrb").child("Inventory").child(mNewItemName).setValue(mNewItemName);
        mDataBase.child("Student").child("-LbdV3uBhsq7xfV4ZQrb").child("Inventory").child(mNewItemName).child("Condition").setValue(mSelectedConditionRadioButton.getText());
        mDataBase.child("Student").child("-LbdV3uBhsq7xfV4ZQrb").child("Inventory").child(mNewItemName).child("Category").setValue(mNewItemCategory);
        mDataBase.child("Student").child("-LbdV3uBhsq7xfV4ZQrb").child("Inventory").child(mNewItemName).child("Trade Value").setValue(mSelectedValueRadioButton.getText());

    }

    public void setOnButtonClickListener() {

        mSubmitItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewItemData();
                addItemToFirebase();
                displayNotification();
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

    public void displayNotification() {


        //build the notification message for the addition of a new word
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_thumb_up_black_24dp).setContentTitle(getResources().getString(R.string.added_item_notification_title)).setContentText(getResources().getString(R.string.new_item_added_notification, mNewItemName));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //build notification manager to display notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

}
