package dynamic.trading.market.data.client.network;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import java.util.concurrent.TimeUnit;

public class MDListener implements ChannelFutureListener {
    private final MDBootstrap mdBootstrap;

    public MDListener(final MDBootstrap mdBootstrap){
        this.mdBootstrap = mdBootstrap;
    }

    @Override
    public void operationComplete(final ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            System.out.println("Reconnect");
            final EventLoop eventLoop = channelFuture.channel().eventLoop();
            eventLoop.schedule(() -> this.mdBootstrap.connect(eventLoop), 1L, TimeUnit.SECONDS);
        }
    }
}
