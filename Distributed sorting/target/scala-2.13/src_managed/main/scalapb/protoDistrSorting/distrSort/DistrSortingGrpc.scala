package protoDistrSorting.distrSort

object DistrSortingGrpc {
  val METHOD_DETERMINE_KEY_RANGE: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.KeyRange, protoDistrSorting.distrSort.DummyText] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "determineKeyRange"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.KeyRange])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val METHOD_ASSIGN_ID: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.ID] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "assignID"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.ID])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(1)))
      .build()
  
  val METHOD_IS_DONE_PARTITIONING: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.DummyText] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "isDonePartitioning"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(2)))
      .build()
  
  val METHOD_SEND_PARTITIONED_VALUES: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.PartitionedValues] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "sendPartitionedValues"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.PartitionedValues])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(3)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("protoDistrSorting.DistrSorting")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor))
      .addMethod(METHOD_DETERMINE_KEY_RANGE)
      .addMethod(METHOD_ASSIGN_ID)
      .addMethod(METHOD_IS_DONE_PARTITIONING)
      .addMethod(METHOD_SEND_PARTITIONED_VALUES)
      .build()
  
  trait DistrSorting extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = DistrSorting
    def determineKeyRange(request: protoDistrSorting.distrSort.KeyRange): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText]
    def assignID(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.ID]
    def isDonePartitioning(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText]
    def sendPartitionedValues(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.PartitionedValues]
  }
  
  object DistrSorting extends _root_.scalapb.grpc.ServiceCompanion[DistrSorting] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[DistrSorting] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = protoDistrSorting.distrSort.DistrSortProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: DistrSorting, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_DETERMINE_KEY_RANGE,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.KeyRange, protoDistrSorting.distrSort.DummyText] {
          override def invoke(request: protoDistrSorting.distrSort.KeyRange, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.DummyText]): Unit =
            serviceImpl.determineKeyRange(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_ASSIGN_ID,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.ID] {
          override def invoke(request: protoDistrSorting.distrSort.DummyText, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.ID]): Unit =
            serviceImpl.assignID(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_IS_DONE_PARTITIONING,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.DummyText] {
          override def invoke(request: protoDistrSorting.distrSort.DummyText, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.DummyText]): Unit =
            serviceImpl.isDonePartitioning(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_SEND_PARTITIONED_VALUES,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.PartitionedValues] {
          override def invoke(request: protoDistrSorting.distrSort.DummyText, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.PartitionedValues]): Unit =
            serviceImpl.sendPartitionedValues(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait DistrSortingBlockingClient {
    def serviceCompanion = DistrSorting
    def determineKeyRange(request: protoDistrSorting.distrSort.KeyRange): protoDistrSorting.distrSort.DummyText
    def assignID(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.ID
    def isDonePartitioning(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText
    def sendPartitionedValues(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.PartitionedValues
  }
  
  class DistrSortingBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[DistrSortingBlockingStub](channel, options) with DistrSortingBlockingClient {
    override def determineKeyRange(request: protoDistrSorting.distrSort.KeyRange): protoDistrSorting.distrSort.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_DETERMINE_KEY_RANGE, options, request)
    }
    
    override def assignID(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.ID = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_ASSIGN_ID, options, request)
    }
    
    override def isDonePartitioning(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_IS_DONE_PARTITIONING, options, request)
    }
    
    override def sendPartitionedValues(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.PartitionedValues = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_SEND_PARTITIONED_VALUES, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): DistrSortingBlockingStub = new DistrSortingBlockingStub(channel, options)
  }
  
  class DistrSortingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[DistrSortingStub](channel, options) with DistrSorting {
    override def determineKeyRange(request: protoDistrSorting.distrSort.KeyRange): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_DETERMINE_KEY_RANGE, options, request)
    }
    
    override def assignID(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.ID] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_ASSIGN_ID, options, request)
    }
    
    override def isDonePartitioning(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_IS_DONE_PARTITIONING, options, request)
    }
    
    override def sendPartitionedValues(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.PartitionedValues] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_SEND_PARTITIONED_VALUES, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): DistrSortingStub = new DistrSortingStub(channel, options)
  }
  
  def bindService(serviceImpl: DistrSorting, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = DistrSorting.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): DistrSortingBlockingStub = new DistrSortingBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): DistrSortingStub = new DistrSortingStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0)
  
}