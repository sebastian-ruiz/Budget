package uk.co.sruiz.budget.Fragments;

import java.util.ArrayList;

import com.keysolutions.ddpclient.android.DDPBroadcastReceiver;
import com.keysolutions.ddpclient.android.DDPStateSingleton;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import uk.co.sruiz.budget.MyDDPState;
import uk.co.sruiz.budget.Adapters.OverviewsAdapter;
import uk.co.sruiz.budget.R;

public class OverviewsFragment extends Fragment implements View.OnClickListener {

    //The fragment argument representing the item ID that this fragment represents.
    public static final String ARG_ITEM_ID = "item_id";

    //Make receiver a member variable of a class to be able to unregister it later
    private BroadcastReceiver mReceiver;

    private View mView;
    private Button mDataChangedBtn;
    private ArrayList mData;
    private ListView mOverviewsListView;
    private OverviewsAdapter mOverviewsAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OverviewsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_overviews, container, false);
        mView = rootView;

        initViews();

        //setup the ListView Adapter
        mOverviewsAdapter = new OverviewsAdapter(getActivity(), mData);
        mOverviewsListView.setAdapter(mOverviewsAdapter);

        return rootView;
    }

    public void initViews() {
        mOverviewsListView = (ListView) mView.findViewById(R.id.overviews_listView);
        mData = new ArrayList();

        mDataChangedBtn = (Button) mView.findViewById(R.id.datachanged_button);
        mDataChangedBtn.setOnClickListener(this);
    }

    public void dataChanged() {

        mData.clear();
        mData.addAll(MyDDPState.getInstance().getOverviews().entrySet());
        mOverviewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mReceiver != null) {
            // unhook the receiver
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // get ready to handle DDP events
        mReceiver = new DDPBroadcastReceiver(MyDDPState.getInstance(), getActivity()) {
            @Override
            protected void onDDPConnect(DDPStateSingleton ddp) {
                super.onDDPConnect(ddp);
                // add our subscriptions needed for the activity here
                ddp.subscribe("overviews", new Object[] {});
                ddp.subscribe("items", new Object[] {});
                Log.v("OverviewsFragment", "subscribed to overviews and items.");
                dataChanged();
            }
            @Override
            protected void onSubscriptionUpdate(String changeType, String subscriptionName, String docId) {
                if (subscriptionName.equals("overviews")) {
                    // show any overviews
                    Log.v("OverviewsFragment", "subscription update!");
                    dataChanged();
                }
            }
            @Override
            protected void onLogin() {
                // update login/logout action button
//                getActivity().invalidateOptionsMenu();
            }
            @Override
            protected void onLogout() {
                // update login/logout action button
//                getActivity().invalidateOptionsMenu();
            }
        };
        // start connection process if we're not connected
        MyDDPState.getInstance().connectIfNeeded();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datachanged_button:
                dataChanged();
                break;
        }
    }
}
