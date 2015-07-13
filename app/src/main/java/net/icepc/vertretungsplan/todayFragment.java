package net.icepc.vertretungsplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class todayFragment extends Fragment implements View.OnClickListener{
    private View viewRoot;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floatingActionButton;

    public todayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewRoot = inflater.inflate(R.layout.fragment_today, container, false);
        floatingActionButton = (FloatingActionButton) viewRoot.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

        recyclerView = (RecyclerView) viewRoot.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getData();


        return viewRoot;
    }

    public void getData() {

        ArrayList<replacementData> testData = new ArrayList<replacementData>();
        testData.add(new replacementData(3, "9.","RAK", "MUC", "Heute kein Nachmittagsunterricht", "Informatik", "204", false));
        testData.add(new replacementData(4, "Q12","KSK", "MUC", "Sporthalle Hahnstraße", "Sport", "Halle", true));
        testData.add(new replacementData(9, "E12","BRN", "SVE", "", "Informatik", "35", false));
        testData.add(new replacementData(1, "8.","BL", "PHI", "", "Mathe", "1", false));
        testData.add(new replacementData(6, "6.", "SMI", "BEC", "", "Physik", "255", false));
        testData.add(new replacementData(10, "Q34", "CLY", "SON", "", "Bio", "TH2", true));
        testData.add(new replacementData(11, "E12","CH", "GMD", "", "E-LK", "311", true));
        testData.add(new replacementData(4, "7.", "MEI", "SJ", "", "Deutsch", "54", true));



        recyclerViewAdapter = new RecyclerViewAdapter(testData);
        recyclerView.setAdapter(recyclerViewAdapter);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.floatingActionButton:
                getData();

        }


    }
}
