package com.example.owner.numberguessinggame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    List<Integer> a = new ArrayList<>();
    List<Integer> notguess = new ArrayList<>();
    List<Integer> maybeAns = new ArrayList<>();
    List<Integer> removedAns = new ArrayList<>();
    int ans[] = new int[2];
    int guess[] = new int[2];
    ListView itemsListView;
    TextView textAns;
    ArrayList<Answer> list;
    Listviewadapter adapter;
    int guessIndex;
    int count =0;
    Random ran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.btn);
        textAns = (TextView)findViewById(R.id.textAns);
        itemsListView  = (ListView)findViewById(R.id.listView);
        ran = new Random();
        list = new ArrayList<>();
        adapter = new Listviewadapter(this, list);

        itemsListView.setAdapter(adapter);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearAnswer();
                count = 0;
                //a = new int[5];
                for(int i =0;i < 5;i++){
                    a.add(i+1);
                }
                generateRandomAns();
                String result = StartGuessing(a);
                list.add(new Answer(result));
                adapter.notifyDataSetChanged();
                notguess.clear();

            }
        });
    }


    void generateRandomAns(){
        textAns.setText("");
        ans[0] = a.get(ran.nextInt(a.size()));
        ans[1] = a.get(ran.nextInt(a.size()));
        if (ans[1] == ans[0]){
            Iterator<Integer> Iterator = a.iterator();
            while(Iterator.hasNext()){
                int e = Iterator.next();
                if(e == ans[0]){
                    Iterator.remove();
                }
            }
            if(a.size()!=0) {
                ans[1] = a.get(ran.nextInt(a.size()));
                a.add(ans[0]);
            }
        }
        if(ans[0]>ans[1]){
            int temp = ans[0];
            ans[0] = ans[1];
            ans[1] = temp;
        }

        textAns.setText(ans[0] + " " + ans[1]);

    }

    String StartGuessing(List a){

        notguess = a;
        if(count == 0) {
            guess[0] = notguess.get(ran.nextInt(notguess.size()));
            guess[1] = notguess.get(ran.nextInt(notguess.size()));

            if (guess[1] == guess[0]) {
                Iterator<Integer> Iterator = notguess.iterator();
                while (Iterator.hasNext()) {
                    int e = Iterator.next();
                    if (e == guess[0]) {
                        Iterator.remove();
                    }
                }
                if (notguess.size() != 0) {
                    guess[1] = notguess.get(ran.nextInt(notguess.size()));
                    notguess.add(guess[0]);
                }

            }
        }else {
            guess[1 - guessIndex] = notguess.get(ran.nextInt(notguess.size()));
            if (guess[1 - guessIndex] == guess[guessIndex]) {
                Iterator<Integer> Iterator = notguess.iterator();
                while (Iterator.hasNext()) {
                    int e = Iterator.next();
                    if (e == guess[guessIndex]) {
                        Iterator.remove();
                    }
                }
                if (notguess.size() != 0) {
                    guess[1 - guessIndex] = notguess.get(ran.nextInt(notguess.size()));
                    notguess.add(guess[guessIndex]);
                }

            }
        }
        /*if(guess[0]>guess[1]){
            int temp = guess[0];
            guess[0] = guess[1];
            guess[1] = temp;
        }*/
        if ((guess[0]!=ans[0]&&guess[1]!=ans[1])&&(guess[1]!=ans[0]&&guess[0]!=ans[1])){
            list.add(new Answer("我猜是"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"全錯0C QAQ"));
            adapter.notifyDataSetChanged();
            Iterator<Integer> Iterator = notguess.iterator();
            while(Iterator.hasNext()){
                int e = Iterator.next();
                if(e == guess[0]||e == guess[1]){
                    Iterator.remove();
                }
            }
            if(count==1){
                guess[guessIndex] = removedAns.get(0);
                removedAns.remove(0);
            }else if(count>1){
                notguess.add(removedAns.get(0));
                removedAns.remove(0);
            }
            return StartGuessing(notguess);
        }
        else if ((guess[0]==ans[0]&&guess[1]!=ans[1])||(guess[0]!=ans[0]&&guess[1]==ans[1])||(guess[1]!=ans[0]&&guess[0]==ans[1])||(guess[1]==ans[0]&&guess[0]!=ans[1])){
            count++;
            list.add(new Answer("我猜是"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"對一個1C >_<"));
            adapter.notifyDataSetChanged();
            if(count ==1){
                Iterator<Integer> Iterator = notguess.iterator();
                while(Iterator.hasNext()){
                    int e = Iterator.next();
                    guessIndex = ran.nextInt(1);
                    if(e == guess[1 - guessIndex]){
                        Iterator.remove();
                        removedAns.add(e);
                    }
                }
            }else {
                Iterator<Integer> Iterator = notguess.iterator();
                while(Iterator.hasNext()) {
                    int e = Iterator.next();
                    if (e == guess[1 - guessIndex]) {
                        Iterator.remove();
                        removedAns.add(e);
                    }
                }
            }

            return StartGuessing(notguess);
        }
        else {
            return "我猜是"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"答對了2C!! ^ ^ ";
        }

    }


}
