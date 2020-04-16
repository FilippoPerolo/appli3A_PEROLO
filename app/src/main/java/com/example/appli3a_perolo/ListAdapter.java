package com.example.appli3a_perolo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Symbol> values;

    private OnNoteListener mOnNoteListener;

    public ListAdapter(Symbol symbol2, SomeActivity someActivity) {
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView txtHeader;
        TextView txtFooter;
        View layout;
        Button button;

        OnNoteListener onNoteListener;

        public ViewHolder(View v, OnNoteListener onNoteListener) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);

            this.onNoteListener = onNoteListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public void add(int position, Symbol item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // constructor
    public ListAdapter(List<Symbol> myDataset, OnNoteListener onNoteListener) {
        values = myDataset;
        this.mOnNoteListener = onNoteListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent,false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, mOnNoteListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Symbol currentSymbol = values.get(position);
        holder.txtHeader.setText(currentSymbol.getName());
        holder.txtFooter.setText(currentSymbol.getSymbol());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
