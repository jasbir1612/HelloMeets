package com.androidtechies.hellomeets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static int flag = 1;
    public static Button btnStartService;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = (Button)findViewById(R.id.btnlink);



        btnStartService.setOnClickListener(lst_StartService);
    }


    Button.OnClickListener lst_StartService = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.d(Utils.LogTag, "lst_StartService -> Utils.canDrawOverlays(Main.this): " + Utils.canDrawOverlays(MainActivity.this));

            if(Utils.canDrawOverlays(MainActivity.this)) {
                {
                    startChatHead();
                }
            }
            else{
                requestPermission(flag);
            }
        }

    };

    public void startChatHead(){
        startService(new Intent(MainActivity.this, ChatHeadService.class));
        Intent i = new Intent(this, WebActivity.class);
//        i.putExtra("url", str);
//        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("url", str);
//        editor.commit();
        startActivity(i);
    }

    private void needPermissionDialog(final int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.setCancelable(false);
        builder.show();
    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }

    private void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (!Utils.canDrawOverlays(MainActivity.this)) {
                needPermissionDialog(requestCode);
            }else{
                startChatHead();
            }

    }


}

