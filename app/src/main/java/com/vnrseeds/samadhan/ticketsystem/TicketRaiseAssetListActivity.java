package com.vnrseeds.samadhan.ticketsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.AssetListAdapter;
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListData;
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListPojo;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;

import java.util.ArrayList;
import java.util.List;

public class TicketRaiseAssetListActivity extends AppCompatActivity {
    private static final String TAG = "TicketRaiseAssetListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private List<AssetListData> asselistArray = new ArrayList<>();
    private AssetListAdapter listAdapter;
    private SearchView searchView;
    private RecyclerView lv_assetlist;
    private ImageButton back_nav;
    private ArrayList<AssetListPojo> AssetArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_raise_asset_list);
    }
}