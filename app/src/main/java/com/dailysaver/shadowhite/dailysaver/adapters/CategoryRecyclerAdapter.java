package com.dailysaver.shadowhite.dailysaver.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailysaver.shadowhite.dailysaver.models.Category;
import com.dailysaver.shadowhite.dailysaver.R;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> categoryList;
    private Dialog dialog;
    private TextView categoryTitle;
    private ImageView categoryIcon;

    public CategoryRecyclerAdapter(Context context, ArrayList<Category> categoryList, Dialog dialog, TextView categoryTitle, ImageView categoryIcon) {
        this.context = context;
        this.categoryList = categoryList;
        this.dialog = dialog;
        this.categoryIcon = categoryIcon;
        this.categoryTitle = categoryTitle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.catgoery_list_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        holder.Title.setText(category.getTitle());
        holder.IconRes.setImageResource(category.getIconRes());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) dialog.dismiss();
                categoryTitle.setText(category.getTitle());
                categoryIcon.setImageResource(category.getIconRes());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        ImageView IconRes;
        LinearLayout itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            IconRes = itemView.findViewById(R.id.IconRes);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
