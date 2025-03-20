import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const accessToken = localStorage.getItem('accessToken');
  const excludedUrls = ['/api/auth/login', '/api/auth/signup'];

  // Check if the request URL should be excluded
  const isExcluded = excludedUrls.some(url => req.url.includes(url));

  if (accessToken && !isExcluded) {
    // Clone and modify the request to add Authorization header
    const clonedRequest = req.clone({
      setHeaders: { Authorization: `Bearer ${accessToken}` }
    });
    return next(clonedRequest);
  }

  return next(req);
};
