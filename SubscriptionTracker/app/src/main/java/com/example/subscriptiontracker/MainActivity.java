package com.example.subscriptiontracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText subscriptionNameEditText;
    private EditText startDateEditText;
    private EditText expirationDateEditText;
    private Button calculateButton;
    private TextView resultTextView;
    private TextView warningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subscriptionNameEditText = findViewById(R.id.subscription_name_edit_text);
        startDateEditText = findViewById(R.id.start_date_edit_text);
        expirationDateEditText = findViewById(R.id.expiration_date_edit_text);
        calculateButton = findViewById(R.id.calculate_button);
        resultTextView = findViewById(R.id.result_text_view);
        warningTextView = findViewById(R.id.warning_text_view);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subscriptionName = subscriptionNameEditText.getText().toString();
                String startDateString = startDateEditText.getText().toString();
                String expirationDateString = expirationDateEditText.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date startDate = null;
                Date expirationDate = null;

                try {
                    startDate = sdf.parse(startDateString);
                    expirationDate = sdf.parse(expirationDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long diffInMilliseconds = expirationDate.getTime() - startDate.getTime();
                long diffInDays = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);

                String resultText = subscriptionName + ": " + diffInDays + " days left";
                resultTextView.setText(resultText);

                Calendar calendar = Calendar.getInstance();
                long currentTime = calendar.getTimeInMillis();
                long timeUntilExpiration = expirationDate.getTime() - currentTime;
                long daysUntilExpiration = TimeUnit.DAYS.convert(timeUntilExpiration, TimeUnit.MILLISECONDS);

                if (daysUntilExpiration < 7) {
                    String warningText = "your subscription will expire soon";
                    warningTextView.setText(warningText);
                } else {
                    warningTextView.setText("");
                }

                Subscription subscription = new Subscription();
                subscription.setSubscriptionName(subscriptionName);
                subscription.setStartDate(startDateString);
                subscription.setExpirationDate(expirationDateString);
                subscription.setDaysLeft(diffInDays);

                new InsertSubscriptionAsyncTask(AppDatabase.getInstance(MainActivity.this).subscriptionDao()).execute(subscription);
            }
        });
    }

    private static class InsertSubscriptionAsyncTask extends AsyncTask<Subscription, Void, Void> {
        private SubscriptionDao subscriptionDao;

        private InsertSubscriptionAsyncTask(SubscriptionDao subscriptionDao) {
            this.subscriptionDao = subscriptionDao;
        }

        @Override
        protected Void doInBackground(Subscription... subscriptions) {
            subscriptionDao.insert(subscriptions[0]);
            return null;
        }
    }
}
