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

package net.malisis.demo.multipleinv;

import net.malisis.demo.IDemo;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This demo shows how to handle multiple inventories.<br>
 * - The Card item holds their own inventory.<br>
 * - The Bank block also has a 3 slot inventory which can only accept Card items. Putting a card in the slot open the Card inventory in the
 * bank so you can transfer items between two cards.
 * 
 * @author Ordinastie
 *
 */
public class MultipleInv implements IDemo
{
	public static Bank bank;
	public static Card card;

	@Override
	public void preInit()
	{
		//create and register the bank
		bank = new Bank();
		bank.register();

		//create and register the card
		card = new Card();
		card.register();

		GameRegistry.registerTileEntity(BankTileEntity.class, "bankTileEntity");
	}

	@Override
	public void init()
	{}

}
