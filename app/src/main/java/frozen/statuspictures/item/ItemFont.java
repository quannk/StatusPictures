package frozen.statuspictures.item;

import java.io.Serializable;

/**
 * Created by QuanNguy on 06/06/2017.
 */

public class ItemFont implements Serializable{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemFont(String name) {

        this.name = name;
    }

    @Override
    public String toString() {
        return "ItemFont{" +
                "name='" + name + '\'' +
                '}';
    }
}
