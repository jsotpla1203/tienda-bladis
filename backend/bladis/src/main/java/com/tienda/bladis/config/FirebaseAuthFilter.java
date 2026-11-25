package com.tienda.bladis.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.tienda.bladis.enums.Rol;
import com.tienda.bladis.models.Usuario;
import com.tienda.bladis.services.UsuarioActualService;
import com.tienda.bladis.services.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FirebaseAuthFilter extends OncePerRequestFilter {

    private final UsuarioService usuarioService;
    private final UsuarioActualService usuarioActualService;

    public FirebaseAuthFilter(UsuarioService usuarioService, UsuarioActualService usuarioActualService) {
        this.usuarioService = usuarioService;
        this.usuarioActualService = usuarioActualService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) { /*Lee la cabecera HTTP Authorization para comprobar que viene el token*/
            enviarError(response, 401, "Falta el token de autenticación o el formato es incorrecto");
            return;
        }

        String token = header.substring(7); /*Recorta los primeros 7 caracteres, esto es el "Bearer " para quedarse solo con el token*/

        try {
            FirebaseToken tokenVerificado = FirebaseAuth.getInstance().verifyIdToken(token); /*Coge el token que envía el cliente y lo comprueba, para esto usa el json con las claves y los token*/

            Usuario usuario = usuarioService.obtenerOCrearUsuario( /*Busca el usuario en Firestore. Si es su primera vez, lo crea y lo guarda en la nube*/
                    tokenVerificado.getUid(),
                    tokenVerificado.getEmail(),
                    tokenVerificado.getName()
            );
            usuarioActualService.setUsuarioActual(usuario); /*Guarda el usuario en usuarioActual para saber qué cliente está operando sin tener que pasar su ID en la URL*/

            if (request.getRequestURI().startsWith("/api/admin") && usuario.getRol() != Rol.ADMIN) { /*Comprueba que el usuario tiene el rol Admin y usado la ruta de admin*/
                enviarError(response, 403, "Acceso denegado: se requiere rol ADMIN");
                return;
            }

            filterChain.doFilter(request, response); /*Si ha superado todos los filtros anteriores, entrega la petición al @RestController correspondiente*/

        } catch (FirebaseAuthException e) {
            enviarError(response, 401, "El token no es válido o ha caducado");
            /*System.out.println("MOTIVO EXACT0 FIREBASE: " + e.getMessage());
            * Esto es un mensaje utilizado para depurar anteriores errores
            * */
        }
    }

    private void enviarError(HttpServletResponse response, int codigoHttp, String mensaje) throws IOException { /*Metodo para devolver errores*/
        response.setStatus(codigoHttp);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"status\": " + codigoHttp + ", \"message\": \"" + mensaje + "\"}");
    }
}