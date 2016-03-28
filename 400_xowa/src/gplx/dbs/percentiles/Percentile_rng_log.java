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
package gplx.dbs.percentiles; import gplx.*; import gplx.dbs.*;
public class Percentile_rng_log {
	private final Log_tbl_fmtr fmtr = new Log_tbl_fmtr();
	private byte[] search; private int rslts_needed;
	private int rdr_idx;
	public Percentile_rng_log(int score_max) {
		fmtr.Add_str("search"			, 50)
			.Add_int("rslts_needed"		,  1, 999)
			.Add_int("rdr_idx"			,  0, 100)		// warn if more than 100 sql queries
			.Add_int("score_bgn"		,  0, score_max)
			.Add_int("score_end"		,  0, score_max)
			.Add_int("score_len"		,  1, 100000)
			.Add_int("rdr_found"		,  0, 9999)		// warn if more than 9.999 seconds
			.Add_int("total_found"		,  0, 999)
			.Add_int("total_needed"		,  1, 999)
			;
	}
	public void Init(byte[] search, int rslts_needed) {
		this.search = search; this.rslts_needed = rslts_needed;
		rdr_idx = -1;
	}
	public void Log(int score_bgn, int score_end, int rdr_found, int total_found, int pass_time) {
		fmtr.Log(search, rslts_needed, ++rdr_idx, score_bgn, score_end, score_end - score_bgn, rdr_found, total_found, pass_time);
	}
	public String To_str_and_clear() {return fmtr.To_str_and_clear();}
}
