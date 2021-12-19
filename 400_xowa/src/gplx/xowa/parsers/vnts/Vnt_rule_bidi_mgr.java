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
package gplx.xowa.parsers.vnts;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
class Vnt_rule_bidi_mgr {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public int Len() {return hash.Len();}
	public boolean Has_none() {return hash.Len() == 0;}
	public void Clear() {hash.Clear();}
	public Vnt_rule_bidi_itm Get_at(int i)		{return (Vnt_rule_bidi_itm)hash.GetAt(i);}
	public Vnt_rule_bidi_itm Get_by(byte[] k)	{return (Vnt_rule_bidi_itm)hash.GetByOrNull(k);}
	public byte[] Get_text_by_ary_or_null(byte[]... ary) {
		int len = ary.length;
		byte[] rv = null;
		for (int i = 0; i < len; ++i) {
			byte[] itm = ary[i];
			Vnt_rule_bidi_itm bidi_itm = (Vnt_rule_bidi_itm)hash.GetByOrNull(itm); if (bidi_itm == null) continue;
			rv = Get_text_by_key_or_null(bidi_itm.Vnt());
			if (rv != null) return rv;
		}
		return rv;
	}
	public byte[] Get_text_by_key_or_null(byte[] vnt) {
		Vnt_rule_bidi_itm rv = (Vnt_rule_bidi_itm)hash.GetByOrNull(vnt);
		return rv == null ? null : rv.Text();
	}
	public byte[] Get_text_at(int i) {
		Vnt_rule_bidi_itm itm = (Vnt_rule_bidi_itm)hash.GetAt(i);
		return itm == null ? null : itm.Text();
	}
	public void Set(byte[] vnt, byte[] text) {
		Vnt_rule_bidi_itm itm = (Vnt_rule_bidi_itm)hash.GetByOrNull(vnt);
		if (itm == null) {
			itm = new Vnt_rule_bidi_itm(vnt, text);
			hash.Add(vnt, itm);
		}
		else
			itm.Text_(text);
	}
	public void To_bry__dbg(BryWtr bfr) {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			if (i != 0)	bfr.AddByteNl();
			Vnt_rule_bidi_itm itm = (Vnt_rule_bidi_itm)hash.GetAt(i);
			bfr.Add(itm.Vnt()).AddByteColon().Add(itm.Text());
		}
	}
}
class Vnt_rule_bidi_itm {
	public Vnt_rule_bidi_itm(byte[] vnt, byte[] text) {this.vnt = vnt; this.text = text;}
	public byte[] Vnt() {return vnt;} private final byte[] vnt;
	public byte[] Text() {return text;} private byte[] text;
	public void Text_(byte[] v) {this.text = v;}
}
