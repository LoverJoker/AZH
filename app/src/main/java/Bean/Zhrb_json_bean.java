package Bean;

import java.util.List;

/**
 * Created by Joker on 2016/10/1.
 */

public class Zhrb_json_bean {


    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    private List<TopStoriesBean> top_stories;

    public Zhrb_json_bean(){

    }

    public static class StoriesBean {
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public List<String> getImages() {
            return images;
        }
    }

    public static class TopStoriesBean {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }
    }
}
