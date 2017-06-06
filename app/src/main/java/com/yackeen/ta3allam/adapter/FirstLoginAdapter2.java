package com.yackeen.ta3allam.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yackeen.ta3allam.Capsule.Category;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.Capsule.Book;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by devar on 8/7/16.
 */
public class FirstLoginAdapter2 extends RecyclerView.Adapter<FirstLoginAdapter2.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        public TextView levelTextView;
        public TextView teacherTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            levelTextView = (TextView) itemView.findViewById(R.id.level);
            teacherTextView = (TextView) itemView.findViewById(R.id.teacher);
        }
        public void bindView(Book book){
            nameTextView.setText(book.getName());
            levelTextView.setText(book.getLevel());
            teacherTextView.setText(book.getTeacher());
            Log.e(TAG, "bindView2: "+book.getName());
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selected_position);
            selected_position = getPosition();
            notifyItemChanged(selected_position);
        }
    }
    private List<Book> mBook;
    private Context mContext;
    int selected_position = -1;

    public FirstLoginAdapter2(Context context)
    {
        mContext=context;
        mBook=new ArrayList<>();
    }
    public Context getmContext()
    {
        return mContext;
    }
    @Override
    public FirstLoginAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View bookView = inflater.inflate(R.layout.book_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(bookView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(FirstLoginAdapter2.ViewHolder viewHolder, final int position) {
        Book book = mBook.get(position);
        viewHolder.bindView(book);
//        if(selected_position == position) {
//            viewHolder.itemView.setBackgroundColor(0xFFF8AC46);
//        }else{
//            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
//        }


//        TextView name = viewHolder.nameTextView;
//        name.setText(book.getName());
//        TextView level = viewHolder.levelTextView;
//        level.setText(book.getLevel());
//        TextView teacher = viewHolder.teacherTextView;
//        teacher.setText(book.getTeacher());
    }
    @Override
    public int getItemCount() {
        return mBook.size();
    }
    //method to add list to adapter
    public void addAll(List<Book> categoryList) {
        this.mBook.clear();
        this.mBook.addAll(categoryList);
        notifyDataSetChanged();

    }
}