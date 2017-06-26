package frozen.statuspictures.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import frozen.statuspictures.activity.MainActivity;
import frozen.statuspictures.R;
import frozen.statuspictures.adapter.ImageAdapter;
import frozen.statuspictures.item.ItemImage;

/**
 * Created by QuanNguy on 30/05/2017.
 */

public class ImageFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ImageAdapter myImageAdapter;
    private GridView gridview;
    private View view;
    private ArrayList<ItemImage> listImages;
    private ImageView backMenu;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_libralry, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);
        listImages = new ArrayList<>();
        getImages();
        myImageAdapter = new ImageAdapter(this.getActivity(), listImages);
        gridview.setAdapter(myImageAdapter);
        gridview.setOnItemClickListener(this);
        backMenu= (ImageView) view.findViewById(R.id.iv_back_menu);
        backMenu.setOnClickListener(this);
        return view;
    }

    public void getImages() {
        Uri imgUri = null;
        Uri selectedImage = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                while (c.moveToNext()) {
                    int columnIndex = c.getColumnIndex(filePathColumn[0]);
                    String picturePath = c.getString(columnIndex);
                    listImages.add(new ItemImage(picturePath));
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (null != listImages && !listImages.isEmpty()) {
                ((MainActivity)getActivity()).showFragment(
                        new EditPhotoFragment().newInstance(listImages.get(position).getFilePath()));
            }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_menu:
                getFragmentManager().popBackStack();
        }
    }

}
