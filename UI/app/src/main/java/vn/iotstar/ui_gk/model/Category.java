package vn.iotstar.ui_gk.model;

import java.util.List;

public class Category {

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCateImage() {
        return cateImage;
    }

    public void setCateImage(String cateImage) {
        this.cateImage = cateImage;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private int categoryId;

    public Category(List<Product> products, String cateImage, String categoryName, int categoryId) {
        this.products = products;
        this.cateImage = cateImage;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    private String categoryName;

    private String cateImage;

    List<Product> products;


}
