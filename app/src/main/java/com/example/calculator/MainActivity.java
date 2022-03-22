package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "CalculatorActivity";
    private TextView tv_result;
    private String operator = "";
    private String firstNum = "";
    private String secondNum = "";
    private String result = "";
    private String showText = ""; // nội dung văn bảng hiển thị

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = findViewById(R.id.tv_result);

        findViewById(R.id.btn_cancel).setOnClickListener(this); // Nút Hủy
        findViewById(R.id.btn_divide).setOnClickListener(this); // Nút chia
        findViewById(R.id.btn_multiply).setOnClickListener(this); // Nút nhân
        findViewById(R.id.btn_clear).setOnClickListener(this); // Nút xóa
        findViewById(R.id.btn_seven).setOnClickListener(this); // Số 7
        findViewById(R.id.btn_eight).setOnClickListener(this); // Số 8
        findViewById(R.id.btn_nine).setOnClickListener(this); // Số 9
        findViewById(R.id.btn_plus).setOnClickListener(this); // Nút cộng
        findViewById(R.id.btn_four).setOnClickListener(this); // Số 4
        findViewById(R.id.btn_five).setOnClickListener(this); // Số 5
        findViewById(R.id.btn_six).setOnClickListener(this); // Số 6
        findViewById(R.id.btn_minus).setOnClickListener(this); // Nút trừ
        findViewById(R.id.btn_one).setOnClickListener(this); // Số 1
        findViewById(R.id.btn_two).setOnClickListener(this); // Số 2
        findViewById(R.id.btn_three).setOnClickListener(this); // Số 3
        findViewById(R.id.btn_reciprocal).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this); // Số 0
        findViewById(R.id.btn_dot).setOnClickListener(this); // Dấu chấm
        findViewById(R.id.btn_equal).setOnClickListener(this); // Dấu bằng
        findViewById(R.id.btn_sqrt).setOnClickListener(this);
    }

    private boolean verify(View v) {
        if (v.getId() == R.id.btn_cancel) { // click vào nút Hủy
            if (operator.equals("") && (firstNum.equals("") || firstNum.equals("0"))) { // Không có toán tử -> hủy toán hạng đầu tiên
                Toast.makeText(this, "Không còn số nào để hủy", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!operator.equals("") && secondNum.equals("")) { // có toán tử -> hủy toán hạng thứ 2
                Toast.makeText(this, "Không còn số nào để hủy", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (v.getId() == R.id.btn_equal) { // click vào nút =
            if (operator.equals("")) { // không có toán tử
                Toast.makeText(this, "Vui lòng nhập 1 toán tử", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (firstNum.equals("") || secondNum.equals("")) { // Không có toán hạng
                Toast.makeText(this, "Vui lòng nhập số", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (operator.equals("÷") && Double.parseDouble(secondNum) == 0) { // Chia cho số- 0
                Toast.makeText(this, "Số chia không được bằng 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (v.getId() == R.id.btn_plus || v.getId() == R.id.btn_minus // Click vào các nút + - x /
                || v.getId() == R.id.btn_multiply || v.getId() == R.id.btn_divide) {
            if (firstNum.equals("")) { // Thiếu toán hạng đầu tiên
                Toast.makeText(this, "Vui lòng nhập số", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!operator.equals("")) {
                Toast.makeText(this, "Vui lòng nhập số", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (v.getId() == R.id.btn_sqrt) { // click vào sqrt
            if (firstNum.equals("")) { // Thiếu cơ sở
                Toast.makeText(this, "Vui lòng nhập số", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (Double.parseDouble(firstNum) < 0) { // Không thể bình phương số âm
                Toast.makeText(this, "Số gốc không được bé hơn 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (v.getId() == R.id.btn_reciprocal) {
            if (firstNum.equals("")) {
                Toast.makeText(this, "Vui lòng nhập số", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (Double.parseDouble(firstNum) == 0) {
                Toast.makeText(this, "Không thể bằng 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (v.getId() == R.id.btn_dot) { // clíck vào dấu thập phân
            if (operator.equals("") && firstNum.contains(".")) { // Không có toán tử, kiểm tra toán hạng đầu tiên đã có dấu thập phân hay chưa
                Toast.makeText(this, "Một số không thể có hai dấu thập phân", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!operator.equals("") && secondNum.contains(".")) { // Nếu có 1 toán tử, kiểm tra toán hạng thứ 2 đã có dấu thập phân hay chưa
                Toast.makeText(this, "Một số không thể có hai dấu thập phân", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (!verify(v)) { // Kiểm tra tính hợp lệ
            return;
        }
        String inputText;
        if (v.getId() == R.id.btn_sqrt) { // nút sqrt
            inputText = "√";
        } else { // Cấc nút khác ngoài sqrt
            inputText = ((TextView) v).getText().toString();
        }
        Log.d(TAG, "inputText=" + inputText);
        if (v.getId() == R.id.btn_clear) { // Nhấp vào nút xóa
            clear();
        } else if (v.getId() == R.id.btn_cancel) { // Nhấp vào nút hủy
            if (operator.equals("")) { // Không có toán tử, hủy toán hạng đầu
                if (firstNum.length() == 1) {
                    firstNum = "0";
                } else if (firstNum.length() > 1) {
                    firstNum = firstNum.substring(0, firstNum.length() - 1);
                }
                refreshText(firstNum);
            } else { // Có toán tử, hủy toán hạng thứ 2
                if (secondNum.length() == 1) {
                    secondNum = "";
                } else if (secondNum.length() > 1) {
                    secondNum = secondNum.substring(0, secondNum.length() - 1);
                }
                refreshText(showText.substring(0, showText.length() - 1));
            }
        } else if (v.getId() == R.id.btn_plus || v.getId() == R.id.btn_minus // Các nút + - x /
                || v.getId() == R.id.btn_multiply || v.getId() == R.id.btn_divide) {
            operator = inputText;
            refreshText(showText + operator);
        } else if (v.getId() == R.id.btn_equal) { // nhấp vào nút =
            double calculate_result = calculateFour(); // + - x /
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "=" + result);
        } else if (v.getId() == R.id.btn_sqrt) {
            double calculate_result = Math.sqrt(Double.parseDouble(firstNum)); // phép căn bậc 2
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "√=" + result);
        } else if (v.getId() == R.id.btn_reciprocal) {
            double calculate_result = 1.0 / Double.parseDouble(firstNum);
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "/=" + result);
        } else { // Các nút còn lại, bao gồm cả dấu thập phân
            if (result.length() > 0 && operator.equals("")) { // Kết quả
                clear();
            }
            if (operator.equals("")) { // Không có toán tử
                firstNum = firstNum + inputText;
            } else { // nếu có toán tử
                secondNum = secondNum + inputText;
            }
            if (showText.equals("0") && !inputText.equals(".")) { // Số nguyên không cần số - đứng đầu
                refreshText(inputText);
            } else {
                refreshText(showText + inputText);
            }
        }
    }


    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }


    private void refreshText(String text) {
        showText = text;
        tv_result.setText(showText);
    }


    private void clear() {
        refreshOperate("");
        refreshText("");
    }

    // kết quả của các phép tính + - x /
    private double calculateFour() {
        double calculate_result = 0;
        if (operator.equals("＋")) {
            calculate_result = Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
        } else if (operator.equals("－")) {
            calculate_result = Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
        } else if (operator.equals("×")) {
            calculate_result = Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
        } else if (operator.equals("÷")) {
            calculate_result = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }
        Log.d(TAG, "calculate_result=" + calculate_result);
        return calculate_result;
    }

}