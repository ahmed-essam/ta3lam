package com.yackeen.ta3allam;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Locale;

public class FirstLogin extends AppCompatActivity {
    ArrayList<category>categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad = "ar"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_first_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button next=(Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent firstlogin2=new Intent(FirstLogin.this,FirstLoign2.class);
                startActivity(firstlogin2);
            }
        });

        RecyclerView rvCategories = (RecyclerView) findViewById(R.id.categories);
        rvCategories.setHasFixedSize(true);
        categories=new ArrayList<>();
        category cat=new category();
        cat.setName("علوم شرعية");
        cat.setDescription("تحت الانشاء");
        cat.setLevel(1);
        cat.setTeacher(10);
        cat.setStudent(23);
        cat.setQuestion(64);
        categories.add(cat);
        cat.setName("علوم شرعية");
        cat.setDescription("تحت الانشاء");
        cat.setLevel(1);
        cat.setTeacher(10);
        cat.setStudent(23);
        cat.setQuestion(64);
        categories.add(cat);
        FirstLoginAdapter adapter = new FirstLoginAdapter(this, categories);
        rvCategories.setAdapter(adapter);
        rvCategories.setLayoutManager(new LinearLayoutManager(this));

    }

}
