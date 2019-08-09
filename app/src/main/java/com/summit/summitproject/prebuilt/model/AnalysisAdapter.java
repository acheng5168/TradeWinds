package com.summit.summitproject.prebuilt.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.summit.summitproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link RecyclerView.Adapter} is used with a {@link RecyclerView}. It takes in the data which
 * should be displayed in the list and tells the UI how each individual piece of data should be
 * rendered.
 */
public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHolder> {

    /**
     * The list of credit card transactions which will be adapted to the UI.
     */
    private List<Analysis> analyses = new ArrayList<>();

    /**
     * A listener to deliver callbacks to whenever a transaction in the list is clicked.
     */
    private AnalysisClickedListener listener;

    /**
     * Takes in the list of transactions that should be rendered and a listener to receive callbacks
     * if the user clicks on a particular row.
     */
    public AnalysisAdapter(List<Analysis> analyses, AnalysisClickedListener listener) {
        this.analyses = analyses;
        this.listener = listener;
    }

    /**
     * Called when the UI needs the a new row (at {position}) to be <b>created</b>. In this case,
     * all of our rows look the same, so we just inflate the same layout for all rows.
     * <br>
     * The new row isn't filled with data yet, that's done by
     * {@link AnalysisAdapter#onBindViewHolder(ViewHolder, int)}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_analytics, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called when the UI needs the next row (at {position}) to be <b>filled with data</b> rendered
     * and passes the {@link ViewHolder} which should be filled with data.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // Use the transaction at index {position} to set up the row's UI widgets
        final Analysis analysis = analyses.get(position);
        holder.stock_name.setText(analysis.getStock());
        holder.percentage.setText("Percent of Total Portfolio: " +analysis.getPercentage() + "%");
    }

    /**
     * Used to determine how many rows the list should be in total.
     */
    @Override
    public int getItemCount() {
        return analyses.size();
    }

    /**
     * Holds the UI widgets which will comprise a single row in the list (to render
     * a {@link Friend}).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        TextView stock_name;

        TextView percentage;

        ViewHolder(View rootView) {
            super(rootView);
            cardView = rootView.findViewById(R.id.card_container_profile);
            stock_name = rootView.findViewById(R.id.stock_name);
            percentage = rootView.findViewById(R.id.percentage);
        }
    }

    /**
     * Will receive callbacks whenever a transaction in the list is clicked.
     * <p>
     * NOTE: Implement this when you want to click a row and go straight to the profile
     */
    public interface AnalysisClickedListener {
        void onActionClicked(Action action);
    }
}
