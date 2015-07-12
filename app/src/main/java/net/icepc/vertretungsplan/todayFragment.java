package net.icepc.vertretungsplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class todayFragment extends Fragment {
    private View viewRoot;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public todayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewRoot = inflater.inflate(R.layout.fragment_today, container, false);
        getData();


        return viewRoot;
    }

    public void getData() {

        ArrayList<replacementData> testData = new ArrayList<replacementData>();
        testData.add(new replacementData(3, "RAK", "MUC", "Heute kein Nachmittagsunterricht", "Informatik", "204", false));
        testData.add(new replacementData(4, "KSK", "MUC", "Sporthalle Hahnstraße", "Sport", "Halle", true));
        testData.add(new replacementData(9, "BRN", "SVE", "", "Informatik", "35", false));
        testData.add(new replacementData(1, "BL", "PHI", "", "Mathe", "1", false));
        testData.add(new replacementData(6, "SMI", "BEC", "", "Physik", "255", false));
        testData.add(new replacementData(10, "CLY", "SON", "", "Bio", "TH2", true));
        testData.add(new replacementData(11, "CH", "GMD", "", "E-LK", "311", true));
        testData.add(new replacementData(4, "MEI", "SJ", "", "Deutsch", "54", true));

        recyclerView = (RecyclerView) viewRoot.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(testData);
        recyclerView.setAdapter(recyclerViewAdapter);


    }


}
