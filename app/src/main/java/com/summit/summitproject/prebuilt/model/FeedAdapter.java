package com.summit.summitproject.prebuilt.model;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.summit.summitproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link RecyclerView.Adapter} is used with a {@link RecyclerView}. It takes in the data which
 * should be displayed in the list and tells the UI how each individual piece of data should be
 * rendered.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    /**
     * The list of credit card transactions which will be adapted to the UI.
     */
    private List<FeedAction> feed = new ArrayList<>();

    /**
     * A listener to deliver callbacks to whenever a transaction in the list is clicked.
     */
    private FriendAdapter.FriendClickedListener listener;

    /**
     * Takes in the list of transactions that should be rendered and a listener to receive callbacks
     * if the user clicks on a particular row.
     */
    public FeedAdapter(List<FeedAction> feed, FriendAdapter.FriendClickedListener listener) {

        this.feed = feed;
        this.listener = listener;
    }

    /**
     * Called when the UI needs the a new row (at {position}) to be <b>created</b>. In this case,
     * all of our rows look the same, so we just inflate the same layout for all rows.
     * <br>
     * The new row isn't filled with data yet, that's done by
     * {@link FeedAdapter#onBindViewHolder(ViewHolder, int)}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_feed, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called when the UI needs the next row (at {position}) to be <b>filled with data</b> rendered
     * and passes the {@link ViewHolder} which should be filled with data.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // Use the transaction at index {position} to set up the row's UI widgets
        FeedAction feedAction = feed.get(position);
        holder.name.setText(feedAction.getName());
        holder.date.setText(feedAction.getDate());

        if (feedAction.wasBought()) {
            holder.priceBought.setText("Bought: $" + String.format("%.2f", feedAction.getActionPrice()));
        } else {
            holder.priceBought.setText("Sold: $" + String.format("%.2f", feedAction.getActionPrice()));
        }

        holder.priceCurrent.setText("Current: $" + String.format("%.2f",feedAction.getCurrentPrice()));
        holder.stock.setText(feedAction.getStock() + "");

        Double gains = feedAction.getCurrentPrice() - feedAction.getActionPrice();
        System.out.print(gains);
        if (feedAction.wasBought()) {
            if (gains >= 0) {

                holder.cardView.setCardBackgroundColor(Color.parseColor("#CEF6D8"));

            } else {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#F8E6E0"));
            }
        } else {
            if (gains < 0) {

                holder.cardView.setCardBackgroundColor(Color.parseColor("#CEF6D8"));

            } else {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#F8E6E0"));
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedAction clickedOnFeedAction = feed.get(holder.getAdapterPosition());
                Log.d("Friend", "Clicked "+ clickedOnFeedAction.getName());
//                // Inform the click listener that this row was clicked and pass the Transaction
//                // associated with this row.
//                if (listener != null) {
//                    listener.onTransactionClicked());
//                }
            }
        });
    }

    /**
     * Used to determine how many rows the list should be in total.
     */
    @Override
    public int getItemCount() {
        return feed.size();
    }

    /**
     * Holds the UI widgets which will comprise a single row in the list (to render
     * a {@link Friend}).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        TextView name;

        TextView date;

        TextView priceCurrent;

        TextView priceBought;

        TextView stock;

        ViewHolder(View rootView) {
            super(rootView);
            cardView = rootView.findViewById(R.id.card_container);
            name = rootView.findViewById(R.id.name);
            date = rootView.findViewById(R.id.date);
            priceCurrent = rootView.findViewById(R.id.priceCurrent);
            priceBought = rootView.findViewById(R.id.priceBought);
            stock = rootView.findViewById(R.id.stock);
        }
    }

    /**
     * Will receive callbacks whenever a transaction in the list is clicked.
     * <p>
     * NOTE: Implement this when you want to click a row and go straight to the profile
     */
    public interface FeedClickedListener {
        void onFeedClicked(Friend feed);
    }
}
