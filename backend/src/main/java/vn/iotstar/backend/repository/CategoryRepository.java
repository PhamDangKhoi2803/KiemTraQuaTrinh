package vn.iotstar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.backend.entity.Category;

import java.util.List;

//PhamDangKhoi 22110357
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByCategoryNameContaining(String categoryName);
}
