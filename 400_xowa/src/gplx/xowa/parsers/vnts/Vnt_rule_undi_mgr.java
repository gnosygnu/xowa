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
class Vnt_rule_undi_mgr {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public int Len() {return hash.Len();}
	public boolean Has_none() {return hash.Len() == 0;}
	public void Clear() {hash.Clear();}
	public Vnt_rule_undi_grp Get_at(int i)		{return (Vnt_rule_undi_grp)hash.GetAt(i);}
	public Vnt_rule_undi_grp Get_by(byte[] key) {return (Vnt_rule_undi_grp)hash.GetByOrNull(key);}
	public byte[] Get_text_by_key_or_null(byte[] key) {
		Vnt_rule_undi_grp grp = (Vnt_rule_undi_grp)hash.GetByOrNull(key); if (grp == null) return null;
		return grp.Len() == 0 ? null : grp.Get_at(0).Trg();	// REF.MW: $disp = $disp[0];
	}
	public byte[] Get_text_at(int i) {
		Vnt_rule_undi_grp grp = (Vnt_rule_undi_grp)hash.GetAt(i); if (grp == null) return null;
		return grp.Len() == 0 ? null : grp.Get_at(0).Trg();
	}
	public Vnt_rule_undi_grp Set(byte[] vnt, byte[] src, byte[] trg) {
		Vnt_rule_undi_grp grp = (Vnt_rule_undi_grp)hash.GetByOrNull(vnt);
		if (grp == null) {
			grp = new Vnt_rule_undi_grp(vnt);
			hash.Add(vnt, grp);
		}
		grp.Set(src, trg);
		return grp;
	}
	public void To_bry__dbg(BryWtr bfr) {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			if (i != 0)	bfr.AddByteNl();
			Vnt_rule_undi_grp grp = (Vnt_rule_undi_grp)hash.GetAt(i);
			bfr.Add(grp.Vnt()).AddByteColon();
			grp.To_bry__dbg(bfr);
		}
	}
}
class Vnt_rule_undi_grp {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public Vnt_rule_undi_grp(byte[] vnt) {this.vnt = vnt;}
	public int Len() {return hash.Len();}
	public Vnt_rule_undi_itm Get_at(int i) {return (Vnt_rule_undi_itm)hash.GetAt(i);}
	public byte[] Vnt() {return vnt;} private final byte[] vnt;
	public Vnt_rule_undi_itm Set(byte[] src, byte[] trg) {
		Vnt_rule_undi_itm itm = (Vnt_rule_undi_itm)hash.GetByOrNull(src);
		if (itm == null) {
			itm = new Vnt_rule_undi_itm(src, trg);
			hash.Add(src, itm);
		}
		return itm;
	}
	public void To_bry__dbg(BryWtr bfr) {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Vnt_rule_undi_itm itm = (Vnt_rule_undi_itm)hash.GetAt(i);
			bfr.Add(itm.Src()).AddByteEq().Add(itm.Trg());
		}
	}
}
class Vnt_rule_undi_itm {
	public Vnt_rule_undi_itm(byte[] src, byte[] trg) {this.src = src; this.trg = trg;}
	public byte[] Src() {return src;} private final byte[] src;
	public byte[] Trg() {return trg;} private byte[] trg;
	public void Trg_(byte[] v) {this.trg = v;}
}
