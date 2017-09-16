package com.saysweb.emis_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

                intent.putExtra("intentID", "EBG");
                intent.putExtra("Grade", listItem.getGrade());
                intent.putExtra("Birth Year", listItem.getBirthYr());
                intent.putExtra("Female Count", listItem.getfCount());
                intent.putExtra("Male Count", listItem.getmCount());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                context.startActivity(intent);
//                Toast.makeText(context, "You Clicked "+listItem.getGrade(), Toast.LENGTH_LONG).show();
            }
        });
        holder.textViewGrade.setText(listItem.getGrade());
        holder.textViewBirthYr.setText(listItem.getBirthYr());
        holder.textViewFCount.setText(listItem.getfCount());
        holder.textViewMCount.setText(listItem.getmCount());



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

        public ViewHolder(View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            textViewGrade = (TextView) itemView.findViewById(R.id.textViewGrade);
            textViewBirthYr = (TextView) itemView.findViewById(R.id.textViewBirthYr);
            textViewFCount = (TextView) itemView.findViewById(R.id.textViewFCount);
            textViewMCount = (TextView) itemView.findViewById(R.id.textViewMCount);

        }
    }

}
