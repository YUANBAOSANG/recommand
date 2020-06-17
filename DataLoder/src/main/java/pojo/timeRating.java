package pojo;

/**
 * @创建人 YDL
 * @创建时间 2020/6/3 16:52
 * @描述
 */
public class timeRating {
    private int productId;
    private double rating;
    private int yearmonth;

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

    public int getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(int yearmonth) {
        this.yearmonth = yearmonth;
    }
}
