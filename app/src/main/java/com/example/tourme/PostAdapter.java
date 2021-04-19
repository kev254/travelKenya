package com.example.tourme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    List<PostModel> postsList;
    Context context;
    RecyclerViewOnclickListener listener;

    public PostAdapter(Context context, List<PostModel> postsList, RecyclerViewOnclickListener listener){
      this.context=context;
      this.postsList=postsList;
      this.listener=listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
     final View view= LayoutInflater.from(context).inflate(R.layout.recycler_item,null,false) ;
     final MyViewHolder viewHolder=new MyViewHolder(view);

     view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           listener.onClick(view,viewHolder.getAdapterPosition());
        }
    });  return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
PostModel postModel=postsList.get(position);

holder.title.setText(postModel.getImageTitle());
holder.desc.setText(postModel.getImageDescrip());
holder.imageid.setText(postModel.getImageId());
holder.imageurl.setText(postModel.getImageUrl());
holder.email.setText(postModel.getEmail());
Glide.with(context).load(postModel.getImageUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,imageid,imageurl,email;
        ImageView imageView;
        TextView button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.description);
            imageid=itemView.findViewById(R.id.imageId);
            imageurl=itemView.findViewById(R.id.imageUrl);
            imageView=itemView.findViewById(R.id.image);
            email=itemView.findViewById(R.id.email);
            button=itemView.findViewById(R.id.view_details);

        }
    }
}
