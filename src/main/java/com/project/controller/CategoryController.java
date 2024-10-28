package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import org.springframework.beans.BeanUtils;

import com.project.entity.CategoryEntity;
import com.project.entity.CategoryModel;
import com.project.service.ICategoryService;

@RestController
@RequestMapping("admin/categories")
public class CategoryController {

	@Autowired
	ICategoryService categoryService;

	@GetMapping("add")
	public String add(ModelMap model) {
		CategoryModel cateModel = new CategoryModel();
		cateModel.setIsEdit(false);
		// chuyển dữ liệu từ model vào biến category để đưa lên view
		model.addAttribute("category", cateModel);
		return "admin/categories/addOrEdit";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Validated @ModelAttribute("category") CategoryModel cateMdoel,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/addOrEdit");
		}
		CategoryEntity entity = new CategoryEntity();
		// copy từ Model sang Entity
		BeanUtils.copyProperties(cateMdoel, entity);
		// goi ham save trong service
		categoryService.save(entity);
		// đưa thông báo về cho biến message
		String message = "";
		if (cateMdoel.getIsEdit() == true) {
			message = "Category is Edited !!!!!!!! ";
		} else {
			message = "Category is saved! !!!!!!! ";
		}
		model.addAttribute("message", message);
		// redirect ve URL controller
		return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@GetMapping("delete/{categoryId}")
	public ModelAndView delet(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		categoryService.deleteById(categoryId);
		model.addAttribute("message", "Category is deleted !!!! ");
		return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
		List<CategoryEntity> list = null;
		// có nội dung truyền ve không, name là tuy chon khi required=false
		if (!StringUtils.isEmpty(name)) {
			list = categoryService.findByNameContaining(name);
		} else {
			list = categoryService.findAll();
		}
		model.addAttribute("categories", list);
		return "admin/categories/search";
	}
}
