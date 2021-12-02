package protoGreet.hello

object GreeterGrpc {
  val METHOD_DETERMINE_KEY_RANGE: _root_.io.grpc.MethodDescriptor[protoGreet.hello.KeyRange, protoGreet.hello.DummyText] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoGreet.Greeter", "determineKeyRange"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoGreet.hello.KeyRange])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoGreet.hello.DummyText])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoGreet.hello.HelloProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val METHOD_ASSIGN_ID: _root_.io.grpc.MethodDescriptor[protoGreet.hello.GreeterRequest, protoGreet.hello.ID] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoGreet.Greeter", "assignID"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoGreet.hello.GreeterRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoGreet.hello.ID])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoGreet.hello.HelloProto.javaDescriptor.getServices().get(0).getMethods().get(1)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("protoGreet.Greeter")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(protoGreet.hello.HelloProto.javaDescriptor))
      .addMethod(METHOD_DETERMINE_KEY_RANGE)
      .addMethod(METHOD_ASSIGN_ID)
      .build()
  
  trait Greeter extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = Greeter
    def determineKeyRange(request: protoGreet.hello.KeyRange): scala.concurrent.Future[protoGreet.hello.DummyText]
    def assignID(request: protoGreet.hello.GreeterRequest): scala.concurrent.Future[protoGreet.hello.ID]
  }
  
  object Greeter extends _root_.scalapb.grpc.ServiceCompanion[Greeter] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[Greeter] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = protoGreet.hello.HelloProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = protoGreet.hello.HelloProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: Greeter, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_DETERMINE_KEY_RANGE,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoGreet.hello.KeyRange, protoGreet.hello.DummyText] {
          override def invoke(request: protoGreet.hello.KeyRange, observer: _root_.io.grpc.stub.StreamObserver[protoGreet.hello.DummyText]): Unit =
            serviceImpl.determineKeyRange(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_ASSIGN_ID,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoGreet.hello.GreeterRequest, protoGreet.hello.ID] {
          override def invoke(request: protoGreet.hello.GreeterRequest, observer: _root_.io.grpc.stub.StreamObserver[protoGreet.hello.ID]): Unit =
            serviceImpl.assignID(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait GreeterBlockingClient {
    def serviceCompanion = Greeter
    def determineKeyRange(request: protoGreet.hello.KeyRange): protoGreet.hello.DummyText
    def assignID(request: protoGreet.hello.GreeterRequest): protoGreet.hello.ID
  }
  
  class GreeterBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterBlockingStub](channel, options) with GreeterBlockingClient {
    override def determineKeyRange(request: protoGreet.hello.KeyRange): protoGreet.hello.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_DETERMINE_KEY_RANGE, options, request)
    }
    
    override def assignID(request: protoGreet.hello.GreeterRequest): protoGreet.hello.ID = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_ASSIGN_ID, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterBlockingStub = new GreeterBlockingStub(channel, options)
  }
  
  class GreeterStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterStub](channel, options) with Greeter {
    override def determineKeyRange(request: protoGreet.hello.KeyRange): scala.concurrent.Future[protoGreet.hello.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_DETERMINE_KEY_RANGE, options, request)
    }
    
    override def assignID(request: protoGreet.hello.GreeterRequest): scala.concurrent.Future[protoGreet.hello.ID] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_ASSIGN_ID, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterStub = new GreeterStub(channel, options)
  }
  
  def bindService(serviceImpl: Greeter, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = Greeter.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): GreeterBlockingStub = new GreeterBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): GreeterStub = new GreeterStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = protoGreet.hello.HelloProto.javaDescriptor.getServices().get(0)
  
}