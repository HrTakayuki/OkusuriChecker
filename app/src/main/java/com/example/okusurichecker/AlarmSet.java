package com.example.okusurichecker;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmSet extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);

        setTitle(R.string.set_title_alarm);

        final TextView textHour = findViewById(R.id.text_hour);
        final TextView textMinute = findViewById(R.id.text_minute);
        final EditText editMemo = findViewById(R.id.edit_memo_prescription);
        ImageButton setTime = findViewById(R.id.btn_set_time);
        Button setAlarm = findViewById(R.id.btn_set_list);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmSet.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textHour.setText(String.format("%02d", hourOfDay));
                        textMinute.setText(String.format("%02d", minute));

                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();

            }
        });

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textHour.getText().toString().isEmpty() && !textMinute.getText().toString().isEmpty()) {
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(textHour.getText().toString()));
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(textMinute.getText().toString()));
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, editMemo.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(AlarmSet.this, "時間をセットしてください", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}