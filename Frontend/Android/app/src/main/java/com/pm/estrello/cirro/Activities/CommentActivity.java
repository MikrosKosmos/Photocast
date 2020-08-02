package com.pm.estrello.cirro.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.R;

import org.json.JSONObject;

public class CommentActivity extends AppCompatActivity implements HTTPConnector.ResponseListener {
    private RecyclerView _commentList;
    private EditText _comment;
    private ImageView _postComment;
    private ProgressDialog progressDialog;
    private String TAG_CLASS = CommentActivity.class.getSimpleName();
    private int requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initializeViews();
    }

    /**
     * Method to initialize the views.
     */
    private void initializeViews() {
        _comment = findViewById(R.id.comment);
        _commentList = findViewById(R.id.commentList);
        _postComment = findViewById(R.id.postComment);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(Constants.LOADING_MESSAGE);
        progressDialog.setCancelable(false);
        _commentList.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.dismiss();
        try {

        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
            Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        Messages.log(TAG_CLASS, error.toString());
        Messages.toast(this, Constants.API_RESPONSE_ERROR, false);
    }
}