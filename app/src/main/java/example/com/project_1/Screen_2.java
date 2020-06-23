package example.com.project_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;


public class Screen_2 extends AppCompatActivity {
    //private static final int PICK_VIDEO_REQUEST = 73;
    String gesture,file,filename;
    Uri fileuri, videoUri;
    File path;
    //private MediaRecorder mediaRecorder;
    private StorageReference mstorageReference;
    //Uri videoUri = null;
    VideoView videoview;

    private static final int VIDEO_CAPTURE = 101;
    private int requestCode;
    private int resultCode;
    private Intent data;
    Button upload;
    //It contains the videoviewer and upload and record button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_2);
        videoview = findViewById(R.id.videoView);
        mstorageReference = FirebaseStorage.getInstance().getReference();
        MediaController mediaController = new MediaController(this);
        videoview.setMediaController(mediaController);
        upload = (Button) findViewById(R.id.upload);
        upload.setEnabled(false);
        Button record = (Button) findViewById(R.id.record);
        //On clicking record button it passes intent to camera app for recording the gesture. This done by startRecording method
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording(view);
            }
        });
        //On clicking upload button uploadFile method gets triggered which uploads the recorded video to the server
        upload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        //We have taken the gesture name from previous screen which is selected in spinner
        gesture = getIntent().getExtras().getString("Value");
        //This download object is created which will trigger the download method and the gesture video will get downloaded
        DownloadTask dw = new DownloadTask();
        dw.execute();

    }
    //This is the upload method, on clicking upload button this method gets triggered
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void uploadFile(){
        Uri uri = Uri.parse(String.valueOf(videoUri));
        if(uri!= null) {
            //In here we are uploading the video which is recorded and renaming it using the credentials
            // which we took from the user in the first screen and we are adding the practice number to
            // it and it gets reset to 1 when the counter reaches 3 and when you upload another video
            // it will again start with 1 and overwrites the first video.
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading..");
            progressDialog.show();
            String save=gesture+MainActivity.nm;
            MainActivity.map.put(save,(MainActivity.map.getOrDefault(save,0)%3)+1);
            int practice_num=MainActivity.map.get(save);
            StorageReference riversRef = mstorageReference.child("G" + MainActivity.grpid + "/" + MainActivity.aid + "/" + gesture + "/Videos/" + gesture + "_" + practice_num + "_" + MainActivity.nm + ".mp4");
            riversRef.putFile(fileuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"File Uploaded", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Screen_2.this, Screen_1.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int)progress)+ "% Uploaded...");
                        }
                    });

        }
        else{
            Toast.makeText(this, "Error Uploading File", Toast.LENGTH_SHORT).show();
        }
    }
    //It downloads the video and we download the video using the spinner value and comparing the value
    // with each case and providing the download link with their respective gestures.
    public class DownloadTask extends AsyncTask<String, String, String> {
        URL url;
        //Here we are making the folder named gesture in the our sdcard which stores all the downloaded gestures
        @Override
        protected String doInBackground(String... strings) {
            File SDCardRoot = Environment.getExternalStorageDirectory();
            File dir = new File(SDCardRoot, "/Gestures/");
            if (!dir.exists()) {
                dir.mkdir();
            }
            filename = gesture + ".mp4";
            path =  new File(dir + filename);
            try
            {
                InputStream input = null;
                try{
                    switch(gesture){

                        case "Gift":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/23/23781.mp4");
                            break;

                        case "Car":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/26/26165.mp4");
                            break;

                        case "Pay":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/14/14618.mp4");
                            break;

                        case "Pet":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/7/7515.mp4");
                            break;

                        case "Sell":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/9/9199.mp4");
                            break;

                        case "Explain":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/22/22623.mp4");
                            break;

                        case "That":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/14/14366.mp4");
                            break;

                        case "Book":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/14/14326.mp4");
                            break;

                        case "Now":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/9/9785.mp4");
                            break;

                        case "Work":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/8/8599.mp4");
                            break;

                        case "Total":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/26/26467.mp4");
                            break;

                        case "Trip":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/9/9117.mp4");
                            break;

                        case "Future":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/14/14738.mp4");
                            break;

                        case "Good":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/21/21534.mp4");
                            break;

                        case "Thank you":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/25/25590.mp4");
                            break;

                        case "Learn":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/21/21560.mp4");
                            break;

                        case "Agency":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/6/6428.mp4");
                            break;

                        case "Should":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/9/9563.mp4");
                            break;

                        case "Like":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/14/14298.mp4");
                            break;

                        case "Movie":
                            url = new URL("https://www.signingsavvy.com/media/mp4-ld/8/8626.mp4");
                            break;
                    }
                    // link of the song which you want to download like (http://...)
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setReadTimeout(95 * 1000);
                    urlConnection.setConnectTimeout(95 * 1000);
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setRequestProperty("X-Environment", "android");


                    urlConnection.setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            /** if it necessarry get url verfication */
                            //return HttpsURLConnection.getDefaultHostnameVerifier().verify("your_domain.com", session);
                            return true;
                        }
                    });
                    urlConnection.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());


                    urlConnection.connect();
                    input = urlConnection.getInputStream();
                    //input = url.openStream();
                    OutputStream output = new FileOutputStream(new File(dir, filename));

                    try {
                        byte[] buffer = new byte[1024];
                        int bytesRead = 0;
                        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0)
                        {
                            output.write(buffer, 0, bytesRead);

                        }
                        output.close();
                        //Toast.makeText(getApplicationContext(),"Read Done", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception exception)
                    {


                        //Toast.makeText(getApplicationContext(),"output exception in catch....."+ exception + "", Toast.LENGTH_LONG).show();
                        //Log.d("Error", String.valueOf(exception));
                        publishProgress(String.valueOf(exception));
                        output.close();

                    }
                }
                catch (Exception exception)
                {

                    //Toast.makeText(getApplicationContext(), "input exception in catch....."+ exception + "", Toast.LENGTH_LONG).show();
                    publishProgress(String.valueOf(exception));

                }
                finally
                {
                    input.close();
                }
            }
            catch (Exception exception)
            {
                publishProgress(String.valueOf(exception));
            }

            return "true";
        }
        // On gesture getting downloaded it automatically starts playing in videoviewer
        @Override
        protected void onPostExecute(String s) {
            if(!path.exists()){
                videoview.setVideoPath(Environment.getExternalStorageDirectory()+"/Gestures/" + filename);
                videoview.start();
            }
        }
    }
    //This method gets implemented when the record button is clicked.
    public void startRecording(View view) {
        String name = MainActivity.nm;
        final String me = null;
        File mediaFile = null;
        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        i.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
            startActivityForResult(i,VIDEO_CAPTURE);
        }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video has been saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
                fileuri= data.getData();
                upload.setEnabled(true);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}