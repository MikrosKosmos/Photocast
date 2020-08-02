package com.pm.estrello.cirro.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.pm.estrello.cirro.Adapters.PostAdapter;
import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.DataStore;
import com.pm.estrello.cirro.Helpers.HTTPConnector;
import com.pm.estrello.cirro.Helpers.Messages;
import com.pm.estrello.cirro.Objects.Customer;
import com.pm.estrello.cirro.Objects.Post;
import com.pm.estrello.cirro.Objects.Vendor;
import com.pm.estrello.cirro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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
        _progressDialog = new ProgressDialog(requireContext());
        _progressDialog.setTitle(Constants.LOADING_MESSAGE);
        _progressDialog.setCancelable(false);
        getPosts();
        return inflate;
    }

    /**
     * Method to get the user id.
     *
     * @return: the user id.
     */
    private int getUserId() {
        try {
            JSONObject jsonObject = new JSONObject(DataStore.getData(requireContext(),
                    Constants.USER_PROFILE));
            String role = DataStore.getData(requireContext(), Constants.ROLE);
            if (role.equals(Constants.ROLE_CUSTOMER)) {
                Customer customer = new Customer(jsonObject);
                return customer.getId();
            } else if (role.equals(Constants.ROLE_VENDOR)) {
                Vendor vendor = new Vendor(jsonObject);
                return vendor.getId();
            }
        } catch (Exception e) {
            Messages.log(TAG_CLASS, e.toString());
        }
        return -1;
    }

    /**
     * Method to get the posts
     */
    private void getPosts() {

        String url = Constants.API_URL + "posts?initial_value=0&post_limit=20&id=" + getUserId();
        HTTPConnector connector = new HTTPConnector(requireContext(), url,
                DataStore.getData(requireContext(), Constants.API_JWT_TOKEN_KEY), this);
        connector.makeQuery(TAG_CLASS);
        _progressDialog.show();
    }

    @Override
    public void onResponse(JSONObject response) {
        _progressDialog.dismiss();
        try {
            JSONArray posts = response.getJSONArray(Constants.API_RESPONSE_KEY);
            postList = new ArrayList<>();
            for (int i = 0; i < posts.length(); i++) {
                JSONObject onePost = posts.getJSONObject(i);
                Post post = new Post(onePost.getInt(Constants.ID),
                        onePost.getString(Constants.FIRST_NAME) + " " + onePost.getString(Constants.LAST_NAME),
                        onePost.getString(Constants.POST_DESCRIPTION),
                        onePost.getInt(Constants.LIKE_COUNT),
                        onePost.getInt(Constants.COMMENT_COUNT),
                        onePost.getString(Constants.IMAGE_URL));
                postList.add(post);
            }
            PostAdapter adapter = new PostAdapter(requireContext(), postList);
            _postsList.setAdapter(adapter);
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