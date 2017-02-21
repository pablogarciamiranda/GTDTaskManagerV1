package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.FieldsCheck;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import uo.sdi.dto.util.FreijeyPabloUtil;
import alb.util.log.Log;

public class ValidarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String nombreUsuario=request.getParameter("nombreUsuario");
		String passwordUsuario = request.getParameter("passwordUsuario");
		HttpSession session=request.getSession();
		
		if (FieldsCheck.invalidFieldCheck(nombreUsuario, passwordUsuario)) {
			request.setAttribute("error", "Existen campos vacios, por favor, rellenalos todos.");
			Log.debug("El usuario no ha rellenado los 4 campos al registrarse");
			return "FRACASO";
		}
		else{
			if (session.getAttribute("user")==null) {
				UserService userService = Services.getUserService();
				User userByLogin=null;
				try {
					userByLogin = userService.findLoggableUser(nombreUsuario);
				} catch (BusinessException b) { 
					//Si ocurre alguna excepción de negocio.					
					session.invalidate();
					Log.debug("Algo ha ocurrido intentando iniciar sesión [%s]: %s", 
							nombreUsuario, b.getMessage());
					request.setAttribute("error", b.getMessage());
					return "FRACASO";
				}
				//Si el nombre de usuario existe
				if (userByLogin!=null) {
					//Si la contraseña es correcta
					if (userByLogin.getPassword().equals(passwordUsuario)){
						//Solo introducimos al usuario en session una vez que hace login
						session.setAttribute("user", userByLogin);
						int contador=Integer.parseInt((String)request.getServletContext()
								.getAttribute("contador"));
						request.getServletContext().setAttribute("contador", 
								String.valueOf(contador+1));
						session.setAttribute("fechaInicioSesion", new java.util.Date());
						Log.info("El usuario [%s] ha iniciado sesión",nombreUsuario);
						//Si el user es admin añadimos la lista de usuarios para gestionar desde el admin panel
						if (userByLogin.getIsAdmin()){
							AdminService adminService = Services.getAdminService();
							List<User> listOfUsers = null;
							try {
								listOfUsers = adminService.findAllUsers();
							} catch (BusinessException b) {
								Log.debug("Algo ha ocurrido intentando iniciar sesión como administrador: [%s]: %s", 
										nombreUsuario, b.getMessage());
								request.setAttribute("error", "Algo ha ocurrido intentando iniciar sesión como administrador: " + b.getMessage());
								return "FRACASO";
							}
							session.setAttribute("listOfUsers", listOfUsers);
						}
						//Si no, es usuario registrado. Añadimos las categorias
						else{
							TaskService taskService = Services.getTaskService();
							List<Category> listaCategorias = null;
							List<Task> listaTareas = null;
							List<Task> listaTareasTerminadas = null;
							try {
								Services.getTaskService();
								listaCategorias = taskService.
										findCategoriesByUserId(userByLogin.getId());
								listaTareas=taskService
										.findInboxTasksByUserId(userByLogin.getId());
								listaTareasTerminadas=taskService.
										findFinishedInboxTasksByUserId(userByLogin.getId());
								
								FreijeyPabloUtil.orderAscending(listaTareas);	
								FreijeyPabloUtil.orderDescending(listaTareasTerminadas);
								
							} catch (BusinessException b) {
								Log.debug("Algo ha ocurrido intentando iniciar sesión como usuario registrado [%s]: %s", 
										nombreUsuario, b.getMessage());
								request.setAttribute("error", "Algo ha ocurrido intentando iniciar sesión como usuario registrado: " + b.getMessage());
								return "FRACASO";
							}
							
							request.setAttribute("listaMostrada","Inbox");
							request.setAttribute("listaTareas",listaTareas);
							request.setAttribute("listaTareasTerminadas",listaTareas);
							session.setAttribute("listaCategorias", listaCategorias);
						}
					}
					//Si la contraseña es incorrecta
					else{
						session.invalidate();
						Log.info("La contraseña del usuario [%s] es incorrecta",nombreUsuario);
						request.setAttribute("error", "La contraseña del usuario ["+
								nombreUsuario+"] es incorrecta");
						return "FRACASO";
					}
					
				}
				//Si el nombre de usuario no existe
				else {
					session.invalidate();
					Log.info("El usuario [%s] no está registrado",nombreUsuario);
					request.setAttribute("error", "El usuario ["+
							nombreUsuario+"] no está registrado");
					return "FRACASO";
				}
			}
			else
				//Si se intenta iniciar sesión teniendo la sesion iniciada
				if (!nombreUsuario.equals(session.getAttribute("user"))) {
					Log.info("Se ha intentado iniciar sesión como [%s] teniendo la sesión iniciada como [%s]",
							nombreUsuario,((User)session.getAttribute("user")).getLogin());
					request.setAttribute("error", 
							"Se ha intentado iniciar sesión como ["+nombreUsuario+
							"] teniendo la sesión iniciada como ["+
							((User)session.getAttribute("user")).getLogin()+"]");
					session.invalidate();
					return "FRACASO";
				}
		}
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
