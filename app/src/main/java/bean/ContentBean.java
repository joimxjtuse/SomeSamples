package bean;

/**
 * Created by joim on 2018/4/20.
 */

public class ContentBean {

    private final String mImageRes;

    private final String mTitle;

    public ContentBean(String mImageRes, String mTitle) {
        this.mImageRes = mImageRes;
        this.mTitle = mTitle;
    }

    public String getImageRes() {
        return mImageRes;
    }

    public String getTitle() {
        return mTitle;
    }
}
