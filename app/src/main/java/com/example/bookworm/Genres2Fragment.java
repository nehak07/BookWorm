package com.example.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;


public class Genres2Fragment extends Fragment implements View.OnClickListener {

    private Button Anxiety;
    private Button Depression;
    private Button Bipolar;
    private Button OCD;
    private Button PTSD;
    private Button Financial;

    private Button Next;

    private boolean AnxietyClicked = false;
    private boolean DepressionClicked = false;
    private boolean BipolarClicked = false;
    private boolean OCDClicked = false;
    private boolean PTSDClicked = false;
    private boolean FinancialClicked = false;




    private View view;
    private String[] Brands;
    private Bundle clothesbrand = new Bundle();
    private ArrayList<String> ListOfBrands = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_genres, container, false);

        getResources().getColor(R.color.color_button_clicked);
        getResources().getColor(R.color.color_button_unclicked);
        getResources().getColor(R.color.White);

        Next = view.findViewById(R.id.btnNextPrice);
        Next.setOnClickListener(this);

        Anxiety = view.findViewById(R.id.btnAnxiety);
        Anxiety.setOnClickListener(this);

        Depression = view.findViewById(R.id.btnDepression);
        Depression.setOnClickListener(this);

        Bipolar= view.findViewById(R.id.BtnBipolar);
        Bipolar.setOnClickListener(this);

        OCD = view.findViewById(R.id.btnOCD);
        OCD.setOnClickListener(this);

        PTSD = view.findViewById(R.id.btnPTSD);
        PTSD.setOnClickListener(this);

        Financial = view.findViewById(R.id.btnFinancial);
        Financial.setOnClickListener(this);


        Intent intent = getActivity().getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        //Toast.makeText(getContext(),CLUBNAME,Toast.LENGTH_SHORT).show();


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextPrice:

                if (ListOfBrands.size() == 0) {
                    Toast.makeText(getContext(), "Atleast one brand must be selected!", Toast.LENGTH_SHORT).show();

                    return;
                }
                // Atif Pervaiz, 2018. Pass Data between Activities using intent [ONLINE] Available at: https://www.youtube.com/watch?v=uNV_qLfc5Zw


                clothesbrand.putStringArrayList("Genres", ListOfBrands);


                Price2Fragment fragment = new Price2Fragment();
                fragment.setArguments(clothesbrand);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                break;
            case R.id.btnAnxiety:

                if (AnxietyClicked == false) {
                    ListOfBrands.add("Anxiety");
                    Anxiety.setBackgroundResource(R.color.color_button_clicked);
                    AnxietyClicked = true;
                    break;
                }
                if (AnxietyClicked == true) {
                    ListOfBrands.remove("Anxiety");
                    Anxiety.setBackgroundResource(R.color.color_button_unclicked);
                    AnxietyClicked = false;
                    break;
                }
            case R.id.btnDepression:

                if (DepressionClicked == false) {
                    ListOfBrands.add("Depression");
                    Depression.setBackgroundResource(R.color.color_button_clicked);
                    DepressionClicked = true;
                    break;
                }
                if (DepressionClicked == true) {
                    ListOfBrands.remove("Depression");
                    Depression.setBackgroundResource(R.color.color_button_unclicked);
                    DepressionClicked = false;
                    break;
                }
            case R.id.BtnBipolar:

                if (BipolarClicked == false) {
                    ListOfBrands.add("Bipolar");
                    Bipolar.setBackgroundResource(R.color.color_button_clicked);
                    BipolarClicked = true;
                    break;
                }
                if (BipolarClicked == true) {
                    ListOfBrands.remove("Bipolar");
                    Bipolar.setBackgroundResource(R.color.color_button_unclicked);
                    BipolarClicked = false;
                    break;
                }
            case R.id.btnOCD:
                if (OCDClicked == false) {
                    ListOfBrands.add("OCD");
                    OCD.setBackgroundResource(R.color.color_button_clicked);
                    OCDClicked = true;
                    break;
                }
                if (OCDClicked == true) {
                    ListOfBrands.remove("OCD");
                    OCD.setBackgroundResource(R.color.color_button_unclicked);
                    OCDClicked = false;
                    break;
                }
            case R.id.btnPTSD:
                if (PTSDClicked == false) {
                    ListOfBrands.add("PTSD");
                    PTSD.setBackgroundResource(R.color.color_button_clicked);
                    PTSDClicked = true;
                    break;
                }
                if (PTSDClicked == true) {
                    ListOfBrands.remove("PTSD");
                    PTSD.setBackgroundResource(R.color.color_button_unclicked);
                    PTSDClicked = false;
                    break;
                }
            case R.id.btnFinancial:
                if (FinancialClicked == false) {
                    ListOfBrands.add("Financial");
                    Financial.setBackgroundResource(R.color.color_button_clicked);
                    Financial.getResources().getColor(R.color.White);
                    FinancialClicked = true;
                    break;
                }
                if (FinancialClicked == true) {
                    ListOfBrands.remove("Financial");
                    Financial.setBackgroundResource(R.color.color_button_unclicked);
                    FinancialClicked = false;
                    break;
                }

        }
    }
}