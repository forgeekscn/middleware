package cn.forgeeks.service.common.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String productDesc;
    private String productCategory;
    private Double productPrice;
    private String productMaster ;
    private String productShopName;
    private String productPic;
    private Boolean onSale;
    private Long saleNumber;
    private String createTime;
    private Long version;
}
