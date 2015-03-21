/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Ordinastie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.malisis.demo.tabinv;

import io.netty.buffer.ByteBuf;
import net.malisis.core.inventory.MalisisInventoryContainer;
import net.malisis.core.network.MalisisMessage;
import net.malisis.demo.MalisisDemos;
import net.minecraft.inventory.Container;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Ordinastie
 *
 */
@MalisisMessage
public class TabInvMessage implements IMessageHandler<TabInvMessage.Packet, IMessage>
{
	public TabInvMessage()
	{
		MalisisDemos.network.registerMessage(this, TabInvMessage.Packet.class, Side.SERVER);
	}

	@Override
	public IMessage onMessage(Packet message, MessageContext ctx)
	{
		//should never be server, but better double check
		if (ctx.side == Side.CLIENT)
			return null;

		//get open container and check it's one of ours
		Container c = ctx.getServerHandler().playerEntity.openContainer;
		if (!(c instanceof MalisisInventoryContainer))
			return null;

		//get the provider via the container (instead of passing the coords via the packet which costs more and is less secure
		//TODO :  maybe add a shortcut for that too
		TabInvTileEntity te = (TabInvTileEntity) ((MalisisInventoryContainer) c).getInventory(1).getProvider();
		//set the tab server side
		te.setTab(message.tab);

		return null;
	}

	@SideOnly(Side.CLIENT)
	public static void sendTab(int tab)
	{
		//just send a new packet with the tab
		MalisisDemos.network.sendToServer(new Packet(tab));
	}

	/**
	 * @author Ordinastie
	 *
	 */
	public static class Packet implements IMessage
	{
		private int tab;

		public Packet()
		{}

		public Packet(int tab)
		{
			this.tab = tab;
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			tab = buf.readInt();
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt(tab);
		}

	}

}
