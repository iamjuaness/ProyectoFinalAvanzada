import { inject } from '@angular/core';
import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { TokenService } from '../services/token.service';
import { AuthService } from '../services/auth-service.service';
import { catchError, from, switchMap, throwError } from 'rxjs';


export const usuarioInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const authService = inject(AuthService); // Inyecta el servicio AuthService

  const isApiUrl = req.url.includes("api/auth");
  const isAPiPublico = req.url.includes("api/publico");
  
  if (!tokenService.isLogged() || isApiUrl || isAPiPublico) {
    return next(req);
  }

  const token = tokenService.getToken();
  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        // Si el error es 401, intenta actualizar el token
        return from(authService.refresh()).pipe(
          switchMap(() => {
            // Si la actualización del token fue exitosa, clona la solicitud original con el nuevo token
            const newToken = tokenService.getToken();
            const newAuthReq = req.clone({
              setHeaders: {
                Authorization: `Bearer ${newToken}`
              }
            });
            // Envía la solicitud nuevamente con el nuevo token actualizado
            return next(newAuthReq);
          }),
          catchError((refreshError: any) => {
            // Maneja cualquier error al intentar actualizar el token
            console.error('Error al actualizar el token', refreshError);
            return throwError(error); // Propaga el error original
          })
        );
      } else {
        return throwError(error); // Propaga cualquier otro error diferente de 401
      }
    })
  );
};

