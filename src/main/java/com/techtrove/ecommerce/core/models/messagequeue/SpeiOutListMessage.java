package com.techtrove.ecommerce.core.models.messagequeue;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeiOutListMessage {

    private List<SpeiOutMessage> speiOutList;

    public List<SpeiOutMessage> getSpeiOutList(){
        if(speiOutList == null)
            speiOutList = new ArrayList<SpeiOutMessage>();
            return speiOutList;
    }
}
