package com.example.piyus.mymandirtask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyus.mymandirtask.Adapters.MyPostsAdapter;
import com.example.piyus.mymandirtask.Model.PostsItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String URL_DATA = "http://staging.mymandir.com/dummy";
    private String text = "";
    private int commentCount = 0, likeCount = 0;
    private String title = "";
    private long createdAt = 0;
    private String attachmentImageUrl = "";
    private String attachmentType = "";
    private String userName = "", userProfileImageUrl = "";
    private String audioVideoUrl;

    View v;
    private RecyclerView recyclerView;
    private List<PostsItems> pstItemLst;
    RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.home_recycler_view);
        MyPostsAdapter myPostsAdapter = new MyPostsAdapter(pstItemLst, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myPostsAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pstItemLst = new ArrayList<>();
        loadPostsData();
    }

    private void loadPostsData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("Sameer1", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        text = jsonObject.getString("text");
                        commentCount = jsonObject.getInt("commentCount");
                        likeCount = jsonObject.getInt("likeCount");
                        title = jsonObject.getString("title");
                        createdAt = jsonObject.getLong("createdAt");

                        try{
                            JSONArray jsonArray1 = jsonObject.getJSONArray("attachments");
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

                            attachmentImageUrl = jsonObject1.getString("url");
                            attachmentType = null;
                            attachmentType = jsonObject1.getString("type");
                            if (attachmentType.equals("audio") || attachmentType.equals("video")) {
                                attachmentImageUrl = jsonObject1.getString("thumbnail_url");
                                audioVideoUrl = jsonObject1.getString("url");
                                Log.d("Sameer1", audioVideoUrl);
                            }

                        }catch(Exception e){
                            Log.d("Exception1", e.toString() + " at index: " + i);
                        }

                        try{
                            JSONObject jsonObject2 = jsonObject.getJSONObject("sender");
                            userName = jsonObject2.getString("friendlyId");
                            userProfileImageUrl = jsonObject2.getString("imageUrl");
                        }catch(Exception e){
                            Log.d("Exception1", e.toString() + "at index: " + i);
                        }
                        PostsItems postsItemsObj = new PostsItems(
                                text,
                                commentCount,
                                likeCount,
                                title,
                                userProfileImageUrl,
                                userName,
                                createdAt,
                                attachmentImageUrl,
                                attachmentType,
                                audioVideoUrl
                        );

                        pstItemLst.add(postsItemsObj);
                    }
                    adapter = new MyPostsAdapter(pstItemLst, getContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Sameer", error.toString());
            }
        });
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }
}
