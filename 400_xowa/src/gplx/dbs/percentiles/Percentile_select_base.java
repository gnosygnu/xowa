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
					boolean none_left = rng.Score_bgn() == 0;
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
