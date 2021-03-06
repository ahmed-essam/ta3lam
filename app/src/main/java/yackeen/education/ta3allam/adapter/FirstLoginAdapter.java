package yackeen.education.ta3allam.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import yackeen.education.ta3allam.Capsule.Category;
import yackeen.education.ta3allam.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by devar on 8/7/16.
 */
public class FirstLoginAdapter extends RecyclerView.Adapter<FirstLoginAdapter.ViewHolder> {
    public static final String TAG = FirstLoginAdapter.class.getSimpleName();

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView levelTextView;
        public TextView teacherTextView;
        public TextView studentTextView;
        public TextView questionTextView;
        LinearLayout categorylayout;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.name);
            descriptionTextView = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.decription);
            levelTextView = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.level);
            teacherTextView = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.teacher);
            studentTextView = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.student);
            questionTextView = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.question);
            categorylayout = (LinearLayout)itemView.findViewById(yackeen.education.ta3allam.R.id.category_linear);
            itemView.setOnClickListener(this);
        }
        public void bindView(Category category){
            nameTextView.setText(category.getName());
            descriptionTextView.setText(category.getDescription());
            levelTextView.setText(category.getLevel() + "مستوى");
            teacherTextView.setText(category.getTeacher() + "معلم");
            studentTextView.setText(category.getStudent() + "طالب");
            questionTextView.setText(category.getQuestion() + "سؤال");
           int index= mCategory.indexOf(category);
            if(index == selected_position){
                categorylayout.setBackgroundColor(itemView.getResources().getColor(yackeen.education.ta3allam.R.color.real_accent_color));
                nameTextView.setTextColor(itemView.getResources().getColor(R.color.colorTextTitle));
                descriptionTextView.setTextColor(itemView.getResources().getColor(R.color.colorTextTitle));
                levelTextView.setTextColor(itemView.getResources().getColor(R.color.colorTextTitle));
                teacherTextView.setTextColor(itemView.getResources().getColor(R.color.colorTextTitle));
                studentTextView.setTextColor(itemView.getResources().getColor(R.color.colorTextTitle));
                studentTextView.setTextColor(itemView.getResources().getColor(R.color.colorTextTitle));
                questionTextView.setTextColor(itemView.getResources().getColor(R.color.colorTextTitle));

            }
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selected_position);
            selected_position = getPosition();
            Log.e(TAG, "onClick: "+ selected_position );
            notifyItemChanged(selected_position);
            Toast.makeText(mContext,"clicked",Toast.LENGTH_SHORT).show();
        }
    }
    private List<Category> mCategory;
    private Context mContext;
    private int selected_position = -1;

    public int getSelected_position() {
        return selected_position;
    }

    public FirstLoginAdapter(Context context)
    {
        mContext=context;
        mCategory=new ArrayList<>();

    }
    public Context getmContext()
    {
        return mContext;
    }
    @Override
    public FirstLoginAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(yackeen.education.ta3allam.R.layout.category_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(FirstLoginAdapter.ViewHolder viewHolder, final int position) {
        if(mCategory.size()!=0) {
            Category category = mCategory.get(position);
            viewHolder.bindView(category);
//        if(selected_position == position) {
//            viewHolder.itemView.setBackgroundColor(0xFFF8AC46);
//        }else{
//            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
//        }

//            TextView name = viewHolder.nameTextView;
//            name.setText(category.getName());
//            TextView description = viewHolder.descriptionTextView;
//            description.setText(category.getDescription());
//            TextView level = viewHolder.levelTextView;
//            level.setText(category.getLevel() + "مستوي");
//            TextView teacher = viewHolder.teacherTextView;
//            teacher.setText(category.getTeacher() + "معلم");
//            TextView student = viewHolder.studentTextView;
//            student.setText(category.getStudent() + "طالب");
//            TextView question = viewHolder.questionTextView;
//            question.setText(category.getQuestion() + "سؤال");
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
}