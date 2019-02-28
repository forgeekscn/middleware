package cn.forgeeks.service.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductQueryDto {
    private Long productId;
    private String productName;
    private String productDesc;
    private String productCategory;
    private Double productPrice;
    private Boolean onSale;
    private String saleNumber;
    private Date createTime;
    private String productMaster ;
    private String productShopName;

    private String productPic;

    private Integer pageNo = 1;
    private Integer pageSize = 20;

    private String keyword;
    private Integer days;

}
