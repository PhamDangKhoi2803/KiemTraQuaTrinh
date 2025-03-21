package vn.iotstar.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.backend.entity.Category;
import vn.iotstar.backend.repository.CategoryRepository;

import java.util.List;

//PhamDangKhoi 22110357
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findByCategoryNameContaining(String categoryName) {
        return categoryRepository.findByCategoryNameContaining(categoryName);
    }

}
