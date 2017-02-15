package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;

public class ModificarTareaAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Long taskid = Long.parseLong(request.getParameter("taskId"));
		TaskService taskService = Services.getTaskService();
		HttpSession session = request.getSession();
		
		try {
			
			User user = ((User) session.getAttribute("user"));
			List<Category> categories = taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("categories", categories);
			
			Task task = taskService.findTaskById(taskid);
			request.setAttribute("task", task);
			
			request.setAttribute("categorySelectedId", task.getCategoryId());
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando la tarea: %s", b.getMessage());
		
			return "FRACASO";
		}
		return "EXITO";
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
