package pojo;

import java.io.Serializable;

/**
 * @创建人 YDL
 * @创建时间 2020/4/28 19:18
 * @描述
 */

public class Reting implements Serializable {
    private int userId;
    private int productId ;
    private double rating ;
    private int timestamp ;
    public Reting(){

    }
    public Reting(int uid,int pid,double rat,int timestamp){
        userId = uid;
        productId = pid;
        rating = rat;
        this.timestamp =timestamp;
    }
    public Object[] userId(){
        return new Object[]{"userId",userId};
    }

    @Override
    public String toString() {
        return "Reting{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", rating=" + rating +
                ", timestamp=" + timestamp +
                '}';
    }

    public Object[] productId(){
        return new Object[]{"productId",productId};
    }

    public Object[] rating(){
        return new Object[]{"rating",rating};
    }

    public Object[] timestamp(){
        return new Object[]{"timestamp",timestamp};
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
