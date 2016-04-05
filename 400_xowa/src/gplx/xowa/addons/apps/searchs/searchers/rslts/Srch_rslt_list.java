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
package gplx.xowa.addons.apps.searchs.searchers.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*; import gplx.xowa.addons.apps.searchs.searchers.*;
public class Srch_rslt_list {
	private final    Ordered_hash key_hash = Ordered_hash_.New_bry();
	private final    Hash_adp__int id_hash = new Hash_adp__int();
	public int				Score_bgn = gplx.dbs.percentiles.Percentile_rng.Score_null;
	public int				Score_len = gplx.dbs.percentiles.Percentile_rng.Score_null;
	public boolean			Rslts_are_first;
	public boolean			Rslts_are_enough;
	public boolean			Rslts_are_done;
	public int				Len()								{return key_hash.Len();}
	public boolean			Has(byte[] key)						{return key_hash.Has(key);}
	public Srch_rslt_row	Get_by(byte[] key)					{return (Srch_rslt_row)key_hash.Get_by(key);}
	public Srch_rslt_row	Get_at(int i)						{return (Srch_rslt_row)key_hash.Get_at(i);}
	public void				Clear()								{key_hash.Clear(); id_hash.Clear();}
	public void				Add(Srch_rslt_row row)				{key_hash.Add(row.Key, row);}
	public void				Sort()								{key_hash.Sort_by(Srch_rslt_row_sorter.Score_dsc);}
	public boolean				Ids__has(int id)					{return (Srch_rslt_row)id_hash.Get_by(id) != null;}
	public Srch_rslt_row	Ids__get(int id)					{return (Srch_rslt_row)id_hash.Get_by(id);}
	public void				Ids__add(int id, Srch_rslt_row r)	{id_hash.Add(id, r);}
	public void Merge(Srch_rslt_list list) {
		list.Sort();
		int list_len = list.Len();
		for (int i = 0; i < list_len; ++i) {
			Srch_rslt_row row = list.Get_at(i);
			this.Add(row);
		}
		list.Clear();
	}
	public void Process_rdr_done(gplx.dbs.percentiles.Percentile_rng rng, boolean rslts_are_enough, boolean rslts_are_done) {
		this.Score_bgn = rng.Score_bgn();
		this.Score_len = rng.Score_len();
		this.Rslts_are_enough = rslts_are_enough;
		this.Rslts_are_done = rslts_are_done;
	}
}
class Srch_rslt_row_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Srch_rslt_row lhs = (Srch_rslt_row)lhsObj;
		Srch_rslt_row rhs = (Srch_rslt_row)rhsObj;
		int rv = -Int_.Compare(lhs.Page_score, rhs.Page_score);
		if (rv != CompareAble_.Same) return rv;
		return Bry_.Compare(lhs.Page_ttl.Page_txt(), rhs.Page_ttl.Page_txt());
	}
	public static final    Srch_rslt_row_sorter Score_dsc = new Srch_rslt_row_sorter();
}
