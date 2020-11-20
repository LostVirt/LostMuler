package org.dreambot.framework.Handlers;

import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class TradeHandler {

    public static int amountInTrade(boolean myScreen, int itemId) {
        return Trade.getItem(myScreen, itemId) != null ? Trade.getItem(myScreen, itemId).getAmount() : 0;
    }

    public static int getTradeAmount(boolean myScreen) {
        WidgetChild tradeText = Widgets.getWidgetChild(335, myScreen ? 24 : 27);
        if (tradeText != null) {
            return parseInt(tradeText.getText().substring(myScreen ? 46 : 51, tradeText.getText().length() - 13).replace(",", ""));
        }
        return 0;
    }

}
