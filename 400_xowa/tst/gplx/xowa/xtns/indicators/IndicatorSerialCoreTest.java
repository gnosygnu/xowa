package gplx.xowa.xtns.indicators;

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.StringUtl;
import org.junit.Test;

public class IndicatorSerialCoreTest {
    private IndicatorSerialCoreTstr tstr = new IndicatorSerialCoreTstr();

    @Test
    public void Save() {
        tstr.TestBoth(StringUtl.ConcatLinesNl
        ( "AA"
        , "2"
        , "5|name0"
        , "5|html0"
        , "5|name1"
        , "5|html1"
        ), tstr.NewXnde("name0", "html0"), tstr.NewXnde("name1", "html1"));
    }
}
class IndicatorSerialCoreTstr {
    public Indicator_xnde NewXnde(String name, String html) {
        Indicator_xnde xnde = new Indicator_xnde();
        xnde.Name_(name);
        xnde.Html_(BryUtl.NewU8(html));
        return xnde;
    }

    public void TestBoth(String data, Indicator_xnde... xndes) {
        byte[] save = TestSave(data, xndes);
        TestLoad(StringUtl.NewU8(save), xndes);
    }
    public byte[] TestSave(String expd, Indicator_xnde... xndes) {
        byte[] actl = IndicatorSerialCore.Save(ToList(xndes));
        GfoTstr.Eq(expd, actl);
        return actl;
    }
    public Ordered_hash TestLoad(String data, Indicator_xnde... expd) {
        Ordered_hash list = IndicatorSerialCore.Load(BryUtl.NewU8(data));

        GfoTstr.Eq(IndicatorSerialCore.Save(ToList(expd)), IndicatorSerialCore.Save(list)); // should probably compare items, but easier to compare seiarlization

        return list;
    }
    private Ordered_hash ToList(Indicator_xnde[] xndes) {
        Ordered_hash list = Ordered_hash_.New();
        for (Indicator_xnde xnde : xndes) {
            list.AddIfDupeUseNth(xnde.Name(), xnde);
        }
        return list;
    }
}
