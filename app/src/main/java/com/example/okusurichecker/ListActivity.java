package com.example.okusurichecker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.okusurichecker.db.MyOpenHelper;

public class ListActivity extends android.app.ListActivity {

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle(R.string.set_title_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteOpenHelper helper = null;
        SQLiteDatabase database = null;

        try {
            helper = new MyOpenHelper(getApplicationContext());
            database = helper.getReadableDatabase();

            cursor = database.query("TODO", null, null, null,
                    null, null, null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, cursor,
                    new String[]{"title"}, new int[]{R.id.list_id}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "DBエラー", e);
        } finally {
            database.close();
        }

        findViewById(R.id.btn_set_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrescriptionList.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_set_next_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AlarmSet.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}