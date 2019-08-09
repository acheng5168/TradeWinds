package com.summit.summitproject;
import java.net.ProtocolException;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.summit.summitproject.prebuilt.model.FeedAction;
import com.summit.summitproject.prebuilt.model.FeedAdapter;
import com.summit.summitproject.prebuilt.model.Friend;
import com.summit.summitproject.prebuilt.model.FriendAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardActivity extends AppCompatActivity implements FriendAdapter.FriendClickedListener {

    private RecyclerView leaderboard_list;
    private RecyclerView feed_list;

    /**
     * Takes the transactions data and instructs the transactionsList on how they should be
     * rendered.
     */
    private FriendAdapter friendsAdapter;
    private FeedAdapter feedAdapter;

    private ArrayList<Friend> friends;
    private ArrayList<FeedAction> feed;




    /**
     * Called the first time an Action is created, but before any UI is shown to the user.
     * Prepares the layout and assigns UI widget variables.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        findViewById(R.id.profileButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderboardActivity.this, ProfileActivity.class));
            }
        });

        leaderboard_list = findViewById(R.id.leaderboard_list);
        feed_list = findViewById(R.id.feed_list);
        feed_list.setVisibility(View.GONE);


        // Prepare the list data

        friends = new ArrayList<>();
        friends.add(new Friend("Sam Edwards", "handstandsam", 2.32, true, R.drawable.sam));
        friends.add(new Friend("Arman Parastaran", "pararaman", 1.71, true, R.drawable.arman));
        friends.add(new Friend("Kenneth Shinn", "kshinn", 1.69, true, R.drawable.kenneth));
        friends.add(new Friend("Alan Cheng", "acheng5168", -2.62, true, R.drawable.alan));
        friends.add(new Friend("Shane Aung", "brown_cowboy", 1.12, true, R.drawable.shane));

        friendsAdapter = new FriendAdapter(friends, this);
        leaderboard_list.setLayoutManager(new LinearLayoutManager(this));
        leaderboard_list.setAdapter(friendsAdapter);

        // Prepare the feed data
        feed = new ArrayList<>();
        feed.add(new FeedAction("Kenneth Shinn", "June 26 2019", "AAPL", 10.2, 9.6, true));
        feed.add(new FeedAction("Arman Parastaran", "June 26 2019", "AMD", 12.3, 18.6, true));
        feed.add(new FeedAction("Shane Aung", "June 26 2019", "AAPL", 10.2, 9.6, false));
        feed.add(new FeedAction("Alan Cheng", "June 26 2019", "GOOG", 100.7, 127.74, false));
        feed.add(new FeedAction("Sam Edwards", "June 26 2019", "TSLA", 464.87, 326.74, false));


        feedAdapter = new FeedAdapter(feed, this);
        feed_list.setLayoutManager(new LinearLayoutManager(this));
        feed_list.setAdapter(feedAdapter);


        final SwipeRefreshLayout leaderboardRefresh = (SwipeRefreshLayout) findViewById(R.id.leaderboard_refresher);

        leaderboardRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.d("hi", "HIIIIII");
                for (Friend friend : friends) {
                    if (Math.random() > .5) {
                        friend.setPercent(friend.getPercent() + Math.random() * Math.random() * Math.random());

                    } else {
                        friend.setPercent(friend.getPercent() - Math.random() * Math.random() * Math.random());
                    }
                    //Log.d("friend", ""+ friend.getPercent());

                }
                friendsAdapter.refreshOrder(friends);
                friendsAdapter.notifyDataSetChanged();
                leaderboardRefresh.setRefreshing(false);
            }
        });

        final SwipeRefreshLayout feedRefresh = (SwipeRefreshLayout) findViewById(R.id.feed_refresher);

        feedRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.d("bye", "BYEEEE");
                try {
                    randomAddToFeed();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                feedAdapter.notifyDataSetChanged();
                feedRefresh.setRefreshing(false);
            }
        });

        feedRefresh.setEnabled(false);

        final View feed_list = findViewById(R.id.feed_list);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIndex = tab.getPosition();
                if (tabIndex == 0) {
                    //leaderboard
                    feed_list.setVisibility(View.GONE);
                    leaderboard_list.setVisibility(View.VISIBLE);
                    feedRefresh.setEnabled(false);
                    leaderboardRefresh.setEnabled(true);
                } else {
                    //feed
                    leaderboard_list.setVisibility(View.GONE);
                    feed_list.setVisibility(View.VISIBLE);
                    feedRefresh.setEnabled(true);
                    leaderboardRefresh.setEnabled(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void randomAddToFeed() throws IOException {
        String[] tickers = {"MSFT", "AAPL", "AMZN", "FB", "JPM",
                "JNJ", "V", "PG", "T", "MA",
                "COF", "DIS", "CVX", "BA", "NFLX"};
        String[] names = {"Kenneth Shinn", "Arman Parastaran", "Alan Cheng", "Shane Aung", "Sam Edwards"};
        int numberOfNewPosts = (int)(Math.random() * 3 + 1);
        for(int i = 0; i < numberOfNewPosts; i++){
            int tickerIndex = (int)(Math.random() * tickers.length);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String randomTicker = tickers[tickerIndex];
            String timeStamp = formatter.format(date).replace(" ", ", ");
            double price = Math.random() * 200;
            int buyOrSell = (int)(Math.round(Math.random()));
            int namesIndex = (int)(Math.random() * names.length);
            String name = names[namesIndex];

            if (buyOrSell == 1) {
                if (Math.random() >= .50){
                    feed.add(0, new FeedAction(name, timeStamp, randomTicker, price, price + Math.random(), true));
                } else {
                    feed.add(0, new FeedAction(name, timeStamp, randomTicker, price, price - Math.random(), true));
                }

            } else {
                if (Math.random() >= .50){
                    feed.add(0, new FeedAction(name, timeStamp, randomTicker, price, price + Math.random(), false));
                } else {
                    feed.add(0, new FeedAction(name, timeStamp, randomTicker, price, price - Math.random(), false));
                }
                
            }

        }


    }


    /**
     * Called when the user clicks on any of the transactions in the list. From here, you could
     * open up a new screen to further show transaction details.
     */
    @Override
    public void onFriendClicked(Friend friend) {
        Toast.makeText(this, getString(R.string.transaction_selected, friend.getName()), Toast.LENGTH_LONG).show();
    }
}
