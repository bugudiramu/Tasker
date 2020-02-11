package com.example.tasker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText editTextTv;
    Button addTodoBtn;
    ArrayList<Tasker> tasks;
    LinearLayout linearLayout;
    ImageView crossBtn;
    int pos;
    String formattedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Initialize an arrayList
        tasks = new ArrayList<Tasker>();
        // Attach to the XML Elements
        editTextTv = findViewById(R.id.editText_tv);
        addTodoBtn = findViewById(R.id.addTodoBtn);
        linearLayout = findViewById(R.id.linearlayout);
        crossBtn = findViewById(R.id.cross_btn);

        //Create a listView and set the adapter to it.
        ListView listView = findViewById(R.id.list);
        final ArrayAdapter<Tasker> adapter = new TaskerAdapter(this, tasks);
        listView.setAdapter(adapter);
        // Don't show cross/clear button in the starting of the app
        crossBtn.setVisibility(View.GONE);
        // Clear the text in the input field
        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTv.getText().clear();
            }
        });
        // TextWatcher for editText
        editTextTv.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    crossBtn.setVisibility(View.GONE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crossBtn.setVisibility(View.VISIBLE);

            }
        });
// Adding task button
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(" MMM d, EEE, h:mm 'PM'");
                formattedDate = df.format(c.getTime());
                if (!TextUtils.isEmpty(editTextTv.getText())) {
                    Snackbar snackbar = Snackbar.make(linearLayout, editTextTv.getText().toString() + " is added", Snackbar.LENGTH_LONG);
                    snackbar.show();
//                    Toast.makeText(getApplicationContext(), editTextTv.getText().toString() + " is added.", Toast.LENGTH_SHORT).show();
                    tasks.add(new Tasker(editTextTv.getText().toString(), formattedDate));
                    Log.d("Tasks", String.valueOf(tasks.size()));
                    // Reversing list
//                    Collections.reverse(tasks);
//                    Log.d("Tasks", tasks.get(pos).getTodoTitle());

//                    editTextTv.getText().clear();
                    // Notify the changes to adapter
                    adapter.notifyDataSetChanged();

                } else {
                    Snackbar snackbar = Snackbar.make(linearLayout, "Please Enter Some Text", Snackbar.LENGTH_LONG);
                    snackbar.show();
//                    Toast.makeText(getApplicationContext(), "Please Enter Some Text ", Toast.LENGTH_SHORT).show();

                }
            }
        });
        //Tap to Edit/Update the task
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                // Show Dialog
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Edit entry")
                        .setMessage("Are you sure you want to Edit this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                String title = tasks.get(pos).getTodoTitle();
                                editTextTv.setText(title);
                                tasks.remove(pos);
                                adapter.notifyDataSetChanged();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });

        // Long Press to Delete task
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                // Show Dialog
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to Delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                Snackbar snackbar = Snackbar
                                        .make(linearLayout, tasks.get(pos).getTodoTitle() + " is deleted", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                // TODO Undo action for Snackbar
                               /* snackbar.setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tasks.add(new Tasker(tasks.get(pos).getTodoTitle(), formattedDate));
                                        // Reversing list
//                                        Collections.reverse(tasks);
                                        adapter.notifyDataSetChanged();
                                    }
                                });*/
                                tasks.remove(pos);
                                Log.d("Tasks", String.valueOf(tasks.size()));
                                adapter.notifyDataSetChanged();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });
        // set random color for every card
//        Random random = new Random();
//        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

}
