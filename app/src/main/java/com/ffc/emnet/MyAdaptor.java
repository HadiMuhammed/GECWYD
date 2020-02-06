package com.ffc.emnet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdaptor extends BaseAdapter implements Filterable {

    public static List<String> item;
    Context context;
    ArrayList<Mycontacts> mycontactsArrayList;
    ArrayList<Mycontacts> filtereddata;
    private ItemFilter mFilter = new ItemFilter();
    public MyAdaptor(Context context, ArrayList<Mycontacts> mycontactsArrayList)
    {
        this.context = context;
        this.mycontactsArrayList = mycontactsArrayList;
        this.filtereddata = mycontactsArrayList;
    }

    @Override
    public int getCount() {
        return (mycontactsArrayList == null) ? 0 : mycontactsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mycontactsArrayList.get(i).getNumber();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try{
            if(view == null)
            {
                view = LayoutInflater.from(context).inflate(R.layout.contacts_list_item,null);

                TextView phone = (TextView) view.findViewById(R.id.phonenumber);
                TextView name = (TextView) view.findViewById(R.id.name);
                Mycontacts mycontacts = mycontactsArrayList.get(i);
                phone.setText(mycontacts.getNumber());
                name.setText(mycontacts.getName().trim());

                item.add(mycontacts.getNumber());

                return view;
            }

        }
        catch (Exception e)
        {}

        return view;

    }

    public Filter getFilter() {
        return mFilter;
    }

private class ItemFilter extends  Filter{

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<Mycontacts> list = mycontactsArrayList;

        int count = list.size();
        final ArrayList<Mycontacts> nlist = new ArrayList<Mycontacts>(count);

        Mycontacts filterableString ;

        for (int i = 0; i < count; i++) {
            filterableString = list.get(i);
            if (filterableString.getName().toLowerCase().contains(filterString.toLowerCase())) {
                { nlist.add(filterableString);
                }
                }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;

    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        filtereddata = (ArrayList<Mycontacts>) filterResults.values;

        notifyDataSetChanged();

    }
}

}
