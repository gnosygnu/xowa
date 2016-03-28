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
import org.junit.*;
public class Percentile_rng_tst {
	private final    Percentile_rng_fxt fxt = new Percentile_rng_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Found__000() {
		fxt.Test__rng(999994, 1000001);
		fxt.Exec__update(  0).Test__rng(999966, 999994);
		fxt.Exec__update(  0).Test__rng(999854, 999966);
	}
	@Test   public void Found__025() {
		fxt.Test__rng(999994, 1000001);
		fxt.Exec__update( 25).Test__rng(999973, 999994);
		fxt.Exec__update( 25).Test__rng(999931, 999973);
		fxt.Exec__update( 25).Test__rng(999889, 999931);
		fxt.Exec__update( 25).Test__rng(999847, 999889);
	}
	@Test   public void Calc_score_unit() {
		fxt.Test__calc_score_unit(50, 16000000, 1000000,       4);	// to fill 50 ->   16 pages per point -> read every 4 points to get 64 pages
		fxt.Test__calc_score_unit(50,     1000, 1000000,   50000);	// to fill 50 -> 1000 points per page -> read every 50k points to get 50 pages
		fxt.Test__calc_score_unit(50,       25, 1000000, 1000000);	// range bounds check; to fill 50, always read full amount
	}
}
class Percentile_rng_fxt {
	private final    Percentile_rng rng = new Percentile_rng();
	public void Clear() {
		this.Exec__init_for_wiki(16000000, 1000000);
		this.Exec__init_for_search(100, 0);
	}
	public Percentile_rng_fxt Exec__init_for_wiki  (int pages_max, int score_max) {
		rng.Init(pages_max, score_max); return this;
	}
	public Percentile_rng_fxt Exec__init_for_search(int request_count, int score_len_adj) {
		rng.Select_init(request_count, Percentile_rng.Score_null, Percentile_rng.Score_null, score_len_adj); return this;
	}
	public Percentile_rng_fxt Exec__update(int rdr_found) {
		rng.Update(rdr_found); return this;
	}
	public void Test__rng(int expd_bgn, int expd_end) {
		Tfds.Eq(expd_end, rng.Score_end(), "rng_end");
		Tfds.Eq(expd_bgn, rng.Score_bgn(), "rng_bgn");
	}
	public void Test__calc_score_unit(int request_count, long pages_max, int score_max, int expd) {
		Tfds.Eq(expd, Percentile_rng.Calc_score_unit(request_count, pages_max, score_max));
	}
}
