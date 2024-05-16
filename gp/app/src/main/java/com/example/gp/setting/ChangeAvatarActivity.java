package com.example.gp.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;

import com.example.gp.BaseActivity;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.database.UserDB;
import com.example.gp.databinding.ActivityChangeAvatarBinding;

/**
 * Activity class for changing the avatar
 * @author Yulong Chen
 * @since 2024-05-14
 */
public class ChangeAvatarActivity extends BaseActivity {
    private final String TAG  = "CAA1000000";
    private com.example.gp.databinding.ActivityChangeAvatarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChangeAvatarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpTitleBar(R.layout.activity_change_avatar,"Avatar");


        binding.ivSampleAvatar1.setOnClickListener(this);
        binding.ivSampleAvatar2.setOnClickListener(this);
        binding.ivSampleAvatar3.setOnClickListener(this);
        binding.ivSampleAvatar4.setOnClickListener(this);
        binding.ivSampleAvatar5.setOnClickListener(this);
        binding.ivSampleAvatar6.setOnClickListener(this);
        binding.ivSampleAvatar7.setOnClickListener(this);
        binding.ivSampleAvatar8.setOnClickListener(this);

    }

    /**
     * Set avatar by choosing sample
     * @param v which button is clicked
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == binding.ivSampleAvatar1) {
            showConfirmationDialog(R.mipmap.sample_avatar_1);
        } else if (v == binding.ivSampleAvatar2) {
            showConfirmationDialog(R.mipmap.sample_avatar_2);
        } else if (v == binding.ivSampleAvatar3) {
            showConfirmationDialog(R.mipmap.sample_avatar_3);
        } else if (v == binding.ivSampleAvatar4) {
            showConfirmationDialog(R.mipmap.sample_avatar_4);
        } else if (v == binding.ivSampleAvatar5) {
            showConfirmationDialog(R.mipmap.sample_avatar_5);
        } else if (v == binding.ivSampleAvatar6) {
            showConfirmationDialog(R.mipmap.sample_avatar_6);
        } else if (v == binding.ivSampleAvatar7) {
            showConfirmationDialog(R.mipmap.sample_avatar_7);
        } else if (v == binding.ivSampleAvatar8) {
            showConfirmationDialog(R.mipmap.sample_avatar_8);
        }
    }

    /**
     * Toast the dialog to confirm or cancel the change
     * @param avatar Avatar waiting to be changed
     */
    private void showConfirmationDialog(int avatar) {
        Object object = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to change avatar?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Database.changeAvatar(String.valueOf(avatar), object,"showResult");
                        Log.d(TAG, "onClick: call method");
                        // Working when the call back method goes wrong
                        binding.ivCurrentAvatar.setImageResource(avatar);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * call back method
     * show the changing operation result. If succeed, set the new avatar
     * @param isSuccess if the changing operation is successful or not
     * @param object the new avatarUUID
     */
    public void showResult(boolean isSuccess, Object object){
        if (!isSuccess) {
            if (object == null) {
                ToastUtil.show(this,"There seems some problem happened, Please try later");
            } else {
                Log.d(TAG, "showResult: false");
                ToastUtil.show(this,object.toString());
            }
        } else {
            Log.d(TAG, "showResult: true");
            String newAvatarUUID = (String) object;
            int newAvatar = Integer.parseInt(newAvatarUUID);
            binding.ivCurrentAvatar.setImageResource(newAvatar);
        }
    }

    /**
     * Reload the avatar to ensure activity shows the latest avatar
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadAvatarData();
    }

    /**
     * get the avatar data from the firebase
     */
    private void loadAvatarData() {
        String avatarUUID = UserDB.getInstance().getAvatarUUID();
        int userAvatar = Integer.parseInt(avatarUUID);
        Log.d("loadAvatarData", "loadAvatarData: "+ avatarUUID);
        binding.ivCurrentAvatar.setImageResource(userAvatar);
    }
}