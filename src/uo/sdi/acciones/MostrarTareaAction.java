package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import alb.util.log.Log;

public class MostrarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		
		//Datos de la categoria
		Long taskid = Long.parseLong(request.getParameter("taskID"));
		
		try {
			TaskService taskService = Services.getTaskService();
			
			Task task = taskService.findTaskById(taskid);
			
			request.setAttribute("task", task);
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
