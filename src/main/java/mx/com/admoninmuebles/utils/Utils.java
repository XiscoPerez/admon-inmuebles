package mx.com.admoninmuebles.utils;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.security.CustomUserDetails;

public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public static String objectToJson(final Object object) {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        ObjectWriter ow = mapper.writer();
        String result = "{}";
        try {
            result = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.FINEST, "", e);
        }
        return result;
    }

    public static Optional<Usuario> getCurrentAuditor() {
        Optional<Usuario> optUsuario = Optional.empty();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
            optUsuario = Optional.of(((CustomUserDetails) authentication.getPrincipal()).getUsuario());
        }

        return optUsuario;
    }
}
