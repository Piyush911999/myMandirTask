package com.example.piyus.mymandirtask.Model;

public class PostsItems {
    private String postsText;
    private int commentsCount;
    private int likesCount;
    private String title;
    private String attachmentUrl;
    private String attachmentType;
    private String senderImageUrl;
    private String userName;
    private long createdAt;
    private String audioVideoUrl;

    public PostsItems(String postsText, int commentsCount, int likesCount, String title, String senderImageUrl,
                      String userName, long createdAt, String attachmentUrl, String attachmentType, String audioVideoUrl) {
        this.postsText = postsText;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
        this.senderImageUrl = senderImageUrl;
        this.title = title;
        this.userName = userName;
        this.createdAt = createdAt;
        this.attachmentType = attachmentType;
        this.attachmentUrl = attachmentUrl;
        this.audioVideoUrl = audioVideoUrl;
    }

    public String getPostsText() {
        return postsText;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getUserName() {
        return userName;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public String getAudioVideoUrl() {
        return audioVideoUrl;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public String getTitle() {
        return title;
    }
}