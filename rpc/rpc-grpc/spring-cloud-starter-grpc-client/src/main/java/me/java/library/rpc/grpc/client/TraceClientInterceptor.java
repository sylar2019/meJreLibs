package me.java.library.rpc.grpc.client;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.cloud.sleuth.Tracer;

/**
 * User: Michael
 * Email: yidongnan@gmail.com
 * Date: 2016/12/8
 */
@Slf4j
public class TraceClientInterceptor implements ClientInterceptor {

    private final SpanInjector<Metadata> spanInjector;
    private Tracer tracer;

    public TraceClientInterceptor(Tracer tracer, SpanInjector<Metadata> spanInjector) {
        this.tracer = tracer;
        this.spanInjector = spanInjector;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(final MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ClientInterceptors.CheckedForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            protected void checkedStart(ClientCall.Listener<RespT> responseListener, Metadata headers)
                    throws StatusException {
                final Span span = tracer.createSpan("invoke gRPC:" + method.getFullMethodName());
                spanInjector.inject(span, headers);
                Listener<RespT> tracingResponseListener = new ForwardingClientCallListener
                        .SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onReady() {
                        span.logEvent(Span.CLIENT_SEND);
                        super.onReady();
                    }

                    @Override
                    public void onClose(Status status, Metadata trailers) {
                        span.logEvent(Span.CLIENT_RECV);
                        if (status.isOk()) {
                            log.debug("Call finish success");
                        } else {
                            log.warn("Call finish failed", status.getDescription());
                        }
                        tracer.close(span);
                        delegate().onClose(status, trailers);
                    }
                };
                delegate().start(tracingResponseListener, headers);
            }
        };
    }
}
