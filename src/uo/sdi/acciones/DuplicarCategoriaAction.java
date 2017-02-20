package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class DuplicarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		//Datos de la categoria
		long categoryid = Long.parseLong(request.getParameter("categoryId"));
		
		try {
			TaskService taskService = Services.getTaskService();
			taskService.duplicateCategory(categoryid);
			
			List<Category> listaCategorias = taskService.findCategoriesByUserId(user.getId());
			session.setAttribute("listaCategorias", listaCategorias);
		}
		catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido duplicando la categoria: %s",
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
