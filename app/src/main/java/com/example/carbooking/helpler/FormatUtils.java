package com.example.carbooking.helpler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
    public static String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        if (formatter instanceof DecimalFormat) {
            ((DecimalFormat) formatter).applyPattern("#,###.##");
        }
        return formatter.format(amount);
    }
}