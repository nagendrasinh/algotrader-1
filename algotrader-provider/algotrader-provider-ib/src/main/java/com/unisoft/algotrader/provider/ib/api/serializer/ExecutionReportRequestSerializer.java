package com.unisoft.algotrader.provider.ib.api.serializer;

import com.unisoft.algotrader.provider.ib.api.model.execution.ExecutionReportFilter;
import com.unisoft.algotrader.provider.ib.api.model.system.Feature;
import com.unisoft.algotrader.provider.ib.api.model.system.OutgoingMessageId;

/**
 * Created by alex on 8/7/15.
 */
public class ExecutionReportRequestSerializer extends Serializer{

    private static final int VERSION = 3;

    public ExecutionReportRequestSerializer(int serverCurrentVersion){
        super(serverCurrentVersion, OutgoingMessageId.EXECUTION_REPORT_REQUEST);
    }

    public byte [] serialize(long requestId, ExecutionReportFilter filter){
        ByteArrayBuilder builder = getByteArrayBuilder();

        builder.append(messageId.getId());
        builder.append(VERSION);
        if (Feature.EXECUTION_MARKER.isSupportedByVersion(getServerCurrentVersion())) {
            builder.append(requestId);
        }
        appendFilter(builder, filter);
        return builder.toBytes();
    }

    protected void appendFilter(ByteArrayBuilder builder, ExecutionReportFilter filter) {
        builder.append(filter.getClientId());
        builder.append(filter.getAccountNumber());
        builder.append(filter.getTime());
        builder.append(filter.getSymbol());
        if (filter.getSecurityType() != null) {
            builder.append(filter.getSecurityType().bytes());
        } else {
            builder.appendEol();
        }
        builder.append(filter.getExchange());
        if (filter.getOrderAction() != null) {
            builder.append(filter.getOrderAction().getBytes());
        } else {
            builder.appendEol();
        }
    }

}
