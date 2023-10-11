package com.techtrove.ecommerce.core.interfaces;

import com.techtrove.ecommerce.core.models.errors.ErrorArgument;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface ErrorCode extends Serializable {


    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: Na.
     * @Date: 06/08/23
     * @return String
     */
    String getDescription();

    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: na.
     * @Date: 06/08/23
     * @return String
     */
    String name();

    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: getInstance.
     * @Date: 06/08/23
     * @return ErrorCodeInstance
     */
    default ErrorCodeInstance getInstance() {
        return new ErrorCodeInstance(this);
    }

    @Getter
    @ToString
    class ErrorCodeInstance implements Serializable {
        private static final long serialVersionUID = 6529685098267757690L;

        private final List<ErrorArgument> args = new ArrayList<>();

        private final ErrorCode errorCode;

        /**
         *
         * @param errorCode
         */
        ErrorCodeInstance(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        /**
         * @Author: Miguel A.R.S.
         * @Email: miguel.reyes@spinbyoxxo.com.mx
         * @Description: addArg.
         * @Date: 06/08/23
         * @param arg
         * @return ErrorCodeInstance
         */
        public ErrorCodeInstance addArg(ErrorArgument arg) {
            this.args.add(arg);
            return this;
        }

        /**
         *
         * @Author: Miguel A.R.S.
         * @Email: miguel.reyes@spinbyoxxo.com.mx
         * @Description: getErrorCode.
         * @Date: 06/08/23
         * @return ErrorCode
         */
        public ErrorCode getErrorCode() {
            return this.errorCode;
        }

    }

}
