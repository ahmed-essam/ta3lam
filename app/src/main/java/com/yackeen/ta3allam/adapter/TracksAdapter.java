package com.yackeen.ta3allam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yackeen.ta3allam.Capsule.Category;
import com.yackeen.ta3allam.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ahmed essam on 09/06/2017.
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder>{

    private List<Category> mCategory;
    private Context mContext;
    int selected_position = -1;

    public TracksAdapter(Context context)
    {
        mContext=context;
        mCategory=new ArrayList<>();

    }
    public Context getmContext()
    {
        return mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.category_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mCategory.size()!=0) {
            Category category = mCategory.get(position);
            holder.bindView(category);
        }else{
            Log.e(TAG, "onBindViewHolder: empty list" );
        }

    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }
    //method to add list to adapter
    public void addAll(List<Category> categoryList) {
        this.mCategory.clear();
        this.mCategory.addAll(categoryList);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView levelTextView;
        public TextView teacherTextView;
        public TextView studentTextView;
        public TextView questionTextView;
        LinearLayout mlinearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.decription);
            levelTextView = (TextView) itemView.findViewById(R.id.level);
            teacherTextView = (TextView) itemView.findViewById(R.id.teacher);
            studentTextView = (TextView) itemView.findViewById(R.id.student);
            questionTextView = (TextView) itemView.findViewById(R.id.question);
            mlinearLayout = (LinearLayout) itemView.findViewById(R.id.category_linear);
        }
        public void bindView(Category category){
            mlinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorLayoutBackground));
            nameTextView.setText(category.getName());
            descriptionTextView.setText(category.getDescription());
            levelTextView.setText(category.getLevel() + "مستوى");
            teacherTextView.setText(category.getTeacher() + "معلم");
            studentTextView.setText(category.getStudent() + "طالب");
            questionTextView.setText(category.getQuestion() + "سؤال");

        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selected_position);
            selected_position = getPosition();
            notifyItemChanged(selected_position);
        }
    }
}
