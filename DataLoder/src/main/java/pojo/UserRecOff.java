package pojo;

import java.util.List;

/**
 * @创建人 YDL
 * @创建时间 2020/5/5 15:56
 * @描述
 */
public class UserRecOff {
    private int userId;
    private long timesamp;
    private List<ProductRating> recomList;

    public List<ProductRating> getRecomList() {
        return recomList;
    }

    public void setRecomList(List<ProductRating> recomList) {
        this.recomList = recomList;
    }

    public UserRecOff(int userId, long timesamp, List<ProductRating> recomList) {
        this.userId = userId;
        this.timesamp = timesamp;
        this.recomList = recomList;
    }

    public static class ProductRating{
        private double rating;
        private int productId;
        public ProductRating(){}

        public ProductRating(double rating, int productId) {
            this.rating = rating;
            this.productId = productId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }




    }
    public UserRecOff(){}


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTimesamp() {
        return timesamp;
    }

    public void setTimesamp(long timesamp) {
        this.timesamp = timesamp;
    }



}
