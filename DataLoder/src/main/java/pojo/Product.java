package pojo;

import java.io.Serializable;

/**
 * @创建人 YDL
 * @创建时间 2020/4/28 18:57
 * @描述
 */
public class Product implements Serializable {
    private int productId;
    private String name;
    private String imgUrl;
    private String categories;
    private String tags;
    private Product(ProductBuider buider){
        this.categories = buider.categories;
        this.imgUrl = buider.imgUrl;
        this.name = buider.name;
        this.productId = buider.productId;
        this.tags = buider.tags;
    }
    public static class ProductBuider{
        private int productId = -1;
        private String name;
        private String imgUrl;
        private String categories;
        private String tags;
        public ProductBuider(){

        }
        public ProductBuider categories(String categories){
            this.categories = categories;
            return this;
        }
        public ProductBuider productId(int id){
            productId = id;
            return this;
        }
        public ProductBuider name(String name){
            this.name = name;
            return this;
        }
        public ProductBuider imgUrl(String url){
            this.imgUrl = url;
            return this;
        }

        public ProductBuider tags(String tag){
            this.tags = tag;
            return this;
        }

        public Product buide() {
                return new Product(this);
        }
    }
    public String[] name(){
        return new String[]{"name",name};
    }
    public Object[] productId(){
        return new Object[]{"productId",productId};
    }

    public String[] imgUrl(){
        return new String[]{"imgUrl",imgUrl};
    }

    public String[] categories(){
        return new String[]{"categories",categories};
    }

    public String[] tags(){
        return new String[]{"tags",tags};
    }
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCategories() {
        return categories;
    }

    public String getTags() {
        return tags;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "{" +
                "productId:" + productId +
                ", name:'" + name + '\'' +
                ", imgUrl:'" + imgUrl + '\'' +
                ", categories:'" + categories + '\'' +
                ", tags:'" + tags + '\'' +
                '}';
    }
}
