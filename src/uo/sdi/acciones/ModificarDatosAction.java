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
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;

public class ModificarDatosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		List<String> errors = new ArrayList<String>();

		String newEmail = request.getParameter("email");
		String newPassword = request.getParameter("newPassword");
		String newPassword2 = request.getParameter("newPassword2");
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		User userClone = Cloner.clone(user);

		// If new fields are empty
		if (FieldsCheck.invalidFieldCheck(newEmail, newPassword, newPassword2)){
			errors.add("Existen campos vacios, por favor, rellenalos todos.");
			Log.debug(
					"El usuario [%s] no ha rellando los 3 campos al actualizar datos",
					user.getLogin());
			resultado = "FRACASO";
		}
		//Logica relacionada con la Vista
		else{
			if (!user.getEmail().equals(newEmail)){
				userClone.setEmail(newEmail);
				Log.debug("Modificado el email de [%s] con el valor [%s]",
						userClone.getLogin(), newEmail);
			}
			if  (user.getEmail().equals(newEmail)){
				errors.add("El email no se ha modificado porque coincide con el anterior.");
				Log.debug("El email no se ha modificado porque coincide con el anterior.");
				resultado = "FRACASO";
			}
			if (!user.getPassword().equals(newPassword) && newPassword.equals(newPassword2)){
				userClone.setPassword(newPassword);
				Log.debug("Modificada la password de [%s]",
						userClone.getLogin());
			}
			if (!newPassword.equals(newPassword2)){
				errors.add("Las nuevas passwords no coinciden");
				Log.debug("Las nuevas passwords no coinciden");
				resultado = "FRACASO";
			}
			if (user.getPassword().equals(newPassword)){
				errors.add("La nueva password debe de ser distinta a la anterior.");
				Log.debug("La password introducida coincide con el anterior");
				resultado = "FRACASO";
			}
		}
		
		try {
			UserService userService = Services.getUserService();
			userService.updateUserDetails(userClone);

			session.setAttribute("user", userClone);
		} catch (BusinessException b) { //Excepciones que tienen que ver con la capa de business.
										//Ej: la password no cumple los caracteres mínimos o el email no es valido
			Log.debug(
					"Algo ha ocurrido actualizando los datos del usuario [%s]",
					user.getLogin());
			resultado = "FRACASO";
		}
		request.setAttribute("errors", errors);
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
