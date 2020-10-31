package com.example.okusurichecker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.okusurichecker.db.MyOpenHelper;

public class PrescriptionList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);

        setTitle(R.string.set_title_prescription);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View btnSet = findViewById(R.id.btn_set_list);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.edit_title);
                EditText editContent = findViewById(R.id.edit_content);
                EditText editMedicine = findViewById(R.id.medicine_nb);

                RadioGroup radioGroupA = findViewById(R.id.radio_Group_A);
                RadioButton radioButtonA = radioGroupA.
                        findViewById(radioGroupA.getCheckedRadioButtonId());

                RadioGroup radioGroupB = findViewById(R.id.radio_Group_B);
                RadioButton radioButtonB = radioGroupB.
                        findViewById(radioGroupB.getCheckedRadioButtonId());


                SQLiteOpenHelper helper = null;
                SQLiteDatabase database = null;

                if (!editText.getText().toString().isEmpty()) {

                    try {
                        helper = new MyOpenHelper(getApplicationContext());
                        database = helper.getWritableDatabase();

                        ContentValues cv = new ContentValues();
                        cv.put("title", editText.getText().toString());
                        cv.put("content", editContent.getText().toString());
                        cv.put("editMedicine", editMedicine.getText().toString());
                        cv.put("radioTextA", radioButtonA.getText().toString());
                        cv.put("radioTextB", radioButtonB.getText().toString());
                        database.insert("TODO", null, cv);

                    } catch (Exception e) {
                        Log.e(this.getClass().getSimpleName(), "DBエラー", e);
                    } finally {
                        database.close();
                    }

                    finish();
                } else {
                    Toast.makeText(PrescriptionList.this, R.string.lbl_set, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}