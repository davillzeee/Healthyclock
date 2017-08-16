package com.example.kittipong.alarmtestt;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;


import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends Activity {


    TimePicker myTimePicker;
    String edt;

    Button buttonstartSetDialog;
    private ListView listAlarm;
    public static ArrayList<String> listValue;
    public static final String editextt = "edtKey";

    SharedPreferences sharedPreferences;


    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listAlarm = (ListView)findViewById(R.id.listView1);
        listValue = new ArrayList<String>();


      //  editText.setText(sharedPreferences.getString(editextt, null));



        buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //openTimePickerDialog(true);


                showTimePickerDialog(buttonstartSetDialog);



               // String n = editText.getText().toString();

               // SharedPreferences.Editor editor = sharedPreferences.edit();

                //editor.putString(editextt, n);

                //editor.commit();

            }});

    }



    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        int callCount = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);



            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            if(callCount==0){
                // Do something with the time chosen by the user
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                EditText _editext = (EditText) findViewById(R.id.editText);


                setAlarm(cal);


            }
            callCount++;

        }
    }

    private void setAlarm(Calendar targetCal){

        EditText _editext = (EditText) findViewById(R.id.editText);
        _editext.setText(_editext.getText().toString());
        sharedPreferences = getSharedPreferences("MyPreFs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("content",_editext.getText().toString());
        edit.commit();
        listValue.add(targetCal.getTime()+" CONTEN : "+_editext.getText().toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listValue);
        listAlarm.setAdapter(adapter);

        final int _id = (int) System.currentTimeMillis();

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);




        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

      /*  if(adapter==adapter){
            Intent i = new Intent(getApplicationContext(),ShowEvent.class);
            EditText _editext = (EditText) findViewById(R.id.editText);
            i.putExtra("data",_editext.getText().toString());
            startActivity(i);
        }*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listValue);
        listAlarm.setAdapter(adapter);



    }

    /* class AlarmReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent arg1) {

            Intent i = new Intent(context, ShowEvent.class);
           // Intent i = new Intent(getApplicationContext(),ShowEvent.class);
            EditText _editext = (EditText) findViewById(R.id.editText);
            i.putExtra("data",_editext.getText().toString());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

            //
        }

    }*/


}