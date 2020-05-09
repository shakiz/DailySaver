package com.sakhawat.expense.tracker.adapters.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.models.note.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Note> allNotes = new ArrayList<>();
    private onItemClick onItemClick;

    public NoteRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void onItemClickListener(onItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public interface onItemClick{
        void itemClick(Note note);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_layout_note_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Note note = allNotes.get(position);
        holder.Title.setText(note.getTitle());
        holder.Date.setText(note.getDate());
        holder.Description.setText(note.getDescription());
        holder.cardItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemClick(note);
            }
        });
    }

    public void setAllNotes(List<Note> allNotes){
        this.allNotes = allNotes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (allNotes.size() > 0) return allNotes.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Date,Description;
        CardView cardItemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Date = itemView.findViewById(R.id.Date);
            Description = itemView.findViewById(R.id.Description);
            cardItemLayout = itemView.findViewById(R.id.cardItemLayout);
        }
    }
}
