package com.example.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;


public class Price2Fragment extends Fragment implements View.OnClickListener {

    private View view;
    private ArrayList<String> Brand;
    private Button Price;
    private Button price5;
    private Button price10;
    private Button price15;
    private Button price20;
    private Button price25;
    private Button price30;

    private boolean Click5 = false;
    private boolean Click10 = false;
    private boolean Click15 = false;
    private boolean Click20 = false;
    private boolean Click25 = false;
    private boolean Click30 = false;
    //private Bundle clothesPrice;
    private Bundle queryBundle;
    private int PriceSelected;
    private int priceflag = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_price, container, false);
        getResources().getColor(R.color.color_button_clicked);
        getResources().getColor(R.color.color_button_unclicked);

        Price = view.findViewById(R.id.btnNextPrice);
        Price.setOnClickListener(this);

        price5 = view.findViewById(R.id.btn5);
        price5.setOnClickListener(this);

        price10 = view.findViewById(R.id.btn10);
        price10.setOnClickListener(this);

        price15 = view.findViewById(R.id.btn15);
        price15.setOnClickListener(this);

        price20 = view.findViewById(R.id.btn20);
        price20.setOnClickListener(this);

        price25 = view.findViewById(R.id.btn25);
        price25.setOnClickListener(this);

        price30 = view.findViewById(R.id.btn30);
        price30.setOnClickListener(this);


        Intent intent = getActivity().getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);

        queryBundle = this.getArguments();

        Brand = queryBundle.getStringArrayList("Genres");

        TextView textViewName = view.findViewById(R.id.textView3);

        textViewName.setText("Your selected brands are: "+ Brand.toString());



        return view;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextPrice:

                if (priceflag == 0){
                    Toast.makeText(getContext(), "Selected a price range!", Toast.LENGTH_SHORT).show();


                    return;
                }

//                CategoryFragment fragment = new CategoryFragment();
//                fragment.setArguments(queryBundle);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                Book2Fragment fragment = new Book2Fragment();
                fragment.setArguments(queryBundle);
                //Changing the layout for the next fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                break;
            case R.id.btn5:
                if (Click5 == false){
                    priceflag++;
                    queryBundle.putInt("Prices" , 5);
                    price5.setBackgroundResource(R.color.color_button_clicked);
                    // Toast.makeText(getContext(),"£10 Selected",Toast.LENGTH_SHORT).show();
                    Click5= true;
                    break;
                }
                if (Click5 == true){
                    priceflag--;
                    queryBundle.remove("Prices");
                    price5.setBackgroundResource(R.color.color_button_unclicked);
                    //Toast.makeText(getContext(),"Unselected £10",Toast.LENGTH_SHORT).show();
                    Click5= false;
                    break;
                }
            case R.id.btn10:
                if (Click10 == false){
                    priceflag++;
                    queryBundle.putInt("Prices" , 10);
                    price10.setBackgroundResource(R.color.color_button_clicked);
                    // Toast.makeText(getContext(),"£20 Selected",Toast.LENGTH_SHORT).show();
                    Click10= true;
                    break;
                }
                if (Click10 == true){
                    priceflag--;
                    queryBundle.remove("Prices");
                    price10.setBackgroundResource(R.color.color_button_unclicked);
                    //Toast.makeText(getContext(),"Unselected £20",Toast.LENGTH_SHORT).show();
                    Click10= false;
                    break;
                }
            case R.id.btn15:
                if (Click15 == false){
                    priceflag++;
                    queryBundle.putInt("Prices" , 15);
                    price15.setBackgroundResource(R.color.color_button_clicked);
                    // Toast.makeText(getContext(),"£30 Selected",Toast.LENGTH_SHORT).show();
                    Click15= true;
                    break;
                }
                if (Click15 == true){
                    priceflag--;
                    queryBundle.remove("Prices");;
                    price15.setBackgroundResource(R.color.color_button_unclicked);
                    // Toast.makeText(getContext(),"Unselected £30",Toast.LENGTH_SHORT).show();
                    Click15= false;
                    break;
                }
            case R.id.btn20:
                if (Click20 == false){
                    priceflag++;
                    queryBundle.putInt("Prices" , 20);
                    price20.setBackgroundResource(R.color.color_button_clicked);
                    //Toast.makeText(getContext(),"Boohoo £40",Toast.LENGTH_SHORT).show();
                    Click20= true;
                    break;
                }
                if (Click20 == true){
                    priceflag--;
                    queryBundle.remove("Prices");
                    price20.setBackgroundResource(R.color.color_button_unclicked);
                    //Toast.makeText(getContext(),"Unselected £40",Toast.LENGTH_SHORT).show();
                    Click20= false;
                    break;
                }
            case R.id.btn25:
                if (Click25 == false){
                    priceflag++;
                    queryBundle.putInt("Prices" , 25);

                    price25.setBackgroundResource(R.color.color_button_clicked);
                    //Toast.makeText(getContext(),"£50 Selected",Toast.LENGTH_SHORT).show();
                    Click25= true;
                    break;
                }
                if (Click25 == true){
                    priceflag--;
                    queryBundle.remove("Prices");
                    price25.setBackgroundResource(R.color.color_button_unclicked);
                    //Toast.makeText(getContext(),"Unselected £50",Toast.LENGTH_SHORT).show();
                    Click25= false;
                    break;
                }
            case R.id.btn30:
                if (Click30 == false){
                    priceflag++;
                    queryBundle.putInt("Prices" , 30);

                    price30.setBackgroundResource(R.color.color_button_clicked);
                    //Toast.makeText(getContext(),"Boohoo £60",Toast.LENGTH_SHORT).show();
                    Click30= true;
                    break;
                }
                if (Click30 == true){
                    priceflag--;
                    queryBundle.remove("Prices");
                    price30.setBackgroundResource(R.color.color_button_unclicked);
                    //Toast.makeText(getContext(),"Unselected £60",Toast.LENGTH_SHORT).show();
                    Click30= false;
                    break;
                }

        }
    }
}