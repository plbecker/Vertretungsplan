package net.icepc.vertretungsplan;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class todayFragment extends Fragment implements View.OnClickListener {
    private View viewRoot;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floatingActionButton;
    public ArrayList<replacementData> data = new ArrayList<replacementData>();
    public String URL = "http://www.aesmtk.de/cms/stundenplan/schueler/subst_0";
    ArrayList<replacementData> testData = new ArrayList<replacementData>();

    public CustomAsyncTask customAsyncTask;
    public SharedPreferences myPrefs;
    public final Handler handler = new Handler();
    public Runnable runnable;

    public todayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewRoot = inflater.inflate(R.layout.fragment_today, container, false);

        myPrefs = this.getActivity().getSharedPreferences("net.icepc.vertretungsplan", Context.MODE_PRIVATE);
        //this.getActivity().getSharedPreferences("net.icepc.vertretungsplan", 0).edit().clear();
        //Log.d("Prefs", "" + myPrefs.getAll());
        // save data to preferences!


        floatingActionButton = (FloatingActionButton) viewRoot.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

        recyclerView = (RecyclerView) viewRoot.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        customAsyncTask = new CustomAsyncTask();
        customAsyncTask.execute();

        return viewRoot;
    }

    public void parseServerData(Document document) {
        //Log.d("Doc: ", document+"");
        Integer first = 0;
        Integer limit = 3;

        Calendar calendar = Calendar.getInstance();
        int day_index = calendar.get(Calendar.DAY_OF_MONTH);

        //Log.d("Day_Index",day_index+"");

        String[] daysOfWeek = {"", "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"};

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
                    } else {
                        limit++;
                    }
                }
                continue;
            }

            Elements td = element.select("td");
            int canceled = 0;

            if (td.get(3).text().equals("---") || td.get(3).text().equals("+")) {
                canceled = 1;
            }

            if (td.get(2).text().equals(" ")) {
                canceled = 2;
            }

            if ((Integer.toString(day_index).charAt(0) + Integer.toString(day_index).charAt(1)) == (td.get(1).text().charAt(0) + td.get(1).text().charAt(1))) {
                replacementData rData = new replacementData(td.get(2).text(), td.get(3).text(), td.get(4).text(), td.get(7).text(), td.get(8).text(), td.get(5).text(), td.get(10).text(), canceled);

                //Log.d("replacementData = ",""+rData);
                //data.add(rData);

                if (data.contains(rData)) {
                    //Log.d("Old Stuff!","");

                } else if (!data.contains(rData)) {
                    //Log.d("New Stuff!","");
                    data.add(new replacementData(td.get(2).text(), td.get(3).text(), td.get(4).text(), td.get(7).text(), td.get(8).text(), td.get(5).text(), td.get(10).text(), canceled));

                    // Make notification
                    Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(getActivity(),
                            123123, notificationIntent,
                            PendingIntent.FLAG_CANCEL_CURRENT);

                    NotificationManager nm = (NotificationManager) getActivity()
                            .getSystemService(Context.NOTIFICATION_SERVICE);

                    Resources res = getActivity().getResources();
                    Notification.Builder builder = new Notification.Builder(getActivity());

                    builder.setContentIntent(contentIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                            .setTicker("Neue Vertretungen")
                            .setWhen(System.currentTimeMillis())
                            .setAutoCancel(true)
                            .setContentTitle("Neue Vertretungen!");

                    if (rData.canceled == 1) {
                        builder.setContentText("Für " + td.get(2).text() + " entfällt am " + td.get(1).text() + " " + td.get(6).text() + " in der " + td.get(4).text() + ". Std.");
                    }


                    nm.notify(1, builder.getNotification());


                    //Toast.makeText(getActivity(), "Neue Vertretungen!", Toast.LENGTH_LONG).show();
                }
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

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                new CustomAsyncTask().execute("X");
                Toast.makeText(getActivity(), "Daten werden neu geladen...", Toast.LENGTH_LONG).show();

        }


    }

    public class CustomAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;
            //getData();
            // Connect to website
            if (!params.equals("X")) {
                handler.postDelayed(runnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Aktualisiere...", Toast.LENGTH_LONG).show();
                        new CustomAsyncTask().execute();
                    }
                }, 100 * 1000);
            } else {
                Log.d("Button pressed", "");
            }

            for (int i = 1; i < 20; i++) {
                try {
                    Log.d("I am connecting", "");
                    if (i < 10) {
                        Document doc = Jsoup.connect(URL + "0" + i + ".html").get();
                        //Log.d("Connecting to ",URL + "0" + i + ".html");
                        parseServerData(doc);
                    } else {
                        Document doc = Jsoup.connect(URL + i + ".html").get();
                        //Log.d("Connecting to ",URL + i + ".html");
                        parseServerData(doc);
                    }
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
                SharedPreferences.Editor prefsEditor = myPrefs.edit();
                prefsEditor.putInt("dataNumber", data.size());
                prefsEditor.apply();
                Log.d("Exists Number Prefs: " + myPrefs.getInt("dataNumber", 1), "Number of data: " + data.size());

                //prefsEditor.commit();
                // Check if Data differs
                // If so, renew Data and make Notification
                // If not, don't do anything
                /*
                if (myPrefs.getString("arrayList1", "NotExisting").equals("NotExisting")){
                    Log.d("Not existing!","");
                    for (int i=0; i < data.size(); i++){
                        prefsEditor.putString("arrayList"+i, data.get(i).toString());
                        prefsEditor.apply();
                    }
                    prefsEditor.putInt("dataNumber",data.size());
                    prefsEditor.apply();
                }*//*
                else  {
                    Log.d("Exists Number Prefs: "+myPrefs.getInt("dataNumber", 42), "Number of data: "+data.size());
                    if (myPrefs.getString("arrayList"+ myPrefs.getAll().size() - 1,"Didn't exist") == data.get(data.size()-1)){

                    }
                }*/

                /*
                for (int i = 0; i < data.size(); i++) {
                    prefsEditor.putString("arrayList"+i, data.get(i).toString());
                }
                prefsEditor.apply();
                */

                //Log.d("prefs: ", "" + myPrefs.getString("arrayList"+1, "Not existing"));

                recyclerViewAdapter = new RecyclerViewAdapter(data);
                recyclerView.setAdapter(recyclerViewAdapter);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aBoolean);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //handler.removeCallbacks(runnable);
    }
}
