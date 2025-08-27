package org.synechron.portfolio.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * The type Logging aspect.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * The Execution.
     */
    static final String EXECUTION = "Execution of ";

    /**
     * The Hyphan.
     */
    static final String HYPHAN = "--";

    /**
     * The Space.
     */
    static final String SPACE = " ";

    /**
     * The Started.
     */
    static final String STARTED = " Started ";

    /**
     * The Args.
     */
    static final String ARGS = "Arg : {}";

    /**
     * The Execution time.
     */
    static final String EXECUTION_TIME = "Execution time of ";

    /**
     * The Completed.
     */
    static final String COMPLETED = " Completed ";

    /**
     * The Dot.
     */
    static final String DOT = ".";

    /**
     * The Colon.
     */
    static final String COLON = ":";

    /**
     * The Miliseconds.
     */
    static final String MILISECONDS = "ms";

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

        LOGGER.info("{} {} {} {} {}", EXECUTION, className, HYPHAN, methodName, STARTED);
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
        LOGGER.info("{}{}{}{} {}{} {} {}", EXECUTION_TIME, className, DOT, methodName, COLON, COLON, stopWatch.getTotalTimeMillis(), MILISECONDS);
        LOGGER.info("{} {} {} {} {}", EXECUTION, className, HYPHAN, methodName, COMPLETED);
        return result;
    }
}