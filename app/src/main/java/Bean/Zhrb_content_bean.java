package Bean;

import java.util.ArrayList;

/**
 * 知乎日报点击详情页面的数据
 * 遇到大括号就创建对象，遇到中括号就创建集合。
 * Created by Joker on 2016/10/5.
 */

public class Zhrb_content_bean {

    public String share_url ;

    public String title ;

    public int type ;

    public ArrayList<String> js ;



    public ArrayList<String> images ;

    public String image_source;

    public String image ;

    public int id ;

    public String getImage() {
        return image;
    }

    public String ga_prefix ;

    public ArrayList<String> css ;

    public ArrayList<String> getCss() {
        return css;
    }

    public String body ;

    public String getBody() {
        return body;
    }
}
