package gplx.xowa.xtns.indicators;

import gplx.Bry_;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.String_;
import gplx.core.tests.Gftest;
import org.junit.Test;

public class IndicatorSerialCoreTest {
    private IndicatorSerialCoreTstr tstr = new IndicatorSerialCoreTstr();

    @Test
    public void Save() {
        tstr.TestBoth(String_.Concat_lines_nl
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
        xnde.Html_(Bry_.new_u8(html));
        return xnde;
    }

    public void TestBoth(String data, Indicator_xnde... xndes) {
        byte[] save = TestSave(data, xndes);
        TestLoad(String_.new_u8(save), xndes);
    }
    public byte[] TestSave(String expd, Indicator_xnde... xndes) {
        byte[] actl = IndicatorSerialCore.Save(ToList(xndes));
        Gftest.Eq__str(expd, actl);
        return actl;
    }
    public Ordered_hash TestLoad(String data, Indicator_xnde... expd) {
        Ordered_hash list = IndicatorSerialCore.Load(Bry_.new_u8(data));

        Gftest.Eq__bry(IndicatorSerialCore.Save(ToList(expd)), IndicatorSerialCore.Save(list)); // should probably compare items, but easier to compare seiarlization

        return list;
    }
    private Ordered_hash ToList(Indicator_xnde[] xndes) {
        Ordered_hash list = Ordered_hash_.New();
        for (Indicator_xnde xnde : xndes) {
            list.Add_if_dupe_use_nth(xnde.Name(), xnde);
        }
        return list;
    }
}
