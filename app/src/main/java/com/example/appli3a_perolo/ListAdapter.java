package com.example.appli3a_perolo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Symbol> symbolsList;
    public OnItemClickListener listener;

    public ListAdapter(List<Symbol> symbolsList, OnItemClickListener listener) {
        this.symbolsList = symbolsList;
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtHeader;
        TextView txtFooter;
        View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);

        }
    }

    public void add(int position, Symbol item) {
        symbolsList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        symbolsList.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtHeader.setText(symbolsList.get(position).getName());
        holder.txtFooter.setText(symbolsList.get(position).getSymbol());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onItemClick(symbolsList.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return symbolsList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Symbol symbolsList);
    }
}
