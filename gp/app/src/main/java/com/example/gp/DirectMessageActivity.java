package com.example.gp;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gp.Adapter.DirectMessageAdapter;
import com.example.gp.databinding.ActivityDirectMessageBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for direct message sending
 * @author Yulong Chen
 * @since 2024-05-14
 */
public class DirectMessageActivity extends BaseActivity implements TextView.OnEditorActionListener {

    private com.example.gp.databinding.ActivityDirectMessageBinding binding;
    private DirectMessageAdapter mDirectAdapter;
    private List<String> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDirectMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpTitleBar(R.layout.activity_direct_message,"Chat");

        binding.btnSendMessage.setOnClickListener(this);
        binding.etMessageInput.setOnEditorActionListener(this);
        messages = new ArrayList<>();
        mDirectAdapter = new DirectMessageAdapter(messages);
        binding.rvDirectMessageConversation.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDirectMessageConversation.setAdapter(mDirectAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == binding.btnSendMessage) {
            sendMessage();
        }
    }

    /**
     * send message to other users
     */
    private void sendMessage() {
        String message = binding.etMessageInput.getText().toString();
        messages.add(message);
        // Notify adapter that data set has changed
        mDirectAdapter.notifyDataSetChanged();
        // Scroll RecyclerView to bottom to show latest message
        binding.rvDirectMessageConversation.smoothScrollToPosition(messages.size() - 1);
        // Clear EditText
        binding.etMessageInput.getText().clear();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Log.d("onEditorAction", "onEditorAction: ");
            binding.btnSendMessage.performClick();
            return true;
        }
        return false;
    }
}