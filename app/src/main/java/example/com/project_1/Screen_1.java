package example.com.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Screen_1 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String gest;
    Spinner sp;

    //This Screen contains the spinner of all the getures asked and when you select the gesture and press the
    // download button it goes to next screen and downloads the video and show in video viewer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_1);
        Button dn;
        dn = (Button) findViewById(R.id.download);
        sp = (Spinner) findViewById(R.id.gestures);
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        //gest = sp.getSelectedItem().toString();
        //Toast.makeText(this, "Your gesture" + gest, Toast.LENGTH_SHORT).show();
        /*dn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                gest = sp.getSelectedItem().toString();
                Intent i = new Intent(Screen_1.this, Screen_2.class);
                startActivity(i);
            }
        });*/
    }
    //Inten is passed which takes you to another screen with videoviewer
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Screen_1.this, Screen_2.class);
        i.putExtra("Value", gest);
        startActivity(i);
        Toast.makeText(view.getContext(),"Your gesture " + gest +" is downloading",Toast.LENGTH_LONG).show();
    }
    //This method is used to take the values from spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        gest = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
