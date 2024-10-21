package com.groupware.erp.market.controller;

import com.groupware.erp.market.model.MarketCategoryEntity;
import com.groupware.erp.market.model.MarketProductEntity;
import com.groupware.erp.market.model.ProductWithDiscountDTO;
import com.groupware.erp.market.service.MarketCategoryService;
import com.groupware.erp.market.service.MarketProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/market")
public class AdminMarketController {
    //관리자인지 로직 추가 요망 //

    @Autowired
    private MarketCategoryService marketCategoryService;
    @Autowired
    private MarketProductService marketProductService;

    // 카테고리 불러오기
    @GetMapping("/category/read")
    public List<MarketCategoryEntity> findAll() {
        return marketCategoryService.findAll();
    }
    // 카테고리 추가 또는 업데이트
    @PostMapping("/category/create")
    public ResponseEntity<MarketCategoryEntity> saveOrUpdate(@RequestBody MarketCategoryEntity category) {
        MarketCategoryEntity savedCategory = marketCategoryService.saveOrUpdate(category);
        return ResponseEntity.ok(savedCategory); // 성공 응답
    }

    // 카테고리 삭제
    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        marketCategoryService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content 반환
    }

    // 카테고리 이름 변경
    @PutMapping("/category/update/{id}")
    public MarketCategoryEntity updateCategoryName(@PathVariable int id, @RequestBody String newName) {
        return marketCategoryService.updateCategoryName(id, newName);
    }

    // 카테고리 순서 변경
    @PutMapping("/categories/order")
    public ResponseEntity<Void> updateCategoryOrder(@RequestParam int currentOrderNo, @RequestParam int newOrderNo) {
        marketCategoryService.changeCategoryOrder(currentOrderNo, newOrderNo);
        return ResponseEntity.ok().build(); // 200 OK
    }

    // 카테고리에 따른 상품 불러오기
    @GetMapping("/category/{categoryNo}")
    public List<ProductWithDiscountDTO> getProductsByCategory(@PathVariable int categoryNo) {
        return marketProductService.findProductsByCategory(categoryNo);
    }
    // 개별 상품 불러오기
    @GetMapping("/product/{productNo}")
    public ProductWithDiscountDTO getProductByProductNo(@PathVariable int productNo) {
        return marketProductService.findProductByProductNo(productNo);
    }
    // 상품 등록
    @PostMapping("/product/create")
    public MarketProductEntity createProduct(@RequestBody MarketProductEntity product) {
        return marketProductService.saveProduct(product);
    }
    // 상품 수정
    @PutMapping("/product/update/{productNo}")
    public ResponseEntity<MarketProductEntity> updateProduct(
            @PathVariable int productNo,
            @RequestBody MarketProductEntity updatedProduct) {
        return marketProductService.updateProduct(productNo, updatedProduct)
                .map(ResponseEntity::ok) // 수정된 상품 반환
                .orElse(ResponseEntity.notFound().build()); // 상품이 없을 경우 404 Not Found 반환
    }
    // 상품 제거
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        marketProductService.deleteProduct(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping
    public String showMarketAdmin(Model model) {
        List<MarketCategoryEntity> categories = marketCategoryService.findAll();
        model.addAttribute("categories", categories);
        return "/market/adminMarket";
    }
}
