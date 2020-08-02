package com.pm.estrello.cirro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.DataStore;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Helpers.ParamsCreator;
import com.pm.estrello.cirro.Helpers.Utils;
import com.pm.estrello.cirro.Objects.Post;
import com.pm.estrello.cirro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> posts;
    private Context context;
    private String TAG_CLASS = PostAdapter.class.getSimpleName();

    public PostAdapter(Context context, List<Post> posts) {
        this.posts = new ArrayList<>(posts);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.post_layout, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.userName.setText(post.getUserName());
        holder.postDescription.setText(post.getDescription());
        Glide.with(context).load(post.getImageURL()).placeholder(R.drawable.loading).into(holder.postImage);
        if (post.getLikesCount() > 0)
            holder.likeCounter.setText(String.format(Locale.getDefault(), "%d Likes", post.getLikesCount()));
        else
            holder.likeCounter.setVisibility(View.INVISIBLE);
        holder.likeButton.setOnClickListener(v -> {
            post.setLikesCount(post.getLikesCount() + 1);
            String url = Constants.API_URL + "likes";
            int userId = Utils.getUserId(context);
            int postId = post.getId();
            JSONObject postLikeObject = ParamsCreator.getJSONForPostLike(userId, postId);
            String jwToken = DataStore.getData(context, Constants.API_JWT_TOKEN_KEY);
            HTTPConnector connector = new HTTPConnector(context, url, jwToken, new HTTPConnector.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(Constants.API_RESPONSE_KEY);
                        int id = jsonObject.getInt(Constants.ID);
                        if (id > 0) {
                            Messages.toast(context, "Success", false);
                        } else {
                            Messages.toast(context, Constants.API_RESPONSE_ERROR, false);
                        }
                    } catch (JSONException e) {
                        Messages.log(TAG_CLASS, e.toString());
                        Messages.toast(context, Constants.API_RESPONSE_ERROR, false);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Messages.log(TAG_CLASS, error.toString());
                }
            });
            connector.makeQuery(postLikeObject, true);
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView profileImage;
        AppCompatTextView userName;
        AppCompatImageView postImage;
        AppCompatTextView postDescription;
        AppCompatTextView likeCounter;
        AppCompatImageView likeButton;
        AppCompatImageView commentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.socialMediaProfilePic);
            userName = itemView.findViewById(R.id.socialMediaUserName);
            postImage = itemView.findViewById(R.id.postImage);
            postDescription = itemView.findViewById(R.id.postDescription);
            likeCounter = itemView.findViewById(R.id.likeCounter);
            likeButton = itemView.findViewById(R.id.postLike);
            commentButton = itemView.findViewById(R.id.postComment);
        }
    }
}
