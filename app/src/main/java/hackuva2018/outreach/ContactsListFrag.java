package hackuva2018.outreach;


import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactsListFrag extends Fragment {

    public ContactsListFrag() {
    }

    public static ContactsListFrag newInstance() {
        ContactsListFrag fragment = new ContactsListFrag();
        return fragment;
    }


    ArrayList<Contact> list;
    RecyclerView rvGlossaryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        // Lookup the recyclerview in activity layout
        rvGlossaryList = (RecyclerView) rootview.findViewById(R.id.recycler_view);
        //Create a layout manager for the recyclerview
        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(getActivity());
        //Create and add dividers to the recyclerview
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(rvGlossaryList.getContext(),
               rvLayoutManager.getOrientation());
        rvGlossaryList.addItemDecoration(mDividerItemDecoration);
        // Initialize bucketlist
        list = prepareContactsList();
        // Create adapter passing in the sample user data
        ContactsListAdapter adapter = new ContactsListAdapter(getContext(), getFragmentManager(), list);
        // Attach the adapter to the recyclerview to populate items
        rvGlossaryList.setAdapter(adapter);
        // Set layout manager to position the items
        rvGlossaryList.setLayoutManager(rvLayoutManager);


        return rootview;
    }

    //Here is where we prepare the Contacts Data
    public ArrayList<Contact> prepareContactsList() {
        final ArrayList<Contact> contactList = new ArrayList<Contact>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);


        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Log.i("test", "Name: " + name);
                        //Log.i("test", "Phone Number: " + phoneNo);
                        Contact contact = new Contact(name, phoneNo);
                        contactList.add(contact);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }


        return contactList;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(String.format("Glossary"));
    }
}