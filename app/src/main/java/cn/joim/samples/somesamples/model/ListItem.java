package cn.joim.samples.somesamples.model;

/**
 * Created by joim on 2018/3/5.
 */

public class ListItem {

    private String mTitle;

    private String mImageURL;

    public ListItem(String mTitle, String mImageURL) {
        this.mTitle = mTitle;
        this.mImageURL = mImageURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageURL() {
        return mImageURL;
    }
}
