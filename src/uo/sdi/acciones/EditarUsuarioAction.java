package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.FieldsCheck;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.dto.util.Cloner;

public class EditarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		String newLogin = request.getParameter("newLogin")
			,login = request.getParameter("login")
			,newEmail = request.getParameter("newEmail")
			,password = request.getParameter("password")
			,newPassword = request.getParameter("newPassword")
			,newPassword2 = request.getParameter("newPassword2")
			,newStatus = request.getParameter("newStatus");
		
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		User userToEdit = null;
		
		//Si el usuario es admin, editará otro user que no es el de la sesión
		if (user.getIsAdmin()){
			UserService userService = Services.getUserService();
			try {
				userToEdit = userService.findLoggableUser(login);
				//Checkbox activado
				if (newStatus!=null){
					userToEdit.setStatus(UserStatus.ENABLED);
					Log.debug("El usuario se ha activado");
				}
				else{
					userToEdit.setStatus(UserStatus.DISABLED);
					Log.debug("El usuario se ha desactivado");
				}
			} catch (BusinessException b) {
				request.setAttribute("error", "Algo ha ocurrido actualizando los datos del usuario: "
						+ b.getMessage());
				Log.debug(
						"Algo ha ocurrido actualizando los datos del usuario [%s]",
						user.getLogin());
				request.setAttribute("user", userToEdit);
				return "FRACASO";
			}
		}
		//Si no es admin, se editará a si mismo
		else{
			userToEdit = user;
		}
		
		User userClone = Cloner.clone(userToEdit);
		

		// If new fields are empty
		if (FieldsCheck.invalidFieldCheck(newEmail, newPassword, newPassword2)) {
			request.setAttribute("error", "Existen campos vacios, por favor, rellenalos todos.");
			Log.debug(
					"El usuario [%s] no ha rellado todos los campos al actualizar datos",
					user.getLogin());
			request.setAttribute("user", userClone);
			return "FRACASO";
		} else {
			// Logica relacionada con la Vista
			if (!userToEdit.getEmail().equals(newEmail)) {
				userClone.setEmail(newEmail);
				Log.debug("Modificado el email de [%s] con el valor [%s]",
						userClone.getLogin(), newEmail);
			}
			//Si las viejas contraseñas y la repetición de la nueva coinciden, se actualiza
			if (userToEdit.getPassword().equals(password) && newPassword.equals(newPassword2)) {
				userClone.setPassword(newPassword);
				Log.debug("Modificada la password de [%s]",
						userClone.getLogin());
			}
			//Las viejas password no coinciden
			if (!userToEdit.getPassword().equals(password)){
				request.setAttribute("error", "La contraseña vieja no coincide con la almacenada en la base de datos, inténtelo de nuevo");
				Log.debug("La contraseña vieja no coincide con la almacenada en la base de datos, inténtelo de nuevo");
				request.setAttribute("user", userClone);
				return "FRACASO";
			}
			//La repetición de la password no coincide
			if (!newPassword.equals(newPassword2)) {
				request.setAttribute("error", "Las nuevas passwords no coinciden, inténtelo de nuevo");
				Log.debug("Las nuevas passwords no coinciden");
				request.setAttribute("user", userClone);
				return "FRACASO";
			}
			//La nueva password es igual a la anterior
			if (userToEdit.getPassword().equals(newPassword)) {
				request.setAttribute("error", "La nueva password debe de ser distinta a la anterior, inténtelo de nuevo");
				Log.debug("La password introducida coincide con el anterior");
				request.setAttribute("user", userClone);
				return "FRACASO";
			}
			if (!userToEdit.getLogin().equals(newLogin)){
				userClone.setLogin(newLogin);
				Log.debug("Modificado el login de [%s] con el valor [%s]", login, newLogin);
			}
			try {
				UserService userService = Services.getUserService();
				userService.updateUserDetails(userClone);
				
				//If everything was ok and the operation was not performed from admin Panel
				if (!user.getIsAdmin())
					session.setAttribute("user", userClone);
				//If everything was ok and the operation was  performed from admin Panel
				request.setAttribute("user", userClone);
			} catch (BusinessException b) {
				request.setAttribute("error", "Algo ha ocurrido actualizando los datos del usuario: "
						+ b.getMessage());
				Log.debug(
						"Algo ha ocurrido actualizando los datos del usuario [%s]",
						user.getLogin());
				request.setAttribute("user", userClone);
				return "FRACASO";
			}
		}
		
		request.setAttribute("message", "Los datos han sido actualizados correctamente.");
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
