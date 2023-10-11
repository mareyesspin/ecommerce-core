package com.techtrove.ecommerce.core.interceptors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.techtrove.ecommerce.core.constants.General;
import com.techtrove.ecommerce.core.models.logging.MaskedLogging;
import com.techtrove.ecommerce.core.utils.ExceptionUtils;
import com.techtrove.ecommerce.core.utils.ReflectionUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StopWatch;
import retrofit2.Invocation;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.techtrove.ecommerce.core.utils.RetrofitBuild;
import org.springframework.http.HttpHeaders;
import okio.Buffer;


@Slf4j
public class EcommerceCoreRfitLogInterceptor implements Interceptor {

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    private static final String SERVICE_NAME_ENVIRONMENT_VARIABLE = "HOSTNAME";
    private static final String REDACTED_STRING = "+*+*+*+*+";
    private final Predicate<String> loggingPredicate;
    private final Set<String> headersToRedact;


    private static final Set<Class<?>> PLAINTYPES =
            Stream.of(MultipartBody.class, MultipartBody.Part.class, String.class, Long.class,
            Character.class, LocalDateTime.class, ZonedDateTime.class, Date.class,
                    LocalDate.class, Integer.class, Double.class, BigInteger.class,
            Float.class, Boolean.class, BigDecimal.class).collect(Collectors.toSet());


    /**
     *
     * @param loggingPredicate
     * @param headersToRedact
     */
    private EcommerceCoreRfitLogInterceptor(Predicate<String> loggingPredicate, Set<String> headersToRedact) {
        this.loggingPredicate = Optional.ofNullable(loggingPredicate).orElse(message -> true);

        this.headersToRedact = headersToRedact;
    }


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        final Request request = chain.request();
        final StopWatch requestTime = new StopWatch();
        final Response response;
        final boolean isSensitiveLoggingEnabled = true;


        String hostname = Optional.ofNullable(System.getenv(SERVICE_NAME_ENVIRONMENT_VARIABLE))
                .orElse("local-service-instance");
        String service = hostname.replaceAll("service-.*$", "service");

        //log request data
        boolean logRequestBody = Optional.ofNullable(
                request.header(com.techtrove.ecommerce.core.constants.HttpHeaders.X_LOG_REQUEST_BODY))
                .map("true"::equals).orElse(true);

        logRequest(chain.connection(), request, isSensitiveLoggingEnabled, logRequestBody);

        try {
            //continue with the request
            requestTime.start();
            response = chain.proceed(chain.request());
            requestTime.stop();
        } catch (Exception e) {
            log.error("<-- HTTP FAILED: " + ExceptionUtils.getSimplifiedStackTrace(e));
            throw e;
        }
        boolean logResponseBody = Optional.ofNullable(
                request.header(com.techtrove.ecommerce.core.constants.HttpHeaders.X_LOG_RESPONSE_BODY))
                .map("true"::equals).orElse(true);

        //log response
        logResponse(chain, response, isSensitiveLoggingEnabled, requestTime.getTotalTimeMillis(), logResponseBody);

        return response;



    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: clase para configurar e incializar el interceptor.
     * @Date: 01/08/23
     * @return SpeiLoggingInterceptorConfigurer
     */
    public static SpeiLoggingInterceptorConfigurer configure() {
        return new SpeiLoggingInterceptorConfigurer();
    }


    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo principal que desencadena toda la logica para logear el request
     * @Date: 27/07/23
     * @param connection
     * @param request
     * @param isSensitiveLoggingEnabled
     * @param logRequestBody
     */
    private void logRequest(Connection connection,
                            Request request,
                            boolean isSensitiveLoggingEnabled,
                            boolean logRequestBody) {
        Object requestObject = null;
        Object clonedRequestBody = null;
        try {
            if (logRequestBody) {
                if (request.body() != null) {
                    requestObject = getRequestBodyInstance(request.tag(Invocation.class));
                }
                if (isSensitiveLoggingEnabled && requestObject != null) {
                    clonedRequestBody = deepClone(requestObject);
                    maskFields(clonedRequestBody, false);
                }
            }
            doLogRequest(isSensitiveLoggingEnabled ? clonedRequestBody
                    : requestObject, request, connection, logRequestBody);
        } catch (Exception e) {
            log.error("error in SpeiCoreRetrofitHttpLoggingInterceptor.logRequest : "
                    + ExceptionUtils.getSimplifiedStackTrace(e));
            try {
                if (requestObject != null) {
                    doLogRequest(requestObject, request, connection, logRequestBody);
                }
            } catch (IOException ioException) {
                log.error("error in SpeiCoreRetrofitHttpLoggingInterceptor.logRequest : "
                        + ExceptionUtils.getSimplifiedStackTrace(e));
            }
        }
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo principal que tiene la logica para loggear el response.
     * @Date: 27/07/23
     * @param chain
     * @param response
     * @param isSensitiveLoggingEnabled
     * @param totalTimeMillis
     * @param logResponseBody
     */
    private void logResponse(Chain chain,
                             Response response,
                             boolean isSensitiveLoggingEnabled,
                             long totalTimeMillis,
                             boolean logResponseBody) {
        try {
            doLogResponse(chain, response, isSensitiveLoggingEnabled, totalTimeMillis, logResponseBody);
        } catch (Exception e) {
            log.error("error in SpinRetrofitHttpLoggingInterceptor.logResponse : "
                    + ExceptionUtils.getSimplifiedStackTrace(e));
        }
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo secundario para loggear el response.
     * @Date: 27/07/23
     * @param chain
     * @param response
     * @param isSensitiveLoggingEnabled
     * @param requestTime
     * @param logResponseBody
     * @throws IOException IOException
     * @throws IllegalAccessException IllegalAccessException
     */
    private void doLogResponse(Chain chain,
                               Response response,
                               boolean isSensitiveLoggingEnabled,
                               long requestTime,
                               boolean logResponseBody) throws IOException, IllegalAccessException {
        final ResponseBody responseBody = logResponseBody ? response.body() : null;
        logMessage("<--SPEI-CORE: ".concat(String.valueOf(response.code()))
                .concat(General.CADENA_VACIA)
                .concat(response.message().isEmpty() ? General.CADENA_VACIA : General.ESPACIO_BLANCO + response.message())
                .concat(General.ESPACIO_BLANCO)
                .concat(response.request().url().toString())
                .concat(General.ESPACIO_BLANCO)
                .concat("[" + requestTime + "ms]"));
        logHeaders(response.headers());

        if (responseBody == null) {
            logMessage("<-- END HTTP");
        } else if (bodyHasUnknownEncoding(response.headers())) {
            logMessage("<-- END HTTP (encoded body omitted)");
        } else {
            final BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            final Buffer buffer = source.getBuffer();
            final String responseString = buffer.clone().readString(UTF8);

            if (responseBody.contentLength() != 0L) {
                logMessage(General.CADENA_VACIA);
                if (isSensitiveLoggingEnabled && response.isSuccessful()) {
                    final Object responseDTO = processResponseClassFromMethodSignature(responseString, chain.request().tag(Invocation.class));
                    if (responseDTO != null) {
                        maskFields(responseDTO, false);
                        logMessage(RetrofitBuild.objectMapper.writeValueAsString(responseDTO));
                    } else {
                        logMessage(responseString);
                    }
                } else {
                    logMessage(responseString);
                }
            }
            logMessage("<-- END HTTP (" + buffer.size() + "-byte body)");
        }
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo para procesar el logeo del request.
     * @Date: 01/08/23
     * @param redactedRequestBody
     * @param request
     * @param connection
     * @param logRequestBody
     * @throws IOException IOException
     */
    private void doLogRequest(Object redactedRequestBody, Request request, Connection connection, boolean logRequestBody) throws IOException {
        final Headers requestHeaders = request.headers();
        final RequestBody requestBody = logRequestBody ? request.body() : null;
        logMessage("--> ".concat(request.method()).concat(General.ESPACIO_BLANCO).concat(request.url().toString())
                .concat(General.ESPACIO_BLANCO)
                .concat(Optional.ofNullable(connection)
                        .map(conn -> conn.protocol().toString()).orElse(General.CADENA_VACIA)));

        if (requestBody != null) {
            if (requestHeaders.get(HttpHeaders.CONTENT_TYPE) != null) {
                logMessage(HttpHeaders.CONTENT_TYPE + ": " + requestHeaders.get(HttpHeaders.CONTENT_TYPE), LogLevel.TRACE);
            }
            if (requestBody.contentLength() != -1L && requestHeaders.get(HttpHeaders.CONTENT_LENGTH) == null) {
                logMessage(HttpHeaders.CONTENT_LENGTH + ": " + requestBody.contentLength(), LogLevel.TRACE);
            }
        }

        logHeaders(requestHeaders);

        if (requestBody == null) {
            logMessage("--> END ".concat(request.method()));
        } else if (bodyHasUnknownEncoding(requestHeaders)) {
            logMessage("--> END ".concat(request.method()).concat(General.ESPACIO_BLANCO).concat("(encoded body omitted)"));
        } else if (requestBody.isDuplex()) {
            logMessage("--> END ".concat(request.method()).concat(General.ESPACIO_BLANCO).concat("(duplex request body omitted)"));
        } else if (requestBody.isOneShot()) {
            logMessage("--> END ".concat(request.method()).concat(General.ESPACIO_BLANCO).concat("(one-shot body omitted)"));
        } else if (redactedRequestBody == null) {
            final Buffer buffer = new Buffer();
            assert request.body() != null;
            request.body().writeTo(buffer);
            final String requestString = buffer.clone().readString(UTF8);
            logMessage(General.CADENA_VACIA);
            logMessage(requestString);
            logMessage("--> END ".concat(request.method()).concat(General.ESPACIO_BLANCO).concat("(" + requestBody.contentLength() + "-byte body)"));
        } else {
            logMessage(General.CADENA_VACIA);
            logMessage(RetrofitBuild.objectMapper.writeValueAsString(redactedRequestBody));
            logMessage("--> END ".concat(request.method()).concat(" (" + requestBody.contentLength() + "-byte body)"));
        }
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: ---
     * @Date: 01/08/23
     * @param json
     * @param invocation
     * @return Object
     */
    private Object processResponseClassFromMethodSignature(String json, Invocation invocation) {
        if (invocation == null) {
            return null;
        }
        try {
            final Method method = invocation.method();
            final Field field = Method.class.getDeclaredField("signature");
            field.setAccessible(true);
            final String signature = (String) field.get(method);
            final String parentOutterMostClass = signature.substring(
                    signature.indexOf("Call<L") + 6, signature.indexOf(";", signature.indexOf("Call<L")))
                    .replace("/", ".");
            final boolean isGenericType = parentOutterMostClass.contains("<L");
            //in case the Call generic type has inner nested generic types:
            if (isGenericType) {
                final String wrapperGenericClassName = parentOutterMostClass.substring(0, parentOutterMostClass.indexOf("<L"));
                final String innerClassName = parentOutterMostClass.substring(parentOutterMostClass.indexOf("<L") + 2);
                final JavaType type = parseGenericDeclaredClass(wrapperGenericClassName, innerClassName);
                try {
                    return RetrofitBuild.objectMapper.readValue(json, type);
                } catch (Exception e) {
                    return null;
                }
            }
            try {
                return RetrofitBuild.objectMapper.readValue(json, Class.forName(parentOutterMostClass));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            log.error("error in spinRetrofitHttpLogging.processResponseClassFromMethodSignature Interceptor "
                    + ExceptionUtils.getSimplifiedStackTrace(e));
            return null;
        }
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: recursively create Generic Java Type that has all nested generic declaration information
     * @Date: 01/08/23
     * @param wrapperParentClassCanonicalName
     * @param innerGenericClassDeclarationSignature
     * @return JavaType
     * @throws ClassNotFoundException ClassNotFoundException
     */
    private JavaType parseGenericDeclaredClass(String wrapperParentClassCanonicalName,
                                               String innerGenericClassDeclarationSignature) throws ClassNotFoundException {
        //check if the child has another nested generic type class declared in the signature
        if (innerGenericClassDeclarationSignature.contains("<L")) {
            final String wrapperClassName = innerGenericClassDeclarationSignature.substring(0, innerGenericClassDeclarationSignature.indexOf("<L"));
            final String innerClassName = innerGenericClassDeclarationSignature.substring(innerGenericClassDeclarationSignature.indexOf("<L") + 2);
            return RetrofitBuild.objectMapper.getTypeFactory()
                    .constructParametricType(Class.forName(wrapperParentClassCanonicalName),
                            parseGenericDeclaredClass(wrapperClassName, innerClassName));
        } else {
            return RetrofitBuild.objectMapper.getTypeFactory()
                    .constructParametricType(Class.forName(wrapperParentClassCanonicalName),
                            Class.forName(innerGenericClassDeclarationSignature));
        }
    }


    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo para logear los headers, si uno de estos se encuentra en la lista
     *               de headers sensibles(headersToRedact) entonces se enmascara.
     * @Date: 30/07/23
     * @param headers
     */
    private void logHeaders(Headers headers) {
        headers.names()
                .forEach(
                        headerName -> logMessage(
                                headerName + ": "
                                        + (headersToRedact.contains(headerName) ? REDACTED_STRING : headers.get(headerName)), LogLevel.INFO));
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo para validar si en los headers viene especificado el encoding UTF-8
     * @Date: 30/07/23
     * @param headers
     * @return boolean
     */
    private boolean bodyHasUnknownEncoding(Headers headers) {
        final String contentEncoding = headers.get(HttpHeaders.CONTENT_ENCODING);
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo para validar si fielValues es una coleccion
     * @Date: 26/07/23
     * @param  fieldValue object
     * @return boolean
     */
    private boolean isCollectionType(Object fieldValue) {
        return fieldValue instanceof List || fieldValue instanceof Set || fieldValue instanceof Queue || fieldValue instanceof Map;
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: sin descripcion.
     * @Date: 01/08/23
     * @param dto
     * @param parentFieldHasMaskedLoggingAnnotation
     * @throws IllegalAccessException IllegalAccessException
     */
    private void maskFields(Object dto, boolean parentFieldHasMaskedLoggingAnnotation) throws IllegalAccessException {
        if (isCollectionType(dto)) {
            maskCollectionElements(dto, parentFieldHasMaskedLoggingAnnotation);
        } else {
            //all fields including inherited ones
            final List<Field> allFields = ReflectionUtils.getInheritedDeclaredFields(dto.getClass(), null);
            for (Field field : allFields) {
                field.setAccessible(true);
                final Object fieldValue = field.get(dto);
                if (isPlainMaskeableReferenceClass(field.getType())) {
                    //plain field, we can mask it
                    if (hasMaskedLoggingAnnotation(field) || parentFieldHasMaskedLoggingAnnotation) {
                        Object maskedValue = null;
                        if (fieldValue != null) {
                            if (fieldValue instanceof String) {
                                maskedValue = getMaskForLogging(field, (String) fieldValue);
                            }
                            field.set(dto, maskedValue);
                        }
                    }
                } else if (field.getType().isPrimitive()) {
                    if (hasMaskedLoggingAnnotation(field) || parentFieldHasMaskedLoggingAnnotation) {
                        maskPrimitiveField(dto, field);
                    }
                } else if (field.getType().isArray()) {
                    if (fieldValue != null) {
                        final Object[] array = ((Object[]) fieldValue);
                        for (int i = 0; i < array.length; i++) {
                            Object element = array[i];
                            if (element != null) {
                                if (isPlainMaskeableReferenceClass(element.getClass())) {
                                    if (hasMaskedLoggingAnnotation(field) || parentFieldHasMaskedLoggingAnnotation) {
                                        Object maskedValue = null;
                                        if (element instanceof String) {
                                            maskedValue = getMaskForLogging(field, (String) element);
                                        }
                                        array[i] = maskedValue;
                                    }
                                } else if (isCollectionType(element)) {
                                    maskCollectionElements(element, parentFieldHasMaskedLoggingAnnotation);
                                }
                            }
                        }
                    }
                } else if (fieldValue != null && (fieldValue.getClass().getCanonicalName().startsWith("com.spin"))) {
                    maskFields(fieldValue, hasMaskedLoggingAnnotation(field) || parentFieldHasMaskedLoggingAnnotation);
                } else if (isCollectionType(fieldValue)) {
                    maskCollectionElements(fieldValue, parentFieldHasMaskedLoggingAnnotation);
                }
            }
        }
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: con este metodo se le da tratamiento a los elemntos de una coleccion
     * @Date: 27/07/23
     * @param dto
     * @param parentFieldHasMaskedLoggingAnnotation
     * @throws IllegalAccessException IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private void maskCollectionElements(Object dto, boolean parentFieldHasMaskedLoggingAnnotation) throws IllegalAccessException {
        if (dto instanceof List) {
            final List<Object> list = ((List<Object>) dto);
            for (int i = 0; i < list.size(); i++) {
                Object element = list.get(i);
                if (element == null){
                    continue;
                }
                if (isPlainMaskeableReferenceClass(element.getClass()) || element.getClass().isPrimitive()) {
                    if (parentFieldHasMaskedLoggingAnnotation) {
                        list.remove(i);
                        if (element instanceof String) {
                            list.add(i, REDACTED_STRING);
                        }
                    }
                } else if (isCollectionType(element)) {
                    maskCollectionElements(element, parentFieldHasMaskedLoggingAnnotation);
                } else if (isCustomWrapperClass(element)) {
                    maskFields(element, parentFieldHasMaskedLoggingAnnotation);
                }

            }
        } else if (dto instanceof Set) {
            final Set<Object> set = ((Set<Object>) dto);
            final Object[] setElements = set.toArray();
            for (Object element : setElements) {
                if (element == null) {
                    continue;
                }
                if (isPlainMaskeableReferenceClass(element.getClass()) || element.getClass().isPrimitive()) {
                    if (parentFieldHasMaskedLoggingAnnotation) {
                        set.remove(element);
                        if (element instanceof String) {
                            set.add(REDACTED_STRING);
                        }
                    }
                } else if (isCollectionType(element)) {
                    maskCollectionElements(element, parentFieldHasMaskedLoggingAnnotation);
                } else if (isCustomWrapperClass(element)) {
                    maskFields(element, parentFieldHasMaskedLoggingAnnotation);
                }

            }
        } //TODO add support for MAP and queue
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo con el cual se valida si la clase que s epasa por parametro se considera un tipo de datos basico o plano
     * @Date: 27/07/23
     * @param clazz
     * @return boolean
     */
    private boolean isPlainMaskeableReferenceClass(Class<?> clazz) {
        return PLAINTYPES.contains(clazz) || clazz.isEnum();
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo con el cual se valida si el parametro que se pasa es un Objejto custom(model, dto)
     * @Date: 27/07/23
     * @param argument
     * @return boolean
     */
    private boolean isCustomWrapperClass(Object argument) {
        return !argument.getClass().isPrimitive()
                && !argument.getClass().isArray() && !isCollectionType(argument)
                && !argument.getClass().isEnum() && !PLAINTYPES.contains(argument.getClass());
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: Metodo para validar si el Field esta anotado con la anotacion @MaskedLogging
     * @Date: 27/07/23
     * @param field
     * @return boolean
     */
    private boolean hasMaskedLoggingAnnotation(Field field) {
        return field.getAnnotation(MaskedLogging.class) != null;
    }
    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: ---.
     * @Date: 27/07/23
     * @param invocation
     * @return Object
     */
    private Object getRequestBodyInstance(Invocation invocation) {
        return invocation != null ? Optional.of(invocation.arguments())
                        .flatMap(arguments -> arguments.stream().filter(Objects::nonNull).filter(argument -> !argument.getClass().isPrimitive()
                                && !argument.getClass().isArray()
                                && !argument.getClass().isEnum()
                                && !PLAINTYPES.contains(argument.getClass())).findAny()).orElse(null) : null;
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: Metodo que enmascara quellos atributos que fueron anotados con @MaskedLogging
     * @Date: 27/07/23
     * @param field
     * @param fieldValue
     * @return String
     */
    private String getMaskForLogging(Field field, String fieldValue) {
        try {
            MaskedLogging maskedLogging = field.getAnnotation(MaskedLogging.class);
            String defaultMask = (!maskedLogging.defaultMask().equals("*")) ? maskedLogging.defaultMask() : REDACTED_STRING;
            String value = maskedLogging.initPosition() > 0 ? fieldValue.substring(0, maskedLogging.initPosition()):"";
            return value.concat(defaultMask);
        } catch (Exception ex) {
            return REDACTED_STRING;
        }
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo con el cual se le da tratamiento a los tipos de datos primitvos
     * @Date: 27/07/23
     * @param dto
     * @param field
     * @throws IllegalAccessException IllegalAccessException
     */
    private void maskPrimitiveField(Object dto, Field field) throws IllegalAccessException {
        if (field.getType() == float.class) {
            field.set(dto, 0.0f);
        }
        if (field.getType() == double.class) {
            field.set(dto, 0.0d);
        }
        if (field.getType() == short.class) {
            field.set(dto, (short) 0);
        }
        if (field.getType() == int.class) {
            field.set(dto, 0);
        }
        if (field.getType() == byte.class) {
            field.set(dto, (byte) 0);
        }
        if (field.getType() == long.class) {
            field.set(dto, 0L);
        }
        if (field.getType() == char.class) {
            field.set(dto, '*');
        }
        if (field.getType() == boolean.class) {
            field.set(dto, false);
        }
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: Metodo para realizar un clonado a profundidad
     * @Date: 27/07/23
     * @param e
     * @return Object
     * @throws JsonProcessingException JsonProcessingException
     */
    private Object deepClone(Object e) throws JsonProcessingException {
        return RetrofitBuild.objectMapper.readValue(RetrofitBuild.objectMapper.writeValueAsString(e), e.getClass());
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo para loggear.
     * @Date: 27/07/23
     * @param message
     */
    private void logMessage(String message) {
        logMessage(message, LogLevel.INFO);
    }


    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo para loggear.
     * @Date: 27/07/23
     * @param message
     * @param logLevel
     */
    private void logMessage(String message, LogLevel logLevel) {
        //default to ingore byte logs
        if (!message.contains("�") && loggingPredicate.test(message)) {
            switch (logLevel) {
                case INFO:
                    log.info(message);
                    break;
                case WARN:
                    log.warn(message);
                    break;
                case DEBUG:
                    log.debug(message);
                    break;
                case TRACE:
                    log.trace(message);
                    break;
                case ERROR:
                    log.error(message);
                    break;

            }
        }
    }



    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: Enum privado para definir los posibles niveles de Log
     * @Date: 27/07/23
     */
    private enum LogLevel {
        TRACE, DEBUG, INFO, WARN, ERROR
    }

    @NoArgsConstructor
    public static class SpeiLoggingInterceptorConfigurer {

        private Predicate<String> loggingPredicate;



        private final Set<String> redactHeaders = new HashSet<>();

        /**
         *
         * @Author: Miguel A.R.S.
         * @Email: miguel.reyes@spinbyoxxo.com.mx
         * @Description: pendiente.
         * @Date: 01/08/23
         * @param condition
         * @return SpeiLoggingInterceptorConfigurer
         */
        public SpeiLoggingInterceptorConfigurer withLoggingCondition(Predicate<String> condition) {
            this.loggingPredicate = condition;
            return this;
        }


        /**
         *
         * @Author: Miguel A.R.S.
         * @Email: miguel.reyes@spinbyoxxo.com.mx
         * @Description: metodo para agregar los headers que se enmascararan.
         * @Date: 01/08/23
         * @param headerNames
         * @return SpeiLoggingInterceptorConfigurer
         */
        public SpeiLoggingInterceptorConfigurer redactHeaders(String... headerNames) {
            this.redactHeaders.addAll(Arrays.asList(headerNames));
            return this;
        }

        /**
         *
         * @Author: Miguel A.R.S.
         * @Email: miguel.reyes@spinbyoxxo.com.mx
         * @Description: crea una instancia de SpeiCoreRetrofitHttpLoggingInterceptor.
         * @Date: 01/08/23
         * @return SpeiCoreRetrofitHttpLoggingInterceptor
         */
        public EcommerceCoreRfitLogInterceptor instance() {
            return new EcommerceCoreRfitLogInterceptor(loggingPredicate, redactHeaders);
        }

    }

}
