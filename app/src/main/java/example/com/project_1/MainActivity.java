package example.com.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name;
    EditText asu_id;
    EditText gid;
    public static String nm, aid, grpid;

    public static HashMap<String,Integer> map=new HashMap<String, Integer>();
    // We created the first screen to enter the lastname, groupid and asu id as it is required for renaming the video which we upload
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.lastName);
        asu_id = (EditText) findViewById(R.id.asu_id);
        gid = (EditText) findViewById(R.id.group_id);
        //Log.e("Name",nm);
        //Log.e("ASU",aid);
        //Log.e("Group",grpid);
        Button submit = findViewById(R.id.button3);
    }
    // On submitting the information we are transferred to the next screen and if every detail is not written
    // it will not move to next screen
    @Override
    public void onClick(View view) {
        nm = name.getText().toString();
        aid = asu_id.getText().toString();
        grpid = gid.getText().toString();

        if (nm.isEmpty() || aid.isEmpty() || grpid.isEmpty()) {

            Toast.makeText(view.getContext(), "Please enter all details", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(MainActivity.this, Screen_1.class);
            startActivity(intent);
        }
    }
}
