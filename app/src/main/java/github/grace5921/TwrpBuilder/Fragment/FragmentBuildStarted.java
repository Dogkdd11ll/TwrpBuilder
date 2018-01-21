package github.grace5921.TwrpBuilder.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import github.grace5921.TwrpBuilder.R;
import github.grace5921.TwrpBuilder.util.FirebaseProgressBar;
import github.grace5921.TwrpBuilder.util.Queue;

/**
 * Created by androidlover5842 on 18/1/18.
 */

public class FragmentBuildStarted extends Fragment {

    private Query query;
    private FirebaseListAdapter<Queue> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_build_started,container,false);

        ListView lvRunningBuilds= view.findViewById(R.id.lv_build_started);

        query = FirebaseDatabase.getInstance()
                .getReference("RunningBuild");

        FirebaseListOptions<Queue> options = new FirebaseListOptions.Builder<Queue>()
                .setLayout(R.layout.list_in_queue)
                .setQuery(query,Queue.class)
                .build();

        adapter=new FirebaseListAdapter<Queue>(options) {
            @Override
            protected void populateView(View v, Queue model, int position) {
                TextView tvEmail = v.findViewById(R.id.list_user_email);
                TextView tvDevice = v.findViewById(R.id.list_user_device);
                TextView tvBoard = v.findViewById(R.id.list_user_board);
                TextView tvDate= v.findViewById(R.id.list_user_date);
                TextView tvBrand = v.findViewById(R.id.list_user_brand);
                tvDate.setText("Date : "+model.MDate());
                tvEmail.setText("Email : "+model.MEmail());
                tvDevice.setText("Model : " + model.MModel());
                tvBoard.setText("Board : "+model.MBoard());
                tvBrand.setText("Brand : " +model.MBrand());

            }
        };

        lvRunningBuilds.setAdapter(adapter);

        ProgressBar progressBar=(ProgressBar)view.findViewById(R.id.pb_builds);
        TextView textView=(TextView)view.findViewById(R.id.tv_no_build);
        new FirebaseProgressBar().start(progressBar,textView,adapter,"RunningBuild");

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
