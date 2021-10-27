package com.accenture.webshop.web.controller;

import com.accenture.webshop.model.ProductItem;
import com.accenture.webshop.service.ProductService;
import com.accenture.webshop.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Validated
@Controller
@RequestMapping("/products")
public class ProductController {

    @Value("${services.productListHtml}")
    private String productListHtml;
    @Value("${services.productAddHtml}")
    private String productAddHtml;
    @Value("${services.productEditDelete}")
    private String productEditDelete;
    @Value("${services.notFound}")
    private String notFound;

    private static final String FLASH_ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String FLASH_ATTR_SUCCESS_MESSAGE = "successMessage";

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllProducts(Model model) {
        List<ProductItem> products = service.findAllProducts();
        model.addAttribute("products", products);
        return productListHtml;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductById(@PathVariable @Min(value = 0L, message = "The value must be positive") Long id, Model model) {
        try {
            Optional<ProductItem> productItem = service.getProductById(id);
            ProductItem product = productItem.get();
            model.addAttribute("productItem", productItem.get());
            model.addAttribute("id", id);
            return productEditDelete;
        } catch (Exception e) {
            return notFound;
        }
    }

    @GetMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductAddPage(Model model) {
        model.addAttribute("productItem", ProductItem.builder().build());
        return productAddHtml;
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addProduct(@ModelAttribute("productItem") @Validated ProductItem productItem, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(FLASH_ATTR_ERROR_MESSAGE, Objects.requireNonNull(bindingResult.getFieldError().getDefaultMessage()));
            return "redirect:" + "/products/add/";
        }
        service.saveProduct(productItem);
        redirectAttributes.addFlashAttribute(FLASH_ATTR_SUCCESS_MESSAGE, "Product successfully added");
        return "redirect:" + "/products/add/";
    }

    @PostMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String editProductById(@PathVariable("id") Long id, @ModelAttribute("productItem") @Validated ProductItem productItem,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute(FLASH_ATTR_ERROR_MESSAGE, bindingResult.getFieldError().getDefaultMessage());
                return "redirect:" + "/products/{id}/";
            }
            productItem.setId(id);
            service.updateProductById(id, productItem);
            redirectAttributes.addFlashAttribute(FLASH_ATTR_SUCCESS_MESSAGE, "Successfully updated");
            return "redirect:" + "/products/";
        } catch (Exception e) {
            return notFound;
        }
    }

    @PostMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteById(@PathVariable("id") Long id) {
        service.deleteProductById(id);
        return "redirect:" + "/products/";
    }
}
