package test.IO;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class TestServer {
	/*public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();
       
        new Thread(() ->{
        	// ServerSocket  ֻ�ܴ����߳�������ServerSocketChannel
        	try {
        		ServerSocketChannel listenerChannel = ServerSocketChannel.open();
				listenerChannel.socket().bind(new InetSocketAddress(8000));
				listenerChannel.configureBlocking(false);
				//SelectionKey.OP_ACCEPT  �������Ӽ����¼�����ʾ�������������˿ͻ����ӣ����������Խ������������
				listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
				while(true){
					//����Ƿ����µ����ӣ������1ָ����������ʱ��Ϊ1ms
					if(serverSelector.select(1)>0){
						Set<SelectionKey> set = serverSelector.selectedKeys();
						Iterator<SelectionKey> keyIterator = set.iterator();//ʹ�õ�������ȡ�±�
						while(keyIterator.hasNext()){
							SelectionKey key = keyIterator.next();
							if(key.isAcceptable()){
								// (1) ÿ��һ�������ӣ�����Ҫ����һ���̣߳�����ֱ��ע�ᵽclientSelector
								SocketChannel clientChannel = ((ServerSocketChannel)key.channel()).accept();
								clientChannel.configureBlocking(false);// ���Ϊ true�����ͨ��������������ģʽ�����Ϊ false�����ͨ���������ڷ�����ģʽ 
								//SelectionKey.OP_READ�������¼�����ʾͨ�����Ѿ����˿ɶ������ݣ�����ִ�ж������ˣ�ͨ��Ŀǰ�����ݣ����Խ��ж������ˣ�
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
                    // (2) ������ѯ�Ƿ�����Щ���������ݿɶ��������1ָ����������ʱ��Ϊ1ms
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> set = clientSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();

                            if (key.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    // (3) ��ȡ�����Կ�Ϊ��λ������ȡ
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
