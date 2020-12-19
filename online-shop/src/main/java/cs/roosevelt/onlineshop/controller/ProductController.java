package cs.roosevelt.onlineshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cs.roosevelt.onlineshop.model.Product;
import cs.roosevelt.onlineshop.service.ProductService;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
    private ProductService productService;

    /**
     * fetch-all-products endpoint
     *
     * NOTE: Used only for debugging or admins; this
     * is not used in the angular project
     *
     * @param session The http session.
     * @return A list of all the products.
     */
    @GetMapping(value = {"/admin/allProducts", "/admin/allProducts/"})
    public ResponseEntity<List<Product>> fetchAllProducts(HttpSession session) {
        return productService.getAll(session);
    }

    /**
     * fetch-one-product endpoint
     * @param productId The ID of the product to fetch.
     * @return The product with given ID.
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<Optional<Product>> fetchProduct(@PathVariable("id") String productId) {
        return productService.getOne(productId);
    }

    /**
     * fetch-all-by-category endpoint
     * @param categoryType The category type to filter with when fetching all products.
     * @return A filtered-by-category-type list of products.
     */
    @GetMapping("/search/category/{categoryType}")
    public ResponseEntity<List<Product>> fetchAllProductsByCategory(@PathVariable("categoryType") Integer categoryType) {
        return productService.getAllByCategory(categoryType);
    }

    /**
     * fetch-all-containing-search-string endpoint
     * @param searchStr The search string to use to find the product(s).
     * @return The product(s) containing the given search string.
     */
    @GetMapping("/search/product/{searchStr}")
    public ResponseEntity<List<Product>> fetchAllProductsContainingSearchString(@PathVariable("searchStr") String searchStr) {
        return productService.getAllContainingSearchString(searchStr);
    }

    /**
     * add-product endpoint
     * @param productToAdd The product to add.
     * @param session The http session.
     * @return A confirmation or denial message.
     */
    @PostMapping(value = {"/add", "/add/"})
     public ResponseEntity<String>  addProduct(@RequestBody Product productToAdd, HttpSession session) {
        return productService.save(productToAdd, session);
    }

    /**
     * edit-product endpoint
     * @param productToUpdate The product to update.
     * @param session The http session.
     * @return A confirmation or denial message.
     */
    @PutMapping(value = {"/edit", "/edit/"})
    public ResponseEntity<String> editProduct(@RequestBody Product productToUpdate, HttpSession session) {
        return productService.update(productToUpdate, session);
    }

    /**
     * delete-product endpoint
     * @param productId The product ID of the product to delete.
     * @param session The http session.
     * @return A confirmation or denial message.
     */
    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String productId, HttpSession session) {
        return productService.delete(productId, session);
    }

}
