package frozen.statuspictures.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import frozen.statuspictures.AppConst;
import frozen.statuspictures.activity.MainActivity;
import frozen.statuspictures.R;

/**
 * Created by QuanNguy on 30/05/2017.
 */

public class MenuFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {
    private View view;
    private ImageView iv_camera, iv_gallery;
    private String path;
    private ContentValues values;
    private Uri mUri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        initViews();
        return view;
    }
    private void initViews() {
        iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
        iv_gallery = (ImageView) view.findViewById(R.id.iv_gallery);
        iv_camera.setOnClickListener(this);
        iv_gallery.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_gallery:
         //       ((MainActivity)getActivity()).addFragment();
                ((MainActivity)getActivity()).showFragment(MainActivity.imageFragment);

                break;
            case R.id.iv_camera:
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                mUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                startActivityForResult(intent, AppConst.CAMERA_REQUEST);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConst.CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    path = getRealPathFromURI(mUri);
                    File file = new File(path);
                    Uri uri = Uri.fromFile(file);
                    Log.e("TAG", path);
                    ((MainActivity)getActivity()).showFragment(new EditPhotoFragment().newInstance(path));
                }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK ) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Log.e("tag","aaaaa");
            return true;
        } else {
            return false;
        }
    }
}


