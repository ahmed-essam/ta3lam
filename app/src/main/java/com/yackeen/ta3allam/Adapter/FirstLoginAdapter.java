package com.yackeen.ta3allam.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.Capsule.Category;

import java.util.List;

/**
 * Created by devar on 8/7/16.
 */
public class FirstLoginAdapter extends RecyclerView.Adapter<FirstLoginAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView levelTextView;
        public TextView teacherTextView;
        public TextView studentTextView;
        public TextView questionTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.decription);
            levelTextView = (TextView) itemView.findViewById(R.id.level);
            teacherTextView = (TextView) itemView.findViewById(R.id.teacher);
            studentTextView = (TextView) itemView.findViewById(R.id.student);
            questionTextView = (TextView) itemView.findViewById(R.id.question);
        }
    }
    private List<Category> mCategory;
    private Context mContext;
    int selected_position = -1;

    public FirstLoginAdapter(Context context, List<Category> categories)
    {
        mContext=context;
        mCategory=categories;
    }
    public Context getmContext()
    {
        return mContext;
    }
    @Override
    public FirstLoginAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.category_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(FirstLoginAdapter.ViewHolder viewHolder, final int position) {
        Category category = mCategory.get(position);
        if(selected_position == position) {
            viewHolder.itemView.setBackgroundColor(0xFFF8AC46);
        }else{
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selected_position);
                selected_position = position;
                notifyItemChanged(selected_position);
            }
        });
        TextView name = viewHolder.nameTextView;
        name.setText(category.getName());
        TextView description = viewHolder.descriptionTextView;
        description.setText(category.getDescription());
        TextView level = viewHolder.levelTextView;
        level.setText(category.getLevel()+"مستوى");
        TextView teacher = viewHolder.teacherTextView;
        teacher.setText(category.getTeacher()+"معلم");
        TextView student = viewHolder.studentTextView;
        student.setText(category.getStudent()+"طالب");
        TextView question = viewHolder.questionTextView;
        question.setText(category.getQuestion()+"سؤال");
    }
    @Override
    public int getItemCount() {
        return mCategory.size();
    }
}