package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Cart;
import com.swp_project_g4.Model.Instruct;
import com.swp_project_g4.Repository.CartRepository;
import com.swp_project_g4.Repository.InstructRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Component("CartService")
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Optional<Cart> findByCourseIdAndLearnerId(int courseId, int learnerId) {
        return cartRepository.findByCourseIdAndLearnerId(courseId, learnerId);
    }

    public List<Cart> findAllByLearnerId(int learnerId) {
        return cartRepository.findAllByLearnerId(learnerId);
    }

    public void deleteByCourseIdAndLearnerId(int courseId, int learnerId) {
        cartRepository.deleteByCourseIdAndLearnerId(courseId, learnerId);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
}
