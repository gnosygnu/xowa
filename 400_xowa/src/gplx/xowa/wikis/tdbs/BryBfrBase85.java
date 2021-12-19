package gplx.xowa.wikis.tdbs;
import gplx.core.encoders.Base85_;
import gplx.types.custom.brys.wtrs.BryWtr;
public class BryBfrBase85 {
	public static BryWtr AddBase85Len5(BryWtr bfr, int v) {return AddBase85(bfr, v, 5);}
	public static BryWtr AddBase85(BryWtr bfr, int v, int pad)    {
		int newLen = bfr.Len() + pad;
		if (newLen > bfr.Max()) bfr.Resize((newLen) * 2);
		Base85_.Set_bry(v, bfr.Bry(), bfr.Len(), pad);
		bfr.LenSet(newLen);
		return bfr;
	}
}
