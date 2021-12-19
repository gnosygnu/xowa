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
package gplx.langs.htmls.docs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.StringUtl;
import gplx.langs.htmls.Gfh_atr_;
import gplx.langs.htmls.Gfh_tag_;
import gplx.langs.htmls.clses.Gfh_class_;
import gplx.langs.htmls.styles.Gfh_style_wkr__val_as_int;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.parsers.htmls.Mwh_atr_mgr;
import gplx.xowa.parsers.htmls.Mwh_atr_parser;
import gplx.xowa.parsers.htmls.Mwh_atr_wkr;
public class Gfh_tag implements Mwh_atr_wkr {
	private Gfh_tag_rdr tag_rdr;
	private Ordered_hash atrs_hash; private boolean atrs_null; private int atrs_bgn, atrs_end;
	private final Gfh_style_wkr__val_as_int style_wkr = new Gfh_style_wkr__val_as_int();
	public Gfh_tag Init(Gfh_tag_rdr tag_rdr, byte[] src, boolean tag_is_tail, boolean tag_is_inline, int src_bgn, int src_end, int atrs_bgn, int atrs_end, int name_id, byte[] name_bry) {
		this.tag_rdr = tag_rdr; this.src = src; this.atrs_null = true;
		this.tag_is_tail = tag_is_tail; this.tag_is_inline = tag_is_inline;
		this.atrs_bgn = atrs_bgn; this.atrs_end = atrs_end;
		this.name_id = name_id; this.name_bry = name_bry; this.src_bgn = src_bgn; this.src_end = src_end;
		return this;
	}
	public Gfh_tag Copy() {
		Gfh_tag rv = new Gfh_tag().Init(tag_rdr, src, tag_is_tail, tag_is_inline, src_bgn, src_end, atrs_bgn, atrs_end, name_id, name_bry);
		rv.atrs_null = false;
		rv.atrs_hash = Copy(atrs_hash);
		return rv;
	}
	public int Name_id() {return name_id;} private int name_id;
	public boolean Tid_is_comment() {return name_id == Gfh_tag_.Id__comment;}
	public byte[] Name_bry() {return name_bry;} private byte[] name_bry;
	public Gfh_tag Chk_name_or_fail(int chk) {
		if (!Chk_name(chk)) tag_rdr.Err_wkr().Fail("name_id chk failed", "expecting", Gfh_tag_.To_str(chk));
		return this;
	}
	public boolean Chk_name(int chk) {
		return (	chk == name_id
			||	(name_id != Gfh_tag_.Id__eos && IntUtl.In(chk, Gfh_tag_.Id__any, Gfh_tag_.Id__comment)));
	}
	public boolean Chk(int chk_name, byte[] chk_cls) {return name_id == chk_name && Atrs__cls_has(chk_cls);}
	public byte[] Src() {return src;} private byte[] src;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public boolean Src_exists() {return src_end > src_bgn;}	// NOTE: only true if EOS where src_end == src_bgn == src_len
	public boolean Tag_is_tail() {return tag_is_tail;} private boolean tag_is_tail;
	public boolean Tag_is_inline() {return tag_is_inline;} private boolean tag_is_inline;
	public Ordered_hash Atrs__hash() {if (atrs_null) Atrs__make(); return atrs_hash;}
	public int Atrs__len() {if (atrs_null) Atrs__make(); return atrs_hash.Len();}
	public boolean Atrs__match_pair(byte[] key, byte[] val) {
		if (atrs_null) Atrs__make();
		Gfh_atr rv = (Gfh_atr)atrs_hash.GetByOrNull(key);
		return rv == null ? false : BryLni.Eq(val, rv.Val());
	}
	public boolean Atrs__cls_has(byte[] val) {
		if (atrs_null) Atrs__make();
		Gfh_atr rv = (Gfh_atr)atrs_hash.GetByOrNull(Gfh_atr_.Bry__class); if (rv == null) return false;
		byte[] rv_val = rv.Val();
		return Gfh_class_.Has(rv_val, 0, rv_val.length, val);
	}
	public boolean Atrs__cls_eq(byte[] val) {
		if (atrs_null) Atrs__make();
		Gfh_atr rv = (Gfh_atr)atrs_hash.GetByOrNull(Gfh_atr_.Bry__class); if (rv == null) return false;
		return BryLni.Eq(val, rv.Val());
	}
	public byte Atrs__cls_find_or_fail(Hash_adp_bry hash) {
		byte rv = Atrs__cls_find_or(hash, ByteUtl.MaxValue127); if (rv == ByteUtl.MaxValue127) tag_rdr.Err_wkr().Fail("cls missing");
		return rv;
	}
	public byte Atrs__cls_find_or(Hash_adp_bry hash, byte or) {
		if (atrs_null) Atrs__make();
		Gfh_atr cls_atr = (Gfh_atr)atrs_hash.GetByOrNull(Gfh_atr_.Bry__class); if (cls_atr == null) return or;
		byte rv = Gfh_class_.Find_1st(src, cls_atr.Val_bgn(), cls_atr.Val_end(), hash); if (rv == ByteUtl.MaxValue127) return or;
		return rv;
	}
	public int Atrs__style_get_as_int(byte[] key) {
		if (atrs_null) Atrs__make();
		Gfh_atr rv = (Gfh_atr)atrs_hash.GetByOrNull(Gfh_atr_.Bry__style); if (rv == null) return -1;
		byte[] rv_val = rv.Val();
		return style_wkr.Parse(rv_val, 0, rv_val.length, key);
	}
	public boolean Atrs__has(byte[] key) {
		if (atrs_null) Atrs__make();
		return atrs_hash.GetByOrNull(key) != null;
	}		
	public byte[] Atrs__get_as_bry(byte[] key) {
		if (atrs_null) Atrs__make();
		Gfh_atr rv = (Gfh_atr)atrs_hash.GetByOrNull(key);
		return rv == null ? BryUtl.Empty : rv.Val();
	}		
	public int Atrs__get_as_int(byte[] key) {
		int rv = Atrs__get_as_int_or(key, IntUtl.MinValue); if (rv == IntUtl.MinValue) tag_rdr.Err_wkr().Fail("atr missing", "key", key);
		return rv;
	}
	public int Atrs__get_as_int_or(byte[] key, int or) {
		if (atrs_null) Atrs__make();
		Gfh_atr rv = (Gfh_atr)atrs_hash.GetByOrNull(key); if (rv == null) return or;
		return BryUtl.ToIntOr(src, rv.Val_bgn(), rv.Val_end(), or);
	}
	public double Atrs__get_as_double_or(byte[] key, double or) {
		if (atrs_null) Atrs__make();
		Gfh_atr rv = (Gfh_atr)atrs_hash.GetByOrNull(key); if (rv == null) return or;
		return BryUtl.ToDoubleOr(src, rv.Val_bgn(), rv.Val_end(), or);
	}
	public Gfh_atr Atrs__get_at(int i)					{return (Gfh_atr)atrs_hash.GetAt(i);}
	public Gfh_atr Atrs__get_by_or_fail(byte[] key)	{return Atrs__get_by_or_fail(key, BoolUtl.Y);}
	public Gfh_atr Atrs__get_by_or_empty(byte[] key)	{return Atrs__get_by_or_fail(key, BoolUtl.N);}
	public Gfh_atr Atrs__get_by_or_fail(byte[] key, boolean fail_if_null) {
		if (atrs_null) Atrs__make();
		Gfh_atr rv = (Gfh_atr)atrs_hash.GetByOrNull(key);
		if (rv == null) {
			if (fail_if_null) tag_rdr.Err_wkr().Fail("atr missing", "key", key);
			else return Gfh_atr.Noop;
		}
		return rv;
	}
	public String Atrs__print() {
		if (atrs_null) Atrs__make();
		BryWtr bfr = BryWtr.New();
		int len = atrs_hash.Len();
		for (int i = 0; i < len; ++i) {
			Gfh_atr atr = (Gfh_atr)atrs_hash.GetAt(i);
			bfr.Add(atr.Key()).AddByteEq().Add(atr.Val()).AddByteNl();
		}
		return bfr.ToStr();
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
		int atr_bgn = itm_ary[itm_idx + Mwh_atr_mgr.Idx_atr_bgn];
		int atr_end = itm_ary[itm_idx + Mwh_atr_mgr.Idx_atr_end];
		if (key_exists) {
			val_bgn = itm_ary[itm_idx + Mwh_atr_mgr.Idx_val_bgn];
			val_end = itm_ary[itm_idx + Mwh_atr_mgr.Idx_val_end];
		}
		else
			val_bry = key_bry;
		Gfh_atr atr = new Gfh_atr(atrs_hash.Len(), atr_bgn, atr_end, key_bry, val_bry, src, val_bgn, val_end);
		atrs_hash.Add(key_bry, atr);
	}
	public String To_str() {
		return src == null || src_bgn < 0 || src_end >= src.length
			? "tag is invalid!: src_bgn=" + IntUtl.ToStr(src_bgn) + ", src_end=" + IntUtl.ToStr(src_end) + " src=" + (src == null ? "NULL" : StringUtl.NewU8(src))
			: StringUtl.NewU8(src, src_bgn, src_end);
	}
	private static Ordered_hash Copy(Ordered_hash src) {
		Ordered_hash rv = Ordered_hash_.New();
		int len = src.Len();
		for (int i = 0; i < len; ++i) {
			Gfh_atr atr = (Gfh_atr)src.GetAt(i);
			rv.Add(atr.Key(), atr);
		}
		return rv;
	}
}
