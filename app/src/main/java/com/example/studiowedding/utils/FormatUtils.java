package com.example.studiowedding.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtils {

    private static final String DATE_REGEX = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    /**
     * Kiểm tra xem danh sách các chuỗi dữ liệu đầu vào có trống hay không.
     *
     * @param datas Danh sách các chuỗi dữ liệu cần kiểm tra.
     * @return `true` nếu ít nhất một chuỗi trong danh sách là null hoặc rỗng,
     * `false` nếu tất cả các chuỗi đều không trống.
     */
    public static boolean isDataInputEmpty(String... datas) {
        for (String str : datas) {
            if (str == null || str.isEmpty()) {
                return true;
            }
        }
        return false;
    }
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
     * Kiểm tra xem chuỗi đầu vào đã cho chỉ chứa các ký tự hợp lệ hay không.
     * <p>
     * Phương thức này sử dụng biểu thức chính quy để xác thực xem chuỗi đầu vào có bao gồm
     * chỉ gồm các ký tự thường dùng trong tên tiếng Việt và tên phương Tây.
     *
     * @param data Chuỗi đầu vào cần kiểm tra.
     * @return `true` nếu chuỗi đầu vào chỉ chứa các ký tự hợp lệ, nếu không thì `false`.
     */
    public static boolean isDataInputString(String data) {
        String regex = "^\\D+$"; // Không chứa ký tự số
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }

    /**
     * Kiểm tra tính hợp lệ của chuỗi ngày tháng theo định dạng yyyy-MM-dd.
     *
     * @param input Chuỗi ngày tháng cần kiểm tra.
     * @return True nếu chuỗi ngày tháng hợp lệ, False nếu không hợp lệ.
     */
    public static boolean isValidDate(String input) {
        Pattern pattern = Pattern.compile(DATE_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateFormat.setLenient(false);

            try {
                dateFormat.parse(input);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        return false;
    }

    /**
     * Kiểm tra tính hợp lệ của địa chỉ email.
     *
     * @param email Địa chỉ email cần kiểm tra.
     * @return `true` nếu địa chỉ email hợp lệ, ngược lại trả về `false`.
     */
    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
