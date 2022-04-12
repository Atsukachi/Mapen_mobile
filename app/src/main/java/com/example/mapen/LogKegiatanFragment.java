package com.example.mapen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class LogKegiatanFragment extends Fragment {

//    String[] items = {"Material","Design","Components","Android","Lolipop"};
//    AutoCompleteTextView dropdown_skp;
//    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_logkeg = inflater.inflate(R.layout.fragment_log_kegiatan, container, false);

//        dropdown_skp = view_logkeg.findViewById(R.id.txtSKP);
//        arrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_skp, items);
//        dropdown_skp.setAdapter(arrayAdapter);
//        dropdown_skp.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
//
//            }
//        };

        return view_logkeg;
    }
}