package cn.joim.samples.somesamples.image_loader;

import java.util.List;

import bean.ContentBean;

/**
 * Created by joim on 2018/4/20.
 */
interface ImageLoaderModule {

    interface View extends BaseView<Presenter> {

        public void bindListContent(List<ContentBean> listItems);

        public void setTitle(String titleStr);


    }

    interface Presenter extends BasePresenter {

        public void loadTitle();


    }

}
