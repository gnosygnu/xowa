/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases.parsers;
import gplx.langs.jsons.Json_ary;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_kv;
import gplx.langs.jsons.Json_nde;
import gplx.langs.jsons.Json_parser;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp_list;
import gplx.xowa.xtns.wbases.claims.Wbase_references_grp;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_entity_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_globecoordinate;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_monolingualtext;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_quantity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_string;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_value;
import gplx.xowa.xtns.wbases.core.Wdata_alias_itm;
import gplx.xowa.xtns.wbases.core.Wdata_langtext_itm;
import gplx.xowa.xtns.wbases.core.Wdata_sitelink_itm;

abstract class Wdata_doc_parser_fxt_base {
	protected Wdata_doc_parser wdoc_parser;
	private final Json_parser json_parser = new Json_parser();
	private final BryWtr tmp_time_bfr = BryWtr.New();
	public void Init() {
		if (wdoc_parser == null) wdoc_parser = Make_parser();
	}
	public abstract Wdata_doc_parser Make_parser();
	public Wdata_sitelink_itm Make_sitelink(String site, String name, String... badges) {return new Wdata_sitelink_itm(BryUtl.NewU8(site), BryUtl.NewU8(name), BryUtl.Ary(badges));}
	public Wdata_langtext_itm Make_langval(String lang, String text) {return new Wdata_langtext_itm(BryUtl.NewU8(lang), BryUtl.NewU8(text));}
	public Wdata_alias_itm Make_alias(String lang, String... vals) {return new Wdata_alias_itm(BryUtl.NewU8(lang), BryUtl.Ary(vals));}
	public Wbase_claim_base Make_claim_string           (int pid, String val) {return new Wbase_claim_string(pid, Wbase_claim_value_type_.Tid__value, BryUtl.NewU8(val));}
	public Wbase_claim_base Make_claim_entity_qid       (int pid, int eid) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__item, IntUtl.ToBry(eid));}
	public Wbase_claim_base Make_claim_entity_pid       (int pid, int eid) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__property, IntUtl.ToBry(eid));}
	public Wbase_claim_base Make_claim_entity_lid       (int pid, int eid) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__lexeme, IntUtl.ToBry(eid));}
	public Wbase_claim_base Make_claim_entity_fid       (int pid, String id) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__form, null, BryUtl.NewU8(id));}
	public Wbase_claim_base Make_claim_entity_sid       (int pid, String id) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__sense, null, BryUtl.NewU8(id));}
	public Wbase_claim_base Make_claim_monolingualtext  (int pid, String lang, String text) {return new Wbase_claim_monolingualtext(pid, Wbase_claim_value_type_.Tid__value, BryUtl.NewU8(lang), BryUtl.NewU8(text));}
	public Wbase_claim_base Make_claim_globecoordinate  (int pid, String lat, String lng, String prc) {return new Wbase_claim_globecoordinate(pid, Wbase_claim_value_type_.Tid__value, BryUtl.NewU8(lat), BryUtl.NewU8(lng), ObjectUtl.NullBry, BryUtl.NewU8(prc), BryUtl.NewA7("http://www.wikidata.org/entity/Q2"));}
	public Wbase_claim_base Make_claim_quantity         (int pid, int val, int unit, int ubound, int lbound) {return new Wbase_claim_quantity(pid, Wbase_claim_value_type_.Tid__value, BryUtl.NewU8(IntUtl.ToStr(val)), BryUtl.NewU8(IntUtl.ToStr(unit)), BryUtl.NewU8(IntUtl.ToStr(ubound)), BryUtl.NewU8(IntUtl.ToStr(lbound)));}
	public Wbase_claim_base Make_claim_time             (int pid, String val) {return new Wbase_claim_time(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_time_.To_bry(tmp_time_bfr, val), Wbase_claim_time_.Dflt__timezone.Val_bry(), Wbase_claim_time_.Dflt__before.Val_bry(), Wbase_claim_time_.Dflt__after.Val_bry(), Wbase_claim_time_.Dflt__precision.Val_bry(), Wbase_claim_time_.Dflt__calendarmodel.Val_bry());}
	public Wbase_claim_base Make_claim_novalue          (int pid) {return new Wbase_claim_value(pid, Wbase_claim_type_.Tid__unknown, Wbase_claim_value_type_.Tid__novalue);}

	public void Test_entity(String raw, String expd)		{GfoTstr.EqObj(expd, StringUtl.NewU8(wdoc_parser.Parse_qid(json_parser.Parse_by_apos(raw))));}
	public void Test_sitelinks(String raw, Wdata_sitelink_itm... expd) {
		Ordered_hash actl_hash = wdoc_parser.Parse_sitelinks(Q1_bry, json_parser.Parse_by_apos(raw));
		GfoTstr.EqAryObjAry((Object[])actl_hash.ToAry(Wdata_sitelink_itm.class), expd);
	}
	public void Test_labels(String raw, Wdata_langtext_itm... expd)		{Test_langvals(raw, BoolUtl.Y, expd);}
	public void Test_descriptions(String raw, Wdata_langtext_itm... expd)	{Test_langvals(raw, BoolUtl.N, expd);}
	private void Test_langvals(String raw, boolean labels_or_descriptions, Wdata_langtext_itm... expd) {
		Ordered_hash actl_hash = wdoc_parser.Parse_langvals(Q1_bry, json_parser.Parse_by_apos(raw), labels_or_descriptions);
		GfoTstr.EqAryObjAry((Object[])actl_hash.ToAry(Wdata_langtext_itm.class), expd);
	}
	public void Test_aliases(String raw, Wdata_alias_itm... expd) {
		Ordered_hash actl_hash = wdoc_parser.Parse_aliases(Q1_bry, json_parser.Parse_by_apos(raw));
		GfoTstr.EqAryObjAry((Object[])actl_hash.ToAry(Wdata_alias_itm.class), expd);
	}
	public void Test_claims(String raw, Wbase_claim_base... expd) {
		Ordered_hash actl_hash = wdoc_parser.Parse_claims(Q1_bry, json_parser.Parse_by_apos(raw));
		List_adp actl_list = Wbase_claim_grp.Xto_list(actl_hash);
		GfoTstr.EqAryObjAry((Object[])actl_list.ToAry(Wbase_claim_base.class), expd);
	}
	public void Test_claims_data(String raw, Wbase_claim_base expd) {
		Json_doc jdoc = json_parser.Parse_by_apos(raw);
		Wbase_claim_base actl = wdoc_parser.Parse_claims_data(Q1_bry, 1, Wbase_claim_value_type_.Tid__value, jdoc.Root_nde());
		GfoTstr.EqObj(expd.toString(), actl.toString());
	}
	public void Test_qualifiers(String raw, Wbase_claim_base... expd_itms) {
		Json_doc jdoc = json_parser.Parse_by_apos(raw);
		Json_nde qualifiers_nde = Json_nde.Cast(Json_kv.Cast(jdoc.Root_nde().Get_at(0)).Val());
		Wbase_claim_grp_list actl = wdoc_parser.Parse_qualifiers(Q1_bry, qualifiers_nde);
		GfoTstr.EqAryObjAry(expd_itms, To_ary(actl));
	}
	public void Test_references(String raw, int[] expd_order, Wbase_claim_base... expd_itms) {
		Json_doc jdoc = json_parser.Parse_by_apos(raw);
		Json_ary owner = Json_ary.cast_or_null(Json_kv.Cast(jdoc.Root_nde().Get_at(0)).Val());
		Wbase_references_grp[] actl = wdoc_parser.Parse_references(Q1_bry, owner);
		Wbase_references_grp actl_grp = actl[0];
		GfoTstr.EqAryObj(expd_order, actl_grp.Snaks_order());
		GfoTstr.EqAryObjAry(expd_itms, To_ary(actl_grp.Snaks()));
	}
	public void Test_pid_order(String raw, int... expd) {
		Json_doc jdoc = json_parser.Parse_by_apos(raw);
		Json_ary nde = Json_ary.cast_or_null(Json_kv.Cast(jdoc.Root_nde().Get_at(0)).Val());
		int[] actl = wdoc_parser.Parse_pid_order(Q1_bry, nde);
		GfoTstr.EqAry(expd, actl);
	}
	Wbase_claim_base[] To_ary(Wbase_claim_grp_list list) {
		List_adp rv = List_adp_.New();
		int list_len = list.Len();
		for (int i = 0; i < list_len; ++i) {
			Wbase_claim_grp grp = list.Get_at(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wbase_claim_base itm = grp.Get_at(j);
				rv.Add(itm);
			}
		}
		return (Wbase_claim_base[])rv.ToAryAndClear(Wbase_claim_base.class);
	}
	private static final byte[] Q1_bry = BryUtl.NewA7("Q1");
}
class Wdata_doc_parser_v2_fxt extends Wdata_doc_parser_fxt_base {
	@Override public Wdata_doc_parser Make_parser() {return new Wdata_doc_parser_v2();}
}
