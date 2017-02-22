package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.*;
import uo.sdi.dto.User;
import uo.sdi.persistence.PersistenceException;

public class Controlador extends javax.servlet.http.HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Accion>> mapaDeAcciones; // <rol, <opcion, objeto Accion>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion; // <rol, <opcion, <resultado, JSP>>>

	public void init() throws ServletException {  
		crearMapaAcciones();
		crearMapaDeNavegacion();
    }
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
		
		String accionNavegadorUsuario, resultado, jspSiguiente;
		Accion objetoAccion;
		String rolAntes, rolDespues;
		
		try {
			accionNavegadorUsuario=request.getServletPath().replace("/","");  // Obtener el string que hay a la derecha de la última /
				
			rolAntes=obtenerRolDeSesion(request);
			
			objetoAccion=buscarObjetoAccionParaAccionNavegador(rolAntes, 
					accionNavegadorUsuario);
			
			request.removeAttribute("mensajeParaElUsuario");
				
			resultado=objetoAccion.execute(request,response);
				
			rolDespues=obtenerRolDeSesion(request);
			
			jspSiguiente=buscarJSPEnMapaNavegacionSegun(rolDespues, 
					accionNavegadorUsuario, resultado);

			request.setAttribute("jspSiguiente", jspSiguiente);

		} catch(PersistenceException e) {
			
			request.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción relacionada con la persistencia [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario", 
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente="/login.jsp";
			
		} catch(Exception e) {
			
			//request.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción no manejada [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario", 
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente="/login.jsp";
		}
			
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspSiguiente); 
			
		dispatcher.forward(request, response);			
	}			
	
	
	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion=req.getSession();
		if (sesion.getAttribute("user")==null)
			return "ANONIMO";
		else
			if (((User)sesion.getAttribute("user")).getIsAdmin())
				return "ADMIN";
			else
				return "USUARIO";
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarObjetoAccionParaAccionNavegador(String rol, String opcion) {
		
		Accion accion=mapaDeAcciones.get(rol).get(opcion);
		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]",accion,opcion,rol);
		return accion;
	}
	
	
	// Obtiene la página JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPEnMapaNavegacionSegun(String rol, String opcion, String resultado) {
		
		String jspSiguiente=mapaDeNavegacion.get(rol).get(opcion).get(resultado);
		Log.debug("Elegida página siguiente [%s] para el resultado [%s] tras realizar [%s] con rol [%s]",
				jspSiguiente,resultado,opcion,rol);
		return jspSiguiente;		
	}
		
		
	private void crearMapaAcciones() {
		
		mapaDeAcciones=new HashMap<String,Map<String,Accion>>();
		
		//// USUARIO ANÓNIMO \\\\
		Map<String,Accion> mapaPublico=new HashMap<String,Accion>();
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("registrarse", new RegistrarseAction());
		
		mapaDeAcciones.put("ANONIMO", mapaPublico);
		
		//// USUARIO REGISTRADO \\\\
		Map<String,Accion> mapaRegistrado=new HashMap<String,Accion>();
		//Panel de Usuario
		mapaRegistrado.put("mostrarUsuario", new MostrarUsuarioAction());
		mapaRegistrado.put("editarUsuario", new EditarUsuarioAction());
		mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());
		
		
		//Listar tareareas
		mapaRegistrado.put("listarTareasHoy", new ListarTareasHoyAction());
		mapaRegistrado.put("listarTareasSemana", new ListarTareasSemanaAction());
		mapaRegistrado.put("listarTareasInbox", new ListarTareasInboxAction());
		mapaRegistrado.put("listarTareas", new ListarTareasAction());
		
		//Gestionar Tareas
		mapaRegistrado.put("mostrarTarea", new MostrarTareaAction());
		mapaRegistrado.put("añadirTarea", new AñadirTareaAction());
		mapaRegistrado.put("editarTarea", new EditarTareaAction());
		mapaRegistrado.put("terminarTarea", new TerminarTareaAction());
		
		//Gestionar Categoría
		mapaRegistrado.put("añadirCategoria", new AñadirCategoriaAction());
		mapaRegistrado.put("editarCategoria", new EditarCategoriaAction());
		mapaRegistrado.put("eliminarCategoria", new EliminarCategoriaAction());
		mapaRegistrado.put("duplicarCategoria", new DuplicarCategoriaAction());
		mapaRegistrado.put("listarCategorias", new ListarCategoriasAction());

		mapaDeAcciones.put("USUARIO", mapaRegistrado);
		
		///// ADMIN \\\\
		Map<String,Accion> mapaAdministrador=new HashMap<String,Accion>();
		mapaAdministrador.put("cerrarSesion", new CerrarSesionAction());
		mapaAdministrador.put("cambiarEstado", new CambiarEstadoAction());
		mapaAdministrador.put("eliminarUsuario", new EliminarUsuarioAction());
		mapaAdministrador.put("mostrarUsuario", new MostrarUsuarioAction());
		mapaAdministrador.put("editarUsuario", new EditarUsuarioAction());
		mapaAdministrador.put("listarUsuarios", new ListarUsuariosAction());
		
		mapaDeAcciones.put("ADMIN", mapaAdministrador);
	}
	
	
	private void crearMapaDeNavegacion() {
				
		mapaDeNavegacion=new HashMap<String,Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		Map<String, String> resultadoYJSP=new HashMap<String, String>();

		//// Mapa de navegación de ANÓNIMO \\\\
		resultadoYJSP.put("FRACASO","/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/login.jsp");
		resultadoYJSP.put("FRACASO","/register.jsp");
		opcionResultadoYJSP.put("registrarse", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);
		
		mapaDeNavegacion.put("ANONIMO",opcionResultadoYJSP);
		
		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		//// Mapa de navegación de usuarios REGISTRADOS \\\\
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);	
		
		// Editar Usuarios, Tareas y Categorias
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/mostrarUsuario.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("mostrarUsuario", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/mostrarUsuario.jsp");
		resultadoYJSP.put("FRACASO","/mostrarUsuario.jsp");
		opcionResultadoYJSP.put("editarUsuario", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/mostrarTarea.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("mostrarTarea", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/mostrarTarea.jsp");
		resultadoYJSP.put("FRACASO","/mostrarTarea.jsp");
		opcionResultadoYJSP.put("editarTarea", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("editarCategoria", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("eliminarCategoria", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("duplicarCategoria", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("añadirCategoria", resultadoYJSP);
		
		//Listar Tareas y Categorias
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarCategorias.jsp");
		resultadoYJSP.put("FRACASO","/listarCategorias.jsp");
		opcionResultadoYJSP.put("listarCategorias", resultadoYJSP);
			
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareasjsp");
		opcionResultadoYJSP.put("listarTareasHoy", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("listarTareasInbox", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("listarTareasSemana", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("listarTareas", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/listarTareas.jsp");
		opcionResultadoYJSP.put("terminarTarea", resultadoYJSP);
				
		mapaDeNavegacion.put("USUARIO",opcionResultadoYJSP);
		
		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		 ////Mapa de navegación del ADMIN \\\\
		resultadoYJSP.put("EXITO","/panelAdmin.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		
		//Gestionar Usuarios
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/panelAdmin.jsp");
		resultadoYJSP.put("FRACASO","/mostrarUsuario.jsp");
		opcionResultadoYJSP.put("eliminarUsuario", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/panelAdmin.jsp");
		resultadoYJSP.put("FRACASO","/mostrarUsuario.jsp");
		opcionResultadoYJSP.put("cambiarEstado", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/mostrarUsuario.jsp");
		resultadoYJSP.put("FRACASO","/panelAdmin.jsp");
		opcionResultadoYJSP.put("mostrarUsuario", resultadoYJSP);	
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/mostrarUsuario.jsp");
		resultadoYJSP.put("FRACASO","/mostrarUsuario.jsp");
		opcionResultadoYJSP.put("editarUsuario", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/panelAdmin.jsp");
		resultadoYJSP.put("FRACASO","/panelAdmin.jsp");
		opcionResultadoYJSP.put("listarUsuarios", resultadoYJSP);
		
		mapaDeNavegacion.put("ADMIN",opcionResultadoYJSP);
	}
			
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}