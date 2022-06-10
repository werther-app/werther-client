package com.com.werther_client.requests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.com.werther_client.R;
import com.com.werther_client.User;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder>{

    private User user;
    private int viewHolderNumber;

    public RequestAdapter(User user){
        this.user=user;
        viewHolderNumber=0;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId= R.layout.request_item;

        LayoutInflater inflater = LayoutInflater.from(context);//xml->java

        View view = inflater.inflate(layoutId, parent,false);//3rd can be true

        RequestViewHolder viewHolder = new RequestViewHolder(view);

        viewHolder.listItemRequestLink.setText("Link: "+user.getFromList(viewHolderNumber).getLink());
        viewHolder.listItemRequestBody.setText("Body: "+user.getFromList(viewHolderNumber).getStatus());//TODO change to body
        viewHolderNumber++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return user.getListSize();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder{

        //Elements of any cell
        TextView listItemRequestLink;
        EditText listItemRequestBody;
        Button listItemRequestButton;


        public RequestViewHolder(View itemView) {
            super(itemView);

            //Find cells by ids in request_item.xml
            listItemRequestLink = itemView.findViewById(R.id.request_link);
            listItemRequestBody = itemView.findViewById(R.id.request_body);
            listItemRequestButton = itemView.findViewById(R.id.request_get_button);

        }

        void bind (int listId){
            listItemRequestLink.setText("Link: "+user.getFromList(listId).getLink());
            listItemRequestBody.setText("Body: "+user.getFromList(listId).getStatus());//TODO change to body

        }
    }
}
