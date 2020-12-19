package cs.roosevelt.onlineshop.service.impl;

import cs.roosevelt.onlineshop.model.Category;
import cs.roosevelt.onlineshop.model.User;
import cs.roosevelt.onlineshop.repository.CategoryRepository;
import cs.roosevelt.onlineshop.repository.ProductRepository;
import cs.roosevelt.onlineshop.repository.UserRepository;
import cs.roosevelt.onlineshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * CategoryServiceImpl defines the method signatures provided
 * by the CategoryService interface for the controller to use.
 *
 * Within each method, it uses methods from
 * the CategoryRepository.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    /**
     * The getAll() retrieves all the categories from the db.
     * @return All the categories.
     */
    @Override
    public ResponseEntity<List<Category>> getAll() {

        // return all the categories
        return new ResponseEntity<>(categoryRepository.findByOrderByCategoryTypeAsc(), HttpStatus.OK);

    }

    /**
     * The add() saves a new category to the db.
     * @param categoryToAdd
     * @return
     */
    @Override
    public ResponseEntity<String> add(Category categoryToAdd, HttpSession session) {

        // is there an active session?
        if (session != null && session.getAttribute("user") != null) {

            // get the user from the session
            User sessionUser = (User) session.getAttribute("user");

            // is the session user valid?
            if (sessionUser != null && userRepository.existsById(sessionUser.getId())) {

                // yes, the user is valid

                // is the valid user an admin?
                if (sessionUser.getRole().equals("ROLE_MANAGER")) {

                    // yes, the user's an admin

                    // find the category
                    Optional<Category> existingCategory = categoryRepository.findById(categoryToAdd.getId());

                    // does the category exist?
                    if (existingCategory.isPresent()) {

                        // yes, the category exists; return a denial response
                        return new ResponseEntity<>("Category already exists", HttpStatus.FOUND);

                    } else {

                        // no, the category doesn't exist; construct the category and return OK status

                        // make a product ID
                        Random rnd = new Random();
                        long n = 10000000 + rnd.nextInt(90000000);

                        // set the lower level details
                        categoryToAdd.setId(n);
                        categoryToAdd.setCreateTime(new Date());
                        categoryToAdd.setUpdateTime(new Date());

                        categoryRepository.save(categoryToAdd);

                        return new ResponseEntity<>("Category added", HttpStatus.OK);

                    }

                } else {

                    // no, the user is not an admin; deny the request
                    return new ResponseEntity<>("User is unauthorized", HttpStatus.UNAUTHORIZED);

                }

            } else {

                // no valid user found
                return new ResponseEntity<>("Current user not valid", HttpStatus.NOT_FOUND);

            }

        } else {

            // no, there's no active session; return denial response
            return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);

        }

    }
    
    /**
     * The add() saves a new category to the db.
     * @param categoryToAdd
     * @return
     */   
    @Override
    public ResponseEntity<String> save(Category categoryToSave, HttpSession session) {

        // is there an active session?
        if (session != null && session.getAttribute("user") != null) {

            // get the user from the session
            User sessionUser = (User) session.getAttribute("user");

            // is the session user valid?
            if (sessionUser != null && userRepository.existsById(sessionUser.getId())) {

                // yes, the user is valid

                // is the valid user an admin?
                if (sessionUser.getRole().equals("ROLE_MANAGER")) {

                    // yes, the user's an admin

                    // does the category exist already?
                	if(categoryToSave.getId() == null) {
                		 if (categoryRepository.findByName(categoryToSave.getName().trim()) == null) {

                             // no, it doesn't exist; construct and save the new category

                             // make a category ID
                             Random rnd = new Random();
                             long n = 10000000 + rnd.nextInt(90000000);

                             // set the new category's lower level details
                             categoryToSave.setId(n);
                             categoryToSave.setCreateTime(new Date());
                             categoryToSave.setUpdateTime(new Date());
                             
                             n = 1000 + rnd.nextInt(9000);
                             categoryToSave.setCategoryType((int)n);
                             // save and return an OK status
                             categoryRepository.save(categoryToSave);

                             return new ResponseEntity<>("Category added Successfully", HttpStatus.OK);

                         } else {
                             // yes, the category exists; return found status
                             return new ResponseEntity<>("Category Already Exists", HttpStatus.FOUND);
                         }
                    }else {
                		List<Category> categorys = categoryRepository.findAllByNameAndIdNot(categoryToSave.getName().trim(),categoryToSave.getId());
                		if(categorys !=null && categorys.size() >0) {
                			 return new ResponseEntity<>("Another Category Exisits with Same name", HttpStatus.FOUND);
                		}else {
                			  // save and return an OK status
                            categoryRepository.save(categoryToSave);
                            return new ResponseEntity<>("Category Updated Successfully", HttpStatus.OK);
                		}
                			
                	}
                   

                } else {

                    // no, the user is not an admin; deny the request
                    return new ResponseEntity<>("User is unauthorized", HttpStatus.UNAUTHORIZED);

                }

            } else {

                // no valid user found
                return new ResponseEntity<>("Current user not valid", HttpStatus.NOT_FOUND);

            }

        } else {

            // no, there's no active session; return denial response
            return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);

        }

    }

    /**
     * The update() updates an existing category in the db.
     * @param categoryToUpdate
     * @return
     */
    @Override
    public ResponseEntity<String> update(Category categoryToUpdate, HttpSession session) {

        // is there an active session?
        if (session != null && session.getAttribute("user") != null) {

            // get the user from the session
            User sessionUser = (User) session.getAttribute("user");

            // is the session user valid?
            if (sessionUser != null && userRepository.existsById(sessionUser.getId())) {

                // yes, the user is valid

                // is the valid user an admin?
                if (sessionUser.getRole().equals("ROLE_MANAGER")) {

                    // yes, the user's an admin

                    // find the category
                    Optional<Category> existingCategory = categoryRepository.findById(categoryToUpdate.getId());

                    // does the category exist?
                    if (existingCategory.isPresent()) {

                        // yes, the category exists; update the category and return OK status

                        categoryToUpdate.setUpdateTime(new Date());

                        categoryRepository.save(categoryToUpdate);

                        return new ResponseEntity<>("Category updated", HttpStatus.OK);

                    } else {

                        // no, the category doesn't exist; return denial response
                        return new ResponseEntity<>("Category does not exist", HttpStatus.NOT_FOUND);

                    }

                } else {

                    // no, the user is not an admin; deny the request
                    return new ResponseEntity<>("User is unauthorized", HttpStatus.UNAUTHORIZED);

                }

            } else {

                // no valid user found
                return new ResponseEntity<>("Current user not valid", HttpStatus.NOT_FOUND);

            }

        } else {

            // no, there's no active session; return denial response
            return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);

        }

    }

    /**
     * The delete() deletes an existing category from the db.
     * @param categoryId
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<String> delete(Long categoryId, HttpSession session) {

        // is there an active session?
        if (session != null && session.getAttribute("user") != null) {

            // get the user from the session
            User sessionUser = (User) session.getAttribute("user");

            // is the session user valid?
            if (sessionUser != null && userRepository.existsById(sessionUser.getId())) {
                Category cat = categoryRepository.findById(categoryId).orElse(new Category());

                // yes, the user is valid

                // is the valid user an admin?
                if (sessionUser.getRole().equals("ROLE_MANAGER")) {

                    // yes, the user's an admin

                    // does the category exist?
                    if (categoryRepository.existsById(categoryId)) {

                        // yes, it does exist; delete it and all products too
                        categoryRepository.deleteById(categoryId);
                        System.out.println("Total Products Deleted:"+productRepository.deleteByCategoryType(cat.getCategoryType()));
                        

                        // respond with confirmation
                        return new ResponseEntity<>("Category deleted", HttpStatus.OK);

                    } else {

                        // no, it doesn't exist; return a denial response
                        return new ResponseEntity<>("Category does not exist", HttpStatus.NOT_FOUND);

                    }

                } else {

                    // no, the user is not an admin; deny the request
                    return new ResponseEntity<>("User is unauthorized", HttpStatus.UNAUTHORIZED);

                }

            } else {

                // no valid user found
                return new ResponseEntity<>("Current user not valid", HttpStatus.NOT_FOUND);

            }

        } else {

            // no, there's no active session; return denial response
            return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);

        }

    }

    /**
     * The getOne() retrieves a category by the
     * given ID from the db.
     *
     * @param id
     * @return The user by ID.
     */
    @Override
    public ResponseEntity<Optional<Category>> getOne(String id) {

        // find the category
        Optional<Category> existingCategory = categoryRepository.findById(Long.parseLong(id));

        // did we find it?
        if (existingCategory.isPresent()) {

            // yes, we found it
            return new ResponseEntity<>(existingCategory, HttpStatus.OK);

        } else {
            // no, we didnt find it
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
}
