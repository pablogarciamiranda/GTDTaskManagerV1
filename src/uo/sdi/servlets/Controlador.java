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
			accionNavegadorUsuario=request.getServletPath().replace("/","");  // Obtener el string que hay a la derecha de la última / //$NON-NLS-1$ //$NON-NLS-2$
				
			rolAntes=obtenerRolDeSesion(request);
			
			objetoAccion=buscarObjetoAccionParaAccionNavegador(rolAntes, 
					accionNavegadorUsuario);
			
			request.removeAttribute("mensajeParaElUsuario"); //$NON-NLS-1$
				
			resultado=objetoAccion.execute(request,response);
				
			rolDespues=obtenerRolDeSesion(request);
			
			jspSiguiente=buscarJSPEnMapaNavegacionSegun(rolDespues, 
					accionNavegadorUsuario, resultado);

			request.setAttribute("jspSiguiente", jspSiguiente); //$NON-NLS-1$

		} catch(PersistenceException e) {
			
			request.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción relacionada con la persistencia [%s]", //$NON-NLS-1$
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario",  //$NON-NLS-1$
					"Error irrecuperable: contacte con el responsable de la aplicación"); //$NON-NLS-1$
			jspSiguiente="/login.jsp"; //$NON-NLS-1$
			
		} catch(Exception e) {
			
			//request.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción no manejada [%s]", //$NON-NLS-1$
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario",  //$NON-NLS-1$
					"Error irrecuperable: contacte con el responsable de la aplicación"); //$NON-NLS-1$
			jspSiguiente="/login.jsp"; //$NON-NLS-1$
		}
			
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspSiguiente); 
			
		dispatcher.forward(request, response);			
	}			
	
	
	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion=req.getSession();
		if (sesion.getAttribute(Maps.getString("Controlador.12"))==null) //$NON-NLS-1$
			return Maps.getString("Controlador.13"); //$NON-NLS-1$
		else
			if (((User)sesion.getAttribute(Maps.getString("Controlador.14"))).getIsAdmin()) //$NON-NLS-1$
				return Maps.getString("Controlador.15"); //$NON-NLS-1$
			else
				return Maps.getString("Controlador.16"); //$NON-NLS-1$
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarObjetoAccionParaAccionNavegador(String rol, String opcion) {
		
		Accion accion=mapaDeAcciones.get(rol).get(opcion);
		Log.debug(Maps.getString("Controlador.17"),accion,opcion,rol); //$NON-NLS-1$
		return accion;
	}
	
	
	// Obtiene la página JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPEnMapaNavegacionSegun(String rol, String opcion, String resultado) {
		
		String jspSiguiente=mapaDeNavegacion.get(rol).get(opcion).get(resultado);
		Log.debug(Maps.getString("Controlador.18"), //$NON-NLS-1$
				jspSiguiente,resultado,opcion,rol);
		return jspSiguiente;		
	}
		
		
	private void crearMapaAcciones() {
		
		mapaDeAcciones=new HashMap<String,Map<String,Accion>>();
		
		//// USUARIO ANÓNIMO \\\\
		Map<String,Accion> mapaPublico=new HashMap<String,Accion>();
		mapaPublico.put(Maps.getString("Controlador.19"), new ValidarseAction()); //$NON-NLS-1$
		mapaPublico.put(Maps.getString("Controlador.20"), new RegistrarseAction()); //$NON-NLS-1$
		
		mapaDeAcciones.put(Maps.getString("Controlador.21"), mapaPublico); //$NON-NLS-1$
		
		//// USUARIO REGISTRADO \\\\
		Map<String,Accion> mapaRegistrado=new HashMap<String,Accion>();
		//Panel de Usuario
		mapaRegistrado.put(Maps.getString("Controlador.22"), new MostrarUsuarioAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.23"), new EditarUsuarioAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.24"), new CerrarSesionAction()); //$NON-NLS-1$
		
		
		//Listar tareareas
		mapaRegistrado.put(Maps.getString("Controlador.25"), new ListarTareasHoyAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.26"), new ListarTareasSemanaAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.27"), new ListarTareasInboxAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.28"), new ListarTareasAction()); //$NON-NLS-1$
		
		//Gestionar Tareas
		mapaRegistrado.put(Maps.getString("Controlador.29"), new MostrarTareaAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.30"), new AñadirTareaAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.31"), new EditarTareaAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.32"), new TerminarTareaAction()); //$NON-NLS-1$
		
		//Gestionar Categoría
		mapaRegistrado.put(Maps.getString("Controlador.33"), new AñadirCategoriaAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.34"), new EditarCategoriaAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.35"), new EliminarCategoriaAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.36"), new DuplicarCategoriaAction()); //$NON-NLS-1$
		mapaRegistrado.put(Maps.getString("Controlador.37"), new ListarCategoriasAction()); //$NON-NLS-1$

		mapaDeAcciones.put(Maps.getString("Controlador.38"), mapaRegistrado); //$NON-NLS-1$
		
		///// ADMIN \\\\
		Map<String,Accion> mapaAdministrador=new HashMap<String,Accion>();
		mapaAdministrador.put(Maps.getString("Controlador.39"), new CerrarSesionAction()); //$NON-NLS-1$
		mapaAdministrador.put(Maps.getString("Controlador.40"), new CambiarEstadoAction()); //$NON-NLS-1$
		mapaAdministrador.put(Maps.getString("Controlador.41"), new EliminarUsuarioAction()); //$NON-NLS-1$
		mapaAdministrador.put(Maps.getString("Controlador.42"), new MostrarUsuarioAction()); //$NON-NLS-1$
		mapaAdministrador.put(Maps.getString("Controlador.43"), new EditarUsuarioAction()); //$NON-NLS-1$
		mapaAdministrador.put(Maps.getString("Controlador.44"), new ListarUsuariosAction()); //$NON-NLS-1$
		
		mapaDeAcciones.put(Maps.getString("Controlador.45"), mapaAdministrador); //$NON-NLS-1$
	}
	
	
	private void crearMapaDeNavegacion() {
				
		mapaDeNavegacion=new HashMap<String,Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		Map<String, String> resultadoYJSP=new HashMap<String, String>();

		//// Mapa de navegación de ANÓNIMO \\\\
		resultadoYJSP.put(Maps.getString("Controlador.46"),Maps.getString("Controlador.47")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.48"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.49"),Maps.getString("Controlador.50")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.51"),Maps.getString("Controlador.52")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.53"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.54"),Maps.getString("Controlador.55")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.56"), resultadoYJSP); //$NON-NLS-1$
		
		mapaDeNavegacion.put(Maps.getString("Controlador.57"),opcionResultadoYJSP); //$NON-NLS-1$
		
		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		//// Mapa de navegación de usuarios REGISTRADOS \\\\
		resultadoYJSP.put(Maps.getString("Controlador.58"),Maps.getString("Controlador.59")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.60"), resultadoYJSP);	 //$NON-NLS-1$
		
		// Editar Usuarios, Tareas y Categorias
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.61"),Maps.getString("Controlador.62")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.63"),Maps.getString("Controlador.64")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.65"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.66"),Maps.getString("Controlador.67")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.68"),Maps.getString("Controlador.69")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.70"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.71"),Maps.getString("Controlador.72")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.73"),Maps.getString("Controlador.74")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.75"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.76"),Maps.getString("Controlador.77")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.78"),Maps.getString("Controlador.79")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.80"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.81"),Maps.getString("Controlador.82")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.83"),Maps.getString("Controlador.84")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.85"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.86"),Maps.getString("Controlador.87")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.88"),Maps.getString("Controlador.89")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.90"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.91"),Maps.getString("Controlador.92")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.93"),Maps.getString("Controlador.94")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.95"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.96"),Maps.getString("Controlador.97")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.98"),Maps.getString("Controlador.99")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.100"), resultadoYJSP); //$NON-NLS-1$
		
		//Listar Tareas y Categorias
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.101"),Maps.getString("Controlador.102")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.103"),Maps.getString("Controlador.104")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.105"), resultadoYJSP); //$NON-NLS-1$
			
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.106"),Maps.getString("Controlador.107")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.108"),Maps.getString("Controlador.109")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.110"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.111"),Maps.getString("Controlador.112")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.113"),Maps.getString("Controlador.114")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.115"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.116"),Maps.getString("Controlador.117")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.118"),Maps.getString("Controlador.119")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.120"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.121"),Maps.getString("Controlador.122")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.123"),Maps.getString("Controlador.124")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.125"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.126"),Maps.getString("Controlador.127")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.128"),Maps.getString("Controlador.129")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.130"), resultadoYJSP); //$NON-NLS-1$
				
		mapaDeNavegacion.put(Maps.getString("Controlador.131"),opcionResultadoYJSP); //$NON-NLS-1$
		
		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		 ////Mapa de navegación del ADMIN \\\\
		resultadoYJSP.put(Maps.getString("Controlador.132"),Maps.getString("Controlador.133")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.134"), resultadoYJSP); //$NON-NLS-1$
		
		//Gestionar Usuarios
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.135"),Maps.getString("Controlador.136")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.137"),Maps.getString("Controlador.138")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.139"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.140"),Maps.getString("Controlador.141")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.142"),Maps.getString("Controlador.143")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.144"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.145"),Maps.getString("Controlador.146")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.147"),Maps.getString("Controlador.148")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.149"), resultadoYJSP);	 //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.150"),Maps.getString("Controlador.151")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.152"),Maps.getString("Controlador.153")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.154"), resultadoYJSP); //$NON-NLS-1$
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put(Maps.getString("Controlador.155"),Maps.getString("Controlador.156")); //$NON-NLS-1$ //$NON-NLS-2$
		resultadoYJSP.put(Maps.getString("Controlador.157"),Maps.getString("Controlador.158")); //$NON-NLS-1$ //$NON-NLS-2$
		opcionResultadoYJSP.put(Maps.getString("Controlador.159"), resultadoYJSP); //$NON-NLS-1$
		
		mapaDeNavegacion.put(Maps.getString("Controlador.160"),opcionResultadoYJSP); //$NON-NLS-1$
	}
			
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}