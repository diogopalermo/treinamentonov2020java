package com.accenture.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accenture.training.domain.ProductsEntity;

public interface ProductsRepository extends JpaRepository<ProductsEntity, String>{
	
	
	public List<ProductsEntity> findByManufacturerOrderByNameDesc(String namufacturer);
	public List<ProductsEntity> findByManufacturerAndNameOrderByNameDesc(String namufacturer, String name);
	
	
	@Query("Select k from ProductsEntity as k where upper(k.name) like :keyword or k.manufacturer like :keyword")
	public List<ProductsEntity> findByKeyword(@Param("keyword") String keyword);
	
	@Query("SELECT k FROM ProductsEntity k WHERE function('contains', k.name , :keyword, function('fuzzy', 0.5, function('fuzzySimilarCalculationModeSearchCompare'))) = function('contains_rhs') or function('contains', k.manufacturer , :keyword, function('fuzzy', 0.5)) = function('contains_rhs')")
	public List<ProductsEntity> findByKeywordWithFuzzy(@Param("keyword") String keyword);
	
}
