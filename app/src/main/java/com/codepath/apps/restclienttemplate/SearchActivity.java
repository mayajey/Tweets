package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by mayajey on 7/7/17.
 */

public class SearchActivity extends AppCompatActivity {

    EditText etSearchQuery;
    String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        etSearchQuery = (EditText) findViewById(R.id.etSearchQuery);
    }

}
