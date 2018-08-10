package com.panzhiev.leogaming.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Utils {

    public static Bitmap getBitmapFromAssets(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(fileName + ".png");
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Spanned formatBalance(String unformattedBalance) {

        String symbol = "₴ ";

        String whole;
        String fractional;

        if (unformattedBalance.contains(".")) {
            String[] split = unformattedBalance.split("\\.");
            whole = split[0];
            fractional = split[1];

            String finalRow = "<big>" + symbol + "</big>" + "<big><b>" + getFormatter(3).format(Integer.parseInt(whole)) + "</b></big>"
                    + "<small><b>" + "." + fractional + "</b></small>";

            return Html.fromHtml(finalRow);

        } else {
            whole = unformattedBalance;
            return Html.fromHtml(whole);
        }
    }

    public static Spanned formatHeaderNumber(String unformatted) {
        String headerNumber = "LeoWallet " + "<b><font color='#ffffff'>" + formatNumber(unformatted) + "</font></b>";
        return Html.fromHtml(headerNumber);
    }

    public static String formatNumber(String unformatted) {
        if (!unformatted.isEmpty()) {
            String formatted = "";
            if (unformatted.length() == 12) {
                formatted = String.format("%s %s %s %s %s", unformatted.substring(0, 2), unformatted.substring(2, 5),
                        unformatted.substring(5, 8), unformatted.substring(8, 10), unformatted.substring(10, 12));
            } else if (unformatted.length() == 16) {
                formatted = String.format("%s %s %s %s", unformatted.substring(0, 4), unformatted.substring(4, 8),
                        unformatted.substring(8, 12), unformatted.substring(12, 16));
            }
            return formatted;
        }
        return "";
    }

    private static DecimalFormat getFormatter(int groupSize) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingSize(groupSize);
        return df;
    }

    public static void showAlertDialog(Context context, String msg) {
        new AlertDialog.Builder(context)
                .setTitle("Детали")
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("OK", (DialogInterface dialog, int which) -> dialog.dismiss())
                .create()
                .show();
    }
}
