package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import yackeen.education.ta3allam.Capsule.Category;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.ui.activity.BooksActivity;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ahmed essam on 09/06/2017.
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder>{
    public class TracksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView levelTextView;
        public TextView teacherTextView;
        public TextView studentTextView;
        public TextView questionTextView;
        LinearLayout mlinearLayout;
        public TracksViewHolder(View itemView) {
            super(itemView);
            mlinearLayout = (LinearLayout) itemView.findViewById(R.id.category_linear);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.decription);
            levelTextView = (TextView) itemView.findViewById(R.id.level);
            teacherTextView = (TextView) itemView.findViewById(R.id.teacher);
            studentTextView = (TextView) itemView.findViewById(R.id.student);
            questionTextView = (TextView) itemView.findViewById(R.id.question);
            itemView.setOnClickListener(this);
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
            Category category = mCategory.get(getPosition());
            Intent intent = BooksActivity.newIntent(getmContext(),category.getId(),category.getName());
            getmContext().startActivity(intent);

        }
    }

    private List<Category> mCategory;
    private Context mContext;

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
    public TracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.category_list_item, parent, false);

        TracksAdapter.TracksViewHolder viewHolder = new TracksAdapter.TracksViewHolder(categoryView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(TracksViewHolder holder, int position) {
        if(mCategory.size()!=0) {
            Category category = mCategory.get(position);
            holder.bindView(category);
        }else{
            Log.e(TAG, "onBindViewHolder: empty list" );
        }

    }//method to add list to adapter
    public void addAll(List<Category> categoryList) {
        Log.e(TAG, "track_adapter_addAll: "+categoryList.size());
        this.mCategory.clear();
        this.mCategory.addAll(categoryList);
        Log.e(TAG, "addAll: mCategory"+mCategory.size() );
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }





}
