package io.mykit.data.monitor.mysql.binlog.impl.parser;


import io.mykit.data.monitor.mysql.binlog.BinlogEventV4Header;
import io.mykit.data.monitor.mysql.binlog.BinlogParserContext;
import io.mykit.data.monitor.mysql.binlog.impl.event.TableMapEvent;
import io.mykit.data.monitor.mysql.binlog.impl.event.UpdateRowsEvent;
import io.mykit.data.monitor.mysql.common.glossary.Pair;
import io.mykit.data.monitor.mysql.common.glossary.Row;
import io.mykit.data.monitor.mysql.io.XInputStream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class UpdateRowsEventParser extends AbstractRowEventParser {

    public UpdateRowsEventParser() {
        super(UpdateRowsEvent.EVENT_TYPE);
    }

    public void parse(XInputStream is, BinlogEventV4Header header, BinlogParserContext context)
            throws IOException {
        final long tableId = is.readLong(6);
        final TableMapEvent tme = context.getTableMapEvent(tableId);
        if (this.rowEventFilter != null && !this.rowEventFilter.accepts(header, context, tme)) {
            is.skip(is.available());
            return;
        }

        final UpdateRowsEvent event = new UpdateRowsEvent(header);
        event.setBinlogFilename(context.getBinlogFileName());
        event.setTableId(tableId);
        event.setReserved(is.readInt(2));
        event.setColumnCount(is.readUnsignedLong());
        event.setUsedColumnsBefore(is.readBit(event.getColumnCount().intValue()));
        event.setUsedColumnsAfter(is.readBit(event.getColumnCount().intValue()));
        event.setRows(parseRows(is, tme, event));
        context.getEventListener().onEvents(event);
    }

    protected List<Pair<Row>> parseRows(XInputStream is, TableMapEvent tme, UpdateRowsEvent ure)
            throws IOException {
        final List<Pair<Row>> r = new LinkedList<Pair<Row>>();
        while (is.available() > 0) {
            final Row before = parseRow(is, tme, ure.getUsedColumnsBefore());
            final Row after = parseRow(is, tme, ure.getUsedColumnsAfter());
            r.add(new Pair<Row>(before, after));
        }
        return r;
    }
}
