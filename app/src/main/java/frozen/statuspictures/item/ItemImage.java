package frozen.statuspictures.item;

/**
 * Created by QuanNguy on 30/05/2017.
 */

public class ItemImage {
    private String filePath;

    public ItemImage(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
