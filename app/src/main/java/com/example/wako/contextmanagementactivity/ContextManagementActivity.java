package com.example.wako.contextmanagementactivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/*
*  the main part of the program
*  have all the function thats connected to bottoms and so on*/
public class ContextManagementActivity extends Activity {

    public RoomContextHttpManager rchm;
    private RoomContextState state = null;
    View contextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rchm = new RoomContextHttpManager(this);
        rchm.init(this);
        setContentView(R.layout.activity_context_management);

        rchm.getSpinnerInfo();

        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String room = ((Spinner) findViewById(R.id.editText1))
                        .getSelectedItem().toString();
                retrieveRoomContextState(room);
            }
        });

        ((Button) findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String room = ((Spinner) findViewById(R.id.editText1))
                        .getSelectedItem().toString();
                switchLight(state,room);
            }
        });

        ((Button) findViewById(R.id.buttonChange)).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String room = ((Spinner) findViewById(R.id.editText1))
                        .getSelectedItem().toString();
                String level = ((EditText) findViewById(R.id.editText2)).getText().toString();
                changeLightLevel(state,room,level);
            }
        }));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void switchLight(RoomContextState state, String room) {
        rchm.switchLight(state,room);
    }
    public void retrieveRoomContextState(String room){
        rchm.retrieveRoomContextState(room);
    }
    public void onUpdate(RoomContextState context){
        this.state = context;
        updateContextView();
    }
    public void createSpinner(ArrayList<String> roomid){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomid);
        Spinner spn = (Spinner) findViewById(R.id.editText1);
        spn.setAdapter(adapter);
    }
    public void changeLightLevel(RoomContextState state, String room, String level){
        rchm.changeLightLevel(state, room, level);
    }

    private void updateContextView() {
        if (this.state != null) {
//          contextView.setVisibility(View.VISIBLE);

            if (state.getStatus().equals("ON")){
                ((ImageView)findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_on);
                ((TextView) findViewById(R.id.textViewLightValue)).setText(Integer.toString(state.getLightLevel()));
            }
            else{
                ((ImageView)findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_off);
                ((TextView) findViewById(R.id.textViewLightValue)).setText(Integer.toString(state.getLightLevel()));
            }

        } else {
            initView();
        }
    }
    private void initView(){
        contextView.setVisibility(View.INVISIBLE);
    }

}
