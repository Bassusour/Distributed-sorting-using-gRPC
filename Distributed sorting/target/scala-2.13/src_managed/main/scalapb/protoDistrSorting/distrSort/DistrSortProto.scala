// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package protoDistrSorting.distrSort

object DistrSortProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq.empty
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      protoDistrSorting.distrSort.KeyRange,
      protoDistrSorting.distrSort.ID,
      protoDistrSorting.distrSort.DummyText,
      protoDistrSorting.distrSort.PartitionedValues,
      protoDistrSorting.distrSort.Partition
    )
  private lazy val ProtoBytes: _root_.scala.Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """Cg9kaXN0clNvcnQucHJvdG8SEXByb3RvRGlzdHJTb3J0aW5nIlQKCEtleVJhbmdlEiMKBm1pbktleRgBIAEoCUIL4j8IEgZta
  W5LZXlSBm1pbktleRIjCgZtYXhLZXkYAiABKAlCC+I/CBIGbWF4S2V5UgZtYXhLZXkiHQoCSUQSFwoCaWQYASABKAVCB+I/BBICa
  WRSAmlkIjkKCUR1bW15VGV4dBIsCglkdW1teVRleHQYASABKAlCDuI/CxIJZHVtbXlUZXh0UglkdW1teVRleHQiYgoRUGFydGl0a
  W9uZWRWYWx1ZXMSTQoKcGFydGl0aW9ucxgBIAMoCzIcLnByb3RvRGlzdHJTb3J0aW5nLlBhcnRpdGlvbkIP4j8MEgpwYXJ0aXRpb
  25zUgpwYXJ0aXRpb25zIlUKCVBhcnRpdGlvbhIjCgZtaW5WYWwYASABKAlCC+I/CBIGbWluVmFsUgZtaW5WYWwSIwoGbWF4VmFsG
  AIgASgJQgviPwgSBm1heFZhbFIGbWF4VmFsMtYCCgxEaXN0clNvcnRpbmcSUAoRZGV0ZXJtaW5lS2V5UmFuZ2USGy5wcm90b0Rpc
  3RyU29ydGluZy5LZXlSYW5nZRocLnByb3RvRGlzdHJTb3J0aW5nLkR1bW15VGV4dCIAEkEKCGFzc2lnbklEEhwucHJvdG9EaXN0c
  lNvcnRpbmcuRHVtbXlUZXh0GhUucHJvdG9EaXN0clNvcnRpbmcuSUQiABJSChJpc0RvbmVQYXJ0aXRpb25pbmcSHC5wcm90b0Rpc
  3RyU29ydGluZy5EdW1teVRleHQaHC5wcm90b0Rpc3RyU29ydGluZy5EdW1teVRleHQiABJdChVzZW5kUGFydGl0aW9uZWRWYWx1Z
  XMSHC5wcm90b0Rpc3RyU29ydGluZy5EdW1teVRleHQaJC5wcm90b0Rpc3RyU29ydGluZy5QYXJ0aXRpb25lZFZhbHVlcyIAYgZwc
  m90bzM="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor = {
    val javaProto = com.google.protobuf.DescriptorProtos.FileDescriptorProto.parseFrom(ProtoBytes)
    com.google.protobuf.Descriptors.FileDescriptor.buildFrom(javaProto, _root_.scala.Array(
    ))
  }
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}