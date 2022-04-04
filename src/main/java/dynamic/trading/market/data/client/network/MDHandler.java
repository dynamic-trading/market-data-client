package dynamic.trading.market.data.client.network;

import dynamic.trading.market.data.client.constant.Instrument;
import dynamic.trading.market.data.client.service.MDService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import java.util.concurrent.TimeUnit;

public class MDHandler extends SimpleChannelInboundHandler<Object> {
    private final MDService mdService;
    private final WebSocketClientHandshaker handshaker;

    private final MDBootstrap mdBootstrap;
    private ChannelPromise handshakeFuture;

    private final PooledByteBufAllocator allocator;

    public MDHandler(final WebSocketClientHandshaker handshaker,
                     final MDBootstrap mdBootstrap,
                     final MDService mdService) {
        this.handshaker = handshaker;
        this.mdBootstrap = mdBootstrap;
        this.mdService = mdService;
        this.allocator = new PooledByteBufAllocator(true);
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> this.mdBootstrap.connect(eventLoop), 30L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        final Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            this.subscribe(ctx);
            return;
        }

        final WebSocketFrame frame = (WebSocketFrame) msg;

        if (frame instanceof BinaryWebSocketFrame) {
            ByteBuf byteBuf = frame.content();
            this.mdService.read(byteBuf);
        }
    }

    private void subscribe(final ChannelHandlerContext ctx){
        for (Integer instrumentId : Instrument.getAllInstruments()){
            ByteBuf byteBuf = this.allocator.ioBuffer(8);
            byteBuf.writeByte(1);
            byteBuf.writeInt(instrumentId);
            ctx.write(new BinaryWebSocketFrame(byteBuf));
        }
        ctx.flush();
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause){
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }
}
