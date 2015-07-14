package net.icepc.vertretungsplan;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class tomorrowFragment extends Fragment implements View.OnClickListener {
    private View viewRoot;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floatingActionButton;
    public ArrayList<replacementData> data = new ArrayList<replacementData>();
    public String URL = "http://www.aesmtk.de/cms/stundenplan/schueler/subst_00";
    ArrayList<replacementData> testData = new ArrayList<replacementData>();

    public tomorrowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewRoot = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        floatingActionButton = (FloatingActionButton) viewRoot.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

        recyclerView = (RecyclerView) viewRoot.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //getData();
        /*
        testData.add(new replacementData(3, "9.", "RAK", "MUC", "Heute kein Nachmittagsunterricht", "Informatik", "204", false));
        testData.add(new replacementData(4, "Q12", "KSK", "MUC", "Sporthalle Hahnstraße", "Sport", "Halle", true));
        testData.add(new replacementData(9, "E12", "BRN", "SVE", "", "Informatik", "35", false));
        testData.add(new replacementData(1, "8.", "BL", "PHI", "", "Mathe", "1", false));
        testData.add(new replacementData(6, "6.", "SMI", "BEC", "", "Physik", "255", false));
        testData.add(new replacementData(10, "Q34", "CLY", "SON", "", "Bio", "TH2", true));
        testData.add(new replacementData(11, "E12", "CH", "GMD", "", "E-LK", "311", true));
        testData.add(new replacementData(4, "7.", "MEI", "SJ", "", "Deutsch", "54", true));
        */

        CustomAsyncTask customAsyncTask = new CustomAsyncTask();
        customAsyncTask.execute();

        return viewRoot;
    }

    public void parseServerData(Document document) {
        //Log.d("Doc: ", document+"");
        Integer first = 0;
        Integer limit = 3;

        Calendar calendar = Calendar.getInstance();
        int day_index = calendar.get(Calendar.DAY_OF_MONTH);
        day_index++;

        Log.d("Day_Index tomorrow : ",day_index+"");

        String[] daysOfWeek = {"","So","Mo","Di","Mi","Do","Fr","Sa"};

        //Log.d("Today it's ",daysOfWeek[day_index]+"");

        for (Element element : document.select("tr")) {
            if (first < limit) {
                first++;

                if (first == limit) {
                    //Log.d("", element.text());
                    if (Character.isDigit(element.text().charAt(0))) {
                        //Log.d("Character",""+element.text());
                        first++;
                        continue;
                    }
                    else {
                        limit++;
                    }
                }
                continue;
            }

            Elements td = element.select("td");
            int canceled = 0;

            if (td.get(3).text().equals("---") || td.get(3).text().equals("+")){
                canceled = 1;
            }
            else if (!StringUtil.isBlank(td.get(2).text())){
                canceled = 0;
            }
            else {
                canceled = 2;
            }

            int tomorrow = Integer.toString(day_index).charAt(0) + Integer.toString(day_index).charAt(1);
            String tomorrowString = Integer.toString(tomorrow);
            int current = td.get(1).text().charAt(0) + td.get(1).text().charAt(1);
            String currentString = Integer.toString(current);


            if (tomorrowString.equals(currentString))
            {
                data.add(new replacementData(td.get(2).text(), td.get(3).text(), td.get(4).text(), td.get(7).text(), td.get(8).text(), td.get(5).text(), td.get(10).text(), canceled));
            }
            // DATUM
            //Log.d(td.get(1).text()+"","");

            // KURS
            //Log.d(td.get(2).text()+"","");

            // VERTRETER
            //Log.d(td.get(3).text()+"","");

            // STUNDEN
            //Log.d(td.get(4).text()+"","");

            // NEUES FACH
            //Log.d(td.get(5).text()+"","");

            // ALTES FACH
            //Log.d(td.get(6).text()+"","");

            // NEUER RAUM
            //Log.d(td.get(7).text()+"","");

            // WEITERE INFO
            //Log.d(td.get(10).text()+"","");


            //new replacementData(td.get(index++).text(),td.get(index++).text(),td.get(index++).text());

            // Here you can do something with each element
            //Log.d("",element.text());
        }

    }

    public void getData() {
        recyclerViewAdapter = new RecyclerViewAdapter(testData);
        recyclerView.setAdapter(recyclerViewAdapter);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                new CustomAsyncTask().execute();
                Toast.makeText(getActivity(), "Daten werden neu geladen...",Toast.LENGTH_LONG).show();

        }


    }

    public class CustomAsyncTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;
            //getData();
            // Connect to website
            for (int i = 1; i < 9; i++) {
                try {
                    Document doc = Jsoup.connect(URL + i + ".html").get();
                    parseServerData(doc);
                    result = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean = true) {
                recyclerViewAdapter = new RecyclerViewAdapter(data);
                recyclerView.setAdapter(recyclerViewAdapter);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aBoolean);
        }
    }

}
