package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.ReviewAdapter;
import com.hcmus.fit.customer_apps.models.ReviewModel;
import com.hcmus.fit.customer_apps.networks.RestaurantNetwork;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private ListView lvReview;
    public ReviewAdapter reviewAdapter;
    public List<ReviewModel> reviewList = new ArrayList<>();

    private TextView tvStarNum;
    private RatingBar ratingBar;
    private TextView tvNumRate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        this.lvReview = findViewById(R.id.lv_review);
        tvStarNum = findViewById(R.id.tv_star_num);
        ratingBar = findViewById(R.id.rating);
        tvNumRate = findViewById(R.id.tv_num_rate);

        reviewAdapter = new ReviewAdapter(this, reviewList);
        this.lvReview.setAdapter(reviewAdapter);

        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderId");
        RestaurantNetwork.getRestaurantReview(this, orderId);
    }

    public void calculateStar() {
        int numRate = reviewList.size();
        int totalStar = 0;

        for (ReviewModel reviewModel : reviewList) {
            totalStar += reviewModel.getStars();
        }

        float avgStar = 0f;

        if (numRate > 0) {
            avgStar = Math.round(totalStar * 10.0 / numRate) / 10f;
        }

        tvStarNum.setText(String.valueOf(avgStar));
        ratingBar.setRating(avgStar);
        tvNumRate.setText("(" + numRate + ")");
    }
}
