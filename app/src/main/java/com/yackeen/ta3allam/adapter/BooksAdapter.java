package com.yackeen.ta3allam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yackeen.ta3allam.Capsule.Book;
import com.yackeen.ta3allam.R;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ahmed essam on 12/06/2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        public TextView levelTextView;
        public TextView teacherTextView;
        LinearLayout bookLinearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            levelTextView = (TextView) itemView.findViewById(R.id.level);
            teacherTextView = (TextView) itemView.findViewById(R.id.teacher);
            bookLinearLayout = (LinearLayout)itemView.findViewById(R.id.book_item_linear);
        }
        public void bindView(Book book){
            bookLinearLayout.setBackgroundColor(itemView.getResources().getColor(R.color.bookItemBackground));
            nameTextView.setText(book.getName());
            levelTextView.setText(Integer.toString(book.getLevel()));
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
    private List<Book> bookList;
    private Context mContext;
    int selected_position = -1;

    public BooksAdapter(Context mContext) {
        this.mContext = mContext;
        this.bookList= new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View bookView = inflater.inflate(R.layout.book_list_item, parent, false);

        BooksAdapter.ViewHolder viewHolder = new BooksAdapter.ViewHolder(bookView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bindView(book);

    }
    public void addAll(List<Book> books) {
        this.bookList.clear();
        this.bookList.addAll(books);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
