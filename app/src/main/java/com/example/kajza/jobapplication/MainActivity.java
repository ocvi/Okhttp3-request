package com.example.kajza.jobapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private EditText user, pass, org;
    private String postUrl = "https://spika.chat/api/v3/signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.et_name);
        pass = (EditText) findViewById(R.id.et_pw);
        org = (EditText) findViewById(R.id.et_org);
        ImageButton submitBtn = (ImageButton) findViewById(R.id.btn_login);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TokenRequest();
            }

        });

    }

    private void TokenRequest() {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Pričekajte!");
        progressDialog.show();


        final MediaType MEDIA_TYPE = MediaType.parse("application/json");
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("organization", org.getText().toString());
            postdata.put("username", user.getText().toString());
            postdata.put("password", pass.getText().toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .addHeader("apikey", "GMUwQIHielm7b1ZQNNJYMAfCC508Giof")
                .addHeader("Content-type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Fail", call.request().body().toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Post post = new Post(obj.getString("access-token"));
                    Intent i = new Intent(MainActivity.this, Message.class);
                    i.putExtra("token", post);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
