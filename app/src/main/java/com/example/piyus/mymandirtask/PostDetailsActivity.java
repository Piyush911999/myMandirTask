package com.example.piyus.mymandirtask;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PostDetailsActivity extends AppCompatActivity {

    DownloadManager downloadManager;

    String userName, title, text, likeCount, commentCount, date, time,
            userImageUrl, attachmentImageUrl, attachmentType, audioVideoUrl;

    private ImageView ivPostsImageDetails;
    private ImageView iv_user_profile_imageDetails;

    private TextView tvPostsTextDetails;
    private TextView tvLikesCountDetails;
    private TextView tvCommentsCountDetails;
    private TextView tvPostsTitleDetails;
    private TextView tvPostsUserNameDetails;
    private TextView tvPostsTimeDetails;
    private TextView tvPostsDateDetails;

    private LinearLayout playIconLinLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        ivPostsImageDetails = findViewById(R.id.ivPostsImageDetails);
        iv_user_profile_imageDetails = findViewById(R.id.iv_user_profile_imageDetails);
        tvPostsTextDetails = findViewById(R.id.tvPostsTextDetails);
        tvLikesCountDetails = findViewById(R.id.tvLikesCountDetails);
        tvCommentsCountDetails = findViewById(R.id.tvCommentsCountDetails);
        tvPostsTitleDetails = findViewById(R.id.tvPostsTitleDetails);
        tvPostsUserNameDetails = findViewById(R.id.tvPostsUserNameDetails);
        tvPostsTimeDetails = findViewById(R.id.tvPostsTimeDetails);
        tvPostsDateDetails = findViewById(R.id.tvPostsDateDetails);
        playIconLinLayout = findViewById(R.id.ivPlayIconLinLayoutDetails);

        Intent intent = getIntent();
        userName = intent.getStringExtra("USER_NAME");
        title = intent.getStringExtra("POST_TITLE");
        text = intent.getStringExtra("POST_TEXT");
        likeCount = intent.getStringExtra("LIKE_COUNT");
        commentCount = intent.getStringExtra("COMMENT_COUNT");
        date = intent.getStringExtra("DATE");
        time = intent.getStringExtra("TIME");
        userImageUrl = intent.getStringExtra("USER_IMAGE");
        attachmentImageUrl = intent.getStringExtra("ATTACHMENT_IMAGE");
        attachmentType = intent.getStringExtra("ATTACHMENT_TYPE");
        audioVideoUrl = intent.getStringExtra("AUDIO_VIDEO_URL");

        loadActivityViewWithDataFromIntent();

    }

    private void initiateDownloadmanager(String urlOfLink) {
        Toast.makeText(getApplicationContext(), "Downloading Attachment, type: " + attachmentType + " \n\n\t\t\t\tCheck Notification Bar", Toast.LENGTH_LONG).show();
        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(urlOfLink);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }

    private void loadActivityViewWithDataFromIntent() {
        tvPostsUserNameDetails.setText(userName);
        tvPostsTitleDetails.setText(title);
        tvPostsTextDetails.setText(text);
        tvLikesCountDetails.setText(likeCount);
        tvCommentsCountDetails.setText(commentCount);
        tvPostsDateDetails.setText(date);
        tvPostsTimeDetails.setText(time);


        Picasso.get()
                .load(userImageUrl)
                .into(iv_user_profile_imageDetails);

        if (attachmentType.equals("video")) {
            initiateDownloadmanager(audioVideoUrl);
            playIconLinLayout.setVisibility(View.VISIBLE);
            if(attachmentType.equals("audio")) {
                playIconLinLayout.setVisibility(View.VISIBLE);
            }
        }

        Picasso.get()
                .load(attachmentImageUrl)
                .into(ivPostsImageDetails);

    }
}
