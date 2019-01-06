package com.example.wako.contextmanagementactivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/*
*  the main part of the program
*  have all the function thats connected to bottoms and so on*/
public class ContextManagementActivity extends Activity {

    public RoomContextHttpManager rchm = new RoomContextHttpManager();
    private RoomContextState state = null;
    View contextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rchm.init(this);
        setContentView(R.layout.activity_context_management);

        System.out.println("-------------------------------");
        rchm.getSpinnerInfo();
        System.out.println("-------------------------------");

        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String room = ((Spinner) findViewById(R.id.editText1))
                        .getSelectedItem().toString();
                System.out.println("input: "+room);
                retrieveRoomContextState(room);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void checkRoom(View view) {
    }
    public void switchLight(RoomContextState state, String room) {
        //encapsulate a RequestQueue
        rchm.switchLight(state,room);
    }
    public void switchRinger(View view) {
    }
    public void retrieveRoomContextState(String room){
        System.out.println("retrieveRoomContextState called");
        rchm.retrieveRoomContextState(room);
    }
    public void onUpdate(RoomContextState context){
        // refresh the view
        this.state = context;
        updateContextView();
    }
    public void createSpinner(ArrayList<String> roomid){
        System.out.println("room id in createSpinncer: "+roomid);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomid);
        Spinner spn = (Spinner) findViewById(R.id.editText1);
        spn.setAdapter(adapter);
    }

    public void testing(){
        System.out.println("testing 関数を呼び出すことに成功");
    }
    private void updateContextView() {/*
//image も contextView　もGUIの部分的なところ（表示の更新が行われている
        if (this.state != null) {
            contextView.setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textViewLightValue)).setText(Integer
                    .toString(state.getLightLevel()));
    //        ((TextView) findViewById(R.id.textViewNoiseValue)).setText(Float.toString(state.getNoiseLevel()));
            if (state.getLightStatus().equals(RoomContextState.ON))
                image.setImageResource(R.drawable.ic_bulb_on);
            else
                image.setImageResource(R.drawable.ic_bulb_off);
        } else {
            initView(); //情報がなかったとき表示する
        }*/
    }

}
