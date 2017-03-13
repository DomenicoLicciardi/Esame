package com.example.d_odo.appunti.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d_odo.appunti.R;
import com.example.d_odo.appunti.activities.MainActivity;
import com.example.d_odo.appunti.models.Appunti;

import java.util.ArrayList;

/**
 * Created by d-odo on 13/03/2017.
 */

public class AppuntiAdapter extends RecyclerView.Adapter<AppuntiAdapter.AppuntiViewHolder> {

    private Context context;
    private ArrayList<Appunti> dataSet= new ArrayList<>();
    private int position;

    public Appunti getAppunti(int position) {
        return dataSet.get(position);
    }

    public void setDataSet(ArrayList<Appunti> appunti) {
        this.dataSet = appunti;
        notifyDataSetChanged();
    }

    public void addAppunto(Appunti appunti) {
        dataSet.add(0, appunti);
        notifyItemInserted(0);
    }

    public void updateAppunti(Appunti appunti, int position) {
        dataSet.set(position,appunti);
        notifyItemChanged(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AppuntiAdapter(Context c) {
        context = c;
    }

    public void removeAppunto(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public AppuntiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appunti, parent, false);
        return new AppuntiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AppuntiViewHolder holder, int position) {
        Appunti currentAppunti = dataSet.get(position);
        holder.titoloTV.setText(currentAppunti.getTitolo());
        holder.testoTV.setText(currentAppunti.getTesto());
        holder.dataTV.setText(currentAppunti.getData());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class AppuntiViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        public TextView titoloTV;
        public TextView testoTV;
        public TextView dataTV;

        public AppuntiViewHolder(View itemView) {

            super(itemView);
            titoloTV= (TextView) itemView.findViewById(R.id.item_title);
            testoTV= (TextView) itemView.findViewById(R.id.item_testo);
            dataTV= (TextView) itemView.findViewById(R.id.item_data);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setPosition(getAdapterPosition());
                    return false;
                }
            });

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = ((MainActivity)context).getMenuInflater();
            inflater.inflate(R.menu.menu_note, menu);
            }
    }
}
