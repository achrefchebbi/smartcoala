package com.example.smartcoala1;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcoala1.DashboardFragment;
import com.example.smartcoala1.R;

import java.util.Locale;

public class LayoutVoiceControlActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_voice_control);

        // Initialize SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        ImageView Voice = findViewById(R.id.Voice);
        ImageView Manuel = findViewById(R.id.Manuel);

        Voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechRecognition();
            }
        });

        Manuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDashboard();
            }
        });
    }

    private void startSpeechRecognition() {
        // Create an intent for speech recognition
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // Start speech recognition
        speechRecognizer.startListening(intent);
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, DashboardFragment.class);
        startActivity(intent);
    }
}
