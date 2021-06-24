package com.example.sampledatabase;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<DataList> dataList;
    private Activity context;
    private DBRoom roomd;

    //constructor
    public MainAdapter(Activity context,List<DataList>dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //intialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataList data = dataList.get(position);
        //intialize database
        roomd = DBRoom.getInstance(context);

        //set view on text view
        holder.textView.setText(data.getText());
        holder.title.setText(data.getTitle());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //intialize data
                DataList d = dataList.get(holder.getAdapterPosition());
                //GetId
                int sId = d.getId();
                //Get text
                String  sText = d.getText();
                String sTitle = d.getTitle();
                //create dialog
                Dialog dialog  = new Dialog(context);
                //content view
                dialog.setContentView(R.layout.dialog_update);
                //intialize width
                int wid = WindowManager.LayoutParams.MATCH_PARENT;
                //intialize ht
                int hig = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set Layout
                dialog.getWindow().setLayout(wid,hig);
                //show dialog
                dialog.show();

                //intilaize and assign
                EditText editText = dialog.findViewById(R.id.edit_text);
                EditText edittile = dialog.findViewById(R.id.edit_title);
                Button btupd = dialog.findViewById(R.id.but_upd);

                editText.setText(sText);
                edittile.setText(sTitle);

                btupd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //dismiss dialog
                        dialog.dismiss();
                        //getupdate text from edittext view
                        String uptext = editText.getText().toString().trim();
                        String uptitle = edittile.getText().toString().trim();
                        //update text in the database
                        roomd.mainDao().update(sId,uptext,uptitle);
                        //notify when data is updated
                        dataList.clear();
                        dataList.addAll(roomd.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intialize data
                DataList d = dataList.get(holder.getAdapterPosition());
                //delete text from main database
                roomd.mainDao().delete(d);
                //notify when data is deleted;
                int pos = holder.getAdapterPosition();
                dataList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos,dataList.size());
            }
        });

        holder.expand.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if(holder.hidden.getVisibility() == View.VISIBLE)
                {
                    TransitionManager.beginDelayedTransition(holder.cardView,new AutoTransition());
                    holder.hidden.setVisibility(View.GONE);
                    holder.expand.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    TransitionManager.endTransitions(holder.cardView);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(holder.cardView,new AutoTransition());
                    holder.hidden.setVisibility(View.VISIBLE);
                    holder.expand.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    TransitionManager.endTransitions(holder.cardView);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView edit;
        ImageView del;
        ImageView expand;
        LinearLayout hidden;
        CardView cardView;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textvieww);
            edit = itemView.findViewById(R.id.edit);
            del = itemView.findViewById(R.id.delete);
            expand = itemView.findViewById(R.id.expand);
            hidden = itemView.findViewById(R.id.hidden);
            cardView = itemView.findViewById(R.id.card_list);
            title = itemView.findViewById(R.id.notetitle);

        }
    }
}
