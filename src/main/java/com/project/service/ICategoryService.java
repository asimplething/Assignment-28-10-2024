package com.project.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import com.project.entity.CategoryEntity;

public interface ICategoryService {
	public <S extends CategoryEntity> S save(S entity);
	public List<CategoryEntity> findAll();
	public Page<CategoryEntity> findAll(Pageable pageable);
	public List<CategoryEntity> findAll(Sort sort);
	public List<CategoryEntity> findAllById(Iterable<Long> ids) ;
	public Optional<CategoryEntity> findById(Long id) ;
	public long count() ;
	public void deleteById(Long id);
	public void delete(CategoryEntity entity);
	public void deleteAll();
	public List<CategoryEntity> findByNameContaining(String name);
	public Page<CategoryEntity> findByNameContaining(String name, Pageable pageable);
}
