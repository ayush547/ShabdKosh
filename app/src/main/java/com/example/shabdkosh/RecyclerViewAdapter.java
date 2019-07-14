package com.example.shabdkosh;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Word> dataNames;
    private Context mContext;
    TextToSpeech ttobj;

    public RecyclerViewAdapter(Context mContext , List<Word> dataNames) {
        this.dataNames = dataNames;
        this.mContext = mContext;
        ttobj=new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        ttobj.setLanguage(Locale.ENGLISH);
        ttobj.setSpeechRate(0.8f);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView initials,names;
        RelativeLayout parentLayout;
        ImageButton readButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initials = itemView.findViewById(R.id.initials);
            names = itemView.findViewById(R.id.names);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            readButton = itemView.findViewById(R.id.readButton);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_layout, viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return dataNames.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d("RecyclerViewAdapter", "onBind Called.");
        viewHolder.initials.setText("" + dataNames.get(i).getWord().charAt(0));
        viewHolder.names.setText(dataNames.get(i).getWord()+"\n"+dataNames.get(i).getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0));
        viewHolder.readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttobj.speak("The Word selected is "+dataNames.get(i).getWord()+"And its Etymologies are "+dataNames.get(i).getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }

}
