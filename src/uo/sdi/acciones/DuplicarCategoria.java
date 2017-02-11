package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class DuplicarCategoria implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		
		//Datos de la categoria
		HttpSession session = request.getSession();
		Category category = (Category) session.getAttribute("category");
		
		Category duplicate = Cloner.clone(category);
		duplicate.setName(category.getName()+" - copy");
		//TODO poner datos en la nueva categoria
		
		try {
			TaskService taskService = Services.getTaskService();
			taskService.createCategory(duplicate);
		}
		catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido creando la categoria: %s",
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
