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
 * Created by rahultrivedy on 17/09/17.
 */

public class RecycleViewAdapterGcc extends Adapter<RecycleViewAdapterGcc.ViewHolder> {

    private List<ListItemGcc> listItemsGcc;
    private Context contextGcc;

    public RecycleViewAdapterGcc(List<ListItemGcc> listItemsGcc, Context contextGcc) {
        this.listItemsGcc = listItemsGcc;
        this.contextGcc = contextGcc;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_gcc, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItemGcc listItem = listItemsGcc.get(position);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contextGcc, GradeClassCount.class);

                intent.putExtra("intentID", "EditGCC");
                intent.putExtra("Grade", listItem.getGrade());
                intent.putExtra("No of Classes", listItem.getNoOfClasses());
                intent.putExtra("Female SCount", listItem.getfStudCount());
                intent.putExtra("Male SCount", listItem.getmStudCount());
                intent.putExtra("Female TCount", listItem.getfTeachCount());
                intent.putExtra("Male TCount", listItem.getmTeachCount());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                contextGcc.startActivity(intent);
//                Toast.makeText(context, "You Clicked "+listItem.getGrade(), Toast.LENGTH_LONG).show();
            }
        });
        holder.textViewGrade.setText(listItem.getGrade());
        holder.textViewNoOfClasses.setText(listItem.getNoOfClasses());
        holder.textViewFSCount.setText(listItem.getfStudCount());
        holder.textViewMSCount.setText(listItem.getmStudCount());
        holder.textViewMTCount.setText(listItem.getmTeachCount());
        holder.textViewFTCount.setText(listItem.getfTeachCount());

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
        return listItemsGcc.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout linearLayout;
        public TextView textViewGrade;
        public TextView textViewNoOfClasses;
        public TextView textViewFSCount;
        public TextView textViewMSCount;
        public TextView textViewFTCount;
        public TextView textViewMTCount;
        public TextView textViewDate;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cvGcc);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutGcc);
            textViewGrade = (TextView) itemView.findViewById(R.id.textViewGrade);
            textViewNoOfClasses = (TextView) itemView.findViewById(R.id.textViewNoOfClasses);
            textViewFSCount = (TextView) itemView.findViewById(R.id.textViewFSCount);
            textViewMSCount = (TextView) itemView.findViewById(R.id.textViewMSCount);
            textViewFTCount = (TextView) itemView.findViewById(R.id.textViewFTCount);
            textViewMTCount = (TextView) itemView.findViewById(R.id.textViewMTCount);
            textViewDate = (TextView) itemView.findViewById(R.id.date);

        }
    }
}
