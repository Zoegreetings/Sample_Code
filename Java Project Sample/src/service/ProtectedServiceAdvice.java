package edu.gatech.saad.p3.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ProtectedServiceAdvice {
	
	@Around("target(edu.gatech.saad.p3.service.MainService) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object mainServiceWrapper(ProceedingJoinPoint pjp) throws Throwable{
		Object result = pjp.proceed();		
		return result;
	}

}
