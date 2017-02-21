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
import uo.sdi.dto.util.FreijeyPabloUtil;
import alb.util.log.Log;

public class ListarTareasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		HttpSession session = request.getSession();
		long categoryID = Long.parseLong(request.getParameter("categoryId"));
		
		List<Task> listaTareas;
		List<Task> listaTareasTerminadas;
		
		try {
			TaskService taskService = Services.getTaskService();
			
			listaTareas=taskService.findTasksByCategoryId(categoryID);
			listaTareasTerminadas=taskService.findFinishedTasksByCategoryId(categoryID);
			
			FreijeyPabloUtil.orderAscending(listaTareas);
			FreijeyPabloUtil.orderDescending(listaTareasTerminadas);
			
			Category category = taskService.findCategoryById(categoryID);
			
			request.setAttribute("listaTareas", listaTareas);
			request.setAttribute("listaTareasTerminadas", listaTareasTerminadas);
			
			request.setAttribute("sePuedeMostrarTerminadas",true);
			
			session.setAttribute("listaMostrada", "custom");
			request.setAttribute("category", category);
			
			Log.debug("Obtenida lista de tareas del día conteniendo [%d] tareas", 
					listaTareas.size());
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas: %s",
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
