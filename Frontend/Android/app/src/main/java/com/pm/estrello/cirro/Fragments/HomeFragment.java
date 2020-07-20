package com.pm.estrello.cirro.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.pm.estrello.cirro.Adapters.PostAdapter;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.DataStore;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Objects.Post;
import com.pm.estrello.cirro.R;

import org.json.JSONObject;

import java.util.List;

public class HomeFragment extends Fragment implements HTTPConnector.ResponseListener {
    private RecyclerView _postsList;
    private List<Post> postList;
    private ProgressDialog _progressDialog;
    private String TAG_CLASS = HomeFragment.class.getSimpleName();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        _postsList = inflate.findViewById(R.id.postLists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        _postsList.setLayoutManager(layoutManager);
        RecyclerView.Adapter<PostAdapter.ViewHolder> adapter = new PostAdapter(requireContext(), postList);
        _postsList.setAdapter(adapter);
        _progressDialog = new ProgressDialog(requireContext());
        _progressDialog.setTitle(Constants.LOADING_MESSAGE);
        _progressDialog.setCancelable(false);
        return inflate;
    }

    /**
     * Method to get the posts
     */
    private void getPosts() {
        String url = Constants.API_URL + "posts?initial_value=0&post_limit=20";
        HTTPConnector connector = new HTTPConnector(requireContext(), url,
                DataStore.getData(requireContext(), Constants.API_JWT_TOKEN_KEY), this);
        connector.makeQuery(TAG_CLASS);
        _progressDialog.show();
    }

    @Override
    public void onResponse(JSONObject response) {
        _progressDialog.dismiss();
        try {

        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
            Messages.toast(requireContext(), Constants.API_RESPONSE_ERROR, false);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        _progressDialog.dismiss();
        Messages.log(TAG_CLASS, error.toString());
        Messages.toast(requireContext(), Constants.API_RESPONSE_ERROR, false);
    }
}