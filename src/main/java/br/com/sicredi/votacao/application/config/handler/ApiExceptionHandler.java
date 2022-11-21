package br.com.sicredi.votacao.application.config.handler;

import br.com.sicredi.votacao.core.exception.RecursoNaoEncontradoException;
import br.com.sicredi.votacao.core.exception.ServicoException;
import br.com.sicredi.votacao.core.exception.SessaoNaoConcluidaException;
import br.com.sicredi.votacao.core.exception.TempoExcedidoException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    protected ResponseEntity<Object> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex, WebRequest request){
        var problema = criarProblemaBuilder(HttpStatus.NOT_FOUND, TipoProblema.RECURSO_NAO_ENCONTRADO,
                "O recurso desejado não foi encontrado.")
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ServicoException.class)
    protected ResponseEntity<Object> handleServicoException(ServicoException ex, WebRequest request){
        var problema = criarProblemaBuilder(HttpStatus.UNPROCESSABLE_ENTITY, TipoProblema.ERRO_NEGOCIO,
                "O fluxo de negócio não obteve êxito.")
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(TempoExcedidoException.class)
    protected ResponseEntity<Object> handleTempoExcedidoException(TempoExcedidoException ex, WebRequest request) {
        var problema = criarProblemaBuilder(HttpStatus.NOT_FOUND, TipoProblema.TEMPO_DE_SESSAO_EXPIRADO,
                "O tempo para utilização do recurso expirou.")
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(SessaoNaoConcluidaException.class)
    protected ResponseEntity<Object> handleSessaoNaoConcluidaException(SessaoNaoConcluidaException ex, WebRequest request) {
        var problema = criarProblemaBuilder(HttpStatus.INTERNAL_SERVER_ERROR, TipoProblema.APLICACAO_ERRO,
                "Aplicação passou por problemas.")
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<MensagemErro> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(TipoProblema.PARAMETRO_INVALIDO.getTitulo(), ex);
        return Arrays.asList(
                MensagemErro.builder()
                        .codigo(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .mensagem("Solicitação Imprópria")
                        .campos(ex.getConstraintViolations().stream()
                                .map(constraint -> {
                                    String jsonName = getJsonPropertyValue(
                                            constraint.getPropertyPath().toString(), constraint.getRootBeanClass());
                                    String nome = StringUtils.isNotBlank(jsonName) ? jsonName :
                                            ((PathImpl)constraint.getPropertyPath()).getLeafNode().getName();
                                    String mensagem = constraint.getMessage() == null ? "Campo Obrigatório" : constraint.getMessage();
                                    String valor = constraint.getInvalidValue() == null ? "null" : constraint.getInvalidValue().toString();
                                    return new MensagemErro.Campo(nome, mensagem, valor);
                                }).collect(Collectors.toList())
                        )
                        .build());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problema> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error(TipoProblema.DADOS_INVALIDOS.getTitulo()
                .concat(", retornando " + HttpStatus.BAD_REQUEST));

        var problemType = TipoProblema.DADOS_INVALIDOS;
        var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        var bindingResult = ex.getBindingResult();
        var problema = criarProblemaBuilder(HttpStatus.BAD_REQUEST, problemType, detail)
                .objetos(bindingResult.getAllErrors().stream()
                        .map(objectError -> Problema.Objeto.builder()
                                .nome(objectError instanceof FieldError ?
                                        ((FieldError) objectError).getField() :
                                        objectError.getObjectName())
                                .mensagemUsuario(messageSource.getMessage(objectError, LocaleContextHolder.getLocale()))
                                .build())
                        .collect(Collectors.toList()))
                .build();


        return ResponseEntity.badRequest().body(problema);
    }

    private Problema.ProblemaBuilder criarProblemaBuilder(HttpStatus status, TipoProblema tipoProblema, String detalhe){
        return Problema.builder()
                .status(status)
                .detalhe(detalhe)
                .tipo(tipoProblema.getUri())
                .titulo(tipoProblema.getTitulo());
    }

    @SuppressWarnings("rawtypes")
    public String getJsonPropertyValue(String declaredName, Class classe) {
        Field[] fields = classe.getDeclaredFields();
        Map<String, String> map = new HashMap<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonProperty.class)) {
                String annotationValue = field.getAnnotation(JsonProperty.class).value();
                map.put(field.getName(), annotationValue);
            }
        }
        return map.get(declaredName);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
