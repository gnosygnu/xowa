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
package gplx.xowa.xtns.wbases;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.parsers.Xow_parser_mgr;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_entity_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_globecoordinate_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_monolingualtext_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_quantity_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time_;
import gplx.xowa.xtns.wbases.claims.itms.times.Wbase_date;
import gplx.xowa.xtns.wbases.claims.itms.times.Wbase_date_;
import gplx.xowa.xtns.wbases.core.Wdata_dict_datavalue;
import gplx.xowa.xtns.wbases.core.Wdata_dict_mainsnak;
public class Wdata_prop_val_visitor_ {
	public static String Render_snaks(Xowe_wiki wiki, byte[] page_url, KeyVal[] snaks) {
		String rv = null;
		int len = snaks.length;
		Wdata_wiki_mgr wdata_mgr = wiki.Appe().Wiki_mgr().Wdata_mgr();
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
		try {
			for (int i = 0; i < len; ++i) {
				KeyVal[] itm = (KeyVal[])snaks[i].Val();
				Render_snak(bfr, wdata_mgr, wiki, wiki.Lang(), page_url, itm, i, len);
			}
			wiki.Lang().Comma_wkr().Comma__end(bfr);
			rv = bfr.ToStrAndClear();
		} finally {bfr.MkrRls();}
		return rv;
	}
	public static String Render_snak(Wdata_wiki_mgr wdata_mgr, Xowe_wiki wiki, byte[] page_url, KeyVal[] props) {
		String rv = null;
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
		try {
			Render_snak(bfr, wdata_mgr, wiki, wiki.Lang(), page_url, props, 0, 1);
			rv = bfr.ToStrAndClear();
		} finally {bfr.MkrRls();}
		return rv;
	}
	private static void Render_snak(BryWtr bfr, Wdata_wiki_mgr wdata_mgr, Xowe_wiki wiki, Xol_lang_itm lang, byte[] page_url, KeyVal[] props, int sub_idx, int sub_len) {
		// loop props to get (a) snaktype; (b) property; (c) datavalue
		byte snak_tid = ByteUtl.MaxValue127;
		int pid = -1;
		KeyVal[] datavalue_ary = null;
		int len = props.length;
		for (int i = 0; i < len; ++i) {
			KeyVal prop = props[i];
			byte prop_tid = Wdata_dict_mainsnak.Reg.Get_tid_or_max_and_log(page_url, prop.KeyToStr()); if (prop_tid == ByteUtl.MaxValue127) continue;
			switch (prop_tid) {
				case Wdata_dict_mainsnak.Tid__snaktype:		snak_tid = Wbase_claim_value_type_.Reg.Get_tid_or_fail(prop.ValToBry()); break;
				case Wdata_dict_mainsnak.Tid__datavalue:	datavalue_ary = (KeyVal[])prop.Val(); break;
				case Wdata_dict_mainsnak.Tid__datatype:		break;		// ignore: has values like "wikibase-property"; EX: www.wikidata.org/wiki/Property:P397; DATE:2015-06-12
				case Wdata_dict_mainsnak.Tid__property:		pid = To_pid_int(prop.ValToBry()); break;
				case Wdata_dict_mainsnak.Tid__hash:			break;		// ignore: "84487fc3f93b4f74ab1cc5a47d78f596f0b49390"
			}
		}

		if (snak_tid == Wbase_claim_value_type_.Tid__novalue) return; // ISSUE#:481; DATE:2019-06-02
		// if (snak_tid == Wbase_claim_value_type_.Tid__somevalue) return; // is also blank DATE:2019-08-14
		if (datavalue_ary == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "datavalue is empty even though not novalue; page_url=~{0} pid=~{1}", page_url, pid);
			return;
		}

		// loop datavalue_ary to get (a) tid,; (b) val_obj
		byte tid = ByteUtl.MaxValue127;
		Object val_obj = null; 
		len = datavalue_ary.length;
		for (int i = 0; i < len; ++i) {
			KeyVal datavalue_itm = datavalue_ary[i];
			byte datavalue_tid = Wdata_dict_datavalue.Reg.Get_tid_or_max_and_log(page_url, datavalue_itm.KeyToStr()); if (datavalue_tid == ByteUtl.MaxValue127) continue;
			switch (datavalue_tid) {
				case Wdata_dict_datavalue.Tid__type:		tid = Wbase_claim_type_.Get_tid_or_unknown((String)datavalue_itm.Val()); break;
				case Wdata_dict_datavalue.Tid__value:		val_obj = datavalue_itm.Val(); break;
				case Wdata_dict_datavalue.Tid__error:		break;	// ignore: "Can only construct GlobeCoordinateValue with a String globe parameter"
			}					
		}

		// write claim
		switch (tid) {
			case Wbase_claim_type_.Tid__entity:				Write_entity			(bfr, wdata_mgr, lang, page_url, (KeyVal[])val_obj); break;
			case Wbase_claim_type_.Tid__string:				Wdata_prop_val_visitor.Write_str(bfr, To_bry_by_str(val_obj)); break;
			case Wbase_claim_type_.Tid__time:				Write_time				(bfr, wdata_mgr, wiki, page_url, pid, snak_tid, (KeyVal[])val_obj); break;
			case Wbase_claim_type_.Tid__globecoordinate:	Write_geo				(bfr, wdata_mgr, lang, page_url, (KeyVal[])val_obj); break;
			case Wbase_claim_type_.Tid__quantity:			Write_quantity			(bfr, wdata_mgr, lang, page_url, (KeyVal[])val_obj); break;
			case Wbase_claim_type_.Tid__monolingualtext:	Write_langtext			(bfr, page_url, (KeyVal[])val_obj); break;
			default:										throw ErrUtl.NewUnhandled(tid);
		}
		lang.Comma_wkr().Comma__itm(bfr, sub_idx, sub_len);
	}
	private static void Write_entity(BryWtr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] page_url, KeyVal[] kvs) {
		byte entity_tid = ByteUtl.MaxValue127;
		byte[] entity_id = null;

		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = kvs[i];
			byte tid = Wbase_claim_entity_.Reg.Get_tid_or_max_and_log(page_url, kv.KeyToStr()); if (tid == ByteUtl.MaxValue127) continue;
			switch (tid) {
				case Wbase_claim_entity_.Tid__entity_type:		entity_tid = Wbase_claim_entity_type_.Reg.Get_tid_or_fail(kv.ValToBry()); break;
				case Wbase_claim_entity_.Tid__numeric_id:		entity_id = kv.ValToBry(); break;
				case Wbase_claim_entity_.Tid__id:				break;	// ignore
			}
		}
		entity_id = Wbase_claim_entity.To_xid__db(entity_tid, entity_id);	// get p/q and number; PAGE:en.v:Mongolia; EX: [numeric-id=6498663, entity-type=item]; DATE:2016-10-18
		Wdata_prop_val_visitor.Write_entity(bfr, wdata_mgr, lang.Key_bry(), entity_id, BoolUtl.N);
	}
	private static void Write_time(BryWtr bfr, Wdata_wiki_mgr wdata_mgr, Xowe_wiki wiki, byte[] page_url, int pid, byte snak_tid, KeyVal[] kvs) {
		byte[] time = null, calendar = null;
		int precision = 0, before = 0, after = 0;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = kvs[i];
			byte val_tid = Wbase_claim_time_.Reg.Get_tid_or_max_and_log(page_url, kv.KeyToStr()); if (val_tid == ByteUtl.MaxValue127) continue;
			switch (val_tid) {
				case Wbase_claim_time_.Tid__time:			time				= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_time_.Tid__before:			before				= IntUtl.Cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__after:			after				= IntUtl.Cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__precision:		precision			= IntUtl.Cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__calendarmodel:	calendar			= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_time_.Tid__timezone:		break;
			}
		}

		Wbase_date date = Wbase_date_.Parse(time, precision, before, after, BryLni.Eq(calendar, Wbase_claim_time.Calendar_julian));
		Xow_parser_mgr parser_mgr = wiki.Parser_mgr();
		Wdata_prop_val_visitor.Write_time(bfr, parser_mgr.Wbase__time__bfr(), parser_mgr.Wbase__time__fmtr(), parser_mgr.Wbase__time__msgs(), page_url, pid, date);
	}
	private static void Write_quantity(BryWtr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] page_url, KeyVal[] kvs) {
		byte[] amount = null, lbound = null, ubound = null, unit = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = kvs[i];
			byte val_tid = Wbase_claim_quantity_.Reg.Get_tid_or_max_and_log(page_url, kv.KeyToStr()); if (val_tid == ByteUtl.MaxValue127) continue;
			switch (val_tid) {
				case Wbase_claim_quantity_.Tid__amount:		amount	= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_quantity_.Tid__unit:		unit	= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_quantity_.Tid__lowerbound:	lbound	= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_quantity_.Tid__upperbound:	ubound	= To_bry_by_str(kv.Val()); break;
			}
		}
		Wdata_prop_val_visitor.Write_quantity(bfr, wdata_mgr, lang, amount, lbound, ubound, unit);
	}
	private static void Write_geo(BryWtr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] page_url, KeyVal[] kvs) {
		byte[] lat = null, lng = null, alt = null, prc = null, glb = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = kvs[i];
			byte val_tid = Wbase_claim_globecoordinate_.Reg.Get_tid_or_max_and_log(page_url, kv.KeyToStr()); if (val_tid == ByteUtl.MaxValue127) continue;
			switch (val_tid) {
				case Wbase_claim_globecoordinate_.Tid__latitude:		lat = To_bry_by_double(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__longitude:		lng = To_bry_by_double(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__altitude:		alt = To_bry_by_str(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__precision:		prc = To_bry_by_double(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__globe:			glb = To_bry_by_str(kv.Val()); break;
			}
		}
		Wdata_prop_val_visitor.Write_geo(BoolUtl.Y, bfr, wdata_mgr.Hwtr_mgr().Lbl_mgr(), wdata_mgr.Hwtr_mgr().Msgs(), lat, lng, alt, prc, glb);
	}
	private static void Write_langtext(BryWtr bfr, byte[] page_url, KeyVal[] kvs) {
		byte[] text = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = kvs[i];
			byte val_tid = Wbase_claim_monolingualtext_.Reg.Get_tid_or_max_and_log(page_url, kv.KeyToStr()); if (val_tid == ByteUtl.MaxValue127) continue;
			switch (val_tid) {
				case Wbase_claim_monolingualtext_.Tid__text:		text = To_bry_by_str(kv.Val()); break;
				case Wbase_claim_monolingualtext_.Tid__language:	break;
			}
		}
		Wdata_prop_val_visitor.Write_langtext(bfr, text);
	}
	public static int To_pid_int(byte[] pid) {return BryUtl.ToIntOr(pid, 1, pid.length, -1);}	// skip "P" at bgn; EX: "p123" -> 123
	private static byte[] To_bry_by_str(Object o) {
		String rv = StringUtl.Cast(o);
		return rv == null ? null : BryUtl.NewU8(rv);
	}
	private static byte[] To_bry_by_double(Object o) {
		double rv = DoubleUtl.Cast(o);
		return BryUtl.NewA7(DoubleUtl.ToStr(rv));
	}
}
