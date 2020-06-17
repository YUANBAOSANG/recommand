package pojo;

import breeze.signal.OptWindowFunction;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/5 15:57
 * @描述
 */
public class ProductRecOff {
    private int productId;
    private List<UserRating> recomList;
    private long timesamp;
    public static class UserRating{
        double rating;
        int userId;

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
        public UserRating(){}
        public UserRating(double rating, int userId) {
            this.rating = rating;
            this.userId = userId;
        }
    }
    public ProductRecOff(int productId, List<UserRating> recomList, long timesamp) {
        this.productId = productId;
        this.recomList = recomList;
        this.timesamp = timesamp;
    }

    public int getProductId() {
        return productId;
    }

    public ProductRecOff() {
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public List<UserRating> getRecomList() {
        return recomList;
    }

    public void setRecomList(List recomList) {
        this.recomList = recomList;
    }

    public long getTimesamp() {
        return timesamp;
    }

    public void setTimesamp(long timesamp) {
        this.timesamp = timesamp;
    }
}
