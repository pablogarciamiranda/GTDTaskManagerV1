package uo.sdi.acciones;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.FieldsCheck;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class EditarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		
		
		// Datos del task
		String taskId = request.getParameter("taskId");
		
		String newTitle = request.getParameter("newTitle");
		String newPlannedDate = request.getParameter("newPlannedDate");
		String newComment = request.getParameter("newComment");
		String newCategoryId = request.getParameter("newCategoryId");
		
		//Find task and categories
		TaskService taskService = Services.getTaskService();
		Task task = null;
		List<Category> categories = null;
		try {
			categories = taskService.findCategoriesByUserId(user.getId());
			task = taskService.findTaskById(Long.parseLong(taskId));
		} catch (BusinessException b) {
			request.setAttribute("error", "Algo ha ocurrido editando la tarea: " +  b.getMessage());
			Log.debug("Algo ha ocurrido editando la tarea: %s", b.getMessage());
			setEditingTask(task, categories, request);
			return "FRACASO";
		}
		
		// If new fields are empty
		if (FieldsCheck.invalidFieldCheck(newTitle, newComment)) {
			request.setAttribute("error", "Existen campos vacios, por favor, rellenalos todos.");
			Log.debug("El usuario no ha rellado los campos al actualizar datos");
			setEditingTask(task, categories, request);
			return "FRACASO";
		}
		
		//Clone task
		Task cloneTask = Cloner.clone(task);

		//Set new fields
		cloneTask.setTitle(newTitle);
		cloneTask.setComments(newComment);
		cloneTask.setCategoryId(Long.parseLong(newCategoryId));
		
		DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
		Date date = new Date();
//		try {
//			//date = formatter.parse(newPlannedDate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			
//		}
		cloneTask.setPlanned(date); // Hay que mirar como hacemos con las fechas
		
		//Update task
		try {
			taskService.updateTask(cloneTask);
			setEditingTask(cloneTask, categories, request);
			
		} catch (BusinessException b) {
			request.setAttribute("error", "Algo ha ocurrido editando la tarea: " +  b.getMessage());
			Log.debug("Algo ha ocurrido editando la tarea: %s", b.getMessage());
			setEditingTask(task, categories, request);
			return "FRACASO";
		}
		
		request.setAttribute("message", "Los datos han sido actualizados correctamente.");
		return resultado;
	}
	
	/**
	 * Method used to set to request the task, it´s category and the list of 
	 * categories of the user in order to not loose any data when navigating.
	 * @param task
	 * @param categories
	 * @param request
	 */
	public void setEditingTask(Task task, List<Category> categories, HttpServletRequest request){
		//Añadimos la tarea y la categoria para mostrarlas de nuevo
		request.setAttribute("task", task);
		request.setAttribute("selectedCategory", task.getCategoryId());
		
		//Añadimos categorias disponibles del usuario
		request.setAttribute("categories", categories);
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
