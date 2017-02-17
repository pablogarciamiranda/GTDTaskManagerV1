package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class MostrarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		
		HttpSession session = request.getSession();
		
		//Datos de la tarea
		Long taskid = Long.parseLong(request.getParameter("taskId"));
		
		try {
			TaskService taskService = Services.getTaskService();
			
			//Tarea a mostrar
			Task task = taskService.findTaskById(taskid);
			request.setAttribute("task", task);
			
			//Categoría de la tarea
			request.setAttribute("selectedCategory", task.getCategoryId());
			
			//Categorias disponibles del usuario
			User user = ((User) session.getAttribute("user"));
			List<Category> categories = taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("categories", categories);
						
			
		}
		catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido buscando la tarea: %s",
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
