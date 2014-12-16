package uk.co.sruiz.budget.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import uk.co.sruiz.budget.Overviews;
import uk.co.sruiz.budget.R;

/**
* Created by Sebastian on 13/12/14.
*/
public class OverviewsAdapter extends BaseAdapter {

    private Activity mContext;
    private ArrayList mData;
    private LayoutInflater mLayoutInflater = null;

    public OverviewsAdapter(Activity context, ArrayList data) {
        mContext = context;
        mData = data;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Overviews> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        OverviewsListViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_overviews, null);
            viewHolder = new OverviewsListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (OverviewsListViewHolder) v.getTag();
        }

        Overviews overview = getItem(position).getValue();

        viewHolder.mText1.setText(overview.getDescription());
        viewHolder.mText2.setText(overview.getName());
        viewHolder.mText3.setText(overview.getAdmin());

        return v;
    }

}

class OverviewsListViewHolder {

    public TextView mText1;
    public TextView mText2;
    public TextView mText3;

    public OverviewsListViewHolder(View base) {
        mText1 = (TextView) base.findViewById(R.id.text1);
        mText2 = (TextView) base.findViewById(R.id.text2);
        mText3 = (TextView) base.findViewById(R.id.text3);
    }
}