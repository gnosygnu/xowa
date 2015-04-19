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
package gplx.xowa.wikis.domains.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
import gplx.xowa.langs.*;
class Xow_domain_crt_itm_parser {
	public Xow_domain_crt_kv[] Parse_as_kv_ary_or_null(byte[] raw) {
		ListAdp rv = ListAdp_.new_();
		byte[][] line_ary = Bry_.Split_lines(raw);
		int line_len = line_ary.length;
		for (int i = 0; i < line_len; ++i) {
			byte[] line = line_ary[i];
			if (line.length == 0) continue; // ignore blank lines
			byte[][] word_ary = Bry_.Split(line, Byte_ascii.Pipe);
			int word_len = word_ary.length;
			if (word_len != 2) return null;	// not A|B; exit now;
			Xow_domain_crt_itm key_itm = Xow_domain_crt_itm_parser.I.Parse_as_in(word_ary[0]);
			if (key_itm == Xow_domain_crt_itm__null.I) return null;	// invalid key; exit
			Xow_domain_crt_itm val_itm = Xow_domain_crt_itm_parser.I.Parse_as_in(word_ary[1]);
			if (val_itm == Xow_domain_crt_itm__null.I) return null;	// invalid val; exit
			rv.Add(new Xow_domain_crt_kv(key_itm, val_itm));
		}
		return (Xow_domain_crt_kv[])rv.Xto_ary_and_clear(Xow_domain_crt_kv.class);
	}
	public Xow_domain_crt_itm Parse_as_in(byte[] raw) {
		byte[][] terms = Bry_.Split(raw, Byte_ascii.Comma, Bool_.Y);
		int len = terms.length;
		Xow_domain_crt_itm[] rv_ary = new Xow_domain_crt_itm[len];
		for (int i = 0; i < len; ++i)
			rv_ary[i] = Parse_itm(terms[i]);
		return new Xow_domain_crt_itm__in(rv_ary);
	}
	public Xow_domain_crt_itm Parse_itm(byte[] raw) {						
		Xow_domain_crt_itm rv = (Xow_domain_crt_itm)itm_hash.Get_by_bry(raw); if (rv != null) return rv;	// singleton; EX: <self>, <same_type>, etc..
		int raw_len = raw.length;
		if		(Bry_.HasAtBgn(raw, Wild_lang)) {		// EX: *.wikipedia
			int wiki_tid = Xow_domain_.Tid__get_int(raw, Wild_lang.length, raw_len);
			return wiki_tid == Xow_domain_.Tid_int_null ? Xow_domain_crt_itm__null.I : new Xow_domain_crt_itm__type(wiki_tid);
		}
		else if	(Bry_.HasAtEnd(raw, Wild_type)) {		// EX: en.*
			Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key(raw, 0, raw_len - Wild_type.length);
			return lang_itm == null ? Xow_domain_crt_itm__null.I : new Xow_domain_crt_itm__lang(lang_itm.Id());
		}
		else
			return new Xow_domain_crt_itm__wiki(raw);	// EX: en.wikipedia.org
	}
	private static final Hash_adp_bry itm_hash = Hash_adp_bry.cs_()
	.Add_str_obj("<self>"		, Xow_domain_crt_itm__self.I)
	.Add_str_obj("<same_type>"	, Xow_domain_crt_itm__same_type.I)
	.Add_str_obj("<same_lang>"	, Xow_domain_crt_itm__same_lang.I)
	.Add_str_obj("*.*"			, Xow_domain_crt_itm__any_standard.I)
	;
	private static final byte[] Wild_lang = Bry_.new_ascii_("*."), Wild_type = Bry_.new_ascii_(".*");
        public static final Xow_domain_crt_itm_parser I = new Xow_domain_crt_itm_parser(); Xow_domain_crt_itm_parser() {}
}
