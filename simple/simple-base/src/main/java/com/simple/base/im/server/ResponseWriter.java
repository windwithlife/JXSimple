package com.simple.base.im.server;

import io.netty.channel.Channel;

interface ResponseWriter {
	public boolean writeAndFlush(Channel ch, String command, Object response);
}
