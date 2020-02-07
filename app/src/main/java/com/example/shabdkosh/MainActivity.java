package com.example.shabdkosh;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//this is my main activity

public class MainActivity extends Activity implements android.support.v7.widget.SearchView.OnQueryTextListener{

    SearchView searchBox;
    TextView wordsView;
    Retrofit retrofit;
    API api;
    ProgressBar bar;
    Word wordData;
    SQLiteHelper helper;
    TextToSpeech ttobj;
    ScrollView controller;
    Cursor res;
    //need to write data from mainactivity to history and also create recycler view
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.bar);
        searchBox = findViewById(R.id.searchWord);
        controller = findViewById(R.id.wordController);
        searchBox.setOnQueryTextListener(this);
        searchBox.setIconifiedByDefault(false);
        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        wordsView = findViewById(R.id.words);
        helper = new SQLiteHelper(this);
        ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        ttobj.setLanguage(Locale.ENGLISH);
        ttobj.setSpeechRate(0.8f);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        bar.setVisibility(View.VISIBLE);
        findWord(s);
        return true;
    }

    private void findWord(final String word) {
        Call<Word> callWords = api.getData(word,API.APP_ID,API.APP_KEY);
        callWords.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                wordData = response.body();
                bar.setVisibility(View.GONE);
                if(wordData!=null&&wordData.getError()==null&&wordData.getResults().get(0).getLexicalEntries().get(0).getEntries()!=null&&wordData.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0)!=null) {
                    controller.setVisibility(View.VISIBLE);
                    wordsView.setText(wordData.getWord()+"\n\nEtymology -\n\t"+wordData.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0));
                    helper.InsertData(wordData);
                }
                else{
                    wordsView.setText("Invalid Input Sir.");
                    Toast.makeText(getApplicationContext(), "No matches Found. Try Again with a different Term", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Word> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String s) {
        controller.setVisibility(View.INVISIBLE);
        ttobj.stop();
        wordsView.setText("");
        return false;
    }

    public void outToHistory(View view) {
        Intent outToHistory = new Intent(this,HistoryActivity.class);
        startActivity(outToHistory);
    }

    public void readStuff(View view){
        if(wordData!=null)
        ttobj.speak("The Word selected is "+wordData.getWord()+"And its Etymologies are "+wordData.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0),TextToSpeech.QUEUE_FLUSH,null);
    }
}
