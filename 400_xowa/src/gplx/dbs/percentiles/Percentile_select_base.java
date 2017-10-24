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
public abstract class Percentile_select_base {	// SELECT * FROM x ORDER BY y LIMIT 10;
	protected Cancelable cxl;
	protected Percentile_rng rng;
	protected Percentile_rng_log rng_log;
	protected void Select() {
		Db_rdr rdr = null;
		try {
			int rdr_found = 0;
			while (true) {
				if (cxl.Canceled()) return;
				if (rdr == null) {
					rdr = Rdr__init();				// EXPENSIVE
					rdr_found = 0;
					if (cxl.Canceled()) return;
				}
				if (!Row__read(rdr)) {				// EXPENSIVE
					if (cxl.Canceled()) return;
					rng_log.Log(rng.Score_bgn(), rng.Score_end(), rng.Found_rdr(), rng.Found_all(), rng.Elapsed());
					rdr = Rdr__term(rdr);
					Rng__update(rdr_found);
					boolean found_enough = Found_enough();
					boolean none_left = rng.Score_end() == 0; // search is done when score_end == 0; note that this is set in Percentile_rng; DATE:2017-04-24
					Rdr__done(found_enough, none_left);
					if (found_enough || none_left)
						break;
					else
						continue;	// resume from top; will create new rdrd
				}
				if (Row__eval()) ++rdr_found;
			}
		}
		catch (Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "error during percentile; err=~{0}", Err_.Message_gplx_log(exc));
		}
		finally {
			rdr = Rdr__term(rdr);
		}
	}
	protected abstract Db_rdr		Rdr__init();
	@gplx.Virtual protected void			Rdr__done(boolean found_enough, boolean none_left) {}
	@gplx.Virtual protected Db_rdr		Rdr__term(Db_rdr rdr) {
		if (rdr != null) rdr.Rls();
		return null;
	}
	@gplx.Virtual protected void			Rng__update(int rdr_found) {rng.Update(rdr_found);}
	@gplx.Virtual protected boolean			Row__read(Db_rdr rdr) {return true;}
	@gplx.Virtual protected boolean			Row__eval() {return true;}	// NOTE: return true by default; DEPENDENCY: Srch_word_count_wkr
	@gplx.Virtual protected boolean			Found_enough() {return false;}
}
