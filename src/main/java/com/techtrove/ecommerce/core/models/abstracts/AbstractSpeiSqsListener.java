package com.techtrove.ecommerce.core.models.abstracts;

import com.techtrove.ecommerce.core.constants.HttpHeaders;
import com.techtrove.ecommerce.core.models.dto.ParamsAuditDto;
import com.techtrove.ecommerce.core.models.messagequeue.ModelMessageSpeiQueues;
import com.techtrove.ecommerce.core.utils.ParamsAuditUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;


/**
 *
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Description: clase abstracta que se debe utilizar en aquellas clases donde  se vaya poner
 * un listener de sqs, ayuda a ejecutar en segundo plano una tarea
 * @Date: 22/09/23
 */
public abstract class AbstractSpeiSqsListener {

    @Qualifier("threadPoolTaskExecutor")
    protected   AsyncTaskExecutor messageListenerExecutor;


    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo abstracto que tendra que implementa la clase que herde desta
     * lo que se busca es que en este metodo se ponga la logica que se ejecutara en segundo plano
     * @Date: 22/09/23
     * @param speiInExecutionMessage
     */
    protected abstract void doProcess(ModelMessageSpeiQueues speiInExecutionMessage);


    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo que no se podra sobresescribir, este metodo es esencial para propagar los parametros
     *  de auditoria cuando se lea un sqs
     * @Date: 22/09/23
     * @param speiInExecutionMessage
     */
    protected final void run(ModelMessageSpeiQueues speiInExecutionMessage){
        ParamsAuditDto paramsAuditDto = speiInExecutionMessage.getParamsAuditDto();
        MDC.put(HttpHeaders.TRACE_ID, paramsAuditDto.getTraceID());
        ParamsAuditUtils.addParamsAuditToMdc(paramsAuditDto);
        doProcess(speiInExecutionMessage);


    }

}
