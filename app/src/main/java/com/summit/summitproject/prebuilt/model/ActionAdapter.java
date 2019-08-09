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
public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {

    /**
     * The list of credit card transactions which will be adapted to the UI.
     */
    private List<Action> actions = new ArrayList<>();

    /**
     * A listener to deliver callbacks to whenever a transaction in the list is clicked.
     */
    private ActionClickedListener listener;

    /**
     * Takes in the list of transactions that should be rendered and a listener to receive callbacks
     * if the user clicks on a particular row.
     */
    public ActionAdapter(List<Action> actions, ActionClickedListener listener) {
        this.actions = actions;
        this.listener = listener;
    }

    /**
     * Called when the UI needs the a new row (at {position}) to be <b>created</b>. In this case,
     * all of our rows look the same, so we just inflate the same layout for all rows.
     * <br>
     * The new row isn't filled with data yet, that's done by
     * {@link ActionAdapter#onBindViewHolder(ViewHolder, int)}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_activity, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called when the UI needs the next row (at {position}) to be <b>filled with data</b> rendered
     * and passes the {@link ViewHolder} which should be filled with data.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // Use the transaction at index {position} to set up the row's UI widgets
        final Action action = actions.get(position);
        holder.stock.setText(action.getStock());
        holder.date.setText(action.getDate());
        if (action.getType() == 1) {
            holder.priceBought.setText("Bought: $"+action.getPriceBought());
            holder.cardView.setCardBackgroundColor(Color.parseColor("#CEF6D8"));
        } else {
            holder.priceBought.setText("Sold: $"+action.getPriceBought());
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F8E6E0"));
        }
        holder.priceCurrent.setText("Current: $"+action.getPriceCurrent());
    }

    /**
     * Used to determine how many rows the list should be in total.
     */
    @Override
    public int getItemCount() {
        return actions.size();
    }

    /**
     * Holds the UI widgets which will comprise a single row in the list (to render
     * a {@link Friend}).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        TextView stock;

        TextView date;

        TextView priceBought;

        TextView priceCurrent;

        ViewHolder(View rootView) {
            super(rootView);
            cardView = rootView.findViewById(R.id.card_container_profile);
            stock = rootView.findViewById(R.id.stock);
            date = rootView.findViewById(R.id.date);
            priceBought = rootView.findViewById(R.id.priceBought);
            priceCurrent = rootView.findViewById(R.id.priceCurrent);
        }
    }

    /**
     * Will receive callbacks whenever a transaction in the list is clicked.
     * <p>
     * NOTE: Implement this when you want to click a row and go straight to the profile
     */
    public interface ActionClickedListener {
        void onActionClicked(Action action);
    }
}
