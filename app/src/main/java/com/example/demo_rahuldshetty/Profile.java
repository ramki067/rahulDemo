package com.example.demo_rahuldshetty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.GraphViewXML;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class Profile extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Toolbar toolbar;

    private NumberPicker numberPicker[];
    private GraphView graphViewXML;
    private Button computeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        numberPicker = new NumberPicker[5];
        numberPicker[0] = findViewById(R.id.numberPicker1);
        numberPicker[1] = findViewById(R.id.numberPicker2);
        numberPicker[2] = findViewById(R.id.numberPicker3);
        numberPicker[3] = findViewById(R.id.numberPicker4);
        numberPicker[4] = findViewById(R.id.numberPicker5);

        graphViewXML = findViewById(R.id.GraphBar);

        toolbar = findViewById(R.id.toolbar);

        computeBtn = findViewById(R.id.profile_btn);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        for(int i=0;i<5;i++)
        {
            numberPicker[i].setMinValue(5);
            numberPicker[i].setMaxValue(20);
            numberPicker[i].setValue(5);
        }


        updateGraph();

        computeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGraph();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            case R.id.toolbar_logout:

                mAuth.signOut();
                Intent activity = new Intent(Profile.this,MainActivity.class);
                startActivity(activity);
                finish();
                return true;


            default:return false;
        }
    }

    void updateGraph()
    {
        graphViewXML.removeAllSeries();
        DataPoint[] dataPoints = new DataPoint[5];
        for(int i=0;i<5;i++){
            dataPoints[i] = new DataPoint(i,numberPicker[i].getValue());
        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(dataPoints);
        graphViewXML.addSeries(series);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth==null){
            mAuth = FirebaseAuth.getInstance();
            if(mAuth.getCurrentUser()==null){
                //user not logged in
                Intent activity = new Intent(Profile.this,MainActivity.class);
                startActivity(activity);
                finish();
            }
        }
    }


}
