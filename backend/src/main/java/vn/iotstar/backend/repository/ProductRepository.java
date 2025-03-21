package vn.iotstar.backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.backend.entity.Product;

import java.util.List;
//HO Vu THanh Binh 22110287

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_CategoryIdOrderByPriceAsc(Long id, Pageable pageable);
}
