package com.example.carbooking.admin.Revenue;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbooking.Entity.Order;
import com.example.carbooking.R;
import com.example.carbooking.repository.OrderRepository;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RevenueDashboardActivity extends AppCompatActivity {

    private BarChart barChart;
    private Spinner spinnerYear;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_dashboard);

        barChart = findViewById(R.id.barChart);
        spinnerYear = findViewById(R.id.spinner_year);
        orderRepository = new OrderRepository(getApplication());

        setupYearSpinner();
    }

    private void setupYearSpinner() {
        List<String> years = new ArrayList<>();
        years.add("2022");
        years.add("2023");
        years.add("2024");
        years.add("2025");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedYear = Integer.parseInt(years.get(position));
                updateChart(selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Chọn năm hiện tại mặc định
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int index = years.indexOf(String.valueOf(currentYear));
        if (index >= 0) {
            spinnerYear.setSelection(index);
        }
    }

    private void updateChart(int selectedYear) {
        List<Order> allOrders = orderRepository.getAllOrder();

        // Tính doanh thu theo quý
        Map<Integer, Double> revenueByQuarter = new HashMap<>();

        for (Order order : allOrders) {
            if (order.getStatusId() == 1 && order.getOrderDate() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(order.getOrderDate());
                int year = calendar.get(Calendar.YEAR);

                if (year == selectedYear) {
                    int quarter = (calendar.get(Calendar.MONTH) / 3) + 1;
                    double fee = order.getTotalFee();
                    revenueByQuarter.put(quarter, revenueByQuarter.getOrDefault(quarter, 0.0) + fee);
                }
            }
        }

        // Dữ liệu cho biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            float revenue = revenueByQuarter.getOrDefault(i, 0.0).floatValue();
            entries.add(new BarEntry(i, revenue));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu các quý năm " + selectedYear);
        dataSet.setColor(getResources().getColor(R.color.purple_500));
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Cấu hình trục X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(12f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "Q" + (int) value;
            }
        });

        // Cấu hình trục Y
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);

        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate(); // Refresh lại chart
    }
}
