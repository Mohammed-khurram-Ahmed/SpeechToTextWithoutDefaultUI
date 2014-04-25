package com.example.speechtest3;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SRActivity extends Activity implements RecognitionListener {

	Context context;
	private SpeechRecognizer speech;

	ProgressDialog progressDialog;
	String tag = getPackageName().toString();
	TextView textView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sr);
		context = SRActivity.this;
		Button speakBtn = (Button) findViewById(R.id.button1);
		textView = (TextView) findViewById(R.id.text1);
		speakBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				speech = SpeechRecognizer
						.createSpeechRecognizer(SRActivity.this);
				speech.setRecognitionListener(SRActivity.this);

				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
						"en");
				intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
						SRActivity.this.getPackageName());

				// workin fine
				intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

				// use judiciously! Additionally, depending on the recognizer
				// implementation, these values may have no effect.
				intent.putExtra(
						RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
						3000L);

				// use judiciously! Additionally, depending on the recognizer
				// implementation, these values may have no effect.
				intent.putExtra(
						RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,
						5000L);

				speech.startListening(intent);
				progressDialog = new ProgressDialog(context);
				progressDialog.show();
				progressDialog.setMessage("My Custom Dialog here");
			}
		});

		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Stop Listning
				speech.stopListening();

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.speech.RecognitionListener#onBeginningOfSpeech()
	 */
	@Override
	public void onBeginningOfSpeech() {
		Log.e(tag, "onBeginningOfSpeech");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.speech.RecognitionListener#onBufferReceived(byte[])
	 */
	@Override
	public void onBufferReceived(byte[] arg0) {
		// Log.e(tag, "onBufferReceived");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.speech.RecognitionListener#onEndOfSpeech()
	 */
	@Override
	public void onEndOfSpeech() {
		progressDialog.dismiss();

		Log.e(tag, "onEndOfSpeech");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.speech.RecognitionListener#onError(int)
	 */
	@Override
	public void onError(int error) {

		String mError = "";
		switch (error) {
		case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
			mError = " network timeout";
			break;
		case SpeechRecognizer.ERROR_NETWORK:
			mError = " network";
			return;
		case SpeechRecognizer.ERROR_AUDIO:
			mError = " audio";
			break;
		case SpeechRecognizer.ERROR_SERVER:
			mError = " server";
			break;
		case SpeechRecognizer.ERROR_CLIENT:
			mError = " client";
			break;
		case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
			mError = " speech time out";
			break;
		case SpeechRecognizer.ERROR_NO_MATCH:
			mError = " no match";
			break;
		case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
			mError = " recogniser busy";
			break;
		case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
			mError = " insufficient permissions";
			break;

		}
		textView.setText(mError);
		Log.e(tag, "onError " + mError);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.speech.RecognitionListener#onEvent(int, android.os.Bundle)
	 */
	@Override
	public void onEvent(int eventType, Bundle params) {
		Log.e(tag, "onEvent");

	}

	/*
	 *
	 */
	@Override
	public void onPartialResults(Bundle partialResults) {
		Log.e(tag, "onPartialResults");

	}

	/*
	 * Called when the endpointer is ready for the user to start speaking.
	 */
	@Override
	public void onReadyForSpeech(Bundle params) {
		Log.e(tag, "onReadyForSpeech");
	}

	/*
	 * Called when recognition results are ready.
	 */
	@Override
	public void onResults(Bundle results) {
		Log.e(tag, "onResults");
		ArrayList<String> matches = results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		textView.setText("" + matches.get(0));
		Log.e(tag, "onResults" + matches.toString());

	}

	/*
	 * The sound level in the audio stream has changed. There is no guarantee
	 * that this method will be called.
	 */
	@Override
	public void onRmsChanged(float rmsdB) {
		// Log.e(tag, "onRmsChanged");
	}

}
