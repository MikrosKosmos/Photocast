package com.pm.estrello.cirro.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.pm.estrello.cirro.Adapters.CommentAdapter;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Helpers.ParamsCreator;
import com.pm.estrello.cirro.Helpers.Utils;
import com.pm.estrello.cirro.Objects.Comment;
import com.pm.estrello.cirro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements HTTPConnector.ResponseListener {
    private RecyclerView _commentList;
    private AppCompatEditText _comment;
    private ImageView _postComment;
    private ProgressDialog _progressDialog;
    private String TAG_CLASS = CommentActivity.class.getSimpleName();
    private int requestCode = 0;
    private List<Comment> comments;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initializeViews();
        _postComment.setOnClickListener(view -> {
            if (Utils.isNotEmpty(new AppCompatEditText[]{_comment})) {
                createComment();
            }
        });
    }

    /**
     * Method to initialize the views.
     */
    private void initializeViews() {
        _comment = findViewById(R.id.comment);
        _commentList = findViewById(R.id.commentList);
        _postComment = findViewById(R.id.postComment);
        _progressDialog = new ProgressDialog(this);
        _progressDialog.setTitle(Constants.LOADING_MESSAGE);
        _progressDialog.setCancelable(false);
        _commentList.setLayoutManager(new LinearLayoutManager(this));
        getComments();
    }

    /**
     * Method to create a comment for the post.
     */
    private void createComment() {
        String url = Constants.API_URL + "comments";
        Comment comment = new Comment();
        comment.setPostId(getPostId());
        comment.setUserId(Utils.getUserId(this));
        comment.setComment(_comment.getText().toString());
        HTTPConnector connector = new HTTPConnector(this, url, Utils.getJWToken(this), this);
        connector.makeQuery(TAG_CLASS, ParamsCreator.getJSONForCreatingComment(comment));
        _progressDialog.show();
        requestCode = Constants.POST_COMMENT_CODE;
        comments.add(comment);
        adapter.notifyDataSetChanged();
    }

    /**
     * Method to get the Post id.
     *
     * @return post Id.
     */
    private int getPostId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getInt(Constants.POST_ID);
        }
        return -1;
    }

    /**
     * Method to fetch the comments for the post.
     */
    private void getComments() {
        String url = Constants.API_URL + "comments?id=" +
                Utils.getUserId(this) + "&post_id=" + getPostId();
        HTTPConnector connector = new HTTPConnector(this, url, Utils.getJWToken(this),
                this);
        connector.makeQuery(TAG_CLASS);
        requestCode = Constants.GET_COMMENT_CODE;
        _progressDialog.show();
    }

    @Override
    public void onResponse(JSONObject response) {
        _progressDialog.dismiss();
        try {
            if (requestCode == Constants.GET_COMMENT_CODE) {
                JSONArray commentsArray = response.getJSONArray(Constants.API_RESPONSE_KEY);
                comments = new ArrayList<>();
                for (int i = 0; i < commentsArray.length(); i++) {
                    JSONObject oneComment = commentsArray.getJSONObject(i);
                    comments.add(new Comment(oneComment.getInt(Constants.ID),
                            oneComment.getInt(Constants.POST_ID),
                            oneComment.getString(Constants.FIRST_NAME),
                            oneComment.getString(Constants.LAST_NAME),
                            oneComment.getInt(Constants.USER_ID),
                            oneComment.getString(Constants.ROLE),
                            oneComment.getString(Constants.COMMENT)));
                }
                adapter = new CommentAdapter(comments, this);
                _commentList.setAdapter(adapter);
            } else if (requestCode == Constants.POST_COMMENT_CODE) {
                JSONObject jsonObject = response.getJSONObject(Constants.API_RESPONSE_KEY);
                int id = jsonObject.getInt(Constants.ID);
                if (id > 0) {
                    Messages.toast(this, "Success.", false);
                } else {
                    Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
                    getComments();
                }
            }
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
            Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        _progressDialog.dismiss();
        Messages.log(TAG_CLASS, error.toString());
        Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
    }
}