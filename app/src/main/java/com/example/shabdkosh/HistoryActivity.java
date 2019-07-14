package com.example.shabdkosh;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {

    SQLiteHelper helper;
    Cursor res;
    List<Word> storageCopy = new ArrayList<>();
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private static final String TAG = "HistoryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toast.makeText(getApplicationContext(), "Fetching Data", Toast.LENGTH_SHORT).show();
        helper = new SQLiteHelper(this);
        res = helper.getHistory();

        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Nothing in Search History", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Loading History", Toast.LENGTH_SHORT).show();
            while (res.moveToNext()) {
                Word newWord = new Word();
                newWord.setWord(res.getString(0));
                newWord.setEtymology(res.getString(1));
                storageCopy.add(newWord);
            }
            initRecyclerView();
        }
    }
    private void initRecyclerView() {
        Log.d(TAG, "initialising Recycler View");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this, storageCopy);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void deleteDb(View view) {
        helper.Clear();
        this.recreate();
    }
}
