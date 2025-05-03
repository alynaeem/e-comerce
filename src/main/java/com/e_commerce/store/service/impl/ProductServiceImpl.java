package com.e_commerce.store.service.impl;

import com.e_commerce.store.dto.ProductDTO;
import com.e_commerce.store.mapper.ProductMapper;
import com.e_commerce.store.model.Product;
import com.e_commerce.store.repository.ProductRepository;
import com.e_commerce.store.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        product = productRepository.save(product);
        return productMapper.productToProductDTO(product);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return productMapper.productToProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        product = productRepository.save(product);
        return productMapper.productToProductDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().
                map(productMapper::productToProductDTO).toList();
    }
}
