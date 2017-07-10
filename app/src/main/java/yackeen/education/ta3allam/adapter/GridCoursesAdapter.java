package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import yackeen.education.ta3allam.Capsule.Book;
import yackeen.education.ta3allam.Capsule.BookDetail;
import yackeen.education.ta3allam.Capsule.UserBooks;
import yackeen.education.ta3allam.ui.activity.BookDetailActivity;

/**
 * Created by ahmed essam on 08/06/2017.
 */

public class GridCoursesAdapter extends ArrayAdapter<UserBooks> implements View.OnClickListener {
    private Context context;
     ViewHolder viewHolder;
    UserBooks userBook;
    public GridCoursesAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=convertView ;
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        if (v==null) {
            v = inflater.inflate(yackeen.education.ta3allam.R.layout.grid_item_laayout, null);
            viewHolder = new ViewHolder();
            viewHolder.bookName=(TextView)v.findViewById(yackeen.education.ta3allam.R.id.book_name);
            viewHolder.emamName=(TextView)v.findViewById(yackeen.education.ta3allam.R.id.emam_name);
            viewHolder.donutProgress=(DonutProgress)v.findViewById(yackeen.education.ta3allam.R.id.donut_progress);
            v.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) v.getTag();
        }

        userBook = getItem(position);

        viewHolder.bookName.setText(getItem(position).getBookName());
        viewHolder.emamName.setText(""+getItem(position).getParticipantsNumber()+" طالب");
        if (getItem(position).getPercentage()*100 > 100){
            viewHolder.donutProgress.setProgress(100);
        }else if(getItem(position).getPercentage()*100<0){
            viewHolder.donutProgress.setProgress(0);
        }else {
            viewHolder.donutProgress.setProgress((int) (getItem(position).getPercentage() * 100));
        }
        v.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        Book book = new Book();
        book.setId(userBook.getBookID());
        Intent intent =BookDetailActivity.newDetailIntent(getContext(),book);
        getContext().startActivity(intent);
    }

    static class ViewHolder {
        TextView bookName, emamName ;
        DonutProgress donutProgress;
    }
}
