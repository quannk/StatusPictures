package frozen.statuspictures.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import frozen.statuspictures.R;
import frozen.statuspictures.item.ItemImage;

/**
 * Created by QuanNguy on 30/05/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ItemImage> listImages;

    public ImageAdapter(Context c, ArrayList<ItemImage> listImages) {
        mContext = c;
        this.listImages = listImages;
    }


    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        Glide.with(mContext).load(listImages.get(position).getFilePath())
                .placeholder(R.drawable.item_bg).centerCrop()
                .into(imageView);
        return imageView;
    }

}


