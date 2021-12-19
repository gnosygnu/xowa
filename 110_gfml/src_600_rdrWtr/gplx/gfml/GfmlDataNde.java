/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfml;
import gplx.core.stores.*; import gplx.core.gfo_ndes.*;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.GfoMsg_;
import gplx.types.basics.utls.StringUtl;
public class GfmlDataNde {
	public GfmlDoc Doc() {return gdoc;} GfmlDoc gdoc;
	public DataRdr XtoRdr() {
		GfmlDataRdr rv = new GfmlDataRdr();
		rv.SetNode(gdoc.RootNde());
		return rv;
	}
	public DataWtr XtoWtr() {
		GfmlDataWtr2 rv = new GfmlDataWtr2();
		rv.Doc_set(gdoc);
		return rv;
	}
	public static GfmlDataNde new_any_eol_(String raw) {return new_(StringUtl.Replace(raw, StringUtl.CrLf, StringUtl.Nl));}
	public static GfmlDataNde new_(String raw) {
		GfmlDataNde rv = new GfmlDataNde();
		GfmlBldr bldr = GfmlBldr_.default_();
		bldr.Doc().RootLxr().SubLxr_Add
			( GfmlDocLxrs.AtrSpr_lxr()
			, GfmlDocLxrs.NdeDot_lxr()
			, GfmlDocLxrs.NdeHdrBgn_lxr()
			, GfmlDocLxrs.NdeHdrEnd_lxr()
			);
		rv.gdoc = bldr.XtoGfmlDoc(raw);
		return rv;
	}
	public static GfoMsg XtoMsg(String raw) {
		GfmlDoc gdoc = GfmlDataNde.new_any_eol_(raw).Doc();
		return XtoMsg(gdoc.RootNde());
	}
	public static GfoMsg XtoMsgNoRoot(String raw) {
		GfmlDoc gdoc = GfmlDataNde.new_any_eol_(raw).Doc();
		GfoMsg msg = XtoMsg(gdoc.RootNde());
		return (GfoMsg)msg.Subs_getAt(0);
	}
	private static String StringUtl_Coalesce(String s, String alt)                       {return StringUtl.IsNullOrEmpty(s) ? alt : s;}
	static GfoMsg XtoMsg(GfmlNde gnde) {
		String msgKey = StringUtl_Coalesce(gnde.Key(), gnde.Hnd());
		GfoMsg msg = GfoMsg_.new_parse_(msgKey);
		for (int i = 0; i < gnde.SubKeys().Count(); i++) {
			GfmlItm subItm = (GfmlItm)gnde.SubKeys().Get_at(i);
			if (subItm.ObjType() == GfmlObj_.Type_atr) {
				GfmlAtr subAtr = (GfmlAtr)subItm;
				String subAtrKey = StringUtl.IsNullOrEmpty(subAtr.Key()) ? "" : subAtr.Key(); // NOTE: needs to be "" or else will fail in GfoConsole; key will be evaluated against NullKey in GfsCtx
				msg.Add(subAtrKey, subAtr.DatTkn().Val());
			}
			else {
				GfmlNde subNde = (GfmlNde)subItm;
				GfoMsg subMsg = XtoMsg(subNde);
				msg.Subs_add(subMsg);
			}
		}
		for (int i = 0; i < gnde.SubHnds().Count(); i++) {
			GfmlItm subItm = (GfmlItm)gnde.SubHnds().Get_at(i);
			GfmlNde subNde = (GfmlNde)subItm;
			GfoMsg subMsg = XtoMsg(subNde);
			msg.Subs_add(subMsg);
		}
		return msg;
	}
}
class GfmlDataWtr2 extends DataWtr_base implements DataWtr {
	@Override public void WriteData(String name, Object val) {
		GfmlTkn nameTkn = GfmlTkn_.raw_(name);
		GfmlTkn valTkn = GfmlTkn_.raw_(To_str(val));
		GfmlAtr atr = GfmlAtr.new_(nameTkn, valTkn, GfmlType_.String);
		GfmlNde nde = gdoc.RootNde().SubHnds().Get_at(0);
		nde.SubKeys().Add(atr);
	}
	public void InitWtr(String key, Object val) {}
	public void WriteTableBgn(String name, GfoFldList fields) {}
	@Override public void WriteNodeBgn(String nodeName) {}
	public void WriteLeafBgn(String leafName) {}
	@Override public void WriteNodeEnd() {}
	public void WriteLeafEnd() {}
	public void Clear() {}
	public String To_str() {return "";}
	String To_str(Object obj) {
		if (obj == null) return "''";
		String s = obj.toString();
		return StringUtl.Concat("'", StringUtl.Replace(s, "'", "''"), "'");
	}
	@Override public SrlMgr SrlMgr_new(Object o) {return new GfmlDataWtr2();}
	public void Doc_set(GfmlDoc v) {gdoc = v;} GfmlDoc gdoc;
}
