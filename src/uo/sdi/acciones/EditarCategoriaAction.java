package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class EditarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
			
		String resultado = "EXITO";
		
		//Datos de la categoria
		Category category = (Category) request.getAttribute("category");
		String newName = request.getParameter("name");
		Category cloneCategory = Cloner.clone(category);
		cloneCategory.setName(newName);
		
		try {
			TaskService taskService = Services.getTaskService();
			
			
			taskService.updateCategory(cloneCategory);
			
			request.setAttribute("category", cloneCategory);
		}
		catch (BusinessException b) {
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
