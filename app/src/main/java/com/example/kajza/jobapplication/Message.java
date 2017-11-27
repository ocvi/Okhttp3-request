package com.example.kajza.jobapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kajza.jobapplication.Token.Post;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Message extends AppCompatActivity {

    EditText email;
    private String postUrl = "https://spika.chat/api/v3/messages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent i = getIntent();
        final Post token = (Post) i.getParcelableExtra("token");

        email = (EditText) findViewById(R.id.et_email);
        ImageButton submitBtn = (ImageButton) findViewById(R.id.btn_submit);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendEmail(token.getAccessToken());
            }

        });
    }

    private void SendEmail(String token) {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Message.this);
        progressDialog.setMessage("Pričekajte!");
        progressDialog.show();

        final MediaType MEDIA_TYPE = MediaType.parse("application/json");
        JSONObject postdata = new JSONObject();
        try {

            postdata.put("targetType", "3");
            postdata.put("messageType", "1");
            postdata.put("target", "5a05ccd4829e64fd1dcd7732");
            postdata.put("message", email.getText().toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
        Log.e("POSTbody", body.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .addHeader("apikey", "GMUwQIHielm7b1ZQNNJYMAfCC508Giof")
                .addHeader("Content-type", "application/json; charset=utf-8")
                .addHeader("access-token", token)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Fail", call.request().body().toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("Uspjeh!", call.request().body().toString());
                }else {
                    Log.e("Neuspjeh", call.request().body().toString());
                }
            }
        });
    }
}
