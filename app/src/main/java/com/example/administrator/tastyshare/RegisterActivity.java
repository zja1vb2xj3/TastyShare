package com.example.administrator.tastyshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_GETIMAGE = 102;
    private ImageView food_IV;

    private RadioGroup tagGroup;
    private RadioButton tags;

    private String imageData;
    private String tagClickedData;

    private EditText registerEateryName;
    private EditText registerMenuName;
    private EditText registerComment;

    private RatingBar eateryScore;

    private Button registerOperateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        food_IV = (ImageView)findViewById(R.id.food_imageView);

        Intent intent = getIntent();

        getCaptureImage(intent);

        //태그 등록
        tagGroup = (RadioGroup)findViewById(R.id.radioGroup);
        tags = (RadioButton)findViewById(tagGroup.getCheckedRadioButtonId());

        tagGroup.setOnCheckedChangeListener(this::tagGroupClick);

        registerEateryName = (EditText)findViewById(R.id.registerEateryName);
        registerMenuName = (EditText)findViewById(R.id.registerMenuName);
        registerComment = (EditText)findViewById(R.id.registerComment);

        eateryScore = (RatingBar)findViewById(R.id.eateryScore);

        registerOperateButton = (Button)findViewById(R.id.registerOperate);

        registerOperateButton.setOnClickListener(this::registerOperateButtonClick);
    }

    private void getCaptureImage(Intent intent){
        if(intent.getExtras() != null) {
            Bitmap bitmap = (Bitmap) intent.getExtras().get("CAPTURE_OK");

            food_IV.setImageBitmap(bitmap);
        }

        else{
            Toast.makeText(getApplicationContext(),"캡쳐한 이미지가 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }


    private void tagGroupClick(RadioGroup radioGroup, int id) {
        switch (id){
            case R.id.fastfood:
                tagClickedData = "패스트푸드";
                break;

            case R.id.chinafood:
                tagClickedData = "중식";
                break;

            case R.id.japanfood:
                tagClickedData = "일식";
                break;

            case R.id.koreafood:
                tagClickedData = "한식";
                break;

            default:
                tagClickedData = null;
                break;
        }
    }

    private void registerOperateButtonClick(View view) {

        getRegisterData();

    }

    private void getRegisterData(){
        System.out.println("이미지 = " + imageData);
        System.out.println("태그 = " + tagClickedData);
        System.out.println("업체 = " + registerEateryName.getText().toString());
        System.out.println("메뉴 = " + registerMenuName.getText().toString());
        System.out.println("후기 = "+ registerComment.getText().toString());
        System.out.println("점수 = "+ eateryScore.getRating());
    }


}
