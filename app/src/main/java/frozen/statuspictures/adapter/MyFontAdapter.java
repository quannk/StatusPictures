package frozen.statuspictures.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import frozen.statuspictures.R;
import frozen.statuspictures.item.ItemFont;

/**
 * Created by QuanNguy on 07/06/2017.
 */

public class MyFontAdapter extends ArrayAdapter {
    private ArrayList<ItemFont> arrFont;
    private LayoutInflater inflater;
    private Context context;
    public MyFontAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ItemFont> data) {
        super(context, resource, data);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrFont  = data;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_font, parent, false);
            TextView tvFont = (TextView) convertView.findViewById(R.id.tv_font_item);
            myViewHolder = new MyViewHolder();
            myViewHolder.tvFont = tvFont;
            convertView.setTag(myViewHolder);
        }
        myViewHolder = (MyViewHolder) convertView.getTag();
        myViewHolder.tvFont.setText(arrFont.get(position).getName());
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + arrFont.get(position).getName());
        myViewHolder.tvFont.setTypeface(typeface);
        return convertView;
    }
    class MyViewHolder{
        private TextView tvFont;
    }
}
