package com.ecommerce.api.config;

import com.ecommerce.api.entity.*;
import com.ecommerce.api.model.enums.Role;
import com.ecommerce.api.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, 
                           CategoryRepository categoryRepository, 
                           ProductRepository productRepository, 
                           CartRepository cartRepository, 
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // 1. Create Admin if not exists
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = User.builder()
                    .name("Admin User")
                    .email("admin@example.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
            log.info(">>> Admin account 'admin@example.com' created.");
        }

        // 2. Create User if not exists
        if (!userRepository.existsByEmail("user@example.com")) {
            User user = User.builder()
                    .name("Default User")
                    .email("user@example.com")
                    .passwordHash(passwordEncoder.encode("user123"))
                    .role(Role.ROLE_USER)
                    .build();
            User savedUser = userRepository.save(user);

            Cart cart = Cart.builder().user(savedUser).build();
            cartRepository.save(cart);
            log.info(">>> User account 'user@example.com' created.");
        }

        // 3. Create Categories & Products if electronics not exists
        if (!categoryRepository.existsByName("Electronics")) {
            Category electronics = Category.builder().name("Electronics").description("Gadgets and devices").build();
            Category fashion = Category.builder().name("Fashion").description("Clothing and accessories").build();
            
            categoryRepository.saveAll(Arrays.asList(electronics, fashion));

            Product laptop = Product.builder()
                    .name("Gaming Laptop")
                    .description("High performance gaming laptop")
                    .price(new BigDecimal("1500.00"))
                    .stockQuantity(10)
                    .category(electronics)
                    .build();

            Product smartphone = Product.builder()
                    .name("iPhone 15")
                    .description("Latest apple smartphone")
                    .price(new BigDecimal("999.00"))
                    .stockQuantity(20)
                    .category(electronics)
                    .build();

            productRepository.saveAll(Arrays.asList(laptop, smartphone));
            log.info(">>> Sample categories and products created.");
        }
    }
}
