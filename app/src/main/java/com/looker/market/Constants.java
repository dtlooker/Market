package com.looker.market;

/**
 * Created by looker on 2017/3/6.
 */

public class Constants {

    public static final String Campaign_ID = "campaign_id";

    public static class API{

        public static final String BASE_URL="http://112.124.22.238:8081/course_api/";

        public static final String BANNER = BASE_URL + "banner/query?type=1";

        public static final String CAMPAIGN_HOME=BASE_URL + "campaign/recommend";

        public static final String WARES_HOT=BASE_URL + "wares/hot";

        public static final String WARES_LIST=BASE_URL +"wares/list";

        public static final String WARES_CAMPAIN_LIST=BASE_URL +"wares/campaign/list";

        public static final String CATEGORY_LIST=BASE_URL +"category/list";
    }

}
