package au.edu.unsw.infs3634.gamifiedlearning;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Filterable {
    private List<Category> categoryList;
    private List<Category> categoryListFull;

    private OnNoteListener mOnNoteListener;

    public RecyclerAdapter(List<Category> categoryList, OnNoteListener onNoteListener) {
        this.categoryList = categoryList;
        this.mOnNoteListener = onNoteListener;
        categoryListFull = new ArrayList<>(categoryList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView img;

        OnNoteListener onNoteListener;

        public MyViewHolder(final View view, OnNoteListener onNoteListener) {
            super(view);
            title = view.findViewById(R.id.mTitle);
            img = view.findViewById(R.id.ivTopic);

            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_list, parent, false);
        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String titleName = categoryList.get(position).getCategory();

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(true);

        holder.title.setText(titleName);
        holder.img.setImageResource(categoryList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(categoryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Category i : categoryListFull) {
                    if (i.getDescription().toLowerCase().contains(filterPattern) || i.getCategory().toLowerCase().contains(filterPattern)) {
                        filteredList.add(i);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categoryList.clear();
            categoryList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
