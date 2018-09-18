package tz.co.aim.msisdn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ListAdapter extends BaseAdapter {
    private final ArrayList mData;

    public  ListAdapter (HashMap<String, String>contactDetails){
        mData = new ArrayList();
        mData.addAll(contactDetails.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public  HashMap.Entry<String, String> getItem(int position) {
        return(HashMap.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final View result;

        if (view == null ){
            result = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_items, viewGroup, false);
        }else {
            result = view;
        }

        return result;
    }
}
