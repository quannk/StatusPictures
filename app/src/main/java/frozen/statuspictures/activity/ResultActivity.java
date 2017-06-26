package frozen.statuspictures.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import frozen.statuspictures.R;

/**
 * Created by HoangHiep on 5/17/2017.
 */

public class ResultActivity extends Activity implements View.OnClickListener {
    private ImageView imageResult;
    private LinearLayout  share;
    private TextView resultTitle, tvShare;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_result);
        imageResult = (ImageView) findViewById(R.id.image_result);
        share = (LinearLayout) findViewById(R.id.ll_share);
        share.setOnClickListener(this);
        resultTitle= (TextView) findViewById(R.id.tv_result_title);
        tvShare= (TextView) findViewById(R.id.tv_result_share);
//        Typeface myFont = Typeface.createFromAsset(getAssets(), "Tabitha full.ttf");
//        resultTitle.setTypeface(myFont);
//        tvShare.setTypeface(myFont);
        (findViewById(R.id.im_back_result)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent mIntent = getIntent();
        switch (mIntent.getAction()) {
            case "show.result":
                try {
                    path = getIntent().getStringExtra("path");
                    Glide.with(ResultActivity.this).load(new File(path)).asBitmap().into(imageResult);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;

            case "show.image":
                try {
                    path = getIntent().getStringExtra("path");
                    Glide.with(ResultActivity.this).load(new File(path)).asBitmap().into(imageResult);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }


    private void shareImage(Uri bmpUri) {
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_share:
                shareImage(Uri.fromFile(new File(path)));
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(ResultActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();

    }
}
