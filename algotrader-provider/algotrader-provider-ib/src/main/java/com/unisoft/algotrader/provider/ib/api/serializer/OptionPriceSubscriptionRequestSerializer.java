package com.unisoft.algotrader.provider.ib.api.serializer;

import com.unisoft.algotrader.model.refdata.Instrument;
import com.unisoft.algotrader.persistence.RefDataStore;
import com.unisoft.algotrader.provider.ib.IBProvider;
import com.unisoft.algotrader.provider.ib.api.exception.RequestException;
import com.unisoft.algotrader.provider.ib.api.model.contract.OptionRight;
import com.unisoft.algotrader.provider.ib.api.model.contract.SecType;
import com.unisoft.algotrader.provider.ib.api.model.system.ClientMessageCode;
import com.unisoft.algotrader.provider.ib.api.model.system.Feature;
import com.unisoft.algotrader.provider.ib.api.model.system.IBModelUtils;
import com.unisoft.algotrader.provider.ib.api.model.system.OutgoingMessageId;

/**
 * Created by alex on 8/7/15.
 */
public class OptionPriceSubscriptionRequestSerializer extends Serializer{

    private static final int VERSION = 2;
    private final RefDataStore refDataStore;

    public OptionPriceSubscriptionRequestSerializer(
            RefDataStore refDataStore, int serverCurrentVersion){
        super(serverCurrentVersion, OutgoingMessageId.OPTION_PRICE_SUBSCRIPTION_REQUEST);
        this.refDataStore = refDataStore;
    }

    public byte [] serialize(final long requestId, final long instId, final double volatility, final double underlyingPrice){
        Instrument instrument = refDataStore.getInstrument(instId);
        return serialize(requestId, instrument, volatility, underlyingPrice);
    }

    public byte [] serialize(final long requestId, final Instrument instrument, final double volatility, final double underlyingPrice){
        checkCancelCalculateOptionPrice();
        ByteArrayBuilder builder = getByteArrayBuilder();
        builder.append(messageId.getId());
        builder.append(VERSION);
        builder.append(requestId);
        appendInstrument(builder, instrument);
        builder.append(volatility);
        builder.append(underlyingPrice);
        return builder.toBytes();
    }


    private void checkCancelCalculateOptionPrice() {
        if (!Feature.CALCULATE_OPTION_PRICE.isSupportedByVersion(getServerCurrentVersion())) {
            throw new RequestException(ClientMessageCode.UPDATE_TWS,
                    "It does not support calculate option price requests.");
        }
    }


    protected void appendInstrument(ByteArrayBuilder builder, Instrument instrument) {

        builder.append(0);//id
        builder.append(instrument.getSymbol(IBProvider.PROVIDER_ID.name()));
        builder.append(SecType.convert(instrument.getType()));
        if (instrument.getExpiryDate() != null) {
            builder.append(IBModelUtils.convertDateTime(instrument.getExpiryDate().getTime()));
        }
        else {
            builder.appendEol();
        }
        builder.append(instrument.getStrike());
        builder.append(OptionRight.convert(instrument.getPutCall()));
        if (instrument.getFactor() == 0.0 || instrument.getFactor() == 1.0){
            builder.appendEol();
        }
        else {
            builder.append(instrument.getFactor());
        }
        builder.append(instrument.getExchId(IBProvider.PROVIDER_ID.name()));
        builder.appendEol(); // primary exch
        builder.append(instrument.getCcyId());
        builder.appendEol(); //localsymbol

        if (Feature.TRADING_CLASS.isSupportedByVersion(getServerCurrentVersion())) {
            builder.appendEol(); // trading class
        }
    }
}
