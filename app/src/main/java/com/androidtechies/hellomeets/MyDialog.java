package com.androidtechies.hellomeets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDialog extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{
    public static boolean active = false;
    public static Activity myDialog;

    public static ListView linkList;
    public static ArrayList<String> list= new ArrayList<String>();
    public static ArrayAdapter arrayAdapter;
    String link;
    EditText edt;
    Button btn;
    View top;
    String add;
    public int pos;
    public static final String Default = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_dialog);

        linkList = (ListView) findViewById(R.id.linklist);
        btn = (Button)findViewById(R.id.btn_link);
        edt = (EditText) findViewById(R.id.et_link);
//        add = getIntent().getStringExtra("add");
        SharedPreferences sharedpreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        add = sharedpreferences.getString("add", null);
        btn.setOnClickListener(this);
        editor.remove("add");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        linkList.setAdapter(arrayAdapter);
        top = (View)findViewById(R.id.dialog_top);
        myDialog = MyDialog.this;
        if(add!=null) {
            list.add(add);
        }
        arrayAdapter.notifyDataSetChanged();

        linkList.setOnItemClickListener(this);

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        active = true;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        active = false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        active = false;
    }

    @Override
    public void onClick(View v) {


        link = edt.getText().toString();
        list.add(link);
        arrayAdapter.notifyDataSetChanged();
        edt.setText("");
        Intent i = new Intent(MyDialog.this, WebActivity.class);
        i.putExtra("url", list.get(pos));
        startActivity(i);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.pos = position;
        Intent i = new Intent(MyDialog.this, WebActivity.class);
        i.putExtra("url", list.get(position));
        startActivity(i);
    }
}