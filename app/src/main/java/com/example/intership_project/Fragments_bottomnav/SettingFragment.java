package com.example.intership_project.Fragments_bottomnav;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intership_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    SwitchCompat switchmode;
    TextView text;
    SharedPreferences sharedPreferences=null;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ImageView imageViewmode;
       View view= inflater.inflate(R.layout.fragment_setting, container, false);
       text=view.findViewById(R.id.textLightmode);
       switchmode=view.findViewById( R.id.switchmode);
       imageViewmode=view.findViewById(R.id.imageViewmode);

       sharedPreferences= getContext().getSharedPreferences("night",0);
       Boolean booleanvalues=sharedPreferences.getBoolean("nightmode",true);

       if (booleanvalues)
       {
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
           switchmode.setChecked(true);
           imageViewmode.setImageResource(R.drawable.night);
           text.setText(R.string.nighttext);

       }
       switchmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                   switchmode.setChecked(true);
                   imageViewmode.setImageResource(R.drawable.night);
                   text.setText(R.string.nighttext);
                   SharedPreferences.Editor editor=sharedPreferences.edit();
                   editor.putBoolean("night mode",true);
                   editor.commit();
               }
               else  {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                   switchmode.setChecked(false);
                   imageViewmode.setImageResource(R.drawable.daymode);
                   text.setText(R.string.daytext);
                   SharedPreferences.Editor editor=sharedPreferences.edit();
                   editor.putBoolean("night mode",false);
                   editor.commit();
               }

           }
       });



       return view;


    }
}