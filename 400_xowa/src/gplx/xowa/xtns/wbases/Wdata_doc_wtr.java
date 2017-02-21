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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.jsons.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.parsers.*;
public class Wdata_doc_wtr {
	private Json_doc_wtr wtr = new Json_doc_wtr();
	public byte[] Xto_bry(Wdata_doc wdoc) {
		wtr.Nde_bgn();
		wtr.Kv(Bool_.N, Wdata_doc_parser_v1.Bry_entity, wdoc.Qid());
		Xto_bry__list(Wdata_doc_parser_v1.Bry_label, wdoc.Label_list());
		Xto_bry__list(Wdata_doc_parser_v1.Bry_description, wdoc.Descr_list());
		Xto_bry__sitelinks(Wdata_doc_parser_v1.Bry_links, wdoc.Slink_list());
		Xto_bry__aliases(wdoc.Alias_list());
		Xto_bry__claims(wdoc.Qid(), wdoc.Claim_list());
		wtr.Nde_end();
		return wtr.Bld();
	}
	private void Xto_bry__list(byte[] key, Ordered_hash list) {
		int len = list.Count();
		if (len == 0) return;
		wtr.Key(true, key);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			Keyval kv = (Keyval)list.Get_at(i);
			wtr.Kv(i != 0, Bry_.new_u8(kv.Key()), Bry_.new_u8(kv.Val_to_str_or_empty()));
		}
		wtr.Nde_end();
		list.Clear();
	}
	private void Xto_bry__sitelinks(byte[] key, Ordered_hash list) {	// NOTE: changed to reflect new sitelinks structure; DATE:2014-02-04
		int len = list.Count();
		if (len == 0) return;
		wtr.Key(true, key);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			if (i != 0) wtr.Comma();
			Keyval kv = (Keyval)list.Get_at(i);
			wtr.Key(false, Bry_.new_u8(kv.Key()));												// write key;	EX: enwiki:
			wtr.Nde_bgn();																			// bgn nde;		EX: {
			wtr.Kv(false, Wdata_doc_parser_v1.Bry_name, Bry_.new_u8(kv.Val_to_str_or_empty()));	// write name;	EX:   name=Earth
			wtr.Nde_end();																			// end nde;		EX: }
		}
		wtr.Nde_end();
		list.Clear();
	}
	private void Xto_bry__aliases(Ordered_hash aliases) {
		int len = aliases.Count();
		if (len == 0) return;
		wtr.Key(true, Wdata_doc_parser_v1.Bry_aliases);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			Wdata_alias_itm alias = (Wdata_alias_itm)aliases.Get_at(i);
			wtr.Key(i != 0, alias.Lang());
			wtr.Ary_bgn();
			byte[][] aliases_ary = alias.Vals();
			int aliases_len = aliases_ary.length;
			for (int j = 0; j < aliases_len; j++) {
				byte[] aliases_itm = aliases_ary[j];
				wtr.Val(j != 0, aliases_itm);
			}
			wtr.Ary_end();
		}
		wtr.Nde_end();
		aliases.Clear();
	}
	private void Xto_bry__claims(byte[] qid, Ordered_hash props) {
		int len = props.Count();
		if (len == 0) return;
		wtr.Key(true, Wdata_doc_parser_v1.Bry_claims);
		wtr.Ary_bgn();
		for (int i = 0; i < len; i++) {
			if (i != 0) wtr.Comma();
			Wbase_claim_base prop = (Wbase_claim_base)props.Get_at(i);
			wtr.Nde_bgn();
			wtr.Key(false, Wdata_dict_claim_v1.Bry_m);
			wtr.Ary_bgn();
			wtr.Val(Bool_.N, Wbase_claim_value_type_.Reg.Get_bry_or_fail(prop.Snak_tid()));
			wtr.Val(Bool_.Y, prop.Pid());
			if (prop.Snak_tid() == Wbase_claim_value_type_.Tid__value) {
				switch (prop.Val_tid()) {
					case Wbase_claim_type_.Tid__string:
						Wbase_claim_string claim_str = (Wbase_claim_string)prop;
						wtr.Val(Bool_.Y, Wbase_claim_type_.Itm__string.Key_bry());
						wtr.Val(Bool_.Y, claim_str.Val_bry());
						break;
					case Wbase_claim_type_.Tid__entity:
						Wbase_claim_entity claim_entity = (Wbase_claim_entity)prop;
						wtr.Val(Bool_.Y, Wbase_claim_type_.Itm__entity.Key_bry());
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv(Bool_.N, Wbase_claim_entity_.Itm__entity_type.Key_bry()			, claim_entity.Entity_tid_bry());
						wtr.Kv(Bool_.Y, Wbase_claim_entity_.Itm__numeric_id.Key_bry()			, claim_entity.Entity_id());
						wtr.Nde_end();
						break;
					case Wbase_claim_type_.Tid__time:
						Wbase_claim_time claim_time = (Wbase_claim_time)prop;
						wtr.Val(Bool_.Y, Wbase_claim_type_.Itm__time.Key_bry());
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv(Bool_.N, Wbase_claim_time_.Itm__time.Key_bry()					, claim_time.Time());
						wtr.Kv(Bool_.Y, Wbase_claim_time_.Itm__precision.Key_bry()				, Wbase_claim_time_.Dflt__timezone.Val_bry());
						wtr.Kv(Bool_.Y, Wbase_claim_time_.Itm__before.Key_bry()					, Wbase_claim_time_.Dflt__before.Val_bry());
						wtr.Kv(Bool_.Y, Wbase_claim_time_.Itm__after.Key_bry()					, Wbase_claim_time_.Dflt__after.Val_bry());
						wtr.Kv(Bool_.Y, Wbase_claim_time_.Itm__timezone.Key_bry()				, Wbase_claim_time_.Dflt__timezone.Val_bry());
						wtr.Kv(Bool_.Y, Wbase_claim_time_.Itm__calendarmodel.Key_bry()			, Wbase_claim_time_.Dflt__calendarmodel.Val_bry());
						wtr.Nde_end();
						break;
					case Wbase_claim_type_.Tid__globecoordinate: {
						Wbase_claim_globecoordinate claim_globecoordinate = (Wbase_claim_globecoordinate)prop;
						wtr.Val(Bool_.Y, Wbase_claim_type_.Itm__globecoordinate.Key_bry());
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv_double	(Bool_.N, Wbase_claim_globecoordinate_.Itm__latitude.Key_bry()	, Double_.parse(String_.new_a7(claim_globecoordinate.Lat())));
						wtr.Kv_double	(Bool_.Y, Wbase_claim_globecoordinate_.Itm__longitude.Key_bry()	, Double_.parse(String_.new_a7(claim_globecoordinate.Lng())));
						wtr.Kv			(Bool_.Y, Wbase_claim_globecoordinate_.Itm__altitude.Key_bry()	, null);
						wtr.Kv			(Bool_.Y, Wbase_claim_globecoordinate_.Itm__globe.Key_bry()		, Wbase_claim_globecoordinate_.Val_globe_dflt_bry);
						wtr.Kv_double	(Bool_.Y, Wbase_claim_globecoordinate_.Itm__precision.Key_bry()	, .00001d);
						wtr.Nde_end();
						break;
					}
					case Wbase_claim_type_.Tid__quantity: {
						Wbase_claim_quantity claim_quantity = (Wbase_claim_quantity)prop;
						wtr.Val(Bool_.Y, Wbase_claim_type_.Itm__quantity.Key_bry());
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv			(Bool_.N, Wbase_claim_quantity_.Itm__amount.Key_bry()		, claim_quantity.Amount());		// +1,234
						wtr.Kv			(Bool_.Y, Wbase_claim_quantity_.Itm__unit.Key_bry()			, claim_quantity.Unit());		// 1
						wtr.Kv			(Bool_.Y, Wbase_claim_quantity_.Itm__upperbound.Key_bry()	, claim_quantity.Ubound());		// +1,235
						wtr.Kv			(Bool_.Y, Wbase_claim_quantity_.Itm__lowerbound.Key_bry()	, claim_quantity.Lbound());		// +1,233
						wtr.Nde_end();
						break;
					}
					case Wbase_claim_type_.Tid__monolingualtext: {
						Wbase_claim_monolingualtext claim_monolingualtext = (Wbase_claim_monolingualtext)prop;
						wtr.Val(Bool_.Y, Wbase_claim_type_.Itm__monolingualtext.Key_bry());
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv			(Bool_.N, Wbase_claim_monolingualtext_.Itm__text.Key_bry()		, claim_monolingualtext.Text());		// text
						wtr.Kv			(Bool_.Y, Wbase_claim_monolingualtext_.Itm__language.Key_bry()	, claim_monolingualtext.Lang());		// en
						wtr.Nde_end();
						break;
					}
					default: throw Err_.new_unhandled(prop.Val_tid());
				}
			}
			wtr.Ary_end();
			wtr.Kv_ary_empty(Bool_.Y, Wdata_dict_claim_v1.Bry_q);
			wtr.Kv(Bool_.Y, Wdata_dict_claim_v1.Bry_g, qid);
			wtr.Kv(Bool_.Y, Wdata_dict_claim_v1.Bry_rank, Wbase_claim_rank_.Tid__normal);
			wtr.Kv_ary_empty(Bool_.Y, Wdata_dict_claim_v1.Bry_refs);
			wtr.Nde_end();
		}
		wtr.Ary_end();
		props.Clear();
	}
}
