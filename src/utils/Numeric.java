/*
 * Programmer:liuboen
 * Date:16/10/3
 */
package utils;

import common.Response;
import common.ResultCode;

import java.util.ArrayList;

public class Numeric {
    public static Response<Boolean> checkBase(char ch, short base) {
        if (!Character.isLetterOrDigit(ch)) {
            return new Response<>(ResultCode.ANOMALY);
        }
        if (Character.isDigit(ch) && base <= ch - 48) {
            return new Response<>(ResultCode.ANOMALY);
        } else if (Character.isLetter(ch) && base - 10 <= Character.toUpperCase(ch) - 65) {
            return new Response<>(ResultCode.ANOMALY);
        }
        return new Response<>(ResultCode.SUCCESS);
    }

    public static Response<String> checkBase(String nString, short base) {
        if (nString.isEmpty() || base <= 1 || base >= 37) {
            return new Response<>(ResultCode.ANOMALY);
        }
        if (Character.isLetterOrDigit(nString.charAt(0))) {
            nString = "+" + nString;
        } else if (nString.charAt(0) != '+' && nString.charAt(0) != '-') {
            return new Response<>(ResultCode.ANOMALY);
        }
        char[] ch_arr = nString.substring(1).toCharArray();
        for (char ch : ch_arr) {
            if (!checkBase(ch, base).isSuccess()) {
                return new Response<>(ResultCode.ANOMALY);
            }
        }
        return new Response<>(nString);
    }

    public static Response<ArrayList<String>> alignment(String num_1, String num_2) {
        if (num_1.isEmpty() || num_2.isEmpty()) {
            return new Response<>(ResultCode.ANOMALY);
        }
        ArrayList<String> arrayList = new ArrayList<>(2);
        String num_sub_1 = num_1.substring(1);
        String num_sub_2 = num_2.substring(1);
        int length_1 = num_sub_1.length();
        int length_2 = num_sub_2.length();
        int max_len = length_1 > length_2 ? length_1 : length_2;
        String str_fmt = String.format("%%%ds", max_len);
        num_sub_1 = String.format(str_fmt, num_sub_1).replace(' ', '0').toUpperCase();
        num_sub_2 = String.format(str_fmt, num_sub_2).replace(' ', '0').toUpperCase();
        num_1 = Character.toString(num_1.charAt(0)) + num_sub_1;
        num_2 = Character.toString(num_2.charAt(0)) + num_sub_2;
        arrayList.add(num_1);
        arrayList.add(num_2);
        return new Response<>(arrayList);
    }

    public static Response<ArrayList<String>> absValueSort(String num_1, String num_2) {
        if (num_1.isEmpty() || num_2.isEmpty()) {
            return new Response<>(ResultCode.ANOMALY);
        }
        Response<ArrayList<String>> response = alignment(num_1, num_2);
        if (!response.isSuccess()) {
            return new Response<>(ResultCode.ANOMALY);
        }
        ArrayList<String> arrayList = response.getResult();
        String value_1 = arrayList.get(0);
        String value_2 = arrayList.get(1);
        String absValue_1 = value_1.substring(1);
        String absValue_2 = value_2.substring(1);
        if (absValue_1.compareTo(absValue_2) < 0) {
            arrayList.clear();
            arrayList.add(value_2);
            arrayList.add(value_1);
        } else if (absValue_1.compareTo(absValue_2) == 0) {
            arrayList.remove(1);
        }
        return new Response<>(arrayList);
    }

    public static Response<ArrayList<Character>> add(char a, char b, int c, short base) {
        if (!checkBase(a, base).isSuccess() || !checkBase(b, base).isSuccess()) {
            return new Response<>(ResultCode.ANOMALY);
        }
        ArrayList<Character> arrayList = new ArrayList<>(2);
        int i1, i2, mod, carry;
        if (Character.isDigit(a)) {
            i1 = a - 48;
        } else {
            i1 = a - 65 + 10;
        }
        if (Character.isDigit(b)) {
            i2 = b - 48;
        } else {
            i2 = b - 65 + 10;
        }
        mod = (i1 + i2 + c) % base;
        mod += mod < 10 ? 48 : 65 - 10;
        carry = (i1 + i2 + c) / base > 0 ? 43 : 48;
        arrayList.add((char) mod);
        arrayList.add((char) carry);
        return new Response<>(arrayList);

    }

    public static Response<ArrayList<Character>> sub(char a, char b, int o, short base) {
        if (!checkBase(a, base).isSuccess() || !checkBase(b, base).isSuccess()) {
            return new Response<>(ResultCode.ANOMALY);
        }
        ArrayList<Character> arrayList = new ArrayList<>(2);
        int i1, i2, diff, borrow;
        if (Character.isDigit(a)) {
            i1 = a - 48;
        } else {
            i1 = a - 65 + 10;
        }
        if (Character.isDigit(b)) {
            i2 = b - 48;
        } else {
            i2 = b - 65 + 10;
        }
        diff = i1 - o - i2;
        borrow = diff < 0 ? 45 : 48;
        diff += diff < 0 ? base : 0;
        diff += diff < 10 ? 48 : 65 - 10;
        arrayList.add((char) diff);
        arrayList.add((char) borrow);
        return new Response<>(arrayList);

    }

    public static Response<ArrayList<Boolean>> judgement(boolean first, boolean operator, boolean second) {
        boolean temp = Boolean.logicalXor(operator, second);
        boolean method = Boolean.logicalXor(first, temp);
        boolean reverse = Boolean.logicalXor(method, temp);
        Boolean f_method = method ? Boolean.TRUE:Boolean.FALSE;
        Boolean f_reverse = reverse ? Boolean.TRUE:Boolean.FALSE;
        ArrayList<Boolean> arrayList = new ArrayList<>();
        arrayList.add(f_method);
        arrayList.add(f_reverse);
        return new Response<>(arrayList);
    }

    public static Response<String> adjustment(char[] ch_arr) {
        int j;
        for (j = 1; j < ch_arr.length; j++) {
            if (ch_arr[j] != '0' && ch_arr[j] != 0) {
                break;
            }
        }
        if (j == ch_arr.length)
            return new Response<>("0");
        String number = new String(ch_arr, j, ch_arr.length - j);
        if (ch_arr[0] == '-') {
            return new Response<>("-" + number);
        }
        return new Response<>(number);
    }

    public static Response<String> sum(String num_1, String num_2, short base) {
        Response<String> response1 = checkBase(num_1, base);
        Response<String> response2 = checkBase(num_2, base);
        if (!response1.isSuccess() || !response2.isSuccess()) {
            return new Response<>(ResultCode.ANOMALY);
        }
        Response<ArrayList<String>> res_ali = alignment(response1.getResult(), response2.getResult());
        if (!res_ali.isSuccess()) {
            return new Response<>(ResultCode.ANOMALY);
        }
        num_1 = res_ali.getResult().get(0);
        num_2 = res_ali.getResult().get(1);
        char[] ch_arr_1 = num_1.toCharArray();
        char[] ch_arr_2 = num_2.toCharArray();
        char[] ch_arr_sum = new char[num_1.length() + 1];
        Response<ArrayList<Character>> result;
        int sum, carry = 0;//carry表示为进位
        for (int i = ch_arr_sum.length - 1; i > 1; i--) {
            result = add(ch_arr_1[i - 1], ch_arr_2[i - 1], carry, base);
            if (!result.isSuccess()) {
                return new Response<>(ResultCode.ANOMALY);
            }
            sum = result.getResult().get(0);
            carry = result.getResult().get(1) == 43 ? 1 : 0;
            ch_arr_sum[i] = (char) sum;
        }
        if (carry == 1) {
            ch_arr_sum[1] = '1';
        }
        return adjustment(ch_arr_sum);
    }

    public static Response<String> part(String num_1, String num_2, short base) {

    }

    public static void main(String[] args) {
        String one = "-0000054";
        String two = "-06";
        Response<String> sum = sum(one, two, (short) 33);
        if (sum.isSuccess()) {
            System.out.println(sum.getResult());
        } else {
            System.out.println("Input error!");
        }

    }

}
