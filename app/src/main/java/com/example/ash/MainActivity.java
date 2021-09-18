package com.example.ash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Message> messages;
    private RecyclerView recyclerView;
    private recyclerAdapter adapter;
    private ImageButton sendButton;
    private EditText msgInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        // Set RecyclerView layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Set an animation
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        messages = new ArrayList<>();
        adapter = new recyclerAdapter(messages);
        recyclerView.setAdapter(adapter);

        sendButton = (ImageButton) findViewById(R.id.msgButton);
        msgInput = (EditText) findViewById(R.id.msgInput);
        
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msgInput.getText().toString();
                if(message.length() != 0){
                    messages.add(new Message(true, message));
                    int newPosition = messages.size() - 1;
                    adapter.notifyItemInserted(newPosition);
                    recyclerView.scrollToPosition(newPosition);
                    msgInput.setText("");
                    getRespose(message);
                }
            }
        });


    }

    private void getRespose(String message) {
        messages.add(new Message(false, message));
        int newPosition = messages.size() - 1;
        adapter.notifyItemInserted(newPosition);
        recyclerView.scrollToPosition(newPosition);
    }
}