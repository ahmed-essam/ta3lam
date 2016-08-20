package com.yackeen.ta3allam.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yackeen.ta3allam.Adapter.FirstLoginAdapter2;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.Capsule.Book;

import java.util.ArrayList;
import java.util.Locale;

public class FirstLoign2 extends AppCompatActivity {
    ArrayList<Book>books;
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
        setContentView(R.layout.activity_first_loign2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView rvBooks = (RecyclerView) findViewById(R.id.books);
        rvBooks.setHasFixedSize(true);
        books=new ArrayList<>();
        Book bk=new Book();
        bk.setName("اى حاجة");
        bk.setLevel("المستوى الاول");
        bk.setTeacher("الامام/احمد الطي");
        books.add(bk);
        bk=new Book();
        bk.setName("اى حاجة");
        bk.setLevel("المستوى الاول");
        bk.setTeacher("الامام/احمد الطي");
        books.add(bk);
        FirstLoginAdapter2 adapter = new FirstLoginAdapter2(this, books);
        rvBooks.setAdapter(adapter);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
    }

}
