package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.domain.User;
import com.coder.ecommerce.repository.UserRepository;
import com.coder.ecommerce.security.SecurityUtils;
import com.coder.ecommerce.service.FileService;
import com.coder.ecommerce.service.ProductService;
import com.coder.ecommerce.service.customReponse.ProductDetailsResponse;
import com.coder.ecommerce.service.customReponse.ProductResponse;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.coder.ecommerce.service.dto.ProductDTO;
import com.coder.ecommerce.service.dto.ProductCriteria;
import com.coder.ecommerce.service.ProductQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.coder.ecommerce.domain.Product}.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "product";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductService productService;

    private final ProductQueryService productQueryService;
    private final UserRepository userRepository;

    public ProductResource(ProductService productService, ProductQueryService productQueryService, FileService fileService, UserRepository userRepository) {
        this.productService = productService;
        this.productQueryService = productQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /products} : Create a new product.
     *
     * @param productDTO the productDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDTO, or with status {@code 400 (Bad Request)} if the product has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);

        if (productDTO.getUserId()==null){
            User user=new User();
            Optional<User> userOptional = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
            if (userOptional.isPresent()){
                user=userOptional.get();
            }
            System.out.println(">>>>>>>>>>"+user.getId());
            productDTO.setUserId(user.getId());
        }

        if (productDTO.getId() != null) {
            throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
        }

        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }




    /**
     * {@code PUT  /products} : Updates an existing product.
     *
     * @param productDTO the productDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDTO,
     * or with status {@code 400 (Bad Request)} if the productDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /products} : get all the products.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/productsList")
    public ResponseEntity<List<ProductResponse>> getAllProducts(ProductCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Products by criteria: {}", criteria);
        Page<ProductDTO> page = productQueryService.findByCriteria(criteria, pageable);



        List<ProductResponse> productResponse=new ArrayList<>();

        for (ProductDTO productDTO:page.getContent()) {

            ArrayList<String> colors=new ArrayList<>();

            ArrayList<String> styles=new ArrayList<>();

            ArrayList<String> size_details=new ArrayList<>();

            if (productDTO.getProductDetails().getColor()!=null){
                try{
                    JSONArray array = new JSONArray(productDTO.getProductDetails().getColor());
                    for(int i=0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        if (!object.getString("color").equals("")){
                            colors.add(object.getString("color"));
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            if (productDTO.getProductDetails().getStyle()!=null){
                try{
                    JSONArray array = new JSONArray(productDTO.getProductDetails().getStyle());
                    for(int i=0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        if (!object.getString("style").equals("")){
                            styles.add(object.getString("style"));
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            if (productDTO.getProductDetails().getSize_details()!=null){
                try{
                    JSONArray array = new JSONArray(productDTO.getProductDetails().getSize_details());
                    for(int i=0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        if (!object.getString("sizeDetails").equals("")){
                            size_details.add(object.getString("sizeDetails"));
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            ProductDetailsResponse productDetailsResponse=new ProductDetailsResponse();
            if (productDTO.getProductDetails()!=null){
               productDetailsResponse = new ProductDetailsResponse(
                    productDTO.getProductDetails().getId(),
                    productDTO.getProductDetails().getBrand(),
                    colors,
                    productDTO.getProductDetails().getGender(),
                    styles,
                    productDTO.getProductDetails().getSize_mesaurments(),
                   size_details
                );
            }
            productResponse.add(new ProductResponse(productDTO.getId(),productDTO.getName(),productDTO.getPrice(),productDTO.getImage(),productDTO.getSubCategoryId(),productDTO.getSubCategoryName(),productDTO.getCreatedBy(),productDTO.getCreatedDate(),productDTO.getLastModifiedBy(),productDTO.getLastModifiedDate(),productDTO.getProductTypeId(),productDTO.getDiscount_amount(),productDetailsResponse,productDTO.getQuantity(),productDTO.getUserId()));
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(productResponse);
    }

    /**
     * {@code GET  /products/count} : count all the products.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/products/count")
    public ResponseEntity<Long> countProducts(ProductCriteria criteria) {
        log.debug("REST request to count Products by criteria: {}", criteria);
        return ResponseEntity.ok().body(productQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /products/:id} : get the "id" product.
     *
     * @param id the id of the productDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/one-products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDTO> productDTO = productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDTO);
    }

    /**
     * {@code DELETE  /products/:id} : delete the "id" product.
     *
     * @param id the id of the productDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
