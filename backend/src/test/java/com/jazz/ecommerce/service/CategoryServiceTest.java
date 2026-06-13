package com.jazz.ecommerce.service;

import com.jazz.ecommerce.api.exception.ConflictException;
import com.jazz.ecommerce.api.exception.NotFoundException;
import com.jazz.ecommerce.dto.CategoryRequest;
import com.jazz.ecommerce.entity.Category;
import com.jazz.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void whenCreateCategory_withUniqueName_shouldReturnCategory() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        request.setName("Electronics");

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Category createdCategory = categoryService.createCategory(request);

        // Assert
        assertNotNull(createdCategory);
        assertEquals("Electronics", createdCategory.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void whenCreateCategory_withExistingName_shouldThrowConflictException() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        request.setName("Electronics");
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(new Category()));

        // Act & Assert
        assertThrows(ConflictException.class, () -> categoryService.createCategory(request));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void whenGetCategoryById_withExistingId_shouldReturnCategory() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        Category foundCategory = categoryService.getCategoryById(1L);

        // Assert
        assertNotNull(foundCategory);
        assertEquals(1L, foundCategory.getId());
    }

    @Test
    void whenGetCategoryById_withNonExistingId_shouldThrowNotFoundException() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> categoryService.getCategoryById(1L));
    }
}
