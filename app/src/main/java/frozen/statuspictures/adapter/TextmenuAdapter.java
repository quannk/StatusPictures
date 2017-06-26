package frozen.statuspictures.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import frozen.statuspictures.R;
import frozen.statuspictures.item.ItemStatus;

/**
 * Created by QuanNguy on 01/06/2017.
 */

public class TextmenuAdapter extends ArrayAdapter<ItemStatus> {
    private String typeStatus;
    public TextmenuAdapter(Context context, int resource, List<ItemStatus> items, String typeStatus) {
        super(context, resource, items);
        this.typeStatus=typeStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_funfact, null);
        }
        ItemStatus p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tv_funfact);
            ImageView iv= (ImageView) v.findViewById(R.id.iv_item_funfact);
            if (tt1 != null) {
                tt1.setText(p.getContent());
            }
            switch (typeStatus){
                case "tbFunfactlife":
                    iv.setImageResource(R.drawable.ic_life);
                    break;
                case "tbfunfactfamily":
                    iv.setImageResource(R.drawable.ic_family);
                    break;
                case "tbfunfactfriend":
                    iv.setImageResource(R.drawable.ic_friends);
                    break;
                case "tbfunfactlove":
                    iv.setImageResource(R.drawable.ic_love);
                    break;
            }
        }
        return v;
    }

}
