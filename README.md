# Distributed sorting using gRPC
Me and my teammate created a distributed sorting algorithm, which has one master computer, and n worker computers, containing the data. It makes use of the gRPC protocol to transfer data between the master and workers.  
  
When the program has finished, each worker contains a range of non-overlapping sorted numbers in their respective output directories, based on ASCII values, and the master knows the range of sorted values for each worker.  
The setup of this program is designed such that all machines are on the same SSH server, but each with a different IP-address.  
  
To run the code, first start the master with `scala Master.scala n` where `n` is the number of workers.  
Then start each worker with `scala Worker.scala ip:45012 -I dir1 dir2 -O dir3` where `ip` is the ip of the master (which currently is hardcoded with port 45012), `dir1` and `dir2` indicates the input directories, which store the files, and `dir3` is the output directory, which contains the sorted partitions. Note that there is no limit to the number of input directories. 
