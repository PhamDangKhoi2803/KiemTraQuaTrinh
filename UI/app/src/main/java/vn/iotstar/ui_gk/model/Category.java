package vn.iotstar.ui_gk.model;

import java.util.List;

public class Category {

    private int cateId;

    private String cateName;

    private String cateImage;

    List<Product> listProduct;

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }


    public Category(int cateId, String cateName, List<Product> listProduct,String cateImage) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.listProduct = listProduct;
        this.cateImage=cateImage;
    }

    public String getCateImage() {
        return cateImage;
    }

    public void setCateImage(String cateImage) {
        this.cateImage = cateImage;
    }

}
