package com.pm.estrello.cirro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.pm.estrello.cirro.Objects.Comment;
import com.pm.estrello.cirro.R;

import java.text.MessageFormat;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> comments;
    private Context context;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View commentView = layoutInflater.inflate(R.layout.comment_layout, parent, false);
        return new ViewHolder(commentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.userName.setText(MessageFormat.format("{0} {1}",
                comment.getFirstName(), comment.getLastName()));
        holder.comments.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView userName, comments;
        AppCompatImageView userPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.commentUserName);
            comments = itemView.findViewById(R.id.comments);
            userPic = itemView.findViewById(R.id.commentUserPic);
        }
    }
}
