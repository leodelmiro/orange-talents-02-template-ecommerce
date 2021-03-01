package com.leodelmiro.mercadolivre.newproduct;

import com.leodelmiro.mercadolivre.newcategory.Category;
import com.leodelmiro.mercadolivre.newuser.CleanPassword;
import com.leodelmiro.mercadolivre.newuser.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.List;

public class ProductTest {

    @DisplayName("Não deve reduzir stock quando quantidade não for positiva")
    @ParameterizedTest
    @CsvSource({"0", "-1", "-100"})
    void reduceStockShouldThrowsIllegalArgumentExceptionWhenQuantityIsNotPositive(long quantity) {
        List<NewSpecificForm> specifics = List.of(new NewSpecificForm("caracteristica1", "descricao"),
                new NewSpecificForm("caracteristica2", "descricao"),
                new NewSpecificForm("caracteristica3", "descricao"));
        Category category = new Category("categoria");
        User owner = new User("leonardo@email.com", new CleanPassword("123456"));
        Product product = new Product("nome", BigDecimal.TEN, 10L, "descricao", category, owner, specifics);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            product.reduceStock(quantity);
        });
    }

    @DisplayName("Verifica estoque do produto")
    @ParameterizedTest
    @CsvSource({"1,1,true", "1,2,false", "4,2,true", "1,5,false"})
    void verifyReduceStock(long stock, long quantityRequested, boolean expectedResult) {
        List<NewSpecificForm> specifics = List.of(new NewSpecificForm("caracteristica1", "descricao"),
                new NewSpecificForm("caracteristica2", "descricao"),
                new NewSpecificForm("caracteristica3", "descricao"));
        Category category = new Category("categoria");
        User owner = new User("leonardo@email.com", new CleanPassword("123456"));
        Product product = new Product("nome", BigDecimal.TEN, stock, "descricao", category, owner, specifics);

        boolean result = product.reduceStock(quantityRequested);

        Assertions.assertEquals(expectedResult, result);
    }
}
