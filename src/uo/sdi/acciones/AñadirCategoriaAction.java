package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class AñadirCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		
		//Datos de la categoria
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		String name = (String) session.getAttribute("categoryName");
		
		Category category = new Category();
		category.setUserId(user.getId());
		category.setName(name);
		//id de la categoria?
		
		try {
			TaskService taskService = Services.getTaskService();
			taskService.createCategory(category);
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
