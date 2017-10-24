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
package gplx.xowa.wikis.domains.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
import gplx.xowa.langs.*;
class Xow_domain_crt_itm_parser {
	public Xow_domain_crt_kv_itm[] Parse_as_kv_itms_or_null(byte[] raw) {
		List_adp rv = Parse_as_obj_or_null(raw, Bool_.N);
		return rv == null ? null : (Xow_domain_crt_kv_itm[])rv.To_ary_and_clear(Xow_domain_crt_kv_itm.class);
	}
	public Xow_domain_crt_kv_ary[] Parse_as_kv_arys_or_null(byte[] raw) {
		List_adp rv = Parse_as_obj_or_null(raw, Bool_.Y);
		return rv == null ? null : (Xow_domain_crt_kv_ary[])rv.To_ary_and_clear(Xow_domain_crt_kv_ary.class);
	}
	public List_adp Parse_as_obj_or_null(byte[] raw, boolean is_ary) {
		List_adp rv = List_adp_.New();
		byte[][] line_ary = Bry_split_.Split_lines(raw);
		int line_len = line_ary.length;
		for (int i = 0; i < line_len; ++i) {
			byte[] line = line_ary[i];
			if (line.length == 0) continue; // ignore blank lines
			byte[][] word_ary = Bry_split_.Split(line, Byte_ascii.Pipe);
			int word_len = word_ary.length;
			if (word_len != 2) return null;	// not A|B; exit now;
			Xow_domain_crt_itm key_itm = Xow_domain_crt_itm_parser.Instance.Parse_as_in(word_ary[0]);
			if (key_itm == Xow_domain_crt_itm_.Null) return null;		// invalid key; exit;
			if (is_ary) {
				Xow_domain_crt_itm[] ary_itm = Xow_domain_crt_itm_parser.Instance.Parse_as_ary(word_ary[1]);
				if (ary_itm == null) return null;
				rv.Add(new Xow_domain_crt_kv_ary(key_itm, ary_itm));
			}
			else {
				Xow_domain_crt_itm val_itm = Xow_domain_crt_itm_parser.Instance.Parse_as_in(word_ary[1]);
				if (val_itm == Xow_domain_crt_itm_.Null) return null;	// invalid val; exit;
				rv.Add(new Xow_domain_crt_kv_itm(key_itm, val_itm));
			}
		}
		return rv;
	}
	public Xow_domain_crt_itm Parse_as_in(byte[] raw) {
		Xow_domain_crt_itm[] in_ary = Parse_as_ary(raw);
		return in_ary == null ? Xow_domain_crt_itm_.Null : new Xow_domain_crt_itm__in(in_ary);
	}
	public Xow_domain_crt_itm[] Parse_as_ary(byte[] raw) {
		byte[][] terms = Bry_split_.Split(raw, Byte_ascii.Comma, Bool_.Y);
		int len = terms.length;
		Xow_domain_crt_itm[] rv_ary = new Xow_domain_crt_itm[len];
		for (int i = 0; i < len; ++i) {
			Xow_domain_crt_itm itm = Parse_itm(terms[i]);
			if (itm == Xow_domain_crt_itm_.Null) return null;	// invalid val; exit;
			rv_ary[i] = itm;
		}
		return rv_ary;
	}
	public Xow_domain_crt_itm Parse_itm(byte[] raw) {						
		Xow_domain_crt_itm rv = (Xow_domain_crt_itm)itm_hash.Get_by_bry(raw); if (rv != null) return rv;	// singleton; EX: <self>, <same_type>, etc..
		int raw_len = raw.length;
		if		(Bry_.Has_at_bgn(raw, Wild_lang)) {		// EX: *.wikipedia
			int wiki_tid = Xow_domain_tid_.Get_type_as_tid(raw, Wild_lang.length, raw_len);
			return wiki_tid == Xow_domain_tid_.Tid__null ? Xow_domain_crt_itm_.Null : new Xow_domain_crt_itm__type(wiki_tid);
		}
		else if	(Bry_.Has_at_end(raw, Wild_type)) {		// EX: en.*
			Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_null(raw, 0, raw_len - Wild_type.length);
			return lang_itm == null ? Xow_domain_crt_itm_.Null : new Xow_domain_crt_itm__lang(lang_itm.Key());
		}
		else
			return new Xow_domain_crt_itm__wiki(raw);	// EX: en.wikipedia.org
	}
	private static final    Hash_adp_bry itm_hash = Hash_adp_bry.cs()
	.Add_str_obj("<self>"		, Xow_domain_crt_itm__self.Instance)
	.Add_str_obj("<same_type>"	, Xow_domain_crt_itm__same_type.Instance)
	.Add_str_obj("<same_lang>"	, Xow_domain_crt_itm__same_lang.Instance)
	.Add_str_obj("<any>"		, Xow_domain_crt_itm__any_wiki.Instance)
	;
	private static final    byte[] Wild_lang = Bry_.new_a7("*."), Wild_type = Bry_.new_a7(".*");
        public static final    Xow_domain_crt_itm_parser Instance = new Xow_domain_crt_itm_parser(); Xow_domain_crt_itm_parser() {}
}
