package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarTareasDiaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("user");
		
		List<Task> listaTareasDia;
		
		try {
			TaskService taskService = Services.getTaskService();
			listaTareasDia=taskService.findTodayTasksByUserId(Long.valueOf(user.getId()));
			request.setAttribute("listaTareasDia", listaTareasDia);
			Log.debug("Obtenida lista de tareas del día conteniendo [%d] tareas", 
					listaTareasDia.size());
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
