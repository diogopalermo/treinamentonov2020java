package com.accenture.training.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.training.domain.ProductsEntity;
import com.accenture.training.dto.ProductsTO;
import com.accenture.training.repository.ProductsRepository;

@Service
public class ProductsService {

	@Autowired
	ProductsRepository rep;

	@Autowired
	ModelMapper mapper;

	public List<ProductsTO> findAll(String keyword, boolean fuzzy) {
		List<ProductsEntity> result = null;
		
		if(Strings.isEmpty(keyword)){
			result = rep.findAll();
		}else {
			
			if(fuzzy){
				result = rep.findByKeywordWithFuzzy(keyword);
			}else{
				keyword = "%"+keyword+"%";
				result = rep.findByKeyword(keyword);
			}
		}
				
	
		
		return result.stream().map(item -> {
			
			
			return mapper.map(item, ProductsTO.class);
		}).collect(Collectors.toList());
	}

	public ProductsTO save(ProductsTO product) {
		
		ProductsEntity mappedEntity = mapper.map(product, ProductsEntity.class);
		ProductsEntity savedEntity = rep.save(mappedEntity);
		return mapper.map(savedEntity, ProductsTO.class);
	}

	public boolean delete(String id) {
		rep.deleteById(id);
		return true;
	}

	public ProductsTO findById(String id) {
		Optional<ProductsEntity> findById = rep.findById(id);
		if (findById.isPresent()) {
			return mapper.map(findById.get(), ProductsTO.class);
		}
		return null;
	}

}
