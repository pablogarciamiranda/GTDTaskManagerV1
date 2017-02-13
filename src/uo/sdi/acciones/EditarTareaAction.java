package uo.sdi.acciones;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.FieldsCheck;
import uo.sdi.dto.Task;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class EditarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		List<String> errors = new ArrayList<String>();
		
		// Datos del task
		String taskId = request.getParameter("taskId");
		
		String newTitle = request.getParameter("newTitle");
		String newPlannedDate = request.getParameter("newPlannedDate");
		String newComment = request.getParameter("newComment");
		String newCategoryId = request.getParameter("newCategoryId");
		
		//Find task
		TaskService taskService = Services.getTaskService();
		Task task;
		try {
			//Find categories from user to add to the model
		

			task = taskService.findTaskById(Long.parseLong(taskId));
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando la categoria: %s", b.getMessage());
			return "FRACASO";
		}
		
		//Clone task
		Task cloneTask = Cloner.clone(task);
		
		// If new fields are empty

		if (FieldsCheck.invalidFieldCheck(newTitle, newPlannedDate, newComment)) {
			errors.add("Existen campos vacios, por favor, rellenalos todos.");
			Log.debug(
					"El usuario no ha rellado los campos al actualizar datos");
			return "FRACASO";
		}
		//Set new fields
		cloneTask.setTitle(newTitle);
		cloneTask.setComments(newComment);
		cloneTask.setCategoryId(Long.valueOf(newCategoryId));
		
		DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");

		Date date = new Date();
		try {
			date = formatter.parse(newPlannedDate);
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		cloneTask.setPlanned(date); // Hay que mirar como hacemos con las fechas
		
		//Update task
		try {
			taskService.updateTask(cloneTask);

			//request.setAttribute("task", cloneTask);
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando la tarea: %s", b.getMessage());
			resultado = "FRACASO";
		}

		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
