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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import yackeen.education.ta3allam.Capsule.SearchProfile;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.ui.activity.UserProfileActivity;

/**
 * Created by ahmed essam on 28/06/2017.
 */

public class SearchAutoCompleteAdapter extends ArrayAdapter<SearchProfile>  implements View.OnClickListener{
    private Context context;
    SearchHolder searchHolder;
    SearchProfile searchProfile;
    public SearchAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=convertView ;
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        searchProfile = getItem(position);
        if (v==null) {
            v = inflater.inflate(R.layout.search_item_layout, null);
            searchHolder = new SearchHolder();
            searchHolder.profileName=(TextView)v.findViewById(R.id.search_name_text);
            searchHolder.profileLevel=(TextView)v.findViewById(R.id.level_search);
            searchHolder.profileImage=(CircleImageView) v.findViewById(R.id.search_profile_image);
            v.setTag(searchHolder);
        }else {
            searchHolder = (SearchHolder) v.getTag();
        }
        searchHolder.profileName.setText(getItem(position).getUserName());
        searchHolder.profileLevel.setText(getItem(position).getUserType());
        Picasso.with(context).load(getItem(position).getUserPictureURL())
                .placeholder(R.drawable.default_emam)
                .error(R.drawable.default_emam)
                .into(searchHolder.profileImage);
        v.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent =UserProfileActivity.newUserProfileIntent(getContext(),searchProfile.getUserID());
        getContext().startActivity(intent);

    }

    static class SearchHolder {
        TextView profileName, profileLevel ;
        CircleImageView profileImage;
    }
}
