package mx.com.admoninmuebles.listener.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import lombok.Data;
import mx.com.admoninmuebles.dto.UsuarioDto;

@Data
public class OnRecuperacionContraseniaEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = -1505878685668697912L;
	
	private final String appUrl;
    private final Locale locale;
    private final UsuarioDto usuarioDto;

    public OnRecuperacionContraseniaEvent(final UsuarioDto usuarioDto, final Locale locale, final String appUrl) {
        super(usuarioDto);
        this.usuarioDto = usuarioDto;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
