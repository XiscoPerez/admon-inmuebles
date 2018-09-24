package mx.com.admoninmuebles.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import mx.com.admoninmuebles.dto.NotificacionDto;

public class ComparacionFechasValidator  implements ConstraintValidator<ComparacionFechas, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		final NotificacionDto notificacionDto = (NotificacionDto) value;
		  return notificacionDto.getFechaExposicionFinal().after( notificacionDto.getFechaExposicionInicial() );
		        
	}

}
