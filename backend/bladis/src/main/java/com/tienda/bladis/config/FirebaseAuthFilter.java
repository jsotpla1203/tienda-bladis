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

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            enviarError(response, 401, "Falta el token de autenticación o el formato es incorrecto");
            return;
        }

        String token = header.substring(7);

        try {
            FirebaseToken tokenVerificado = FirebaseAuth.getInstance().verifyIdToken(token);

            Usuario usuario = usuarioService.obtenerOCrearUsuario(
                    tokenVerificado.getUid(),
                    tokenVerificado.getEmail(),
                    tokenVerificado.getName()
            );
            usuarioActualService.setUsuarioActual(usuario);

            if (request.getRequestURI().startsWith("/api/admin") && usuario.getRol() != Rol.ADMIN) {
                enviarError(response, 403, "Acceso denegado: se requiere rol ADMIN");
                return;
            }

            filterChain.doFilter(request, response);

        } catch (FirebaseAuthException e) {
            enviarError(response, 401, "El token no es válido o ha caducado");
            System.out.println("MOTIVO EXACT0 FIREBASE: " + e.getMessage());
            enviarError(response, 401, "El token no es válido o ha caducado");
        }
    }

    private void enviarError(HttpServletResponse response, int codigoHttp, String mensaje) throws IOException {
        response.setStatus(codigoHttp);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"status\": " + codigoHttp + ", \"message\": \"" + mensaje + "\"}");
    }
}