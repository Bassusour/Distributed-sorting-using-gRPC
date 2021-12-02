// import io.grpc.ServerInterceptors;

// class IPInterceptor implements ServerInterceptor {       
//   @Override        
//   public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {            
//     String ipAddress = call.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR).toString();
//     log.warn("Client IP address = {}", ipAddress);
//     return next.startCall(call, headers);        
// 	}
// }