package com.example.musicshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int quantity = 0;
    Spinner spinner;
    ArrayList<String> spinnerArrayList;
    ArrayAdapter<String> spinnerAdapter;
    HashMap<String, Double> goodsMap;
    String goodsName;
    Double price;
    EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBarColor();
        setStatusBarColor();

        createSpinner();

        createMap();

        userNameEditText = findViewById(R.id.user_name_et);
    }

    void createMap(){
        goodsMap = new HashMap<>();
        goodsMap.put("Guitar", 499.9);
        goodsMap.put("Drum", 1499.9);
        goodsMap.put("Keyboard", 999.9);
    }

    void createSpinner(){
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerArrayList = new ArrayList<>();
        spinnerArrayList.add("Guitar");
        spinnerArrayList.add("Drum");
        spinnerArrayList.add("Keyboard");


        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }


    public void setActionBarColor() {
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#CF5700"));

        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void setStatusBarColor() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }

    public void increaseQuantity(View view) {
        quantity++;
        TextView valueQuantity = findViewById(R.id.value_quantity);
        valueQuantity.setText("" + quantity);
        TextView priceTv = findViewById(R.id.price_tv);
        double priceTvForRound =  Math.round(quantity * price * 10);
        double roundedPrice = priceTvForRound / 10;
        priceTv.setText(roundedPrice + "$");
    }

    public void decreaseQuantity(View view) {
        quantity--;
        if (quantity < 0){
            quantity = 0;
        }
        TextView valueQuantity = findViewById(R.id.value_quantity);
        valueQuantity.setText("" + quantity);
        TextView priceTv = findViewById(R.id.price_tv);
        double priceTvForRound =  Math.round(quantity * price * 10);
        double roundedPrice = priceTvForRound / 10;
        priceTv.setText(roundedPrice + "$");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        price = goodsMap.get(goodsName);
        TextView priceTv = findViewById(R.id.price_tv);
        double priceTvForRound =  Math.round(quantity * price * 10);
        double roundedPrice = priceTvForRound / 10;
        priceTv.setText(roundedPrice + "$");

        ImageView goodsImageView = findViewById(R.id.goods_iv);
        switch (goodsName) {
            case "Drum":
                goodsImageView.setImageResource(R.drawable.drum);
                break;
            case "Guitar":
                goodsImageView.setImageResource(R.drawable.guitar);
                break;
            case "Keyboard":
                goodsImageView.setImageResource(R.drawable.keys);
                break;
            default:
                goodsImageView.setImageResource(R.drawable.drum);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addToCart(View view) {

        double priceTvForRound =  Math.round(quantity * price * 10);

        Order order = new Order();
        order.userName = userNameEditText.getText().toString();
        order.goodsName = goodsName;
        order.quantity = quantity;
        order.price = price;
        order.orderPrice = priceTvForRound / 10;

        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        orderIntent.putExtra("userName", order.userName);
        orderIntent.putExtra("goodsName", order.goodsName);
        orderIntent.putExtra("quantity", order.quantity);
        orderIntent.putExtra("price", order.price);
        orderIntent.putExtra("orderPrice", order.orderPrice);
        startActivity(orderIntent);
    }
}