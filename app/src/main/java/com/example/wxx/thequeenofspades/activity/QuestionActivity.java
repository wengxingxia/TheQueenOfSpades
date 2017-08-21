package com.example.wxx.thequeenofspades.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.wxx.thequeenofspades.R;
import com.hanks.htextview.scale.ScaleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionActivity extends AppCompatActivity {

    @BindView(R.id.scaleTv)
    ScaleTextView mScaleTv;
    @BindView(R.id.etAnswer)
    EditText mEtAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        mScaleTv.setText("从前有两个人\n 一个叫我爱你 一个叫我不爱你 \n一天 我不爱你死了\n 还剩下谁");
    }
}
