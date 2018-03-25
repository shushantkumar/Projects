package com.example.shushantkumar.videorequest;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button upload;
    ImageView imageView;
    TextView textView;
    String url;
//     = "http://10.100.2.141:8000/video/"
    EditText urlText;
    VideoView videoView;
    private MediaController mc;
    String path;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBrowse();
            }
        });

        urlText = (EditText) findViewById(R.id.url);


        videoView = findViewById(R.id.video);
        textView = (TextView) findViewById(R.id.textView);
    }


    private void imageBrowse() {
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CALENDAR)
//                == PackageManager.PERMISSION_GRANTED) {
        // Permission is not granted
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);
//        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            url = "http://" + urlText.getText().toString() +   ":8000/video/";
            if (null != selectedImageUri) {
                // Get the path from the Uri
                path = getPathFromURI(selectedImageUri);
                Log.i("Image Path : ", "Image Path : " + path);
                // Set the image in ImageView
                try {
                    uri = Uri.parse(path);
                    mc = new MediaController(this);
                    videoView.setMediaController(mc);
                    videoView.requestFocus();
                    videoView.setVideoURI(uri);
                    videoView.start();
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//                    imageView = (ImageView) findViewById(R.id.imageView);
//                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                imageView.setImageURI(selectedImageUri);
                uploadBitmap();
            }
        }
    }


    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void uploadBitmap() {
//url = urlText.getText().toString();
        Toast.makeText(MainActivity.this, "in uploadBitmap", Toast.LENGTH_SHORT).show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                textView.setText(resultResponse);
                Toast.makeText(MainActivity.this, resultResponse, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                textView.setText(error.toString());
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
//                Toast.makeText(AfterLogin.this, errorMessage, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", "name");
                return params;
            }

//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                params.put("video", new DataPart(videoView));
//                return params;
//            }



            @Override
            protected Map<String, DataPart> getByteData() throws IOException {
                Map<String, DataPart> params = new HashMap<>();
//                params.put("name", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), R.id.textView)));
                params.put("video", new DataPart("file_cover.mp4", AppHelper.getFileDataFromDrawable(getBaseContext(), path), "image/jpeg"));
                return params;
            }

        };
        SingletonRequestQueue.getInstance(this).addToRequestQueue(multipartRequest);


        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
    }
}
