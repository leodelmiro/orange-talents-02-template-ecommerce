package com.leodelmiro.mercadolivre.newquestion;

import com.leodelmiro.mercadolivre.common.validation.ProductNotFoundException;
import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class NewQuestionController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Emails emails;

    @PostMapping("/{id}/questions")
    @Transactional
    public List<QuestionDTO> insert(@AuthenticationPrincipal User loggedUser,
                                    @PathVariable Long id, @RequestBody @Valid NewQuestionForm newQuestionForm) {

        Product product = entityManager.find(Product.class, id);
        if (product == null) throw new ProductNotFoundException("Produto não encontrado!");

        Question newQuestion = newQuestionForm.toModel(product, loggedUser);
        entityManager.persist(newQuestion);

        emails.newQuestion(newQuestion);

        List<Question> list = product.getQuestions();
        return list.stream().map(QuestionDTO::new).collect(Collectors.toList());
    }
}
