package protoSampling.sampling

object StockQuoteProviderGrpc {
  val METHOD_SERVER_SIDE_STREAMING_GET_LIST_STOCK_QUOTES: _root_.io.grpc.MethodDescriptor[protoSampling.sampling.Stock, protoSampling.sampling.StockQuote] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoSampling.StockQuoteProvider", "serverSideStreamingGetListStockQuotes"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoSampling.sampling.Stock])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoSampling.sampling.StockQuote])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoSampling.sampling.SamplingProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val METHOD_CLIENT_SIDE_STREAMING_GET_STATISTICS_OF_STOCKS: _root_.io.grpc.MethodDescriptor[protoSampling.sampling.Stock, protoSampling.sampling.StockQuote] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoSampling.StockQuoteProvider", "clientSideStreamingGetStatisticsOfStocks"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoSampling.sampling.Stock])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoSampling.sampling.StockQuote])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoSampling.sampling.SamplingProto.javaDescriptor.getServices().get(0).getMethods().get(1)))
      .build()
  
  val METHOD_BIDIRECTIONAL_STREAMING_GET_LISTS_STOCK_QUOTES: _root_.io.grpc.MethodDescriptor[protoSampling.sampling.Stock, protoSampling.sampling.StockQuote] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("protoSampling.StockQuoteProvider", "bidirectionalStreamingGetListsStockQuotes"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoSampling.sampling.Stock])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[protoSampling.sampling.StockQuote])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(protoSampling.sampling.SamplingProto.javaDescriptor.getServices().get(0).getMethods().get(2)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("protoSampling.StockQuoteProvider")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(protoSampling.sampling.SamplingProto.javaDescriptor))
      .addMethod(METHOD_SERVER_SIDE_STREAMING_GET_LIST_STOCK_QUOTES)
      .addMethod(METHOD_CLIENT_SIDE_STREAMING_GET_STATISTICS_OF_STOCKS)
      .addMethod(METHOD_BIDIRECTIONAL_STREAMING_GET_LISTS_STOCK_QUOTES)
      .build()
  
  trait StockQuoteProvider extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = StockQuoteProvider
    def serverSideStreamingGetListStockQuotes(request: protoSampling.sampling.Stock, responseObserver: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): Unit
    def clientSideStreamingGetStatisticsOfStocks(responseObserver: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.Stock]
    def bidirectionalStreamingGetListsStockQuotes(responseObserver: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.Stock]
  }
  
  object StockQuoteProvider extends _root_.scalapb.grpc.ServiceCompanion[StockQuoteProvider] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[StockQuoteProvider] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = protoSampling.sampling.SamplingProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = protoSampling.sampling.SamplingProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: StockQuoteProvider, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_SERVER_SIDE_STREAMING_GET_LIST_STOCK_QUOTES,
        _root_.io.grpc.stub.ServerCalls.asyncServerStreamingCall(new _root_.io.grpc.stub.ServerCalls.ServerStreamingMethod[protoSampling.sampling.Stock, protoSampling.sampling.StockQuote] {
          override def invoke(request: protoSampling.sampling.Stock, observer: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): Unit =
            serviceImpl.serverSideStreamingGetListStockQuotes(request, observer)
        }))
      .addMethod(
        METHOD_CLIENT_SIDE_STREAMING_GET_STATISTICS_OF_STOCKS,
        _root_.io.grpc.stub.ServerCalls.asyncClientStreamingCall(new _root_.io.grpc.stub.ServerCalls.ClientStreamingMethod[protoSampling.sampling.Stock, protoSampling.sampling.StockQuote] {
          override def invoke(observer: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.Stock] =
            serviceImpl.clientSideStreamingGetStatisticsOfStocks(observer)
        }))
      .addMethod(
        METHOD_BIDIRECTIONAL_STREAMING_GET_LISTS_STOCK_QUOTES,
        _root_.io.grpc.stub.ServerCalls.asyncBidiStreamingCall(new _root_.io.grpc.stub.ServerCalls.BidiStreamingMethod[protoSampling.sampling.Stock, protoSampling.sampling.StockQuote] {
          override def invoke(observer: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.Stock] =
            serviceImpl.bidirectionalStreamingGetListsStockQuotes(observer)
        }))
      .build()
  }
  
  trait StockQuoteProviderBlockingClient {
    def serviceCompanion = StockQuoteProvider
    def serverSideStreamingGetListStockQuotes(request: protoSampling.sampling.Stock): scala.collection.Iterator[protoSampling.sampling.StockQuote]
  }
  
  class StockQuoteProviderBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[StockQuoteProviderBlockingStub](channel, options) with StockQuoteProviderBlockingClient {
    override def serverSideStreamingGetListStockQuotes(request: protoSampling.sampling.Stock): scala.collection.Iterator[protoSampling.sampling.StockQuote] = {
      _root_.scalapb.grpc.ClientCalls.blockingServerStreamingCall(channel, METHOD_SERVER_SIDE_STREAMING_GET_LIST_STOCK_QUOTES, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): StockQuoteProviderBlockingStub = new StockQuoteProviderBlockingStub(channel, options)
  }
  
  class StockQuoteProviderStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[StockQuoteProviderStub](channel, options) with StockQuoteProvider {
    override def serverSideStreamingGetListStockQuotes(request: protoSampling.sampling.Stock, responseObserver: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): Unit = {
      _root_.scalapb.grpc.ClientCalls.asyncServerStreamingCall(channel, METHOD_SERVER_SIDE_STREAMING_GET_LIST_STOCK_QUOTES, options, request, responseObserver)
    }
    
    override def clientSideStreamingGetStatisticsOfStocks(responseObserver: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.Stock] = {
      _root_.scalapb.grpc.ClientCalls.asyncClientStreamingCall(channel, METHOD_CLIENT_SIDE_STREAMING_GET_STATISTICS_OF_STOCKS, options, responseObserver)
    }
    
    override def bidirectionalStreamingGetListsStockQuotes(responseObserver: _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.StockQuote]): _root_.io.grpc.stub.StreamObserver[protoSampling.sampling.Stock] = {
      _root_.scalapb.grpc.ClientCalls.asyncBidiStreamingCall(channel, METHOD_BIDIRECTIONAL_STREAMING_GET_LISTS_STOCK_QUOTES, options, responseObserver)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): StockQuoteProviderStub = new StockQuoteProviderStub(channel, options)
  }
  
  def bindService(serviceImpl: StockQuoteProvider, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = StockQuoteProvider.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): StockQuoteProviderBlockingStub = new StockQuoteProviderBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): StockQuoteProviderStub = new StockQuoteProviderStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = protoSampling.sampling.SamplingProto.javaDescriptor.getServices().get(0)
  
}