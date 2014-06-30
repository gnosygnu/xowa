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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
public class Xosrh_rslt_itm_sorter implements gplx.lists.ComparerAble {
	public byte Tid() {return tid;} public Xosrh_rslt_itm_sorter Tid_(byte v) {tid = v; return this;} private byte tid = Tid_len_dsc;
	public int compare(Object lhsObj, Object rhsObj) {
		Xodb_page lhs = (Xodb_page)lhsObj;
		Xodb_page rhs = (Xodb_page)rhsObj;
		if		(lhs == null || rhs == null || tid == Tid_none)	return CompareAble_.Same;
//			else if	(lhs == null)					return CompareAble_.Less;
//			else if	(rhs == null)					return CompareAble_.More;
		else {
			switch (tid) {
				case Tid_len_dsc:	return Int_.Compare(lhs.Text_len(), rhs.Text_len()) * -1;
				case Tid_ttl_asc:	return Bry_.Compare(lhs.Ttl_wo_ns(), rhs.Ttl_wo_ns());
				case Tid_id:		return Int_.Compare(lhs.Id(), rhs.Id());
				default:			throw Err_mgr._.unhandled_(tid);
			}
		}
	}
	public static final byte Tid_none = 0, Tid_len_dsc = 1, Tid_ttl_asc = 2, Tid_id = 3;
	public static byte parse_(String v) {
		if		(String_.Eq(v, "none"))			return Tid_none;
		else if	(String_.Eq(v, "len_desc"))		return Tid_len_dsc;
		else if	(String_.Eq(v, "title_asc"))	return Tid_ttl_asc;
		else									throw Err_mgr._.unhandled_(v);
	}
	public static String Xto_url_arg(byte v) {
		switch (v) {
			case Tid_none:		return "";
			case Tid_len_dsc:	return "&xowa_sort=len_desc";
			case Tid_ttl_asc:	return "&xowa_sort=title_asc";
			default:			throw Err_mgr._.unhandled_(v);
		}
	}
}
