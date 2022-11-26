package com.jnu.booklistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRGroup;
    private RadioButton mRBtn1;
    private RadioButton mRBtn2;
    private RadioButton mRBtn3;
    private RadioButton mRBtn4;
    private Fragment mFragment;
    public FragmentBookList fragmentBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
        mRGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton:
                        switchFragment(new FragmentBookList());break;
                    case R.id.radioButton2:
                        switchFragment(new FragmentBookList());break;
                    case R.id.radioButton3:
                        switchFragment(new FragmentBookList());break;
                    case R.id.radioButton4:
                        switchFragment(new FragmentBookList());break;
                    default:break;
                }
            }
        });
    }

    private void initView() {
        mRGroup=findViewById(R.id.radioGroup);
        mRBtn1=findViewById(R.id.radioButton);
        mRBtn2=findViewById(R.id.radioButton2);
        mRBtn3=findViewById(R.id.radioButton3);
        mRBtn4=findViewById(R.id.radioButton4);

        setStyle(R.drawable.book,mRBtn1);
        setStyle(R.drawable.classify,mRBtn2);
        setStyle(R.drawable.game,mRBtn3);
        setStyle(R.drawable.mine,mRBtn4);
    }

    private void initFragment() {
        mFragment= fragmentBookList =new FragmentBookList();//adapter需要获取对象执行CreateMenu方法
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,mFragment,"main").commit();
    }

    private void switchFragment(Fragment fragment) {
        if (fragment!=mFragment){
            if (!fragment.isAdded()){
                getSupportFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.container,fragment).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .hide(mFragment).show(fragment).commit();
            }
            mFragment=fragment;
        }
    }

    private void setStyle(int id, RadioButton RBtn) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0,0,80,80);
        RBtn.setCompoundDrawables(null,drawable,null,null);
    }

}