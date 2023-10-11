package com.techtrove.ecommerce.core.interfaces;


import com.techtrove.ecommerce.core.models.dto.ServiceModel;
import com.techtrove.ecommerce.core.models.dto.Transaction;

import java.util.Optional;

/**
 *
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Date: 11/05/23
 */
public interface TransaccionalService {

    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: ---
     * @Date: 07/08/23
     * @param serviceModel
     * @return Optional<Transaction>
     */
    Optional<Transaction> procesaTransaccion(ServiceModel serviceModel);



}
