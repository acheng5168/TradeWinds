package com.summit.summitproject;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.summit.summitproject.prebuilt.model.Action;
import com.summit.summitproject.prebuilt.model.ActionAdapter;
import com.summit.summitproject.prebuilt.model.Analysis;
import com.summit.summitproject.prebuilt.model.AnalysisAdapter;
import com.summit.summitproject.prebuilt.model.Friend;
import com.summit.summitproject.prebuilt.model.FriendAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a user's name, the last 4 numbers of a credit card, and their recent transactions
 * for that card.
 * <br>
 * Expects the following pieces of data to be supplied via the {@link android.content.Intent}:
 * <ul>
 * <li>User's name -- via {@link ProfileActivity#KEY_NAME}</li>
 * <li>The last four numbers of a credit card -- via {@link ProfileActivity#KEY_CARD_NUM}</li>
 * <li>Recent transactions for the credit card -- via {@link ProfileActivity#KEY_TRANSACTIONS}</li>
 * </ul>
 */
public class ProfileActivity extends AppCompatActivity implements ActionAdapter.ActionClickedListener, AnalysisAdapter.AnalysisClickedListener {

    /**
     * Used to extract the user's name from the launch {@link android.content.Intent}
     */
    public static final String KEY_NAME = "NAME";

    /**
     * Used to extract a credit card last 4 from the launch {@link android.content.Intent}
     */
    public static final String KEY_CARD_NUM = "CARD_NUM";

    /**
     * Used to extract a credit card's transaction from the launch {@link android.content.Intent}
     */
    public static final String KEY_TRANSACTIONS = "TRANSACTIONS";

    // Data passed in via the Intent

    private String name;

    private String cardNum;

//    private List<Transaction> transactions;

    // UI Widgets

    private TextView title;

    private TextView subtitle;

    private RecyclerView profile_activity_list;
    private RecyclerView profile_analytics_list;

    /**
     * Takes the transactions data and instructs the transactionsList on how they should be
     * rendered.
     */
    private RecyclerView.Adapter actionAdapter;
    private RecyclerView.Adapter analysesAdapter;

    /**
     * Called the first time an Action is created, but before any UI is shown to the user.
     * Prepares the layout and assigns UI widget variables.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        name = getIntent().getStringExtra(KEY_NAME);
        cardNum = getIntent().getStringExtra(KEY_CARD_NUM);
//        transactions = (List<Friend>) getIntent().getSerializableExtra(KEY_TRANSACTIONS);

        profile_activity_list = findViewById(R.id.profile_activity_list);

        List<Action> actions = new ArrayList<>();
        actions.add(new Action("XLK", "08/08/2019, 10:16:19", 78.07, 79.39, 0));
        actions.add(new Action("KO", "08/05/2019, 13:02:28", 52.91, 53.69, 1));
        actions.add(new Action("CS", "07/29/2019, 13:15:38", 11.46, 11.58, 1));
        actions.add(new Action("TCEHY", "07/28/2019, 11:28:49", 43.68, 43.84, 1));
        actions.add(new Action("BAC", "07/26/2019, 14:32:28", 28.16, 28.38, 0));
        actions.add(new Action("AAPL", "07/25/2019, 09:23:10", 200.23, 203.43, 0));
        actions.add(new Action("GOOG", "07/18/2019, 12:48:29", 1194.37, 1204.80, 1));
        actions.add(new Action("HAL", "07/17/2019, 12:21:28", 19.82, 19.93, 0));
        actions.add(new Action("WFC", "07/15/2019, 09:42:49", 45.30, 46.40, 1));

        actionAdapter = new ActionAdapter(actions, this);

        profile_analytics_list = findViewById(R.id.profile_analytics_list);

        List<Analysis> analyses = new ArrayList<>();
        analyses.add(new Analysis("GOOG", 71.35));
        analyses.add(new Analysis("AAPL", 11.96));
        analyses.add(new Analysis("XLK", 4.66));
        analyses.add(new Analysis("KO", 3.16));
        analyses.add(new Analysis("WFC", 2.71));
        analyses.add(new Analysis("TCEHY", 2.16));
        analyses.add(new Analysis("BAC", 1.68));
        analyses.add(new Analysis("HAL", 1.18));
        analyses.add(new Analysis("CS", 0.68));



        analysesAdapter = new AnalysisAdapter(analyses, this);

        profile_activity_list.setLayoutManager(new LinearLayoutManager(this));
        profile_activity_list.setAdapter(actionAdapter);

        profile_analytics_list.setLayoutManager(new LinearLayoutManager(this));
        profile_analytics_list.setAdapter(analysesAdapter);

        final View profile_analytics_list = findViewById(R.id.profile_analytics_list);

        TabLayout tabLayout = findViewById(R.id.profile_tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIndex = tab.getPosition();
                if (tabIndex == 0) {
                    //Action
                    profile_analytics_list.setVisibility(View.GONE);
                    profile_activity_list.setVisibility(View.VISIBLE);
                } else {
                    //Analytics
                    profile_activity_list.setVisibility(View.GONE);
                    profile_analytics_list.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        TabItem analyticTab = findViewById(R.id.analytics_tab);
//        analyticTab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


//        TabItem activityTab = findViewById(R.id.activity_tab);
//        activityTab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        // Substitute in the user's name and card last 4 in the text widget
        // Prepare the list data
//        transactionsAdapter = new TransactionAdapter(transactions, this);
//        transactionsList.setLayoutManager(new LinearLayoutManager(this));
//        transactionsList.setAdapter(transactionsAdapter);
    }
//
//    /**
//     * Called when the user clicks on any of the transactions in the list. From here, you could
//     * open up a new screen to further show transaction details.
//     */
//    @Override
//    public void onTransactionClicked(Transaction transaction) {
//        Toast.makeText(this, getString(R.string.transaction_selected, transaction.getMerchant()), Toast.LENGTH_LONG).show();
//    }
    @Override
    public void onActionClicked(Action action) {
//        Toast.makeText(this, "", Toast.LENGTH_LONG).show();
    }
}
