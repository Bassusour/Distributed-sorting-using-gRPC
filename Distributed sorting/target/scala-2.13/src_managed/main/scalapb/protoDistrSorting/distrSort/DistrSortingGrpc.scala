package protoDistrSorting.distrSort

object DistrSortingGrpc {
  val METHOD_ASSIGN_ID: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.ConnectionInformation, protoDistrSorting.distrSort.ID] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "assignID"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.ConnectionInformation])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.ID])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val METHOD_DETERMINE_KEY_RANGE: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.KeyRange, protoDistrSorting.distrSort.DummyText] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "determineKeyRange"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.KeyRange])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
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
  
  val METHOD_GET_UNWANTED_PARTITIONS: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.Dataset, protoDistrSorting.distrSort.DummyText] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "getUnwantedPartitions"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.Dataset])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(4)))
      .build()
  
  val METHOD_SEND_WANTED_PARTITIONS: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.ID, protoDistrSorting.distrSort.Dataset] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "sendWantedPartitions"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.ID])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.Dataset])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(5)))
      .build()
  
  val METHOD_WAIT_FOR_ALL_WORKERS: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.DummyText] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "waitForAllWorkers"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(6)))
      .build()
  
  val METHOD_IS_DONE_RECEIVING_PARTITIONS: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.DummyText] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "isDoneReceivingPartitions"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(7)))
      .build()
  
  val METHOD_RESET_COUNTERS: _root_.io.grpc.MethodDescriptor[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.DummyText] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoDistrSorting.DistrSorting", "resetCounters"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoDistrSorting.distrSort.DummyText])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0).getMethods().get(8)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("protoDistrSorting.DistrSorting")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(protoDistrSorting.distrSort.DistrSortProto.javaDescriptor))
      .addMethod(METHOD_ASSIGN_ID)
      .addMethod(METHOD_DETERMINE_KEY_RANGE)
      .addMethod(METHOD_IS_DONE_PARTITIONING)
      .addMethod(METHOD_SEND_PARTITIONED_VALUES)
      .addMethod(METHOD_GET_UNWANTED_PARTITIONS)
      .addMethod(METHOD_SEND_WANTED_PARTITIONS)
      .addMethod(METHOD_WAIT_FOR_ALL_WORKERS)
      .addMethod(METHOD_IS_DONE_RECEIVING_PARTITIONS)
      .addMethod(METHOD_RESET_COUNTERS)
      .build()
  
  trait DistrSorting extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = DistrSorting
    def assignID(request: protoDistrSorting.distrSort.ConnectionInformation): scala.concurrent.Future[protoDistrSorting.distrSort.ID]
    def determineKeyRange(request: protoDistrSorting.distrSort.KeyRange): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText]
    def isDonePartitioning(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText]
    def sendPartitionedValues(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.PartitionedValues]
    def getUnwantedPartitions(request: protoDistrSorting.distrSort.Dataset): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText]
    def sendWantedPartitions(request: protoDistrSorting.distrSort.ID): scala.concurrent.Future[protoDistrSorting.distrSort.Dataset]
    def waitForAllWorkers(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText]
    def isDoneReceivingPartitions(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText]
    def resetCounters(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText]
  }
  
  object DistrSorting extends _root_.scalapb.grpc.ServiceCompanion[DistrSorting] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[DistrSorting] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = protoDistrSorting.distrSort.DistrSortProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: DistrSorting, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_ASSIGN_ID,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.ConnectionInformation, protoDistrSorting.distrSort.ID] {
          override def invoke(request: protoDistrSorting.distrSort.ConnectionInformation, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.ID]): Unit =
            serviceImpl.assignID(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_DETERMINE_KEY_RANGE,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.KeyRange, protoDistrSorting.distrSort.DummyText] {
          override def invoke(request: protoDistrSorting.distrSort.KeyRange, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.DummyText]): Unit =
            serviceImpl.determineKeyRange(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
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
      .addMethod(
        METHOD_GET_UNWANTED_PARTITIONS,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.Dataset, protoDistrSorting.distrSort.DummyText] {
          override def invoke(request: protoDistrSorting.distrSort.Dataset, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.DummyText]): Unit =
            serviceImpl.getUnwantedPartitions(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_SEND_WANTED_PARTITIONS,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.ID, protoDistrSorting.distrSort.Dataset] {
          override def invoke(request: protoDistrSorting.distrSort.ID, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.Dataset]): Unit =
            serviceImpl.sendWantedPartitions(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_WAIT_FOR_ALL_WORKERS,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.DummyText] {
          override def invoke(request: protoDistrSorting.distrSort.DummyText, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.DummyText]): Unit =
            serviceImpl.waitForAllWorkers(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_IS_DONE_RECEIVING_PARTITIONS,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.DummyText] {
          override def invoke(request: protoDistrSorting.distrSort.DummyText, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.DummyText]): Unit =
            serviceImpl.isDoneReceivingPartitions(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .addMethod(
        METHOD_RESET_COUNTERS,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[protoDistrSorting.distrSort.DummyText, protoDistrSorting.distrSort.DummyText] {
          override def invoke(request: protoDistrSorting.distrSort.DummyText, observer: _root_.io.grpc.stub.StreamObserver[protoDistrSorting.distrSort.DummyText]): Unit =
            serviceImpl.resetCounters(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait DistrSortingBlockingClient {
    def serviceCompanion = DistrSorting
    def assignID(request: protoDistrSorting.distrSort.ConnectionInformation): protoDistrSorting.distrSort.ID
    def determineKeyRange(request: protoDistrSorting.distrSort.KeyRange): protoDistrSorting.distrSort.DummyText
    def isDonePartitioning(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText
    def sendPartitionedValues(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.PartitionedValues
    def getUnwantedPartitions(request: protoDistrSorting.distrSort.Dataset): protoDistrSorting.distrSort.DummyText
    def sendWantedPartitions(request: protoDistrSorting.distrSort.ID): protoDistrSorting.distrSort.Dataset
    def waitForAllWorkers(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText
    def isDoneReceivingPartitions(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText
    def resetCounters(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText
  }
  
  class DistrSortingBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[DistrSortingBlockingStub](channel, options) with DistrSortingBlockingClient {
    override def assignID(request: protoDistrSorting.distrSort.ConnectionInformation): protoDistrSorting.distrSort.ID = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_ASSIGN_ID, options, request)
    }
    
    override def determineKeyRange(request: protoDistrSorting.distrSort.KeyRange): protoDistrSorting.distrSort.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_DETERMINE_KEY_RANGE, options, request)
    }
    
    override def isDonePartitioning(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_IS_DONE_PARTITIONING, options, request)
    }
    
    override def sendPartitionedValues(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.PartitionedValues = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_SEND_PARTITIONED_VALUES, options, request)
    }
    
    override def getUnwantedPartitions(request: protoDistrSorting.distrSort.Dataset): protoDistrSorting.distrSort.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_GET_UNWANTED_PARTITIONS, options, request)
    }
    
    override def sendWantedPartitions(request: protoDistrSorting.distrSort.ID): protoDistrSorting.distrSort.Dataset = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_SEND_WANTED_PARTITIONS, options, request)
    }
    
    override def waitForAllWorkers(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_WAIT_FOR_ALL_WORKERS, options, request)
    }
    
    override def isDoneReceivingPartitions(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_IS_DONE_RECEIVING_PARTITIONS, options, request)
    }
    
    override def resetCounters(request: protoDistrSorting.distrSort.DummyText): protoDistrSorting.distrSort.DummyText = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_RESET_COUNTERS, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): DistrSortingBlockingStub = new DistrSortingBlockingStub(channel, options)
  }
  
  class DistrSortingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[DistrSortingStub](channel, options) with DistrSorting {
    override def assignID(request: protoDistrSorting.distrSort.ConnectionInformation): scala.concurrent.Future[protoDistrSorting.distrSort.ID] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_ASSIGN_ID, options, request)
    }
    
    override def determineKeyRange(request: protoDistrSorting.distrSort.KeyRange): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_DETERMINE_KEY_RANGE, options, request)
    }
    
    override def isDonePartitioning(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_IS_DONE_PARTITIONING, options, request)
    }
    
    override def sendPartitionedValues(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.PartitionedValues] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_SEND_PARTITIONED_VALUES, options, request)
    }
    
    override def getUnwantedPartitions(request: protoDistrSorting.distrSort.Dataset): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_GET_UNWANTED_PARTITIONS, options, request)
    }
    
    override def sendWantedPartitions(request: protoDistrSorting.distrSort.ID): scala.concurrent.Future[protoDistrSorting.distrSort.Dataset] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_SEND_WANTED_PARTITIONS, options, request)
    }
    
    override def waitForAllWorkers(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_WAIT_FOR_ALL_WORKERS, options, request)
    }
    
    override def isDoneReceivingPartitions(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_IS_DONE_RECEIVING_PARTITIONS, options, request)
    }
    
    override def resetCounters(request: protoDistrSorting.distrSort.DummyText): scala.concurrent.Future[protoDistrSorting.distrSort.DummyText] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_RESET_COUNTERS, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): DistrSortingStub = new DistrSortingStub(channel, options)
  }
  
  def bindService(serviceImpl: DistrSorting, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = DistrSorting.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): DistrSortingBlockingStub = new DistrSortingBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): DistrSortingStub = new DistrSortingStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = protoDistrSorting.distrSort.DistrSortProto.javaDescriptor.getServices().get(0)
  
}