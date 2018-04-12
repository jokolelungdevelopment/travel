package com.jokolelung.travel.web.rest;

import com.jokolelung.travel.TravelApp;

import com.jokolelung.travel.domain.Product;
import com.jokolelung.travel.repository.ProductRepository;
import com.jokolelung.travel.service.ProductService;
import com.jokolelung.travel.service.dto.ProductDTO;
import com.jokolelung.travel.service.mapper.ProductMapper;
import com.jokolelung.travel.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jokolelung.travel.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jokolelung.travel.domain.enumeration.Size;
/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelApp.class)
public class ProductResourceIntTest {

    private static final String DEFAULT_PRODUCTNAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SPESIALTREATMENT = false;
    private static final Boolean UPDATED_SPESIALTREATMENT = true;

    private static final Boolean DEFAULT_FRAGILE = false;
    private static final Boolean UPDATED_FRAGILE = true;

    private static final Long DEFAULT_PRODUCTWEIGHT = 1L;
    private static final Long UPDATED_PRODUCTWEIGHT = 2L;

    private static final Size DEFAULT_PRODUCTSIZE = Size.SMALL;
    private static final Size UPDATED_PRODUCTSIZE = Size.MEDIUM;

    private static final Double DEFAULT_PRODUCTPRICE = 1D;
    private static final Double UPDATED_PRODUCTPRICE = 2D;

    private static final Double DEFAULT_PRUDUCTTIP = 1D;
    private static final Double UPDATED_PRUDUCTTIP = 2D;

    private static final Double DEFAULT_ADDITIONALCHARGE = 1D;
    private static final Double UPDATED_ADDITIONALCHARGE = 2D;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .productname(DEFAULT_PRODUCTNAME)
            .description(DEFAULT_DESCRIPTION)
            .brand(DEFAULT_BRAND)
            .url(DEFAULT_URL)
            .quantity(DEFAULT_QUANTITY)
            .spesialtreatment(DEFAULT_SPESIALTREATMENT)
            .fragile(DEFAULT_FRAGILE)
            .productweight(DEFAULT_PRODUCTWEIGHT)
            .productsize(DEFAULT_PRODUCTSIZE)
            .productprice(DEFAULT_PRODUCTPRICE)
            .pruducttip(DEFAULT_PRUDUCTTIP)
            .additionalcharge(DEFAULT_ADDITIONALCHARGE)
            .total(DEFAULT_TOTAL);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductname()).isEqualTo(DEFAULT_PRODUCTNAME);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProduct.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.isSpesialtreatment()).isEqualTo(DEFAULT_SPESIALTREATMENT);
        assertThat(testProduct.isFragile()).isEqualTo(DEFAULT_FRAGILE);
        assertThat(testProduct.getProductweight()).isEqualTo(DEFAULT_PRODUCTWEIGHT);
        assertThat(testProduct.getProductsize()).isEqualTo(DEFAULT_PRODUCTSIZE);
        assertThat(testProduct.getProductprice()).isEqualTo(DEFAULT_PRODUCTPRICE);
        assertThat(testProduct.getPruducttip()).isEqualTo(DEFAULT_PRUDUCTTIP);
        assertThat(testProduct.getAdditionalcharge()).isEqualTo(DEFAULT_ADDITIONALCHARGE);
        assertThat(testProduct.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productname").value(hasItem(DEFAULT_PRODUCTNAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.toString())))
            .andExpect(jsonPath("$.[*].spesialtreatment").value(hasItem(DEFAULT_SPESIALTREATMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].fragile").value(hasItem(DEFAULT_FRAGILE.booleanValue())))
            .andExpect(jsonPath("$.[*].productweight").value(hasItem(DEFAULT_PRODUCTWEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productsize").value(hasItem(DEFAULT_PRODUCTSIZE.toString())))
            .andExpect(jsonPath("$.[*].productprice").value(hasItem(DEFAULT_PRODUCTPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].pruducttip").value(hasItem(DEFAULT_PRUDUCTTIP.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalcharge").value(hasItem(DEFAULT_ADDITIONALCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productname").value(DEFAULT_PRODUCTNAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.toString()))
            .andExpect(jsonPath("$.spesialtreatment").value(DEFAULT_SPESIALTREATMENT.booleanValue()))
            .andExpect(jsonPath("$.fragile").value(DEFAULT_FRAGILE.booleanValue()))
            .andExpect(jsonPath("$.productweight").value(DEFAULT_PRODUCTWEIGHT.intValue()))
            .andExpect(jsonPath("$.productsize").value(DEFAULT_PRODUCTSIZE.toString()))
            .andExpect(jsonPath("$.productprice").value(DEFAULT_PRODUCTPRICE.doubleValue()))
            .andExpect(jsonPath("$.pruducttip").value(DEFAULT_PRUDUCTTIP.doubleValue()))
            .andExpect(jsonPath("$.additionalcharge").value(DEFAULT_ADDITIONALCHARGE.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findOne(product.getId());
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .productname(UPDATED_PRODUCTNAME)
            .description(UPDATED_DESCRIPTION)
            .brand(UPDATED_BRAND)
            .url(UPDATED_URL)
            .quantity(UPDATED_QUANTITY)
            .spesialtreatment(UPDATED_SPESIALTREATMENT)
            .fragile(UPDATED_FRAGILE)
            .productweight(UPDATED_PRODUCTWEIGHT)
            .productsize(UPDATED_PRODUCTSIZE)
            .productprice(UPDATED_PRODUCTPRICE)
            .pruducttip(UPDATED_PRUDUCTTIP)
            .additionalcharge(UPDATED_ADDITIONALCHARGE)
            .total(UPDATED_TOTAL);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductname()).isEqualTo(UPDATED_PRODUCTNAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testProduct.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.isSpesialtreatment()).isEqualTo(UPDATED_SPESIALTREATMENT);
        assertThat(testProduct.isFragile()).isEqualTo(UPDATED_FRAGILE);
        assertThat(testProduct.getProductweight()).isEqualTo(UPDATED_PRODUCTWEIGHT);
        assertThat(testProduct.getProductsize()).isEqualTo(UPDATED_PRODUCTSIZE);
        assertThat(testProduct.getProductprice()).isEqualTo(UPDATED_PRODUCTPRICE);
        assertThat(testProduct.getPruducttip()).isEqualTo(UPDATED_PRUDUCTTIP);
        assertThat(testProduct.getAdditionalcharge()).isEqualTo(UPDATED_ADDITIONALCHARGE);
        assertThat(testProduct.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId(2L);
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDTO.class);
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(1L);
        ProductDTO productDTO2 = new ProductDTO();
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO2.setId(productDTO1.getId());
        assertThat(productDTO1).isEqualTo(productDTO2);
        productDTO2.setId(2L);
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO1.setId(null);
        assertThat(productDTO1).isNotEqualTo(productDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productMapper.fromId(null)).isNull();
    }
}
