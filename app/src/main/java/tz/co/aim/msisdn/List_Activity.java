package tz.co.aim.msisdn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

public class List_Activity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = findViewById(R.id.list_items);

        Intent intent = getIntent();

        HashMap<String,String> contactDetails = (HashMap<String,String>)intent.getSerializableExtra("map");

        ListAdapter adapter = new ListAdapter(contactDetails);
        listView.setAdapter(adapter);

    }
}
