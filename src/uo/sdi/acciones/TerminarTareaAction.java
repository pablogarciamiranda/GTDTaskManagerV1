package uo.sdi.acciones;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import alb.util.log.Log;

public class TerminarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Long taskid = Long.parseLong(request.getParameter("taskId"));
		String categoryId = request.getParameter("categoryId");
		TaskService taskService = Services.getTaskService();		
		try {
			
			taskService.markTaskAsFinished(taskid);
			Task task = taskService.findTaskById(taskid);
			
			if (categoryId!=null){
				request.getRequestDispatcher("listarTareas?id=" + task.getCategoryId()).forward(request, response);
			}
			else{
				request.getRequestDispatcher("listarTareasInbox").forward(request, response);
			}
			
		} catch (BusinessException | ServletException | IOException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido marcando la tarea como terminada: %s", b.getMessage());
		
			return "FRACASO";
		}
		return "EXITO";
	}

}
