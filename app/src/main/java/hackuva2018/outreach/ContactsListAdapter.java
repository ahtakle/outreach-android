package hackuva2018.outreach;

/**
 * Created by htakle on 3/25/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;


public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {

    private List<Contact> contactList;
    private android.support.v4.app.FragmentManager mFragmentManager;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox title;

        public ViewHolder(View view) {
            super(view);
            title = (CheckBox) view.findViewById(R.id.contactsItem);
        }
    }

    // Store the context for easy access
    public Context mContext;


    // Pass in the contact array into the constructor
    public ContactsListAdapter(Context context, FragmentManager fragmentManager, List<Contact> list) {
        contactList = list;
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        final View contactView = inflater.inflate(R.layout.contacts_item, parent, false);
        //Holder instance to be returned
        final ViewHolder viewHolder = new ViewHolder(contactView);

        // Return a new holder instance
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Contact term = contactList.get(position);
        //holder.title. = new TextView(getContext());
        holder.title.setText(term.getName());

        //int textColor = R.color.colorText;
        //holder.title.setTextColor(term.getColor());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}