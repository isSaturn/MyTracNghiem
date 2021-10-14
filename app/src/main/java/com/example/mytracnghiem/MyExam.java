package com.example.mytracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyExam extends AppCompatActivity {
    TextView dscauhoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exam);

        matching();

        quanlycauhoi db = new quanlycauhoi(this);
        try {
            db.createDataBase();
            Log.d("Thanh cong", "Da tao duoc db");
        } catch (IOException e) {
//            e.printStackTrace();
            Log.d("Bi loi roi", "khong tao duoc db");
        }
        Cursor contro=db.laytatcacauhoi();
        contro.moveToFirst();
        String chuoi="";
        List<cauhoi> ds_cauhoi=new ArrayList<cauhoi>();
        ds_cauhoi=db.layNcaungaunhien(3);
        for(cauhoi x: ds_cauhoi)
        {
            chuoi+=x.cauhoi+"\n";
        }

        dscauhoi.setText(chuoi);

    }

    private void matching() {
        dscauhoi = (TextView) findViewById(R.id.textView);
    }
}
