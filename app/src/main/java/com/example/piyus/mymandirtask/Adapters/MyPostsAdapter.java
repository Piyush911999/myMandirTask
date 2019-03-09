package com.example.piyus.mymandirtask.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.piyus.mymandirtask.Model.PostsItems;
import com.example.piyus.mymandirtask.PostDetailsActivity;
import com.example.piyus.mymandirtask.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.MyViewHolder> {

    private List<PostsItems> postsData;
    private Context context;
    private String date = "", time = "";

    public MyPostsAdapter(List<PostsItems> postsItemsList, Context context) {
        this.postsData = postsItemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.posts_item_card_view, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        final PostsItems postsItems1 = postsData.get(position);
        viewHolder.tvPostsUserName.setText(postsItems1.getUserName());
        viewHolder.tvPostsTitle.setText(postsItems1.getTitle());
        viewHolder.tvPostsText.setText(postsItems1.getPostsText());
        viewHolder.tvLikesCount.setText(Integer.toString(postsItems1.getLikesCount()));
        viewHolder.tvCommentsCount.setText(Integer.toString(postsItems1.getCommentsCount()));

        date = findDate(postsItems1.getCreatedAt());
        viewHolder.tvPostsDate.setText(date);
        time = findTime(postsItems1.getCreatedAt());
        viewHolder.tvPostsTime.setText(time);

        Picasso.get()
                .load(postsItems1.getSenderImageUrl())
                .into(viewHolder.iv_user_profile_image);
        if (postsItems1.getAttachmentType().equals("video") || postsItems1.getAttachmentType().equals("audio")) {
            viewHolder.ivPlayIconLinLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivPlayIconLinLayout.setVisibility(View.INVISIBLE);
        }
        Picasso.get()
                .load(postsItems1.getAttachmentUrl())
                .into(viewHolder.ivPostsImage);

        viewHolder.cardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharedIntent = new Intent(context, PostDetailsActivity.class);
                sharedIntent.putExtra("USER_NAME", postsItems1.getUserName());
                sharedIntent.putExtra("POST_TITLE", postsItems1.getTitle());
                sharedIntent.putExtra("POST_TEXT", postsItems1.getPostsText());
                sharedIntent.putExtra("LIKE_COUNT", Integer.toString(postsItems1.getLikesCount()));
                sharedIntent.putExtra("COMMENT_COUNT", Integer.toString(postsItems1.getCommentsCount()));
                sharedIntent.putExtra("DATE", date);
                sharedIntent.putExtra("TIME", time);
                sharedIntent.putExtra("USER_IMAGE", postsItems1.getSenderImageUrl());
                sharedIntent.putExtra("ATTACHMENT_IMAGE", postsItems1.getAttachmentUrl());
                sharedIntent.putExtra("ATTACHMENT_TYPE", postsItems1.getAttachmentType());
                sharedIntent.putExtra("AUDIO_VIDEO_URL", postsItems1.getAudioVideoUrl());

                // to avoid crashing during intent transition
                sharedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sharedIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvPostsText;
        private TextView tvLikesCount;
        private TextView tvCommentsCount;
        private TextView tvPostsTitle;
        private TextView tvPostsUserName;
        private TextView tvPostsTime;
        private TextView tvPostsDate;
        private ImageView ivPostsImage;
        private ImageView iv_user_profile_image;
        private LinearLayout ivPlayIconLinLayout;
        private RelativeLayout cardContainer;

        private MyViewHolder(View itemView) {
            super(itemView);

            tvPostsText = itemView.findViewById(R.id.tvPostsText);
            tvCommentsCount = itemView.findViewById(R.id.tvCommentsCount);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            tvPostsTitle = itemView.findViewById(R.id.tvPostsTitle);
            tvPostsUserName = itemView.findViewById(R.id.tvPostsUserName);
            tvPostsTime = itemView.findViewById(R.id.tvPostsTime);
            tvPostsDate = itemView.findViewById(R.id.tvPostsDate);
            ivPostsImage = itemView.findViewById(R.id.ivPostsImage);
            iv_user_profile_image = itemView.findViewById(R.id.iv_user_profile_image);

            ivPlayIconLinLayout = itemView.findViewById(R.id.ivPlayIconLinLayout);
            cardContainer = itemView.findViewById(R.id.cardContainer);

        }
    }

    private String findTime(long unixMilliSeconds) {
        Date date = new Date(unixMilliSeconds);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

    private String findDate(long unixMilliSeconds) {
        Date date = new Date(unixMilliSeconds);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

}
