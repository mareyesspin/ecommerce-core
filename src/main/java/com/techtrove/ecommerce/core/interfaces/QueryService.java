package com.techtrove.ecommerce.core.interfaces;

import com.techtrove.ecommerce.core.models.dto.ServiceModel;
import com.techtrove.ecommerce.core.models.response.MasterQueryResponse;

import java.util.Optional;

public interface QueryService {

    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo generico para consultas
     * @Date: 23/08/23
     * @param serviceModel
     * @return Optional<?>
     */
    Optional<? extends MasterQueryResponse> query(ServiceModel serviceModel);

}
