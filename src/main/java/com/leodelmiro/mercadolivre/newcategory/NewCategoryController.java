package com.leodelmiro.mercadolivre.newcategory;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class NewCategoryController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public CreatedCategoryDTO insert(@RequestBody @Valid NewCategoryForm newCategoryForm) {
        Category newCategory = newCategoryForm.toModel(entityManager);

        entityManager.persist(newCategory);

        return new CreatedCategoryDTO(newCategory);
    }
}
