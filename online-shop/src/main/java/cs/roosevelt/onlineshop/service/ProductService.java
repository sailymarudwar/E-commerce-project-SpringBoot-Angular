package cs.roosevelt.onlineshop.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import cs.roosevelt.onlineshop.model.Product;

/**
 * The methods that must be implemented. Typically,
 * by a class of the same name with an 'Impl' suffix.
 *
 * Used for abstraction.
 */
public interface ProductService {

    ResponseEntity<List<Product>> getAll(HttpSession session);

    ResponseEntity<Optional<Product>> getOne(String productId);

    ResponseEntity<List<Product>> getAllByCategory(Integer categoryType);

    ResponseEntity<List<Product>> getAllContainingSearchString(String searchStr);

    public ResponseEntity<String>  save(Product productToSave, HttpSession session);

    ResponseEntity<String> update(Product productToUpdate, HttpSession session);

    ResponseEntity<String> delete(String productId, HttpSession session);

}
