package com.example.appone;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    public boolean onCreateOptionsMenu(Menu menu){
        //启用菜单；
        getMenuInflater().inflate(R.menu.first_menu, (android.view.Menu) menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //实现功能
        if(item.getItemId()==R.id.menu1){

        }
        return super.onOptionsItemSelected(item);
    }
}
