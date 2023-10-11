package com.techtrove.ecommerce.core.interfaces;

import com.techtrove.ecommerce.core.models.dto.ServiceModel;
import com.techtrove.ecommerce.core.models.response.MasterQueryResponse;

import java.util.List;

public interface ListService {

    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: ---
     * @Date: 07/08/23
     * @param serviceModel
     * @return List<?>
     */
    List<? extends MasterQueryResponse> list(ServiceModel serviceModel);


}
