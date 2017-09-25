package com.saysweb.emis_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by rahultrivedy on 14/09/17.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private List<ListItem> listItems;
    private Context context;


    public RecyclerViewAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItem listItem = listItems.get(position);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EnrollmentByGrade.class);

                intent.putExtra("intentID", "EditEBG");
                intent.putExtra("Grade", listItem.getGrade());
                intent.putExtra("Birth Year", listItem.getBirthYr());
                intent.putExtra("Female Count", listItem.getfCount());
                intent.putExtra("Male Count", listItem.getmCount());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                context.startActivity(intent);
            }
        });
        holder.textViewGrade.setText(listItem.getGrade());
        holder.textViewBirthYr.setText(listItem.getBirthYr());
        holder.textViewFCount.setText(listItem.getfCount());
        holder.textViewMCount.setText(listItem.getmCount());
        holder.textViewDate.setText(listItem.getDate());

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
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout linearLayout;
        public TextView textViewGrade;
        public TextView textViewBirthYr;
        public TextView textViewFCount;
        public TextView textViewMCount;
        public CardView cardView;
        public TextView textViewDate;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cv);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            textViewGrade = (TextView) itemView.findViewById(R.id.textViewGrade);
            textViewBirthYr = (TextView) itemView.findViewById(R.id.textViewBirthYr);
            textViewFCount = (TextView) itemView.findViewById(R.id.textViewFCount);
            textViewMCount = (TextView) itemView.findViewById(R.id.textViewMCount);
            textViewDate = (TextView) itemView.findViewById(R.id.date);

        }
    }

}
