import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenService } from '../services/token.service';
import { from, switchMap } from 'rxjs';
import { response } from 'express';
import { error } from 'console';

export const usuarioInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const isApiUrl = req.url.includes("api/auth");
  const isApiPublic = req.url.includes("api/public");
  
  if (!tokenService.isLogged() || isApiUrl || isApiPublic) {
    return next(req);
  }

  return from(checkAndUpdateToken(tokenService)).pipe(
    switchMap(() => {
      const token = tokenService.getToken();
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next(authReq);
    })
  );
};

function checkAndUpdateToken(tokenService: TokenService): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    const token = tokenService.getToken();
    if (!token) {
      resolve();
      return;
    }
    const tokenExpiration = tokenService.getTokenExpiration();
    const now = Date.now();
    if (tokenExpiration && tokenExpiration > now) {
      // Token aún válido
      resolve();
    } else {
      // Token vencido, intentar actualizar
      tokenService.refreshToken().then((response) => {
        tokenService.setToken(response.data.respuesta)
      })
      .catch((error) => {
        console.log('No se pudo actualizar el token', error)
      })
    }
  });
}

