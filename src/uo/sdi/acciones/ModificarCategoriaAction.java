package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import alb.util.log.Log;

public class ModificarCategoriaAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Long categoryid = Long.parseLong(request.getParameter("categoryId"));
		TaskService taskService = Services.getTaskService();		
		try {
			
			Category category = taskService.findCategoryById(categoryid);
			request.setAttribute("category", category);
			
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando la categoria: %s", b.getMessage());
		
			return "FRACASO";
		}
		return "EXITO";
	}

}
