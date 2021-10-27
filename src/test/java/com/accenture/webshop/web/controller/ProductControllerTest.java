package com.accenture.webshop.web.controller;

import com.accenture.webshop.model.ProductItem;
import com.accenture.webshop.service.ProductService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private static final String URL = "/products";
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private ProductController controller;
    @MockBean
    private ProductService service;
    @MockBean
    Model model;

    @Before
    void init () {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getProductList() throws Exception {
        when(service.findAllProducts()).thenReturn(createProductItemList());
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/productList.html"))
                .andExpect(model().attribute("products", service.findAllProducts()));
    }

    @Test
    void getByIdTest() throws Exception {
        ProductItem productItem = createProductItemById(5L);
        when(service.getProductById(anyLong())).thenReturn(of(productItem));

        mockMvc.perform(get("/products/" + 5L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("products/productEditDelete.html"));
    }

    @Test
    void getByIdTestInvalid() throws Exception {
        when(service.getProductById(anyLong()))
                .thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/" + null)
                .flashAttr("errorMessage", "not"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testSaveProduct() throws Exception{
        ProductItem model = ProductItem.builder()
                .name("product")
                .category("Milk")
                .price(BigDecimal.valueOf(9))
                .build();
        when(service.saveProduct(any())).thenReturn(model);

        mockMvc.perform(post("/products/add", model).flashAttr("productItem", model))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("successMessage", "Product successfully added"))
                .andExpect(redirectedUrl("/products/add/"));
    }

    @Test
    void testSaveProductUnsuccess() throws Exception{
        ProductItem model = ProductItem.builder()
                .name("product")
                .price(BigDecimal.valueOf(9))
                .build();
        when(service.saveProduct(any())).thenReturn(model);

        mockMvc.perform(post("/products/add", model).flashAttr("productItem", model))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errorMessage", "Enter product category"))
                .andExpect(redirectedUrl("/products/add/"));
    }

    @Test
    void deleteByIdTest() throws Exception {
        ProductItem productItem = createProductItemById(21L);
        when(service.getProductById(anyLong())).thenReturn(Optional.of(productItem));
        mockMvc.perform(post("/products/delete/" + 21L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/"));
    }

    @Test
    void updateByIdTest() throws Exception {
        ProductItem model = ProductItem.builder()
                .name("product")
                .category("Milk")
                .price(BigDecimal.valueOf(9))
                .build();

        when(service.getProductById(any())).thenReturn(Optional.of(model));
        mockMvc.perform(post("/products/" + 21L).flashAttr("productItem", model))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("successMessage", "Successfully updated"))
                .andExpect(redirectedUrl("/products/"));
    }

    @Test
    void updateByIdTestUnsuccessful() throws Exception {
        ProductItem model = ProductItem.builder()
                .name("product")
                .category("Milk")
                .price(BigDecimal.valueOf(9))
                .build();
        ProductItem updateModel = ProductItem.builder()
                .category("Milk")
                .build();

        when(service.getProductById(any())).thenReturn(Optional.of(model));
        mockMvc.perform(post("/products/" + 21L).flashAttr("productItem", updateModel))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errorMessage", "Enter product name"))
                .andExpect(redirectedUrl("/products/" + 21L + "/"));
    }

    private ProductItem createProductItemById(Long id) {
        return ProductItem.builder()
                .id(id)
                .name("test")
                .category("Milk")
                .price(BigDecimal.ZERO)
                .build();
    }

    private List<ProductItem> createProductItemList() {
        return Collections.singletonList(ProductItem.builder()
                .id(1L)
                .name("product")
                .category("Milk")
                .price(BigDecimal.valueOf(10.10))
                .build());
    }
}