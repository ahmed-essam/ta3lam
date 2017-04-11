package com.yackeen.ta3allam.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yackeen.ta3allam.adapter.FirstLoginAdapter;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.Capsule.Category;

import java.util.ArrayList;
import java.util.Locale;

public class FirstLogin extends AppCompatActivity {
    ArrayList<Category>categories;
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


        RecyclerView rvCategories = (RecyclerView) findViewById(R.id.categories);
        rvCategories.setHasFixedSize(true);
        categories=new ArrayList<>();
        Category cat=new Category();
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
