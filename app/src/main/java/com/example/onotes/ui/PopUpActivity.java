package com.example.onotes.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.onotes.R;

public class PopUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button popup;
    private Button one;
    private Button two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        initView();

    }

    private void initView() {
        popup = (Button) findViewById(R.id.popup);

        popup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup:
                initPopupWindow(v);
                break;

        }
    }

    PopupWindow popupWindow;

    private void initPopupWindow(View view) {
        if (popupWindow == null) {
            View popupView = LayoutInflater.from(PopUpActivity.this).inflate(R.layout.edit_actions_sheet, null);
            // 三部曲第二  构造函数关联
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
       /*  popupView.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(PopUpActivity.this, "Your click one", Toast.LENGTH_SHORT).show();
             }
         });
            popupView.findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PopUpActivity.this, "Your click two", Toast.LENGTH_SHORT).show();
                }
            });*/
           /* one = (Button) findViewById(R.id.one);
            one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PopUpActivity.this, "Your click one", Toast.LENGTH_SHORT).show();
                }
            });
            two = (Button) findViewById(R.id.two);
            two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PopUpActivity.this, "Your click two", Toast.LENGTH_SHORT).show();
                }
            });*/
        }

        // =======  两者结合才能让popup点击外部消失
     //   popupWindow.setOutsideTouchable(false);
       // popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // =======  两者结合才能让popup点击外部消失
        // 让popup占有优先于activity的交互响应能力，不单单是焦点问题。
     //   popupWindow.setFocusable(true);

        /*if (Build.VERSION.SDK_INT >= 21) {
            popupWindow.setElevation(10.0f);
        }*/


      //  popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        // 三部曲第三   展示popup
      //  popupWindow.showAsDropDown(view);

        popupWindow.showAtLocation(view,Gravity.TOP, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}