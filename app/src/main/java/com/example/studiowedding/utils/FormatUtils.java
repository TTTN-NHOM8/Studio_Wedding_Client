package com.example.studiowedding.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {
    /**
     * Chuyển đổi một đối tượng Date thành một chuỗi có định dạng "dd/MM/yyyy".
     *
     * @param date Đối tượng Date cần chuyển đổi.
     * @return Biểu diễn chuỗi ngày ở định dạng "dd/MM/yyyy".
     */
    public static String formatDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * Chuyển đổi một chuỗi có định dạng "dd/MM/yyyy" thành đối tượng Ngày.
     *
     * @param dateString Chuỗi biểu thị ngày ở định dạng "dd/MM/yyyy".
     * @return Một đối tượng Date được phân tích cú pháp từ chuỗi đầu vào.
     * @throws ParseException Nếu chuỗi đầu vào không thể được phân tích thành đối tượng Date.
     */
    public static Date parserStringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.parse(dateString);
    }
    /**
     * Kiểm tra xem ngày truyền vào có trùng với ngày hiện tại không
     */
    public static boolean checkData(Date date){
        return FormatUtils.formatDateToString(date).equals(FormatUtils.formatDateToString(new Date()));

    /**
     * Chuyển đổi số tiền sang định dạng tiền tệ Việt Nam với ký hiệu "₫".
     *
     * @param amount Số tiền cần chuyển đổi.
     * @return Biểu diễn chuỗi số tiền ở định dạng tiền tệ Việt Nam với ký hiệu "₫".
     */
    public static String formatCurrencyVietnam(float amount) {
        DecimalFormat formatter = new DecimalFormat("#,###₫");
        return formatter.format(amount);
    }

    /**
     * Chuyển đổi chuỗi tiền tệ Việt Nam về số float.
     *
     * @param currencyString Chuỗi biểu thị số tiền ở định dạng tiền tệ Việt Nam.
     * @return Số float tương ứng với số tiền từ chuỗi tiền tệ Việt Nam.
     */
    public static float reverseCurrencyVietnam(String currencyString) {
        // Loại bỏ tất cả ký tự không phải là số từ chuỗi
        String digitsOnly = currencyString.replaceAll("\\D", "");

        float amount = Float.parseFloat(digitsOnly);
        return amount;
    }

    /**
     * Chuyển đổi chuỗi ngày đầu vào sang định dạng tương thích với MySQL ("yyyy-MM-dd").
     *
     * @param dateString Chuỗi ngày đầu vào ở định dạng dd/MM/yyyy.
     * @return Chuỗi ngày được định dạng là "yyyy-MM-dd" phù hợp với cơ sở dữ liệu MySQL.
     * @throws RuntimeException nếu có vấn đề khi phân tích chuỗi ngày đầu vào.
     */
    public static String formatStringToStringMySqlFormat(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return sdf.format(parserStringToDate(dateString));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
