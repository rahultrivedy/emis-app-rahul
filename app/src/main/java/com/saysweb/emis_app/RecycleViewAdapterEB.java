package com.saysweb.emis_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by rahultrivedy on 18/09/17.
 */

public class RecycleViewAdapterEB extends Adapter<RecycleViewAdapterEB.ViewHolder> {

    private List<ListItemEB> listItemsEB;
    private Context contextEB;

    public RecycleViewAdapterEB(List<ListItemEB> listItemsEB, Context contextEB) {
        this.listItemsEB = listItemsEB;
        this.contextEB = contextEB;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_eb, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItemEB listItem = listItemsEB.get(position);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contextEB, BoardingEnrollment.class);

                intent.putExtra("intentID", "EditEB");
                intent.putExtra("Grade", listItem.getGrade());
                intent.putExtra("Females", listItem.getFemale());
                intent.putExtra("Males", listItem.getMale());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                contextEB.startActivity(intent);
            }
        });

        holder.textViewGrade.setText(listItem.getGrade());
        holder.textViewFemale.setText(listItem.getFemale());
        holder.textViewMale.setText(listItem.getMale());

         /*-----------------------------------------------*/

        long dbTimeInMilis = Long.parseLong(listItem.getDate()) * 1000L;
        Calendar dbTime = Calendar.getInstance();
        dbTime.setTimeInMillis(dbTimeInMilis);

        Calendar now = Calendar.getInstance();

        if(now.get(Calendar.DATE) == dbTime.get(Calendar.DATE))
        {
            FrameLayout frameLayout = (FrameLayout)holder.cardView.findViewById(R.id.frame);
            frameLayout.setVisibility(View.VISIBLE);
            holder.textViewDate.setVisibility(View.VISIBLE);
        }else{
            FrameLayout frameLayout = (FrameLayout)holder.cardView.findViewById(R.id.frame);
            frameLayout.setVisibility(View.VISIBLE);
            frameLayout.setBackgroundColor(Color.parseColor("#B0BEC5"));

        }
/*--------------------------------------------------------*/
        for(int i = 0; i<= getItemCount();i+=2){
            if(position == i) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#DCEBE4"));


            }
        }

    }

    @Override
    public int getItemCount() {
        return listItemsEB.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public LinearLayout linearLayout;
        public TextView textViewGrade;
        public TextView textViewFemale;
        public TextView textViewMale;
        public TextView textViewDate;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cvBE);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutBE);
            textViewGrade = (TextView) itemView.findViewById(R.id.textViewGrade);
            textViewMale = (TextView) itemView.findViewById(R.id.textViewMale);
            textViewFemale = (TextView) itemView.findViewById(R.id.textViewFemale);
            textViewDate = (TextView) itemView.findViewById(R.id.date);
        }
    }

}