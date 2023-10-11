package com.techtrove.ecommerce.core.utils;

import com.techtrove.ecommerce.core.constants.General;
import com.techtrove.ecommerce.core.enums.PagopopErrorCode;
import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import com.techtrove.ecommerce.core.enums.GenericErrorCode;
import com.techtrove.ecommerce.core.models.exception.AbstractCoreException;
import lombok.experimental.UtilityClass;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.techtrove.ecommerce.core.utils.StringUtils.lineBreakRemove;

@UtilityClass
public class ExceptionUtils {


    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: convierte toda la pila de una excepcion en un string
     * @Date: 05/08/23
     * @param e
     * @return String
     */
    public String getSimplifiedStackTrace(Throwable e) {
        return getSimplifiedStackTrace(e, true);
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: convierte toda la pila de una excepcion en un string
     *               de manera recursiva.
     * @Date: 05/08/23
     * @param e
     * @param recursiveCauseLog
     * @return String
     */
    private String getSimplifiedStackTrace(Throwable e, boolean recursiveCauseLog) {
        String args = Optional.ofNullable(e)
                .filter(ex -> ex instanceof AbstractCoreException)
                .map(ex -> ((AbstractCoreException) ex).getErrorCodeInstance())
                .map(ErrorCode.ErrorCodeInstance::getArgs)
                .map(args1 -> args1.stream().map(a -> a.getArg() + ":" + a.getValue()).collect(Collectors.joining(",")))
                .orElse("NO_ARGS");

        final String errorCode = e instanceof AbstractCoreException ?
                ((AbstractCoreException) e).getErrorCodeInstance() != null && ((AbstractCoreException) e).getErrorCodeInstance().getErrorCode() != null ?
                ((AbstractCoreException) e).getErrorCodeInstance().getErrorCode().name() : "UNKNOWN_ERROR_CODE" : General.CADENA_VACIA;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String sStackTrace = lineBreakRemove(sw.toString());
        return e.getClass() + " : " + errorCode + " : " + e.getMessage() + " - args=" + args + " - " + sStackTrace
                + (recursiveCauseLog ? Optional.ofNullable(e.getCause())
                .map(cause -> " cause: " + getSimplifiedStackTrace(cause, false))
                .orElse(General.CADENA_VACIA) : General.CADENA_VACIA);
    }


    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: devuelve una instancia de ErrorCode, basicamente en base al parametro name, busca una
     *               coincidencia en el enum que se pasa como parametro[type] en el caso de este proyecto spei
     *               type suele ser el enum SpeiErrorCode, una vez que se encuentra una conicidencia entre name
     *               y los distintos enum que se encuentran en SpeiErrorCode, se genera la instancia ErrorCode.
     *               Si no se encuentra coincidencia, se busca en un segundo enum(PagopopErrorCode), si tampoco encuentra
     *               una coicidencia busca en un tercer Enum en este caso GenericErrorCode,
     *               si tampoco se encuentra conicidencia en ester ultimo se regresa un valor pordefault.
     * @Date: 05/08/23
     * @param type
     * @param name
     * @return ErrorCode
     */
    public ErrorCode getError(Type type, String name) {
        try {
            return (ErrorCode) createEnumInstance(name, type);
        } catch (IllegalArgumentException e) {
            try {
                return (ErrorCode) createEnumInstance(name, PagopopErrorCode.class);
            }catch (IllegalArgumentException i) {
                try {
                    return (ErrorCode) createEnumInstance(name, GenericErrorCode.class);
                }catch (Exception ex) {
                    return GenericErrorCode.GENERIC_ERROR;
                }
            }
        }

    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: devuelve un enum, en base al parametro name, este enum es buscado en el enum que se pasa en el
     *               parametro Type
     * @Date: 05/08/23
     * @param name
     * @param type
     * @return T
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> T createEnumInstance(String name, Type type) {
        return Enum.valueOf((Class<T>) type, name);
    }


}
