package com.tienda.bladis.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.tienda.bladis.dtos.UsuarioActualizarDto;
import com.tienda.bladis.dtos.UsuarioDto;
import com.tienda.bladis.enums.Rol;
import com.tienda.bladis.exceptions.FormatoInvalidoException;
import com.tienda.bladis.exceptions.RecursoNoEncontradoException;
import com.tienda.bladis.models.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioService {

    private static final String COLECCION_USUARIOS = "usuarios";

    @Value("${tienda.admins:}")
    private String adminsConfig;

    // 1. Busca el usuario en Firestore. Si es su primera vez, lo crea y lo guarda en la nube.
    public Usuario obtenerOCrearUsuario(String uid, String email, String nombreFirebase) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentSnapshot documento = db.collection(COLECCION_USUARIOS).document(uid).get().get();

            if (documento.exists()) {
                return documento.toObject(Usuario.class);
            }

            int nuevoId = calcularSiguienteId(db);

            Rol rolAsignado = Rol.CLIENTE;
            if (esAdmin(email)) {
                rolAsignado = Rol.ADMIN;
            }

            String nombreInicial = nombreFirebase;
            if (nombreInicial == null || nombreInicial.isBlank()) {
                nombreInicial = email;
            }

            Usuario nuevo = new Usuario(
                    nuevoId,
                    uid,
                    email,
                    nombreInicial,
                    "",
                    rolAsignado,
                    LocalDate.now().toString()
            );

            // Guardamos el nuevo usuario en Firestore usando su uid como nombre del documento
            db.collection(COLECCION_USUARIOS).document(uid).set(nuevo).get();
            return nuevo;

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error al conectar con Firestore", e);
        }
    }

    public UsuarioDto actualizarPerfil(Usuario usuarioActual, UsuarioActualizarDto dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new FormatoInvalidoException("El nombre no puede estar vacío");
        }

        usuarioActual.setNombre(dto.getNombre().trim());
        if (dto.getDireccionEnvio() != null) {
            usuarioActual.setDireccionEnvio(dto.getDireccionEnvio().trim());
        } else {
            usuarioActual.setDireccionEnvio("");
        }

        try {
            Firestore db = FirestoreClient.getFirestore();
            // Sobrescribimos el documento en Firestore con los datos actualizados
            db.collection(COLECCION_USUARIOS).document(usuarioActual.getUid()).set(usuarioActual).get();
            return convertirADto(usuarioActual);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error al actualizar el usuario en Firestore", e);
        }
    }

    public List<UsuarioDto> listarTodos() {
        try {
            Firestore db = FirestoreClient.getFirestore();
            List<QueryDocumentSnapshot> documentos = db.collection(COLECCION_USUARIOS).get().get().getDocuments();

            List<UsuarioDto> lista = new ArrayList<>();
            for (QueryDocumentSnapshot doc : documentos) {
                Usuario u = doc.toObject(Usuario.class);
                lista.add(convertirADto(u));
            }
            return lista;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error al listar usuarios de Firestore", e);
        }
    }

    public UsuarioDto cambiarRol(Integer id, Rol nuevoRol) {
        if (nuevoRol == null) {
            throw new FormatoInvalidoException("Debe indicar un rol válido (CLIENTE o ADMIN)");
        }

        try {
            Firestore db = FirestoreClient.getFirestore();
            List<QueryDocumentSnapshot> documentos = db.collection(COLECCION_USUARIOS).get().get().getDocuments();

            for (QueryDocumentSnapshot doc : documentos) {
                Usuario u = doc.toObject(Usuario.class);
                if (u.getId() != null && u.getId().equals(id)) {
                    u.setRol(nuevoRol);
                    db.collection(COLECCION_USUARIOS).document(u.getUid()).set(u).get();
                    return convertirADto(u);
                }
            }
            throw new RecursoNoEncontradoException("No existe ningún usuario con ID: " + id);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error al cambiar el rol en Firestore", e);
        }
    }


    public UsuarioDto convertirADto(Usuario u) {
        return new UsuarioDto(
                u.getId(), u.getUid(), u.getEmail(), u.getNombre(),
                u.getDireccionEnvio(), u.getRol(), u.getFechaAlta()
        );
    }

    private int calcularSiguienteId(Firestore db) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documentos = db.collection(COLECCION_USUARIOS).get().get().getDocuments();
        int maxId = 0;
        for (QueryDocumentSnapshot doc : documentos) {
            Usuario u = doc.toObject(Usuario.class);
            if (u.getId() != null && u.getId() > maxId) {
                maxId = u.getId();
            }
        }
        return maxId + 1;
    }

    private boolean esAdmin(String email) {
        if (email == null || adminsConfig.isBlank()) {
            return false;
        }
        for (String admin : adminsConfig.split(",")) {
            if (admin.trim().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}