package com.groupware.erp.market.controller;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.service.EmployeeService;
import com.groupware.erp.market.model.MarketCategoryEntity;
import com.groupware.erp.market.model.MarketProductEntity;
import com.groupware.erp.market.model.ProductWithDiscountDTO;
import com.groupware.erp.market.repository.MarketEmployeeRepository;
import com.groupware.erp.market.service.MarketCartService;
import com.groupware.erp.market.service.MarketEmployeeService;
import com.groupware.erp.market.service.MarketProductService;
import com.groupware.erp.market.service.MarketCategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/market")
public class MarketController {
    @Autowired
    private MarketCategoryService marketCategoryService;
    @Autowired
    private MarketProductService marketProductService;
    @Autowired
    private EmployeeService employeeService;
    //카테고리 불러오기
    @GetMapping("/categories") // "/market/categories"에 대한 GET 요청
    public List<MarketCategoryEntity> findAll() {
        return marketCategoryService.findAll();
    }

    // 카테고리에 따른 상품 불러오기

    @GetMapping("/category/{categoryNo}")
    @ResponseBody
    public List<ProductWithDiscountDTO> getProductsByCategory(@PathVariable int categoryNo) {
        return marketProductService.findProductsByCategory(categoryNo);

    }


    // 개별 상품 불러오기
    @GetMapping("/product/{productNo}")
    @ResponseBody // 필요한 경우 이 어노테이션을 추가합니다.
    public ResponseEntity<ProductWithDiscountDTO> getProductByProductNo(@PathVariable int productNo) {
        try {
            ProductWithDiscountDTO product = marketProductService.findProductByProductNo(productNo);
            return ResponseEntity.ok(product);
        } catch (NoSuchElementException e) {
            log.error("Product not found for productNo: {}", productNo, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public String showMarket(Model model) {

        List<MarketCategoryEntity> categories = marketCategoryService.findAll();
        model.addAttribute("categories", marketCategoryService.findAll());
        return "/market/market";
    }

}
