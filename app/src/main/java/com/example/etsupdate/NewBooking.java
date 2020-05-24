package com.example.etsupdate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class NewBooking extends Fragment {

    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout linearLayoutBSheet;
    private ToggleButton tbUpDown;
    private ListView listView;
    private TextView txtCantante, txtCancion;
    View view;
    Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.new_booking,container,false);

        init();

        rellenarListView();



//        tbUpDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                }else{
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//            }
//        });

//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(View view, int newState) {
//                if(newState == BottomSheetBehavior.STATE_EXPANDED){
//                    tbUpDown.setChecked(true);
//                }else if(newState == BottomSheetBehavior.STATE_COLLAPSED){
//                    tbUpDown.setChecked(false);
//                }
//            }
//
//            @Override
//            public void onSlide(View view, float v) {
//
//            }
//        });

        return view;
    }


    private void init() {
      //  View v  = view.findViewById(R.id.bottamsheet);
        this.linearLayoutBSheet = view.findViewById(R.id.bottamsheet);
//        this.bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBSheet);
        this.tbUpDown = view.findViewById(R.id.toggleButton);
        this.listView = view.findViewById(R.id.listView);
        this.txtCantante = view.findViewById(R.id.txtCantante);

    }
    private void rellenarListView() {

        String[] loc = {"Office To Home","Home To Office","Airport Drop","Airport Pickup","Others"};



        ArrayList<Map<String, Object>> lista = new ArrayList<>();

        int nombresLen = loc.length;

        for (int i = 0; i < nombresLen; i++) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("Cantante", loc[i]);

            lista.add(listItem);
        }

        this.listView.setAdapter(getAdapterListViewCT(lista));

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              switch (position){
                  case 0:
                      Intent fstIntent = new Intent(getContext(), CrtNewBooking.class);
                      fstIntent.putExtra("inname","Office To Home");
                      startActivity(fstIntent);
                      Animatoo.animateSlideUp(getContext());
                      break;

                  case 1:
                      Intent SndInt = new Intent(getContext(),CrtNewBooking.class);
                      SndInt.putExtra("sndName","Home To Office");
                      startActivity(SndInt);
                      Animatoo.animateSlideUp(getContext());
                      break;
              }

            }
        });
    }

    private SimpleAdapter getAdapterListViewCT(ArrayList<Map<String, Object>> lista) {
        return new SimpleAdapter(getContext(), lista,
                android.R.layout.simple_list_item_2, new String[]{"Cantante", "Titulo"},
                new int[]{android.R.id.text1, android.R.id.text2}) {

            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView txtNombre = view.findViewById(android.R.id.text1);
                txtNombre.setTypeface(Typeface.DEFAULT_BOLD);


                return view;
            }

        };
    }

}
