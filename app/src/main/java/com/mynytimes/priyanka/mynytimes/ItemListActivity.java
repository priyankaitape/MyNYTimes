package com.mynytimes.priyanka.mynytimes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mynytimes.priyanka.mynytimes.models.NewsItem;
import com.mynytimes.priyanka.mynytimes.models.TopNewsResponse;
import com.mynytimes.priyanka.mynytimes.rest.ApiClient;
import com.mynytimes.priyanka.mynytimes.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a top list of News.
 */
public class ItemListActivity extends AppCompatActivity {

    private static final String TAG = ItemListActivity.class.getSimpleName();
    private boolean mTwoPane; //for handling two-pane mode for tablet device
    private final static String API_KEY = "apNRXNGDZFjcmSvogfEOGNXmTwwnZ0f5";// Api key for our registered account
    private List<NewsItem> newsItemList = new ArrayList<>();
    View recyclerView;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.item_list);
        final TextView errorView = (TextView) findViewById(R.id.errorView);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        if(isNetworkAvailable()) {
            Call<TopNewsResponse> call = apiService.getTopRatedNews(API_KEY);
            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<TopNewsResponse>() {

                @Override
                public void onResponse(Call<TopNewsResponse> call, Response<TopNewsResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    TopNewsResponse topNewsResponse = response.body();
                    Log.d(TAG, "Number of news received: " + topNewsResponse.getNum_results());
                    for (int i = 0; i < topNewsResponse.getNewsItemList().size(); i++) {
                        newsItemList.add(topNewsResponse.getNewsItemList().get(i));
                    }

                    assert recyclerView != null;
                    setupRecyclerView((RecyclerView) recyclerView);
                }

                @Override
                public void onFailure(Call<TopNewsResponse> call, Throwable t) {
                    // Log error here since request failed
                    progressBar.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    errorView.setText(t.toString());
                    Log.e(TAG, t.toString());
                }
            });

        }else{
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(getResources().getString(R.string.no_internet));
        }

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(newsItemList));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<NewsItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<NewsItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTitle.setText(mValues.get(position).getNewsTitle());
            holder.mSubtitle.setText(mValues.get(position).getNewsSubtitle());
            holder.mDate.setText(mValues.get(position).getNewsDate());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isNetworkAvailable()) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getNewsDetailsURL());
                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ItemDetailActivity.class);
                            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getNewsDetailsURL());
                            context.startActivity(intent);
                        }
                    }else{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG);

                        snackbar.show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitle;
            public final TextView mSubtitle;
            public final TextView mDate;
            public NewsItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitle = view.findViewById(R.id.newsTitle);
                mSubtitle = view.findViewById(R.id.newsSubTitle);
                mDate = view.findViewById(R.id.newsDate);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mSubtitle.getText() + "'";
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
