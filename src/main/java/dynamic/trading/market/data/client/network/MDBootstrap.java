package dynamic.trading.market.data.client.network;

import dynamic.trading.market.data.client.config.Configuration;
import dynamic.trading.market.data.client.service.MDService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.timeout.IdleStateHandler;
import java.net.URI;

public class MDBootstrap {
    private final int PORT = Configuration.getInstance()
            .getConfig()
            .getPort();

    private final String HOST = Configuration.getInstance()
            .getConfig()
            .getHost();

    private final String URL = "ws://" + HOST + "/marketdata";

    private final URI uri;
    private final MDService mdService;

    public MDBootstrap() throws Exception {
        this.uri = new URI(URL);
        this.mdService = new MDService();
    }

    public void connect(){
        EventLoopGroup eventLoop = Epoll.isAvailable() ?
                new EpollEventLoopGroup() :
                new NioEventLoopGroup();
        System.out.printf("Using Event Loop Group: %s %n", eventLoop.toString());
        this.connect(eventLoop);
    }

    public void connect(final EventLoopGroup eventLoop){
        Class channelClass = Epoll.isAvailable() ? EpollSocketChannel.class :
                NioSocketChannel.class;

        System.out.printf("Using Channel: %s %n", channelClass.toString());

        Bootstrap bootstrap = new Bootstrap();
        MDHandler mdHandler = new MDHandler(WebSocketClientHandshakerFactory.newHandshaker(
                uri,
                WebSocketVersion.V13,
                null,
                false,
                new DefaultHttpHeaders(),
                1655360), this, this.mdService);

        bootstrap.group(eventLoop)
                .channel(channelClass)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpClientCodec());
                        p.addLast(new HttpObjectAggregator(8192), mdHandler);
                        p.addLast(new IdleStateHandler(0, 30, 0));
                    }
                });

        bootstrap.connect(HOST, PORT).addListener(new MDListener(this));
        mdHandler.handshakeFuture();
    }
}
