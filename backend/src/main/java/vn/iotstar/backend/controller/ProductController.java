package vn.iotstar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.backend.entity.Product;
import vn.iotstar.backend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
//HO Vu THanh Binh 22110287
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/{id}")
    public ResponseEntity<List<Product>> findAllByCategory_CategoryIdOrderByPriceAsc(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findByCategoryAndOrderByPrice(id));
    }
}
