package gplx.xowa.xtns.indicators;

import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.core.serials.binarys.BinaryLoadMgr;
import gplx.core.serials.core.SerialCoreFactory;
import gplx.core.serials.core.SerialLoadMgr;
import gplx.core.serials.core.SerialLoadWkr;
import gplx.core.serials.core.SerialSaveMgr;
import gplx.core.serials.core.SerialSaveWkr;

public class IndicatorSerialCore {
    private static final int DATA_VERSION = 0;

    public static byte[] Save(Ordered_hash list) {
        // get wkr
        SerialSaveMgr mgr = SerialCoreFactory.NewSaveMgr(BinaryLoadMgr.CORE_VERSION);
        SerialSaveWkr wkr = mgr.NewSaveWkr();

        // save header
        wkr.SaveHdr(DATA_VERSION);

        // save items
        int len = list.Len();
        wkr.SaveInt(len);
        for (int i = 0; i < len; i++) {
            Indicator_xnde xnde = (Indicator_xnde)list.Get_at(i);
            wkr.SaveStr(xnde.Name());
            wkr.SaveBry(xnde.Html());
        }
        return wkr.ToBry();
    }

    public static Ordered_hash Load(byte[] data) {
        // get wkr
        SerialLoadMgr mgr = SerialCoreFactory.NewLoadMgr(data);
        SerialLoadWkr wkr = mgr.NewLoadWkr();

        // init
        wkr.Init(mgr.Data(), mgr.HeaderEnd());

        // load items
        int len = wkr.LoadInt();
        Ordered_hash list = Ordered_hash_.New();
        for (int i = 0; i < len; i++) {
            Indicator_xnde xnde = new Indicator_xnde();
            xnde.Name_(wkr.LoadStr());
            xnde.Html_(wkr.LoadBry());
            list.AddIfDupeUseNth(xnde.Name(), xnde);
        }
        return list;
    }
}
