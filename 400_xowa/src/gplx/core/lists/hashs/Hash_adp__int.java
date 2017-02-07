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
package gplx.core.lists.hashs; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
import gplx.core.primitives.*;
public class Hash_adp__int {
	private final    Hash_adp hash = Hash_adp_.New();
	private final    Int_obj_ref tmp_key = Int_obj_ref.New_neg1();
	public void Clear()								{hash.Clear();}
	public int Len()								{return hash.Count();}
	public Object Get_by_or_fail(int key)			{synchronized (tmp_key) {return hash.Get_by_or_fail(tmp_key.Val_(key));}}	// LOCK:used by Xomp_ns_ord_mgr in xomp; DATE:2016-10-18
	public Object Get_by_or_null(int key)			{synchronized (tmp_key) {return hash.Get_by(tmp_key.Val_(key));}}	// LOCK:used by Xomp_ns_ord_mgr in xomp; DATE:2016-10-18
	public void Add(int key, Object obj)			{hash.Add(Int_obj_ref.New(key), obj);}
	public void Add(Int_obj_ref key, Object obj)	{hash.Add(key, obj);}
	public void Add_if_dupe_use_1st(int key, Object obj)			{hash.Add_if_dupe_use_1st(Int_obj_ref.New(key), obj);}
	public void Add_if_dupe_use_nth(Int_obj_ref key, Object obj)	{hash.Add_if_dupe_use_nth(key, obj);}
}
