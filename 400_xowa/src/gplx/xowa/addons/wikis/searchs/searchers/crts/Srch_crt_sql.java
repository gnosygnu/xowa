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
package gplx.xowa.addons.wikis.searchs.searchers.crts;
import gplx.types.basics.strings.unicodes.Utf8Utl;
import gplx.dbs.sqls.SqlQryWtrUtl;
import gplx.langs.regxs.*;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.BryBfrUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.utls.StringUtl;
public class Srch_crt_sql {
	public Srch_crt_sql(int tid, String eq, String rng_bgn, String rng_end, String like, Gfo_pattern pattern) {
		this.Tid = tid;
		this.Eq = eq;
		this.Rng_bgn = rng_bgn;
		this.Rng_end = rng_end;
		this.Like = like;
		this.Pattern = pattern;
	}
	public final int				Tid;
	public final String			Eq;
	public final String			Rng_bgn;
	public final String			Rng_end;
	public final String			Like;
	public final Gfo_pattern		Pattern;	// NOTE: only supports LIKE; GLOB requires regex
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
		int wildcard_pos = BryFind.FindFwd(raw, wildcard_byte, 0, raw_len);
		int tid = -1;
		if		(wildcard_pos == BryFind.NotFound)	tid = Srch_crt_sql.Tid__eq;		// EX: 'a'
		else if (wildcard_pos == raw_len - 1)			tid = Srch_crt_sql.Tid__rng;	// EX: 'a*'
		else											tid = Srch_crt_sql.Tid__like;	// EX: '*a'

		// get rng_bgn, rng_end or like
		String eq = "", rng_bgn = "", rng_end = "", like = "";
		byte[] pattern_raw = raw;
		switch (tid) {
			case Srch_crt_sql.Tid__eq:
				eq = StringUtl.NewA7(raw);
				break;
			case Srch_crt_sql.Tid__rng:
				byte[] rng_tmp = BryLni.Mid(raw, 0, raw_len - 1);
				rng_bgn = StringUtl.NewU8(rng_tmp);
				rng_end = StringUtl.NewU8(Utf8Utl.IncrementCharAtLastPos(rng_tmp));
				break;
			case Srch_crt_sql.Tid__like:
				like = StringUtl.NewU8(BryUtl.Replace(raw, wildcard_byte, SqlQryWtrUtl.Like_wildcard));
				byte like_escape_byte = gplx.xowa.addons.wikis.searchs.searchers.wkrs.Srch_link_wkr_sql.Like_escape_byte;
				BryWtr tmp_bfr = BryBfrUtl.Get();
				try {pattern_raw = BryUtlByWtr.ResolveEscape(tmp_bfr, like_escape_byte, raw, 0, raw.length);}
				finally {tmp_bfr.MkrRls();}
				break;
		}
		Gfo_pattern pattern = tid == Srch_crt_sql.Tid__eq ? null : new Gfo_pattern(pattern_raw);
		return new Srch_crt_sql(tid, eq, rng_bgn, rng_end, like, pattern);
	}
}
