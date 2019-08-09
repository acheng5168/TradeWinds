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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link RecyclerView.Adapter} is used with a {@link RecyclerView}. It takes in the data which
 * should be displayed in the list and tells the UI how each individual piece of data should be
 * rendered.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    /**
     * The list of credit card transactions which will be adapted to the UI.
     */
    private List<Friend> friends = new ArrayList<>();

    public int tester = 1;

    /**
     * A listener to deliver callbacks to whenever a transaction in the list is clicked.
     */
    private FriendClickedListener listener;

    /**
     * Takes in the list of transactions that should be rendered and a listener to receive callbacks
     * if the user clicks on a particular row.
     */
    public FriendAdapter(List<Friend> friends, FriendClickedListener listener) {

        Collections.sort(friends);
        Collections.reverse(friends);
        this.friends = friends;
        this.listener = listener;
    }

    /**
     * Called when the UI needs the a new row (at {position}) to be <b>created</b>. In this case,
     * all of our rows look the same, so we just inflate the same layout for all rows.
     * <br>
     * The new row isn't filled with data yet, that's done by
     * {@link FriendAdapter#onBindViewHolder(ViewHolder, int)}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_friend, parent, false);
        return new ViewHolder(view);
    }

    public void refreshOrder(List<Friend> newFriends) {
        Collections.sort(newFriends);
        Collections.reverse(newFriends);

        for (Friend friend: newFriends) {
            Log.d("friend", "" + friend.getPercent());
        }
        this.friends = newFriends;
    }
    /**
     * Called when the UI needs the next row (at {position}) to be <b>filled with data</b> rendered
     * and passes the {@link ViewHolder} which should be filled with data.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // Use the transaction at index {position} to set up the row's UI widgets

        Friend friend = friends.get(position);
        holder.name.setText(friend.getName());
        holder.username.setText(friend.getUsername());
        if (friend.getPercent() >= 0) {
            holder.gainArrow.setImageResource(R.drawable.arrowup);
        } else {
            holder.gainArrow.setImageResource(R.drawable.arrowdown);
        }

        holder.gainAmount.setText(String.format("%.2f", friend.getPercent()) + "%");
        holder.username.setText(friend.getUsername());
        if (friend.getPercent() >= 0) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#CEF6D8"));

        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F8E6E0"));
        }

        holder.profilePic.setImageResource(friend.imageResource);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend clickedOnFriend = friends.get(holder.getAdapterPosition());
                Log.d("Friend", "Clicked "+ clickedOnFriend.getName());
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
        return friends.size();
    }

    public void updateData(List<Friend> friends) {
        this.friends = friends;
        this.notifyDataSetChanged();
    }

    /**
     * Holds the UI widgets which will comprise a single row in the list (to render
     * a {@link Friend}).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        TextView name;

        TextView gainAmount;

        TextView username;

        ImageView gainArrow;

        ImageView profilePic;

        ViewHolder(View rootView) {
            super(rootView);
            cardView = rootView.findViewById(R.id.card_container);
            name = rootView.findViewById(R.id.name);
            gainAmount = rootView.findViewById(R.id.gainAmount);
            username = rootView.findViewById(R.id.username);
            gainArrow = rootView.findViewById(R.id.gainArrow);
            profilePic = rootView.findViewById(R.id.profilePic);
        }
    }

    /**
     * Will receive callbacks whenever a transaction in the list is clicked.
     * <p>
     * NOTE: Implement this when you want to click a row and go straight to the profile
     */
    public interface FriendClickedListener {
        void onFriendClicked(Friend friend);
    }
}
