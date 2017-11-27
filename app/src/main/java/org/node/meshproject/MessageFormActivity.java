package org.node.meshproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MessageFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_form);
    }

    public void onButtonClick(View view) {

        EditText uuidEditText = (EditText) findViewById(R.id.uuidEditText);
        EditText messageEditText = (EditText) findViewById(R.id.messageEditText);
        String uuid = uuidEditText.getText().toString();
        String message = messageEditText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("uuid", uuid);
        intent.putExtra("message", message);
        setResult(RESULT_OK, intent);
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        finish();
    }
}
