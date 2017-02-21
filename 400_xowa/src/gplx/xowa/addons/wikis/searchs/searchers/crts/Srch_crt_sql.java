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
package gplx.xowa.addons.wikis.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.langs.regxs.*;
public class Srch_crt_sql {
	public Srch_crt_sql(int tid, String eq, String rng_bgn, String rng_end, String like, Gfo_pattern pattern) {
		this.Tid = tid;
		this.Eq = eq;
		this.Rng_bgn = rng_bgn;
		this.Rng_end = rng_end;
		this.Like = like;
		this.Pattern = pattern;
	}
	public final    int				Tid;
	public final    String			Eq;
	public final    String			Rng_bgn;
	public final    String			Rng_end;
	public final    String			Like;
	public final    Gfo_pattern		Pattern;	// NOTE: only supports LIKE; GLOB requires regex
	public int						Eq_id;

	public static final int
	  Tid__eq		= 0		// EX: 'ab'			-> "word_text = 'ab'"
	, Tid__rng		= 1		// EX: 'ab*'		-> "word_text >= 'ab' AND word_text < 'ac'"
	, Tid__like		= 2		// EX: 'a*b', '*a*b'-> "word_text LIKE 'a%b%'"
	;
	public static Srch_crt_sql New_or_null(byte[] raw, byte wildcard_byte) {
		if (raw == null) return null;	// null for join itms; EX: "+", ","
		int raw_len = raw.length;

		// get tid
		int wildcard_pos = Bry_find_.Find_fwd(raw, wildcard_byte, 0, raw_len);
		int tid = -1;
		if		(wildcard_pos == Bry_find_.Not_found)	tid = Srch_crt_sql.Tid__eq;		// EX: 'a'
		else if (wildcard_pos == raw_len - 1)			tid = Srch_crt_sql.Tid__rng;	// EX: 'a*'
		else											tid = Srch_crt_sql.Tid__like;	// EX: '*a'

		// get rng_bgn, rng_end or like
		String eq = "", rng_bgn = "", rng_end = "", like = "";
		byte[] pattern_raw = raw;
		switch (tid) {
			case Srch_crt_sql.Tid__eq:
				eq = String_.new_a7(raw);
				break;
			case Srch_crt_sql.Tid__rng:
				byte[] rng_tmp = Bry_.Mid(raw, 0, raw_len - 1);
				rng_bgn = String_.new_u8(rng_tmp);
				rng_end = String_.new_u8(gplx.core.intls.Utf8_.Increment_char_at_last_pos(rng_tmp));
				break;
			case Srch_crt_sql.Tid__like:
				like = String_.new_u8(Bry_.Replace(raw, wildcard_byte, gplx.dbs.sqls.Sql_qry_wtr_.Like_wildcard));
				byte like_escape_byte = gplx.xowa.addons.wikis.searchs.searchers.wkrs.Srch_link_wkr_sql.Like_escape_byte;
				Bry_bfr tmp_bfr = Bry_bfr_.Get();
				try {pattern_raw = Bry_.Resolve_escape(tmp_bfr, like_escape_byte, raw, 0, raw.length);}
				finally {tmp_bfr.Mkr_rls();}
				break;
		}
		Gfo_pattern pattern = tid == Srch_crt_sql.Tid__eq ? null : new Gfo_pattern(pattern_raw);
		return new Srch_crt_sql(tid, eq, rng_bgn, rng_end, like, pattern);
	}
}
