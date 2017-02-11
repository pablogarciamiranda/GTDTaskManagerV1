package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class FinalizarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		
		//Datos de la categoria
		Long taskID = Long.parseLong(request.getParameter("taskName"));
		
		try {
			TaskService taskService = Services.getTaskService();
			taskService.deleteTask(taskID);
		}
		catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido eliminando la tarea: %s",
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
