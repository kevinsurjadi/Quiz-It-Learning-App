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

public class LeaderBoardRecyclerAdapter extends RecyclerView.Adapter<LeaderBoardRecyclerAdapter.MyViewHolder> implements Filterable {
    private List<LeaderBoard> leaderBoardList;
    private List<LeaderBoard> leaderBoardListFull;

    public LeaderBoardRecyclerAdapter(List<LeaderBoard> leaderBoardList) {
        this.leaderBoardList = leaderBoardList;
        leaderBoardListFull = new ArrayList<>(leaderBoardList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView level;
        private TextView points;

        OnNoteListener onNoteListener;

        public MyViewHolder(final View view) {
            super(view);
            name = view.findViewById(R.id.lead_name);
            level = view.findViewById(R.id.lead_lev);
            points = view.findViewById(R.id.lead_points);

            this.onNoteListener = onNoteListener;
        }
    }

    @NonNull
    @Override
    public LeaderBoardRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardRecyclerAdapter.MyViewHolder holder, int position) {
        String lName = leaderBoardList.get(position).getName();
        String lPoints = String.format("%s points",leaderBoardList.get(position).getScore());
        String lLev = String.format("Level %s",leaderBoardList.get(position).getLevel());

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(true);

        holder.name.setText(lName);
        holder.level.setText(lLev);
        holder.points.setText(lPoints);
    }

    @Override
    public int getItemCount() {
        return leaderBoardList.size();
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
            List<LeaderBoard> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(leaderBoardListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            leaderBoardList.clear();
            leaderBoardList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
