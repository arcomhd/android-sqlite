package com.example.praktikan.myapplication;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText dni;
    public EditText name;
    public EditText university;
    public EditText subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dni = (EditText) findViewById(R.id.inputDNI);
        name = (EditText) findViewById(R.id.inputName);
        university = (EditText) findViewById(R.id.inputUniversity);
        subject = (EditText) findViewById(R.id.inputSubject);
    }

    public void addAction(View view) {
        String dniString = dni.getText().toString();
        String nameString = name.getText().toString();
        String universityString = university.getText().toString();
        String subjectString = subject.getText().toString();

        AdminSQLiteOpenHelper sqlite = new AdminSQLiteOpenHelper(this, "management", null, 1);
        SQLiteDatabase db = sqlite.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put("dni", dniString);
        registry.put("name", nameString);
        registry.put("university", universityString);
        registry.put("subject", subjectString);
        db.insert("people", null, registry);
        db.close();
        Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
    }

    public void showAction(View view){
        String dniString = dni.getText().toString();
        AdminSQLiteOpenHelper sqlite =
                new AdminSQLiteOpenHelper(this, "management", null, 1);

        SQLiteDatabase db = sqlite.getWritableDatabase();
        Cursor row = db.rawQuery("SELECT name, university, subject FROM people WHERE dni="
                + dniString, null);
        if(row.moveToFirst()){
            name.setText(row.getString(0));
            university.setText(row.getString(1));
            subject.setText(row.getString(2));
        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void updateAction(View view){
        String dniString        = dni.getText().toString();
        String nameString       = name.getText().toString();
        String universityString = university.getText().toString();
        String subjectString    = subject.getText().toString();

        AdminSQLiteOpenHelper sqlite = new AdminSQLiteOpenHelper(this, "management", null, 1);
        SQLiteDatabase db = sqlite.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put("dni", dniString);
        registry.put("name", nameString);
        registry.put("university", universityString);
        registry.put("subject", subjectString);
        int quantity = db.update("people", registry, "dni=" + dniString, null);
        db.close();
        if(quantity == 1){
            Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data tidak berhasil diupdate", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAction(View view){
        String dniString = dni.getText().toString();

        AdminSQLiteOpenHelper sqlite = new AdminSQLiteOpenHelper(this, "management", null, 1);
        SQLiteDatabase db = sqlite.getWritableDatabase();
        int quantity = db.delete("people", "dni=" + dniString, null);
        db.close();
        dni.setText("");
        name.setText("");
        university.setText("");
        subject.setText("");
        if(quantity == 1){
            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data tidak berhasil dihapus", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
