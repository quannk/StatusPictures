package frozen.statuspictures.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import frozen.statuspictures.AppConst;
import frozen.statuspictures.activity.MainActivity;
import frozen.statuspictures.R;
import frozen.statuspictures.custom.StickerTextView;
import frozen.statuspictures.activity.ResultActivity;
import frozen.statuspictures.adapter.MyFontAdapter;
import frozen.statuspictures.adapter.TextmenuAdapter;
import frozen.statuspictures.item.ItemFont;
import frozen.statuspictures.item.ItemStatus;

import static android.app.Activity.RESULT_OK;


/**
 * Created by QuanNguy on 31/05/2017.
 */

public class EditPhotoFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_image, iv_done, iv_done2, iv_back;
    private EditText edt_text;
    private int pos;
    private LinearLayout loveButon, friendButon, familyButon, lifeButon;
    private Button sizeTextButton, colorButon, fontButton, editTextButon;
    private String image_path;
    private static Bitmap bm;
    ;
    private Paint paint;
    private ArrayList<ItemStatus> listStatusFamily, listStatusLove, listStatusFriend, listStatusLife, curentList;
    private ArrayList<ItemFont> listFont;
    private TextmenuAdapter adapter;
    private MyFontAdapter mAdapter;
    private ListView lv_fucfact, lv_font;
    private SeekBar seekBarTextSize;
    private StickerTextView tv_sticker;
    private FrameLayout canvas;
    private int current_Size;

    public static EditPhotoFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putString(AppConst.KEY_PATH, path);
        EditPhotoFragment fragment = new EditPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editphoto, container, false);
        initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            pos = bundle.getInt(AppConst.KEY_POSTION, 0);
            image_path = bundle.getString(AppConst.KEY_PATH, "");
        }
        cropImage();
        paint = new Paint();
        canvas = (FrameLayout) v.findViewById(R.id.canvasView);
        tv_sticker = new StickerTextView(this.getActivity());
        return v;
    }

    private void initFont() {
        listFont = new ArrayList<>();
        listFont.add(new ItemFont("Biortec.ttf"));
        listFont.add(new ItemFont("ChinaCat.ttf"));
        listFont.add(new ItemFont("Chococooky.ttf"));
        listFont.add(new ItemFont("Corsiva.ttf"));
        listFont.add(new ItemFont("Gothic - San.ttf"));
        listFont.add(new ItemFont("Southland.ttf"));
        listFont.add(new ItemFont("Tabitha full.ttf"));
        listFont.add(new ItemFont("Thu phap.ttf"));
        listFont.add(new ItemFont("UVF_Chikita.ttf"));
        listFont.add(new ItemFont("Victoria.ttf"));
        listFont.add(new ItemFont("Zephyr.ttf"));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_image = (ImageView) view.findViewById(R.id.iv_big_image);
        loveButon = (LinearLayout) view.findViewById(R.id.btn_love);
        lifeButon = (LinearLayout) view.findViewById(R.id.btn_life);
        familyButon = (LinearLayout) view.findViewById(R.id.btn_family);
        friendButon = (LinearLayout) view.findViewById(R.id.btn_friend);
        editTextButon = (Button) view.findViewById(R.id.btn_edit_text);
        colorButon = (Button) view.findViewById(R.id.btn_color);
        fontButton = (Button) view.findViewById(R.id.btn_font);
        sizeTextButton = (Button) view.findViewById(R.id.btn_textsize);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        edt_text = (EditText) view.findViewById(R.id.tv_text);
        iv_done = (ImageView) view.findViewById(R.id.iv_done);
        iv_done2 = (ImageView) view.findViewById(R.id.iv_done_edit);
        lv_fucfact = (ListView) view.findViewById(R.id.lv_funfact);
        lv_font = (ListView) view.findViewById(R.id.lv_font);

        lv_fucfact.setVisibility(View.GONE);
        lv_font.setVisibility(View.GONE);
        seekBarTextSize = (SeekBar) view.findViewById(R.id.seek_size);
        seekBarTextSize.setVisibility(View.GONE);
        initViews();
    }

    private void changeColor() {
        ColorPickerDialogBuilder colora = null;
        colora.with(getActivity())
                .setTitle("Choose color")
                //.initialColor(edt_text.getTextColors())
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        edt_text.setTextColor(selectedColor);
                        paint.setColor(selectedColor);
                        tv_sticker.setTextColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
    private void initViews() {
        edt_text.setVisibility(View.GONE);
        iv_done2.setVisibility(View.GONE);
        iv_done.setOnClickListener(this);
        iv_done2.setOnClickListener(this);
        loveButon.setOnClickListener(this);
        lifeButon.setOnClickListener(this);
        friendButon.setOnClickListener(this);
        familyButon.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        editTextButon.setOnClickListener(this);
        colorButon.setOnClickListener(this);
        sizeTextButton.setOnClickListener(this);
        fontButton.setOnClickListener(this);
        canvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lv_fucfact.isShown()) {
                    lv_fucfact.setVisibility(View.GONE);
                }
                if (lv_font.isShown()) {
                    lv_font.setVisibility(View.GONE);
                }
                if (seekBarTextSize.isShown()) {
                    seekBarTextSize.setVisibility(View.GONE);
                }
                if (tv_sticker.isShown()) {
                    tv_sticker.makeStaticSticker();
                }
            }
        });
        lv_fucfact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View viewList, int position, long id) {
                                                  //  edt_text.setText(curentList.get(position).getContent());
                                                  canvas.removeView(tv_sticker);
                                                  canvas.addView(tv_sticker);
                                                  lv_fucfact.setVisibility(View.GONE);
                                                  tv_sticker.setText(curentList.get(position).getContent());
                                                  edt_text.setText(tv_sticker.getText());
                                                  //    ivItemFunfact= (ImageView) viewList.findViewById(R.id.iv_item_funfact);
                                              }
                                          }
        );
        lv_font.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Typeface myFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + listFont.get(position).getName());
                tv_sticker.setFont(myFont);
                lv_font.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_done:
                tv_sticker.makeStaticSticker();
                saveImageToStorage();
                Toast.makeText(this.getActivity(), "Lưu ảnh thành công", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_life:
                adapter = new TextmenuAdapter(this.getActivity(), R.id.lv_funfact, listStatusLife, AppConst.TABLE_FUNFACT_LIFE);
                lv_fucfact.setAdapter(adapter);
                lv_fucfact.setVisibility(View.VISIBLE);
                curentList = listStatusLife;
                break;
            case R.id.btn_love:
                adapter = new TextmenuAdapter(this.getActivity(), R.id.lv_funfact, listStatusLove, AppConst.TABLE_FUNFACT_LOVE);
                lv_fucfact.setAdapter(adapter);
                lv_fucfact.setVisibility(View.VISIBLE);
                curentList = listStatusLove;
                break;
            case R.id.btn_family:
                adapter = new TextmenuAdapter(this.getActivity(), R.id.lv_funfact, listStatusFamily, AppConst.TABLE_FUNFACT_FAMILY);
                lv_fucfact.setAdapter(adapter);
                lv_fucfact.setVisibility(View.VISIBLE);
                curentList = listStatusFamily;
                break;
            case R.id.btn_friend:
                adapter = new TextmenuAdapter(this.getActivity(), R.id.lv_funfact, listStatusFriend, AppConst.TABLE_FUNFACT_FRIEND);
                lv_fucfact.setAdapter(adapter);
                lv_fucfact.setVisibility(View.VISIBLE);
                curentList = listStatusFriend;
                break;
            case R.id.btn_color:
                changeColor();
                break;
            case R.id.btn_font:
                showListVIewFont();
                break;
            case R.id.btn_textsize:
                seekBarTextSize.setVisibility(View.VISIBLE);
                initSeekBar();
                break;
            case R.id.btn_edit_text:
                edt_text.setVisibility(View.VISIBLE);
                iv_done2.setVisibility(View.VISIBLE);
                tv_sticker.setVisibility(View.GONE);
                break;
            case R.id.iv_done_edit:
                edt_text.setVisibility(View.GONE);
                iv_done2.setVisibility(View.GONE);
                tv_sticker.setVisibility(View.VISIBLE);
                tv_sticker.setText(edt_text.getText().toString());
                canvas.removeView(tv_sticker);
                tv_sticker.setText(edt_text.getText().toString());
                canvas.addView(tv_sticker);
                break;
            case R.id.iv_back:
                if (lv_fucfact.isShown()) {
                    lv_fucfact.setVisibility(View.GONE);
                } else if (lv_font.isShown()) {
                    lv_font.setVisibility(View.GONE);
                } else getFragmentManager().popBackStack();
            default:
                break;
        }
    }

    private void initSeekBar() {
        seekBarTextSize.setMax(60);
        if (current_Size != 0) {
            seekBarTextSize.setProgress(current_Size);
        } else seekBarTextSize.setProgress(0);
        seekBarTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_sticker.setTextSize(progress * 2);
                seekBarTextSize.setProgress(progress);
                current_Size = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //   seekBarTextSize.setVisibility(View.GONE);
            }
        });
    }

    private void showListVIewFont() {
        initFont();
        mAdapter = new MyFontAdapter(getActivity(), android.R.layout.simple_list_item_1, listFont);
        lv_font.setAdapter(mAdapter);
        lv_font.setVisibility(View.VISIBLE);
    }
    private void cropImage() {
        if (image_path != null) {
            File file = new File(image_path);
            Uri uri = Uri.fromFile(file);
            CropImage.activity(uri)
                    .start(getActivity().getApplicationContext(), this);
        } else {
            Log.e("TAG", "Null file path");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                //  iv_image.setImageURI(resultUri);
//                try {
//                    bm = getBitmap(this.getActivity().getContentResolver(), resultUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                Glide.with(this.getActivity()).load(resultUri).override(canvas.getWidth(), Target.SIZE_ORIGINAL)
                        .fitCenter()
                        .into(iv_image);
            } else  getFragmentManager().popBackStack();
            Log.e("tag","abcdcd");
            }
    }

    private void initData() {
        listStatusFamily = MainActivity.databaseStatus.getAll(AppConst.TABLE_FUNFACT_FAMILY);
        listStatusLife = MainActivity.databaseStatus.getAll(AppConst.TABLE_FUNFACT_LIFE);
        listStatusFriend = MainActivity.databaseStatus.getAll(AppConst.TABLE_FUNFACT_FRIEND);
        listStatusLove = MainActivity.databaseStatus.getAll(AppConst.TABLE_FUNFACT_LOVE);
    }
    private void saveImageToStorage() {
        try {
            canvas.setDrawingCacheEnabled(true);
            canvas.destroyDrawingCache();
            Bitmap bitmap = canvas.getDrawingCache();
            String root = Environment.getExternalStorageDirectory().toString()
                    + "/" + Environment.DIRECTORY_PICTURES;
            String folderPath = root + "/Status Tam Trang";
            File newDir = new File(folderPath);
            newDir.mkdirs();
            DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date date = new Date(System.currentTimeMillis());
            String sdt = df.format(date);
            String imageName = "Status" + sdt + ".jpg";
            System.out.println(imageName);
            File file = new File(newDir, imageName);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATE_TAKEN, date.getTime());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA, folderPath + "/" + imageName);
            this.getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            out.flush();
            out.close();
            Intent intentResult = new Intent(getActivity(), ResultActivity.class);
            intentResult.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentResult.setAction("show.result");
            intentResult.putExtra("path", folderPath + "/" + imageName);
            startActivity(intentResult);
            getActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
