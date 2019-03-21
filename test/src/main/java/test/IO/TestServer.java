package test.IO;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class TestServer {
	/*public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();
       
        new Thread(() ->{
        	// ServerSocket  只能处理单线程所以用ServerSocketChannel
        	try {
        		ServerSocketChannel listenerChannel = ServerSocketChannel.open();
				listenerChannel.socket().bind(new InetSocketAddress(8000));
				listenerChannel.configureBlocking(false);
				//SelectionKey.OP_ACCEPT  接收连接继续事件，表示服务器监听到了客户连接，服务器可以接收这个连接了
				listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
				while(true){
					//监测是否有新的连接，这里的1指的是阻塞的时间为1ms
					if(serverSelector.select(1)>0){
						Set<SelectionKey> set = serverSelector.selectedKeys();
						Iterator<SelectionKey> keyIterator = set.iterator();//使用迭代器获取下标
						while(keyIterator.hasNext()){
							SelectionKey key = keyIterator.next();
							if(key.isAcceptable()){
								// (1) 每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector
								SocketChannel clientChannel = ((ServerSocketChannel)key.channel()).accept();
								clientChannel.configureBlocking(false);// 如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式 
								//SelectionKey.OP_READ读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
								clientChannel.register(clientSelector, SelectionKey.OP_READ);
							}
						}
						keyIterator.remove();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }).start();
        
        new Thread(() -> {
            try {
                while (true) {
                    // (2) 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为1ms
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> set = clientSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();

                            if (key.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    // (3) 读取数据以块为单位批量读取
                                    clientChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer)
                                            .toString());
                                } finally {
                                    keyIterator.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }

                        }
                    }
                }
            } catch (IOException ignored) {
            }
        }).start();
        
        
        
        
        
	}*/
}
