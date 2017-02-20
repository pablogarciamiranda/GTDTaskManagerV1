package uo.sdi.acciones;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class AñadirTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		
		//Datos de la categoria
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		String taskName = request.getParameter("taskName");
		String categoryId = request.getParameter("categoryId");
		
		Task task = new Task();
		task.setTitle(taskName);
		if (categoryId != null)
			task.setCategoryId(Long.parseLong(categoryId));
		task.setUserId(user.getId());
		
		try {
			TaskService taskService = Services.getTaskService();
			taskService.createTask(task);
			if (categoryId!=null){
				request.getRequestDispatcher("listarTareas?id=" + task.getCategoryId()).forward(request, response);
			}
			else{
				request.getRequestDispatcher("listarTareasInbox").forward(request, response);
			}

		}
		catch (BusinessException | ServletException | IOException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido creando la tarea: %s",
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
