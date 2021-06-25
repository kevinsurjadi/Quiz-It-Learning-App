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

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class LearnRecyclerAdapter extends RecyclerView.Adapter<LearnRecyclerAdapter.MyViewHolder> implements Filterable {
    private List<Result> categoryList;
    private List<Result> categoryListFull;

//    private OnNoteListener mOnNoteListener;

    public LearnRecyclerAdapter(List<Result> categoryList) {
        this.categoryList = categoryList;
//        this.mOnNoteListener = onNoteListener;
        categoryListFull = new ArrayList<>(categoryList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView question;
        private TextView answer;
        //private ImageView img;

//        OnNoteListener onNoteListener;

        public MyViewHolder(final View view) {
            super(view);
            question = view.findViewById(R.id.tvQuestionContent);
            answer = view.findViewById(R.id.tvAnswerContent);
            //title = view.findViewById(R.id.title2);
            //img = view.findViewById(R.id.img2);

//            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
//
    @NonNull
    @Override
    public LearnRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnRecyclerAdapter.MyViewHolder holder, int position) {
        String titleName = Jsoup.clean(categoryList.get(position).getQuestion(), new Whitelist());
        String ans = Jsoup.clean(categoryList.get(position).getCorrectAnswer(), new Whitelist());

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(true);

        holder.question.setText(titleName);
        holder.answer.setText(ans);
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
            List<Result> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(categoryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Result i : categoryListFull) {
                    if (false) {
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
