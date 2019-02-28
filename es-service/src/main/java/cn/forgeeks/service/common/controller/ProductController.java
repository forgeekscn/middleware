package cn.forgeeks.service.common.controller;

import cn.forgeeks.service.common.dto.ProductDto;
import cn.forgeeks.service.common.dto.ProductQueryDto;
import cn.forgeeks.service.common.service.EsCommonService;
import cn.forgeeks.service.common.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/tmall")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    EsCommonService esCommonService;

    /**
     * scroll分页获取产品 筛选条件 日期 种类 关键字
     */
    @ResponseBody
    @RequestMapping(value = "/getProductsByScroll",method = RequestMethod.POST)
    public Map getProductsByPage(@RequestBody ProductQueryDto productQueryDto){
        // return productService.getProductsByScroll(productQueryDto);
        return productService.getProductsByTermAndMatch(productQueryDto);
    }

    /**
     * 批量写入数据
     */
    @ResponseBody
    @RequestMapping(value = "/insertProducts",method = RequestMethod.POST)
    public Map insertProducts(@RequestBody List<ProductDto> productDtoList){
        return productService.insertProducts(productDtoList);
    }


    /**
     * 批量写入10W数据-測試
     */
    @ResponseBody
    @RequestMapping(value = "/insertProductsForTest",method = RequestMethod.POST)
    public Map insertProducts(Integer num){
        return productService.insertProductsForTest(num);
    }

    /**
     * 批量刪除
     */
    @ResponseBody
    @RequestMapping(value = "/deleteProducts",method = RequestMethod.POST)
    public Map deleteProducts(@RequestBody  ProductDto productDto ){
        return productService.deleteProducts(productDto);
    }

    /**
     *
     * 批量更新
     */
    @ResponseBody
    @RequestMapping(value = "/updateProducts",method = RequestMethod.POST)
    public Map updateProducts(@RequestBody  ProductDto productDto ){
        return productService.updateProducts(productDto);
    }

    /**
     * 删除index
     */
    @ResponseBody
    @RequestMapping(value = "/dropProductIndex",method = RequestMethod.POST)
    public Map deleteIndex(String index){
        esCommonService.deleteIndex(index);
        Map map = new HashMap();
        map.put("msg","删除成功");
        return map;
    }

    /**
     * 清空index
     */
    @ResponseBody
    @RequestMapping(value = "/deleteProductIndex",method = RequestMethod.POST)
    public Map dropIndex(String index){
        esCommonService.dropIndex(index);
        Map map = new HashMap();
        map.put("msg","删除成功");
        return map;
    }

    /**
     * 刪除type
     */
    @ResponseBody
    @RequestMapping(value = "/deleteProductType",method = RequestMethod.POST)
    public Map deleteType(String index,String type){
        esCommonService.deleteIndexTypeAllData(index,type);
        Map map = new HashMap();
        map.put("msg","删除成功");
        return map;
    }


    /**
     * 商品详情
     */
    @ResponseBody
    @RequestMapping(value = "/product/get/{productId}",method = RequestMethod.GET)
    public Map getProductDetail(@PathVariable long productId ){
        return  productService.getProductDetail(productId);
    }

    /**
     * 更新商品信息
     */
    @ResponseBody
    @RequestMapping(value = "/product/set",method = RequestMethod.POST)
    public Map setProductDetail(@RequestBody ProductDto productDto ){
        return  productService.setProductDetail(productDto.getProductId());
    }

    /**
     * 下订单减库存
     */
    @ResponseBody
    @RequestMapping(value = "/order/{productId}",method = RequestMethod.GET)
    public Map createOrder(@PathVariable long productId ){
        return  productService.createOrder(productId);
    }

}
