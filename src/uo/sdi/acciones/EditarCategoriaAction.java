package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.FieldsCheck;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class EditarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		
		//Datos de la category
		String categoryId = request.getParameter("categoryId");
		
		String newName = request.getParameter("newName");
		
		//Find category
		TaskService taskService = Services.getTaskService();
		Category category;
		List<Category> listaCategorias;
		
		try {
			category = taskService.findCategoryById(Long.parseLong(categoryId));
			category.setName(newName);
			taskService.updateCategory(category);
			
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			session.setAttribute("listaCategorias", listaCategorias);
			Log.debug("Añadida nueva categoria");
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando la categoria: %s", b.getMessage());
			return "FRACASO";
		}
		
		//Clone Category
		Category cloneCategory = Cloner.clone(category);
		
		// If new fields are empty
		if (FieldsCheck.invalidFieldCheck(newName)) {
			request.setAttribute("error", "Existen campos vacios, por favor, rellenalos todos.");
			Log.debug(
					"El usuario no ha rellado los campos al actualizar datos");
			return "FRACASO";
		}
		
		//Set new fields
		cloneCategory.setName(newName);
		
		//Update category
		try {		
			taskService.updateCategory(cloneCategory);
			
			request.setAttribute("category", cloneCategory);
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando la categoria: %s",
					b.getMessage());
			return "FRACASO";
		}
		request.setAttribute("message", "Los datos han sido actualizados correctamente.");
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
