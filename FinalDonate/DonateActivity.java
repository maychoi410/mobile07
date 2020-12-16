package com.cookandroid.teamproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Intent.ACTION_PICK;

public class DonateActivity extends Activity {

    private static String IP_ADDRESS = "192.168.0.8";
    private static String TAG = "phptest";

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview1, imageview2, imageview3, imageview4, imageview5, imageview6;
    private int OPTION = 0;

//    private TextView mTextViewResult;

    Button btnWeb, btnHome, btnOk;
    EditText edName;
    RadioGroup rGroup;
    RadioButton radiobtn1, radiobtn2, radiobtn3, radiobtn4, radiobtn5, radiobtn6;
    String category, name, donor;

    String image1, image2, image3, image4, image5, image6;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate);
        setTitle("Donate");

        //        레이아웃 1 : 홈페이지 이동
        btnWeb = (Button) findViewById(R.id.btnWeb);

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.beautifulstore.org/intro-donation#none");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //        레이아웃 2 : 카테고리 라디오버튼
        rGroup = (RadioGroup) findViewById((R.id.rGroup));
        radiobtn1 = (RadioButton) findViewById(R.id.radiobtn1);
        radiobtn2 = (RadioButton) findViewById(R.id.radiobtn2);
        radiobtn3 = (RadioButton) findViewById(R.id.radiobtn3);
        radiobtn4 = (RadioButton) findViewById(R.id.radiobtn4);
        radiobtn5 = (RadioButton) findViewById(R.id.radiobtn5);
        radiobtn6 = (RadioButton) findViewById(R.id.radiobtn6);

        //        레이아웃 3 :
        edName = (EditText) findViewById(R.id.edName);

        //        레이아웃 4 :
        imageview1 = (ImageView)findViewById(R.id.imageView1);
        imageview2 = (ImageView)findViewById(R.id.imageView2);
        imageview3 = (ImageView)findViewById(R.id.imageView3);
        imageview4 = (ImageView)findViewById(R.id.imageView4);
        imageview5 = (ImageView)findViewById(R.id.imageView5);
        imageview6 = (ImageView)findViewById(R.id.imageView6);

        imageview1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OPTION = 1;

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        imageview2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OPTION = 2;

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        imageview3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OPTION = 3;

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        imageview4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OPTION = 4;

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        imageview5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OPTION = 5;

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        imageview6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OPTION = 6;

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });


        //        레이아웃 5 :
        btnOk = (Button) findViewById(R.id.btnOk);


        // insert data
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(DonateActivity.this,
                        "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                progressDialog.dismiss();
                Log.d(TAG, "POST response  - " + result);

                Toast.makeText(DonateActivity.this,result,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                String name = (String) params[1];
                String category = (String) params[2];
                String donor = (String) params[3];
                String image1 = (String) params[4];

                String serverURL = (String) params[0];
                String postParameters = "name=" + name + "&category=" + category + "&donor=" + donor + "&image1=" + image1;

                try {
                    // POST 방식으로 데이터 전송
                    URL url = new URL(serverURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.connect();

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(postParameters.getBytes("UTF-8"));

                    outputStream.flush();
                    outputStream.close();

                    // 응답을 읽음
                    int responseStatusCode = httpURLConnection.getResponseCode();
                    Log.d(TAG, "POST response code - " + responseStatusCode);

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();
                    }

                    // 수신되는 데이터 저장
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();

                    // 저장된 데이터를 string으로 변환하여 return
                    return sb.toString();

                } catch (Exception e) {

                    Log.d(TAG, "InsertData: Error ", e);

                    return new String("Error: " + e.getMessage());
                }

            }
        }


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //카테고리
                switch (rGroup.getCheckedRadioButtonId()) {
                    case R.id.radiobtn1:
                        category = "의류";
                        break;
                    case R.id.radiobtn2:
                        category = "영유아 잡화";
                        break;
                    case R.id.radiobtn3:
                        category = "주방, 생활 잡화";
                        break;
                    case R.id.radiobtn4:
                        category = "패션, 미용 잡화";
                        break;
                    case R.id.radiobtn5:
                        category = "도서, 음반";
                        break;
                    case R.id.radiobtn6:
                        category = "가전";
                        break;

                }
                name = edName.getText().toString();
                donor = "김영희";


                // Image Code
                image1 = BitmapToString(bitmap1);
//                image2 = BitmapToString(bitmap2);
//                image1 = BitmapToString(bitmap1);
//                image1 = BitmapToString(bitmap1);
//                image1 = BitmapToString(bitmap1);
//                image1 = BitmapToString(bitmap1);

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", name, category, donor, image1);

                edName.setText("");
            }
        });


        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }


    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String image = Base64.encodeToString(bytes, Base64.DEFAULT);

        String temp = "";
        try{
            temp = URLEncoder.encode(image,"utf-8");
        }catch (Exception e){
            Log.e("exception",e.toString());
        }

        return temp;
    }

    private Bitmap resize(Bitmap bm){
        Configuration config=getResources().getConfiguration();
        if(config.smallestScreenWidthDp>=800)
            bm = Bitmap.createScaledBitmap(bm, 400, 240, true);
        else if(config.smallestScreenWidthDp>=600)
            bm = Bitmap.createScaledBitmap(bm, 300, 180, true);
        else if(config.smallestScreenWidthDp>=400)
            bm = Bitmap.createScaledBitmap(bm, 200, 120, true);
        else if(config.smallestScreenWidthDp>=360)
            bm = Bitmap.createScaledBitmap(bm, 180, 108, true);
        else
            bm = Bitmap.createScaledBitmap(bm, 160, 96, true);
        return bm;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            switch(OPTION) {
                case 1:
                    try {
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        bitmap1 = resize(bitmap1);
                        imageview1.setImageBitmap(bitmap1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (OutOfMemoryError e){
                        Toast.makeText(getApplicationContext(), "이미지 용량이 너무 큽니다.", Toast.LENGTH_SHORT).show();
                    }
//                    imageview1.setImageURI(selectedImageUri);
                    break;
                case 2:
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        bitmap2 = resize(bitmap2);
                        imageview2.setImageBitmap(bitmap2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    imageview2.setImageURI(selectedImageUri);
                    break;
                case 3:
                    try {
                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        bitmap3 = resize(bitmap3);
                        imageview3.setImageBitmap(bitmap3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    imageview3.setImageURI(selectedImageUri);
                    break;
                case 4:
                    try {
                        bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        bitmap4 = resize(bitmap4);
                        imageview4.setImageBitmap(bitmap4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    imageview4.setImageURI(selectedImageUri);
                    break;
                case 5:
                    try {
                        bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        bitmap5 = resize(bitmap5);
                        imageview5.setImageBitmap(bitmap5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    imageview5.setImageURI(selectedImageUri);
                    break;
                case 6:
                    try {
                        bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        bitmap6 = resize(bitmap6);
                        imageview6.setImageBitmap(bitmap6);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    imageview6.setImageURI(selectedImageUri);
                    break;
            }
        }

    }

}
