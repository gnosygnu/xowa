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
package gplx.dbs.percentiles; import gplx.*; import gplx.dbs.*;
public class Percentile_rng {
	private long total_max; private int total_needed;
	private int score_max, score_len_max;
	private long prv_time;
	public int Score_bgn()		{return score_bgn;} private int score_bgn;
	public int Score_end()		{return score_end;} private int score_end;
	public int Score_len()		{return score_len;} private int score_len;
	public int Found_rdr()		{return found_rdr;} private int found_rdr;
	public int Found_all()		{return found_all;} private int found_all;
	public int Elapsed()		{return elapsed;}	private int elapsed;
	public Percentile_rng Init(long total_max, int score_max) {
		this.total_max = total_max;
		this.score_max = score_max;
		this.score_len_max = score_max / 20;	// limit to 5%
		return this;
	}
	public void Select_init(int total_needed, int prv_score_bgn, int prv_score_len, int score_len_adj) {
		this.total_needed = total_needed;
		this.found_all = 0;
		this.prv_time = gplx.core.envs.System_.Ticks();
		int score_unit = Calc_score_unit(total_needed, total_max, score_max);
		if (prv_score_bgn == Score_null) {
			score_len = score_unit + (score_unit * score_len_adj);
			score_bgn = score_max;
			Rng_len_(Bool_.Y);
		}
		else {
			score_len = prv_score_len;
			score_bgn = prv_score_bgn;
			score_end = score_bgn + score_len;
		}
	}
	public void Update(int found_rdr) {
		this.found_rdr    = found_rdr;
		this.found_all += found_rdr;

		// calc rng_multiplier based on found_rdr and total_needed; EX: 100=total_needed; 10=found_rdr; 40=found_all -> 6=rng_multiplier; (100 - 40 / 10)
		int rng_multiplier = 1;
		if (found_rdr == 0) {
			rng_multiplier = 4;
		} else {
			int total_remaining = total_needed - found_all;
			rng_multiplier = total_remaining == 0 ? 1 : Math_.Ceil_as_int(total_remaining / found_rdr);
		}

		// calc new score_len
		int new_score_len = score_len * rng_multiplier;
		if		(new_score_len < 1)				new_score_len = score_len;
		else if (new_score_len > score_len_max) new_score_len = score_len_max;
		score_len = new_score_len;
		Rng_len_(Bool_.N);

		// update times
		long new_time = gplx.core.envs.System_.Ticks();
		this.elapsed = Int_.Subtract_long(new_time, prv_time);
		prv_time = new_time;
	}
	private void Rng_len_(boolean first) {
		score_end = score_bgn + (first ? 1 : 0);	// + 1 to include rows with scores at max; EX: > 999,998 AND < 1,000,001
		score_bgn = score_end - score_len;

		// note that score_bgn will be first to go negative; EX: (2,000 : 12,000) -> (-8,000 : 2,000) -> (0 : 2,000) -> search still run for 0 - 2000; DATE:2017-04-24
		if (score_bgn < 0)
			score_bgn = 0;

		// note that score_end will go to 0 only at end of lookup; EX: (0 : 2,000) -> (-10,000 : -8,000) -> (0 : 0) -> search will not be run; DATE:2017-04-24
		if (score_end < 0)
			score_end = 0;
	}
	@gplx.Internal protected static int Calc_score_unit(int total_needed, long total_max, int score_max) {// TEST:
		int rv = (int)Math_.Ceil(Math_.Div_safe_as_double(total_needed, Math_.Div_safe_as_double(total_max, score_max)));	// EX: 100 needed / (16 M / 1 M) -> 7 units to fill 100
		if (rv > score_max) rv = score_max;			// never allow score_unit to be > score_max; occurs when total_needed > total_max; EX: 50 needed; 10 available
		return rv;
	}
	public static final int Score_null = -1;
}
