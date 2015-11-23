/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.xowa.parsers.htmls.*; import gplx.langs.htmls.parsers.styles.*; import gplx.langs.htmls.parsers.clses.*;
public class Html_tag implements Mwh_atr_wkr {
	private Html_tag_rdr tag_rdr;
	private Ordered_hash atrs_hash; private boolean atrs_null; private int atrs_bgn, atrs_end;
	public Html_tag Init(Html_tag_rdr tag_rdr, byte[] src, boolean tag_is_tail, boolean tag_is_inline, int src_bgn, int src_end, int atrs_bgn, int atrs_end, int name_id) {
		this.tag_rdr = tag_rdr; this.src = src; this.atrs_null = true;
		this.tag_is_tail = tag_is_tail; this.tag_is_inline = tag_is_inline;
		this.atrs_bgn = atrs_bgn; this.atrs_end = atrs_end;
		this.name_id = name_id; this.src_bgn = src_bgn; this.src_end = src_end;
		return this;
	}
	public Html_tag Copy() {
		Html_tag rv = new Html_tag().Init(tag_rdr, src, tag_is_tail, tag_is_inline, src_bgn, src_end, atrs_bgn, atrs_end, name_id);
		rv.atrs_null = false;
		rv.atrs_hash = Copy(atrs_hash);
		return rv;
	}
	public int Name_id() {return name_id;} private int name_id;
	public Html_tag Chk_id(int chk) {
		if (	chk == name_id
			||	(name_id != Html_tag_.Id__eos && Int_.In(chk, Html_tag_.Id__any, Html_tag_.Id__comment))) {
		}
		else
			tag_rdr.Rdr().Fail("name_id chk failed", "expecting", Html_tag_.To_str(chk));
		return this;
	}
	public byte[] Src() {return src;} private byte[] src;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public boolean Src_exists() {return src_end > src_bgn;}	// NOTE: only true if EOS where src_end == src_bgn == src_len
	public boolean Tag_is_tail() {return tag_is_tail;} private boolean tag_is_tail;
	public boolean Tag_is_inline() {return tag_is_inline;} private boolean tag_is_inline;
	public boolean Atrs__match_pair(byte[] key, byte[] val) {
		if (atrs_null) Atrs__make();
		Html_atr rv = (Html_atr)atrs_hash.Get_by(key);
		return rv == null ? false : Bry_.Eq(val, rv.Val());
	}
	public boolean Atrs__cls_has(byte[] val) {
		if (atrs_null) Atrs__make();
		Html_atr rv = (Html_atr)atrs_hash.Get_by(Html_atr_.Bry__class); if (rv == null) return false;
		byte[] rv_val = rv.Val();
		return Html_atr_class_.Has(rv_val, 0, rv_val.length, val);
	}
	public byte Atrs__cls_find_or_fail(Hash_adp_bry hash) {
		if (atrs_null) Atrs__make();
		Html_atr cls_atr = (Html_atr)atrs_hash.Get_by(Html_atr_.Bry__class); if (cls_atr == null) tag_rdr.Rdr().Fail("cls missing", String_.Empty, String_.Empty);
		byte rv = Html_atr_class_.Find_1st(src, cls_atr.Val_bgn(), cls_atr.Val_end(), hash); if (rv == Byte_.Max_value_127) tag_rdr.Rdr().Fail("cls val missing", String_.Empty, String_.Empty);
		return rv;
	}
	private static final Html_atr_style_wkr__get_val_as_int style_wkr = new Html_atr_style_wkr__get_val_as_int();
	public int Atrs__style_get_as_int(byte[] key) {
		if (atrs_null) Atrs__make();
		Html_atr rv = (Html_atr)atrs_hash.Get_by(Html_atr_.Bry__style); if (rv == null) return -1;
		byte[] rv_val = rv.Val();
		return style_wkr.Parse(rv_val, 0, rv_val.length, key);
	}
	public byte[] Atrs__get_as_bry(byte[] key) {
		if (atrs_null) Atrs__make();
		Html_atr rv = (Html_atr)atrs_hash.Get_by(key);
		return rv == null ? Bry_.Empty : rv.Val();
	}		
	public int Atrs__get_as_int(byte[] key) {
		int rv = Atrs__get_as_int_or(key, Int_.Min_value); if (rv == Int_.Min_value) tag_rdr.Rdr().Fail("atr missing", "key", key);
		return rv;
	}
	public int Atrs__get_as_int_or(byte[] key, int or) {
		if (atrs_null) Atrs__make();
		Html_atr rv = (Html_atr)atrs_hash.Get_by(key); if (rv == null) return or;
		return Bry_.To_int_or(src, rv.Val_bgn(), rv.Val_end(), or);
	}
	public Html_atr Atrs__get_by_or_fail(byte[] key)			{return Atrs__get_by_or_fail(key, Bool_.Y);}
	public Html_atr Atrs__get_by_or_empty(byte[] key)	{return Atrs__get_by_or_fail(key, Bool_.N);}
	public Html_atr Atrs__get_by_or_fail(byte[] key, boolean fail_if_null) {
		if (atrs_null) Atrs__make();
		Html_atr rv = (Html_atr)atrs_hash.Get_by(key);
		if (rv == null) {
			if (fail_if_null) tag_rdr.Rdr().Fail("atr missing", "key", key);
			else return Html_atr.Noop;
		}
		return rv;
	}
	public String Atrs__print() {
		if (atrs_null) Atrs__make();
		Bry_bfr bfr = Bry_bfr.new_();
		int len = atrs_hash.Count();
		for (int i = 0; i < len; ++i) {
			Html_atr atr = (Html_atr)atrs_hash.Get_at(i);
			bfr.Add(atr.Key()).Add_byte_eq().Add(atr.Val()).Add_byte_nl();
		}
		return bfr.To_str();
	}
	private void Atrs__make() {
		atrs_null = false;
		if (atrs_hash == null)	atrs_hash = Ordered_hash_.New_bry();
		else					atrs_hash.Clear(); 
		tag_rdr.Atrs__make(this, atrs_bgn, atrs_end);
	}
	public void On_atr_each	(Mwh_atr_parser mgr, byte[] src, int nde_tid, boolean valid, boolean repeated, boolean key_exists, byte[] key_bry, byte[] val_bry_manual, int[] itm_ary, int itm_idx) {
		if (!valid) return;
		byte[] val_bry = val_bry_manual;
		int val_bgn = -1, val_end = -1;
		if (key_exists) {
			val_bgn = itm_ary[itm_idx + Mwh_atr_mgr.Idx_val_bgn];
			val_end = itm_ary[itm_idx + Mwh_atr_mgr.Idx_val_end];
		}
		else
			val_bry = key_bry;
		Html_atr atr = new Html_atr(atrs_hash.Count(), key_bry, val_bry, src, val_bgn, val_end);
		atrs_hash.Add(key_bry, atr);
	}
	private static Ordered_hash Copy(Ordered_hash src) {
		Ordered_hash rv = Ordered_hash_.New();
		int len = src.Count();
		for (int i = 0; i < len; ++i) {
			Html_atr atr = (Html_atr)src.Get_at(i);
			rv.Add(atr.Key(), atr);
		}
		return rv;
	}
}
