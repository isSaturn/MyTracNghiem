package com.example.mytracnghiem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class AddExam extends AppCompatActivity {
    EditText themcauhoi, caua, caub, cauc, caud, dapan;
    Button addcauhoi;
    quanlycauhoi myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        matching();
        myDb = new quanlycauhoi(this);
        addcauhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(themcauhoi.getText().toString(),
                        caua.getText().toString(),
                        caub.getText().toString(),
                        cauc.getText().toString(),
                        caud.getText().toString(),
                        dapan.getText().toString());
                Toast.makeText(AddExam.this,"Du lieu da duoc them vao",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void matching() {
        addcauhoi = (Button) findViewById(R.id.btn_themcauhoi);
        themcauhoi = (EditText) findViewById(R.id.et_themcauhoi);
        caua = (EditText) findViewById(R.id.et_caua);
        caub = (EditText) findViewById(R.id.et_caub);
        cauc = (EditText) findViewById(R.id.et_cauc);
        caud = (EditText) findViewById(R.id.et_caud);
        dapan = (EditText) findViewById(R.id.et_dapan);
    }
}