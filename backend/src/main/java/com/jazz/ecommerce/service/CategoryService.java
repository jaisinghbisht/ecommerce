package com.jazz.ecommerce.service;

import com.jazz.ecommerce.api.exception.ConflictException;
import com.jazz.ecommerce.api.exception.NotFoundException;
import com.jazz.ecommerce.dto.CategoryRequest;
import com.jazz.ecommerce.entity.Category;
import com.jazz.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with ID " + id + " not found"));
    }

    @Transactional
    public Category createCategory(CategoryRequest categoryRequest) {
        categoryRepository.findByName(categoryRequest.getName()).ifPresent(c -> {
            throw new ConflictException("Category with name '" + categoryRequest.getName() + "' already exists.");
        });

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = getCategoryById(id);

        // Check if the new name is already taken by another category
        categoryRepository.findByName(categoryRequest.getName()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ConflictException("Category with name '" + categoryRequest.getName() + "' already exists.");
            }
        });

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with ID " + id + " not found");
        }
        // The foreign key constraint `ON DELETE SET NULL` will handle updating associated products
        categoryRepository.deleteById(id);
    }
}
