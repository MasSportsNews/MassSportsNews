package com.example.masssportsnews.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.masssportsnews.R;
import com.example.masssportsnews.adapter.TicketAdapter;
import com.example.masssportsnews.models.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class TicketFragment extends Fragment {

    RecyclerView rvTicket;
    public static final String API_KEY = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=Qirum071LVfXmJxgaBK6c4Z1GRkMRbjP";
    public static final String TAG = "TicketFragment";

    TicketAdapter ticketAdapter;

    List<Ticket> ticketList;

    public TicketFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ticketList = new ArrayList<>();

        rvTicket = view.findViewById(R.id.rvTicket);

        ticketAdapter = new TicketAdapter(getContext(), ticketList);

        rvTicket.setAdapter(ticketAdapter);

        rvTicket.setLayoutManager(new LinearLayoutManager(getContext()));

        AsyncHttpClient client = new AsyncHttpClient();
        Log.i(TAG, "fragment created");


        client.get(API_KEY, new JsonHttpResponseHandler()
        {


            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json)
            {
                JSONObject jsonObject = json.jsonObject;
                Log.i(TAG, "onSuccess");

                try
                {
                    JSONObject embedded = jsonObject.getJSONObject("_embedded");
                    JSONArray events = embedded.getJSONArray("events");
//                    for(int i = 0; i < jsonObject.length(); i++){
//                        name = jsonObject.getJSONObject(String.valueOf(i)).getString("name");
//                    }
                    //                  String name = events.getJSONObject(i).getString("name");


                    ticketList.addAll(Ticket.fromJSONArray(events));
                    Log.i(TAG, "ticket count: " + ticketList.size());
                    Log.i(TAG, "tickets: " + ticketList);
                    for (int i = 0; i < ticketList.size(); i++) {
                        Log.i(TAG, "ticket #" + i + ": " + ticketList.get(i).getGenreName());
                    }
                    ticketAdapter.notifyDataSetChanged();

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "exception: " + throwable);
            }







        });


        }
}
