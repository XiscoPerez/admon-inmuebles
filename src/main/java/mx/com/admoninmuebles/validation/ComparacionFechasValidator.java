package mx.com.admoninmuebles.validation;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import mx.com.admoninmuebles.dto.NotificacionDto;

public class ComparacionFechasValidator  implements ConstraintValidator<ComparacionFechas, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		final NotificacionDto notificacionDto = (NotificacionDto) value;
			Date hoy = new Date();
			if(notificacionDto.getFechaExposicionInicial().before(hoy) || notificacionDto.getFechaExposicionFinal().before(hoy)){
				return false;
			}else if(notificacionDto.getFechaExposicionFinal().before( notificacionDto.getFechaExposicionInicial())){
				return false;
			} else {
				return true;
			}
		        
	}

}
