package com.groupware.erp.market.service;

import com.groupware.erp.market.model.MarketDiscountEntity;
import com.groupware.erp.market.model.MarketProductEntity;
import com.groupware.erp.market.repository.MarketDiscountRepository;
import com.groupware.erp.market.repository.MarketProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupware.erp.market.model.ProductWithDiscountDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MarketProductService {

    @Autowired
    private MarketProductRepository marketProductRepository;
    @Autowired
    private MarketDiscountRepository marketDiscountRepository;

    // 상품 풀러오기
    public List<ProductWithDiscountDTO> findProductsByCategory(int categoryNo) {
        List<MarketProductEntity> products = marketProductRepository.findByCategoryNo(categoryNo);
        List<ProductWithDiscountDTO> productsWithDiscount = new ArrayList<>();

        for (MarketProductEntity product : products) {
            MarketDiscountEntity discount = marketDiscountRepository.findByProductNo(product.getProductNo());

            // int형 가격을 BigDecimal로 변환
            BigDecimal price = BigDecimal.valueOf(product.getPrice()); // 제품 가격
            // 할인율 가져오기
            BigDecimal discountRate = (discount != null && discount.getDiscount_rate() != null) ?
                    discount.getDiscount_rate() : BigDecimal.ZERO; // 할인율

            // 할인된 가격 계산
            BigDecimal discountedPrice = price.subtract(price.multiply(discountRate));

            // ProductWithDiscountDTO 객체를 생성하여 목록에 추가
            productsWithDiscount.add(new ProductWithDiscountDTO(product, discountedPrice));
        }

        return productsWithDiscount;
    }

    // 개별 상품 조회
    public ProductWithDiscountDTO findProductByProductNo(int productNo) {
        // 상품을 조회
        MarketProductEntity product = marketProductRepository.findByProductNo(productNo);

        if (product == null) {
            throw new NoSuchElementException("Product not found with productNo: " + productNo);
        }

        // 해당 상품에 대한 할인 정보를 조회
        MarketDiscountEntity discount = marketDiscountRepository.findByProductNo(product.getProductNo());

        // 가격을 BigDecimal로 변환
        BigDecimal price = BigDecimal.valueOf(product.getPrice());

        // 할인율 적용
        BigDecimal discountRate = (discount != null && discount.getDiscount_rate() != null) ?
                discount.getDiscount_rate() : BigDecimal.ZERO;

        // 할인된 가격 계산
        BigDecimal discountedPrice = price.subtract(price.multiply(discountRate));

        // ProductWithDiscountDTO 객체로 반환
        return new ProductWithDiscountDTO(product, discountedPrice);
    }
    // 개별 상품 등록
    public MarketProductEntity saveProduct(MarketProductEntity product) {
        return marketProductRepository.save(product); // save() 메서드는 insert 및 update 기능을 모두 수행
    }
    // 상품 수정
    public Optional<MarketProductEntity> updateProduct(int productNo, MarketProductEntity updatedProduct) {
        return marketProductRepository.findById(productNo).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setImg(updatedProduct.getImg());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setCategoryNo(updatedProduct.getCategoryNo()); // 카테고리 번호도 포함
            return marketProductRepository.save(existingProduct);
        });
    }
    public void deleteProduct(int id) {
        marketProductRepository.deleteById(id); // JPA의 deleteById 메서드 사용
    }
}
