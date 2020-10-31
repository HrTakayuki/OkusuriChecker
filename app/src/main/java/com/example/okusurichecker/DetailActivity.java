package com.example.okusurichecker;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.okusurichecker.db.MyOpenHelper;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(R.string.set_title_detail);
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

        final long id = getIntent().getLongExtra("id", 0);

        SQLiteOpenHelper helper = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            helper = new MyOpenHelper(getApplicationContext());
            database = helper.getReadableDatabase();

            cursor = database.query("TODO", null, "_id =?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                String strTitle = cursor.getString(cursor.getColumnIndex("title"));
                String strContent = cursor.getString(cursor.getColumnIndex("content"));
                String strMedicine = cursor.getString(cursor.getColumnIndex("editMedicine"));
                String strRadioA = cursor.getString(cursor.getColumnIndex("radioTextA"));
                String strRadioB = cursor.getString(cursor.getColumnIndex("radioTextB"));

                EditText titleView = findViewById(R.id.detail_edit_title);
                EditText contentView = findViewById(R.id.detail_edit_content);
                EditText medicineView = findViewById(R.id.edit_Medicine);
                EditText radioAView = findViewById(R.id.edit_radioAa);
                EditText radioBView = findViewById(R.id.edit_radioBb);

                titleView.setText(strTitle);
                contentView.setText(strContent);
                medicineView.setText(strMedicine);
                radioAView.setText(strRadioA);
                radioBView.setText(strRadioB);
            }

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "DBエラー", e);
        } finally {
            cursor.close();
            database.close();
        }

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(DetailActivity.this)
                        .setTitle("確認")
                        .setMessage("本当に削除しますか？")
                        .setNegativeButton("キャンセル", null)
                        .setPositiveButton("削除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteOpenHelper deleteHelper = null;
                                SQLiteDatabase deleteDatabase = null;

                                try {
                                    deleteHelper = new MyOpenHelper(getApplicationContext());
                                    deleteDatabase = deleteHelper.getWritableDatabase();

                                    int deleteCount = deleteDatabase.delete("TODO", "_id =?",
                                            new String[]{String.valueOf(id)});

                                    if (deleteCount == 1) {
                                        Toast.makeText(DetailActivity.this, R.string.lbl_delete_success, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(DetailActivity.this, R.string.lbl_delete_failed, Toast.LENGTH_SHORT).show();
                                    }


                                } catch (Exception e) {
                                    Log.e(this.getClass().getSimpleName(), "DBエラー", e);

                                } finally {
                                    if (deleteDatabase != null) {
                                        deleteDatabase.close();
                                    }
                                }
                                finish();
                            }
                        })
                        .show();

            }

        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteOpenHelper updateHelper = null;
                SQLiteDatabase updateDatabase = null;

                String title = ((EditText) findViewById(R.id.detail_edit_title)).getText().toString();
                String content = ((EditText) findViewById(R.id.detail_edit_content)).getText().toString();
                String medicine = ((EditText) findViewById(R.id.edit_Medicine)).getText().toString();
                String radioAa = ((EditText) findViewById(R.id.edit_radioAa)).getText().toString();
                String radioBb = ((EditText) findViewById(R.id.edit_radioBb)).getText().toString();

                try {
                    updateHelper = new MyOpenHelper(getApplicationContext());
                    updateDatabase = updateHelper.getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    cv.put("title", title);
                    cv.put("content", content);
                    cv.put("editMedicine", medicine);
                    cv.put("radioTextA", radioAa);
                    cv.put("radioTextB", radioBb);

                    int updateCount = updateDatabase.update("TODO", cv, "_id = ?", new String[]{String.valueOf(id)});

                    if (updateCount == 1) {
                        Toast.makeText(DetailActivity.this, R.string.lbl_update_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, R.string.lbl_update_failed, Toast.LENGTH_SHORT).show();
                    }

                    finish();


                } catch (Exception e) {
                    Log.e(this.getClass().getSimpleName(), "DBエラー", e);

                } finally {
                    if (updateDatabase != null) {
                        updateDatabase.close();
                    }
                }
            }
        });
    }
}