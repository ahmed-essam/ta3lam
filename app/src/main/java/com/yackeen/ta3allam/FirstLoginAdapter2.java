package com.yackeen.ta3allam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by devar on 8/7/16.
 */
public class FirstLoginAdapter2 extends RecyclerView.Adapter<FirstLoginAdapter2.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView levelTextView;
        public TextView teacherTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            levelTextView = (TextView) itemView.findViewById(R.id.level);
            teacherTextView = (TextView) itemView.findViewById(R.id.teacher);
        }
    }
    private List<Book> mBook;
    private Context mContext;

    public FirstLoginAdapter2(Context context, List<Book> books)
    {
        mContext=context;
        mBook=books;
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
    public void onBindViewHolder(FirstLoginAdapter2.ViewHolder viewHolder, int position) {
        Book book = mBook.get(position);

        TextView name = viewHolder.nameTextView;
        name.setText(book.getName());
        TextView level = viewHolder.levelTextView;
        level.setText(book.getLevel());
        TextView teacher = viewHolder.teacherTextView;
        teacher.setText(book.getTeacher());
    }
    @Override
    public int getItemCount() {
        return mBook.size();
    }
}