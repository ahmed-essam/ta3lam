package com.yackeen.ta3allam.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.yackeen.ta3allam.Capsule.UserBooks;
import com.yackeen.ta3allam.R;

/**
 * Created by ahmed essam on 08/06/2017.
 */

public class GridCoursesAdapter extends ArrayAdapter<UserBooks> {
    private Context context;
     ViewHolder viewHolder;
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
            v = inflater.inflate(R.layout.grid_item_laayout, null);
            viewHolder = new ViewHolder();
            viewHolder.bookName=(TextView)v.findViewById(R.id.book_name);
            viewHolder.emamName=(TextView)v.findViewById(R.id.emam_name);
            viewHolder.donutProgress=(DonutProgress)v.findViewById(R.id.donut_progress);
            v.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.bookName.setText(getItem(position).getBookName());
        viewHolder.emamName.setText(getItem(position).getTeacherName());
        viewHolder.donutProgress.setProgress((int)(getItem(position).getPercentage()*100));
        return v;
    }
    static class ViewHolder {
        TextView bookName, emamName ;
        DonutProgress donutProgress;
    }
}
