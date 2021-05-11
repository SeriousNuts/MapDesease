package com.example.mapdesease;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapdesease.Label;
import com.example.mapdesease.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DeseaseListRecView extends FirestoreRecyclerAdapter<Label, DeseaseListRecView.myviewholder> {
    private OnItemListener onItemListener;

    public DeseaseListRecView(@NonNull FirestoreRecyclerOptions<Label> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull Label label) {
        holder.Symptoms.setText(label.getSymptoms());
        holder.Date.setText(label.getDate());
        holder.itemView.setId(position);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desease_list_one_row,parent,false);
        view.setOnClickListener(new RV_ItemListener());
        view.setOnLongClickListener(new RV_ItemListener());
        return new myviewholder(view);
    }

    static class myviewholder extends RecyclerView.ViewHolder{
        TextView Symptoms, Date;
        public myviewholder(@NonNull final View itemView) {
            super(itemView);
            Symptoms = itemView.findViewById(R.id.SymptomsonDeseaseList);
            Date = itemView.findViewById(R.id.DateDeseaseList);




        }
    }
    public  interface OnItemListener{
        void OnItemClickListener(View view, int position);
        void OnItemLongClickListener(View view, int position);
    }
    class RV_ItemListener implements View.OnClickListener, View.OnLongClickListener{

        @Override
        public void onClick(View view) {
            if (onItemListener != null){
                onItemListener.OnItemClickListener(view, view.getId());
            }
        }
        @Override
        public boolean onLongClick(View view) {
            if (onItemListener != null){
                onItemListener.OnItemLongClickListener(view,view.getId());
            }
            return true;
        }
    }
    public void setOnItemListenerListener(OnItemListener listener){
        this.onItemListener = listener;
    }
}
