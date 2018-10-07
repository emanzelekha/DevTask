package emanzelekha.com.devtask.MVP.Model.adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import emanzelekha.com.devtask.MVP.Model.RecyclerModel;
import emanzelekha.com.devtask.R;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<RecyclerModel> displayList;
    Context context;

    public RecyclerAdapter(List<RecyclerModel> displayList) {
        this.displayList = displayList;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        context = itemView.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
        final RecyclerModel DisModel = displayList.get(position);
        if (DisModel.getName() != null) {
            holder.Repos.setText(DisModel.getName());
        }else {
            holder.Repos.setText("Not Found");
        }
        if (DisModel.getLogin() != null) {
            holder.Log.setText(DisModel.getLogin());
        }else {
            holder.Log.setText("Not Found");
        }
        holder.Description.setText(DisModel.getDescription());
        if (DisModel.getFork() == false || DisModel.getFork() == null) {
            holder.Back.setBackgroundResource(R.drawable.green);
        } else {
            holder.Back.setBackgroundResource(R.drawable.withe);
        }
        holder.Back.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                View Repostoryd = dialog.findViewById(R.id.Repostiry);
                View Ownerd = dialog.findViewById(R.id.owner);
                Repostoryd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(DisModel.getHtml_url()!=null){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DisModel.getHtml_url()));
                       context. startActivity(browserIntent);}

                    }
                });
                Ownerd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(DisModel.getHtml_url_owner()!=null){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DisModel.getHtml_url_owner()));
                            context. startActivity(browserIntent);}

                    }
                });
                dialog.show();
                dialog.getWindow().setLayout((6 * DisModel.getWidth()) / 6, ViewGroup.LayoutParams.WRAP_CONTENT);

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Repos)
        TextView Repos;
        @BindView(R.id.Description)
        TextView Description;
        @BindView(R.id.log)
        TextView Log;
        @BindView(R.id.Back)
        View Back;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
