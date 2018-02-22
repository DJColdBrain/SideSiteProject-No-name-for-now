package com.djcoldbrain.giflib.web.controller;

import com.djcoldbrain.giflib.model.Category;
import com.djcoldbrain.giflib.service.CategoryService;
import com.djcoldbrain.giflib.web.Color;
import com.djcoldbrain.giflib.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Index of all categories
    @RequestMapping("/categories")
    public String listCategories(Model model) {
        //  Get all categories
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        // Get the category given by categoryId
        Category category = categoryService.findById(categoryId);

        model.addAttribute("category", category);
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        // Add model attributes needed for new form
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", new Category());
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", "/categories");
        model.addAttribute("heading", "New Category");
        model.addAttribute("submit", "Add");
        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        // Add model attributes needed for edit form
        model.addAttribute("colors", Color.values());
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", categoryService.findById(categoryId));
        }
        model.addAttribute("action", String.format("/categories/%s", categoryId));
        model.addAttribute("heading", "Edit Category");
        model.addAttribute("submit", "Update");
        return "category/form";
    }

    // Update an existing category
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        //  Update category if valid data was received
        if (result.hasErrors()){
            //include validation errors
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category failed to be updated", FlashMessage.Status.FAILURE));
            //redirect back to the form
            redirectAttributes.addFlashAttribute("category",category);
            return String.format("redirect:/categories/%s/edit", category.getId());
        }
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category successfully edited", FlashMessage.Status.SUCCESS));

        categoryService.save(category);

        //  Redirect browser to /categories
        return "redirect:/categories";
    }

    // Add a category
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            //include validation errors
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category failed to be added", FlashMessage.Status.FAILURE));
            //redirect back to the form
            redirectAttributes.addFlashAttribute("category",category);
            return "redirect:/categories/add";
        }
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category successfully added", FlashMessage.Status.SUCCESS));
        // Add category if valid data was received
        categoryService.save(category);
        // Redirect browser to /categories
        return "redirect:/categories";
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes) {
        // Delete category if it contains no GIFs
        Category category = categoryService.findById(categoryId);
        if (category.getGifs().size() > 0){
            redirectAttributes.addFlashAttribute("flash",new FlashMessage("Only empty categories can be deleted", FlashMessage.Status.FAILURE));
            return String.format("redirect:/categories/%s/edit", categoryId);
        }
        categoryService.delete(category);
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category Deleted", FlashMessage.Status.SUCCESS));
        // Redirect browser to /categories
        return "redirect:/categories";
    }
}
