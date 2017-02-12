package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.FieldsCheck;
import uo.sdi.dto.Category;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class EditarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
			
		String resultado = "EXITO";
		List<String> errors = new ArrayList<String>();
		
		//Datos de la category
		String categoryId = request.getParameter("categoryId");
		
		String newName = request.getParameter("name");
		
		//Find category
		TaskService taskService = Services.getTaskService();
		Category category;
		try {
			category = taskService.findCategoryById(Long.parseLong(categoryId));
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando la categoria: %s", b.getMessage());
			return "FRACASO";
		}
		
		//Clone Category
		Category cloneCategory = Cloner.clone(category);
		
		// If new fields are empty
		if (FieldsCheck.invalidFieldCheck(newName)) {
			errors.add("Existen campos vacios, por favor, rellenalos todos.");
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
			resultado="FRACASO";
		}
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
