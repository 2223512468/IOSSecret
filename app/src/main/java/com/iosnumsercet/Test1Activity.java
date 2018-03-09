package com.iosnumsercet;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iosnumsercet.util.Consts;
import com.iosnumsercet.util.MyPrefs;
import com.iosnumsercet.widget.NumericKeyboard;
import com.iosnumsercet.widget.PasswordTextView;


public class Test1Activity extends Activity {
    private NumericKeyboard nk;
    private PasswordTextView et_pwd1, et_pwd2, et_pwd3, et_pwd4;
    private int type;
    private TextView tv_info;
    private String input;
    private StringBuffer fBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        initWidget();
        initListener();
        type = getIntent().getIntExtra("type", 1);
    }


    private void initWidget() {
        nk = (NumericKeyboard) findViewById(R.id.nk);
        et_pwd1 = (PasswordTextView) findViewById(R.id.et_pwd1);
        et_pwd2 = (PasswordTextView) findViewById(R.id.et_pwd2);
        et_pwd3 = (PasswordTextView) findViewById(R.id.et_pwd3);
        et_pwd4 = (PasswordTextView) findViewById(R.id.et_pwd4);
        tv_info = (TextView) findViewById(R.id.tv_info);
    }


    private void initListener() {
        nk.setOnNumberClick(new NumericKeyboard.OnNumberClick() {
            @Override
            public void onNumberReturn(int number) {
                setText(number + "");
            }
        });
        et_pwd4.setOnTextChangedListener(new PasswordTextView.OnTextChangedListener() {
            @Override
            public void textChanged(String content) {
                input = et_pwd1.getTextContent() + et_pwd2.getTextContent() +
                        et_pwd3.getTextContent() + et_pwd4.getTextContent();
                if (type == Consts.SETTING_PASSWORD) {
                    tv_info.setText(getString(R.string.please_input_pwd_again));
                    type = Consts.SURE_SETTING_PASSWORD;
                    fBuffer.append(input);
                    clearText();//�������
                } else if (type == Consts.LOGIN_PASSWORD) {

                } else if (type == Consts.SURE_SETTING_PASSWORD) {
                    if (input.equals(fBuffer.toString())) {//һ��
                        showToastMsg(getString(R.string.setting_pwd_success));
                        MyPrefs.getInstance().initSharedPreferences(Test1Activity.this);
                    } else {
                        showToastMsg(getString(R.string.not_equals));
                        clearText();//�������
                    }
                }
            }
        });
    }


    private void setText(String text) {
        if (TextUtils.isEmpty(et_pwd1.getTextContent())) {
            et_pwd1.setTextContent(text);
        } else if (TextUtils.isEmpty(et_pwd2.getTextContent())) {
            et_pwd2.setTextContent(text);
        } else if (TextUtils.isEmpty(et_pwd3.getTextContent())) {
            et_pwd3.setTextContent(text);
        } else if (TextUtils.isEmpty(et_pwd4.getTextContent())) {
            et_pwd4.setTextContent(text);
        }
    }

    private void clearText() {
        et_pwd1.setTextContent("");
        et_pwd2.setTextContent("");
        et_pwd3.setTextContent("");
        et_pwd4.setTextContent("");
    }


    private void deleteText() {
        if (!TextUtils.isEmpty(et_pwd4.getTextContent())) {
            et_pwd4.setTextContent("");
        } else if (!TextUtils.isEmpty(et_pwd3.getTextContent())) {
            et_pwd3.setTextContent("");
        } else if (!TextUtils.isEmpty(et_pwd2.getTextContent())) {
            et_pwd2.setTextContent("");
        } else if (!TextUtils.isEmpty(et_pwd1.getTextContent())) {
            et_pwd1.setTextContent("");
        }
    }


    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.btn_again:
                clearText();
                break;
            case R.id.btn_delete:
                deleteText();
                break;

            default:
                break;
        }
    }


    private void showToastMsg(String text) {
        Toast.makeText(this, text, 1000).show();
    }
}
