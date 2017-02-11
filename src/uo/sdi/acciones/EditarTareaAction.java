package uo.sdi.acciones;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class EditarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		
		//Datos de la categoria
		Task task = (Task) request.getAttribute("task");
		String newName = request.getParameter("name");
		String plannedDate = request.getParameter("plannedDate");
		String comment = request.getParameter("comment");
		Long categoryid = Long.parseLong(request.getParameter("categoryID"));
		Task cloneTask = Cloner.clone(task);
		cloneTask.setTitle(newName);
		DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
		
		Date date = new Date();
		try {
			date = formatter.parse(plannedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cloneTask.setPlanned(date);	//Hay que mirar como hacemos con las fechas
		cloneTask.setComments(comment);
		cloneTask.setCategoryId(categoryid);
		
		try {
			TaskService taskService = Services.getTaskService();
			
			
			taskService.updateTask(cloneTask);
			
			request.setAttribute("task", cloneTask);
		}
		catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando la tarea: %s",
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
