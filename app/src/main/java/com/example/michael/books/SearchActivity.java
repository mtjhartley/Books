package com.example.michael.books;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        final EditText etAuthor= (EditText) findViewById(R.id.etAuthor);
        final EditText etPublisher = (EditText) findViewById(R.id.etPublisher);
        final EditText etISBN = (EditText) findViewById(R.id.etISBN);
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String authors = etAuthor.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String isbn = etISBN.getText().toString().trim();
                if (title.isEmpty() && authors.isEmpty() && publisher.isEmpty() && isbn.isEmpty()){
                    String message = getString(R.string.no_search_data);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else {
                    URL queryURL = ApiUtil.buildUrl(title, authors, publisher, isbn);

                    //SharedPreferences
                    Context context = getApplicationContext();
                    int position = SpUtil.getPreferenceInt(context, SpUtil.POSITION);
                    if (position == 0 || position == 5) {
                        position = 1;
                    } else{
                        position++;
                    }
                    String key = SpUtil.QUERY + String.valueOf(position);
                    String value = title + "," + authors + "," + publisher + "," + isbn;
                    SpUtil.setPreferenceString(context, key, value);
                    SpUtil.setPreferenceInt(context, SpUtil.POSITION, position);

                    Intent intent = new Intent(getApplicationContext(), BookListActivity.class);
                    intent.putExtra("query", queryURL.toString());
                    startActivity(intent);
                }
            }
        });
    }
}
