package vn.iotstar.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.backend.entity.Category;
import vn.iotstar.backend.entity.Product;
import vn.iotstar.backend.repository.CategoryRepository;
import vn.iotstar.backend.repository.ProductRepository;

import java.util.List;

@Service
//HO Vu THanh Binh 22110287
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> findByCategoryAndOrderByPrice(Long categoryId, int page){
        int size = 6;
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAllByCategory_CategoryIdOrderByPriceAsc(categoryId, pageable);
    }
}
