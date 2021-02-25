package com.leodelmiro.mercadolivre.newcategory;

import com.leodelmiro.mercadolivre.utils.builders.CategoryBuilder;
import com.leodelmiro.mercadolivre.utils.builders.NewCategoryFormBuilder;
import com.leodelmiro.mercadolivre.common.validation.CategoryNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class NewCategoryFormTest {

    @Mock
    EntityManager entityManager;


    @Test
    @DisplayName("To model Deveria retornar objecto category quando tudo Ok")
    public void toModelShouldReturnCategoryWhenMotherCategoryNotNullAndValid() {
        Category motherCategory = new CategoryBuilder().withName("Jogos").withMotherCategory(null).build();
        NewCategoryForm newCategoryForm = new NewCategoryFormBuilder().withName("Cartas").withMotherCategoryId(2L).build();

        when(entityManager.find(Category.class, 2L)).thenReturn(motherCategory);
        Category expected = newCategoryForm.toModel(entityManager);

        assertNotNull(expected);
        assertEquals(expected.getName(), "Cartas");
        assertEquals(expected.getMotherCategory(), motherCategory);

        verify(entityManager, Mockito.times(1)).find(Category.class, 2L);
    }

    @Test
    @DisplayName("To model Deveria retornar objecto category quando tudo Ok")
    public void toModelShouldReturnCategoryWhenValidAndMotherCategoryNull() {
        NewCategoryForm newCategoryForm = new NewCategoryFormBuilder().withName("Cartas").withMotherCategoryId(null).build();

        Category expected = newCategoryForm.toModel(entityManager);

        assertNotNull(expected);
        assertEquals(expected.getName(), "Cartas");
        assertNull(expected.getMotherCategory());

        verify(entityManager, Mockito.times(0)).find(Category.class, eq(anyLong()));
    }

    @Test
    @DisplayName("To Model Deveria lançar Category Not Found Exception com Id de categoria mão não existente ")
    public void toModelShouldThrowsCategoryNotFoundExceptionWhenMotherCategoryIdInvalid() {
        NewCategoryForm newCategoryForm = new NewCategoryFormBuilder().withName("Geral").withMotherCategoryId(30L).build();

        when(entityManager.find(Category.class, 30L)).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> newCategoryForm.toModel(entityManager));

        verify(entityManager, Mockito.times(1)).find(Category.class, 30L);
    }
}
