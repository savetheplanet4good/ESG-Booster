package org.synechron.esg.alternative.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * The type Logging aspect.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);
    /**
     * The Execution.
     */
    final String EXECUTION = "Execution of ";
    /**
     * The Hyphan.
     */
    final String HYPHAN = "--";
    /**
     * The Space.
     */
    final String SPACE = " ";
    /**
     * The Started.
     */
    final String STARTED = " Started ";
    /**
     * The Args.
     */
    final String ARGS = "Arg : {}";
    /**
     * The Execution time.
     */
    final String EXECUTION_TIME = "Execution time of ";
    /**
     * The Completed.
     */
    final String COMPLETED = " Completed ";
    /**
     * The Dot.
     */
    final String DOT = ".";
    /**
     * The Colon.
     */
    final String COLON = ":";
    /**
     * The Miliseconds.
     */
    final String MILISECONDS = "ms";

    /**
     * Profile all methods object.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @return the object
     * @throws Throwable the throwable
     */
//AOP expression for which methods shall be intercepted
    @Around("execution(* org.synechron..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Object[] args = proceedingJoinPoint.getArgs();
        LOGGER.info(EXECUTION + className + SPACE + HYPHAN + SPACE + methodName + STARTED);
        if (args != null) {
            for (Object signatureArg : args) {
                LOGGER.debug(ARGS, signatureArg);
            }
        }
        final StopWatch stopWatch = new StopWatch();
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        //Log method execution time
        LOGGER.info(EXECUTION_TIME + className + DOT + methodName + SPACE + COLON + COLON + SPACE + stopWatch.getTotalTimeMillis() + MILISECONDS);
        LOGGER.info(EXECUTION + SPACE + className + SPACE + HYPHAN + SPACE + methodName + COMPLETED);
        return result;
    }
}