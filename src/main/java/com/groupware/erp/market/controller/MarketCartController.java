package com.groupware.erp.market.controller;


import com.groupware.erp.market.model.MarketCartEntity;
import com.groupware.erp.market.service.MarketCartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/market/cart")
public class MarketCartController {
    @Autowired
    private MarketCartService marketCartService;

    //카트 불러오기
    @GetMapping("/read")
    public ResponseEntity<List<MarketCartEntity>> getCart(@RequestParam String empNo) {
        List<MarketCartEntity> cartItems = marketCartService.getCart(empNo);
        log.info("아 왜 안되냐고@!!"+cartItems.toString());
        return ResponseEntity.ok(cartItems);
    }

    //카트 항목 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCartItem(@RequestParam String empNo, @RequestParam int productNo) {
        marketCartService.deleteCartItem(empNo, productNo);
        return ResponseEntity.ok("Cart item deleted successfully.");
    }

    //카트 항목 등록
    @PutMapping("/update")
    public ResponseEntity<MarketCartEntity> addToCart(@RequestParam String empNo, @RequestParam int productNo, @RequestParam int productAmount, @RequestParam String name, @RequestParam int price) {
        // 카트에 새로운 항목을 추가하고 그 결과를 반환받습니다.
        MarketCartEntity addedCart = marketCartService.addToCart(empNo, productNo, productAmount,name ,price);

        return ResponseEntity.ok(addedCart);
    }
    @GetMapping
    public String showCart() {
        return "/market/cart";
    }
}
