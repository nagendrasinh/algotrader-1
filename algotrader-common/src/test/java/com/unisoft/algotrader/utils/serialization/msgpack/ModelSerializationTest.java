package com.unisoft.algotrader.utils.serialization.msgpack;

import com.unisoft.algotrader.model.event.execution.ExecutionReport;
import com.unisoft.algotrader.model.event.execution.Order;
import com.unisoft.algotrader.model.event.execution.OrderCancelReject;
import com.unisoft.algotrader.model.refdata.Currency;
import com.unisoft.algotrader.model.refdata.Exchange;
import com.unisoft.algotrader.model.refdata.Instrument;
import com.unisoft.algotrader.model.series.DoubleTimeSeries;
import com.unisoft.algotrader.model.trading.*;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.map.hash.TLongIntHashMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by alex on 7/1/15.
 */
public class ModelSerializationTest {
    public static MsgpackSerializer serializer;

    @BeforeClass
    public static void setup() {
        serializer = new MsgpackSerializer();
        serializer.register(Currency.class);
        serializer.register(Exchange.class);
        serializer.register(AccountTransaction.class);
        serializer.register(AccountPosition.class);
        serializer.register(Account.class);


        serializer.register(ExecType.class);
        serializer.register(PositionSide.class);
        serializer.register(Instrument.PutCall.class);
        serializer.register(Instrument.InstType.class);
        serializer.register(Instrument.class);

        serializer.register(OrdType.class);
        serializer.register(OrdStatus.class);
        serializer.register(TimeInForce.class);
        serializer.register(Side.class);
        serializer.register(ExecutionReport.class);
        serializer.register(OrderCancelReject.class);
        serializer.register(CxlRejResponseTo.class);
        serializer.register(CxlRejReason.class);
        serializer.register(Order.class);

        serializer.register(TLongArrayList.class);
        serializer.register(TLongIntHashMap.class);
        serializer.register(TDoubleArrayList.class);

        serializer.register(DoubleTimeSeries.class);
        serializer.register(Performance.class);

        serializer.register(Position.class);
        serializer.register(Portfolio.class);
    }


    @Test
    public void testSerializeAccount()throws Exception{
        Account account = new Account("Test", "", Currency.USD, 100000);
        account.add(new AccountTransaction(System.currentTimeMillis(), Currency.USD, 500, ""));

        byte [] bytes = serializer.serialize(account);

        Account account2 = serializer.deserialize(bytes);
        Assert.assertEquals(account, account2);
    }


    @Test
    public void testSerializeCurrency()throws Exception{
        Currency currency = Currency.USD;

        byte [] bytes = serializer.serialize(currency);

        Currency currency2 = serializer.deserialize(bytes);
        Assert.assertEquals(currency, currency2);
    }

    @Test
    public void testSerializeExchange()throws Exception{
        Exchange exchange = new Exchange("test exchange", "test exchange");

        byte [] bytes = serializer.serialize(exchange);

        Exchange exchange2 = serializer.deserialize(bytes);
        Assert.assertEquals(exchange, exchange2);
    }


    @Test
    public void testSerializeInstrument()throws Exception{
        Instrument inst1 = new Instrument(1, Instrument.InstType.Stock, "0005.HK", "HSBC", "HKEX", "HKD");
        inst1.addAltSymbol("IB", "1");
        inst1.addAltExchId("IB", "SEHK");
        inst1.addAltExchId("Esignal", "SEHK");


        byte [] bytes = serializer.serialize(inst1);

        Instrument inst2 = serializer.deserialize(bytes);
        Assert.assertEquals(inst1, inst2);
    }

    @Test
    public void testSerializeOrder()throws Exception{
        Order order = new Order();
        order.clOrderId = 1;
        order.instId = 2;
        order.strategyId = 1;
        order.providerId = 1;
        order.side= Side.Buy;
        order.ordType = OrdType.Limit;
        order.ordQty=108000;
        order.limitPrice = 8.9;
        order.stopPrice = 90;
        order.tif = TimeInForce.Day;

        byte [] bytes = serializer.serialize(order);

        Order order2 = serializer.deserialize(bytes);
        Assert.assertEquals(order, order2);
    }

    @Test
    public void testSerializeExecutionReport()throws Exception{
        ExecutionReport er = new ExecutionReport();
        er.execId = 100;
        er.clOrderId = 1;
        er.instId = 2;
        er.side= Side.Buy;
        er.ordType = OrdType.Limit;
        er.ordQty=108000;
        er.limitPrice = 8.9;
        er.stopPrice = 90;
        er.tif = TimeInForce.Day;
        er.lastQty = 100;
        er.lastPrice = 8.8;
        er.filledQty = 2000;
        er.avgPrice = 8.7;

        byte [] bytes = serializer.serialize(er);

        ExecutionReport er2 = serializer.deserialize(bytes);
        Assert.assertEquals(er, er2);
    }

    @Test
    public void testSerializePosition()throws Exception{
        Position position = new Position(1, 2);
        position.marketPrice(89);
        position.qtyBought(1000);
        position.qtySold(0);
        position.qtySoldShort(0);

        position.margin(1);
        position.debt(-100);

        byte [] bytes = serializer.serialize(position);

        Position position2 = serializer.deserialize(bytes);
        Assert.assertEquals(position, position2);
    }

    @Test
    public void testSerializePortfolio()throws Exception{
        Portfolio portfolio = new Portfolio(1,"acct");


        byte [] bytes = serializer.serialize(portfolio);

        Portfolio portfolio2 = serializer.deserialize(bytes);
        Assert.assertEquals(portfolio, portfolio2);
    }

    @Test
    public void testSerializePerformance()throws Exception{
        Performance performance = new Performance();

        byte [] bytes = serializer.serialize(performance);

        Performance performance2 = serializer.deserialize(bytes);
        Assert.assertEquals(performance, performance2);

    }

    @Test
    public void testSerializeAccountTransaction()throws Exception{
        AccountTransaction accountTransaction =  new AccountTransaction(System.currentTimeMillis(), Currency.USD, 500, "");


        byte [] bytes = serializer.serialize(accountTransaction);

        AccountTransaction accountTransaction2 = serializer.deserialize(bytes);
        Assert.assertEquals(accountTransaction, accountTransaction2);
    }

    @Test
    public void testSerializeAccountPosition()throws Exception{
        AccountPosition position = new AccountPosition(Currency.USD);
        position.add(new AccountTransaction(System.currentTimeMillis(), Currency.USD, 500, ""));

        byte [] bytes = serializer.serialize(position);

        AccountPosition position2 = serializer.deserialize(bytes);
        Assert.assertEquals(position, position2);
    }

}
