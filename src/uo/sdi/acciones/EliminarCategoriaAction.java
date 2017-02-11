package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class EliminarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
String resultado = "EXITO";
		
		//Datos de la categoria
		long categoryid = Long.parseLong(request.getParameter("categoryID"));
		
		try {
			TaskService taskService = Services.getTaskService();
			taskService.deleteCategory(categoryid);
		}
		catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido eliminando la categoria la categoria: %s",
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
