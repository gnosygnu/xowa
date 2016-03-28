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
package gplx.xowa.addons.searchs.searchers.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*; import gplx.xowa.addons.searchs.searchers.*;
import gplx.core.primitives.*;
class Hash_adp__int {
	private final Hash_adp hash = Hash_adp_.new_();
	private final Int_obj_ref tmp_key = Int_obj_ref.neg1_();
	public void Clear()						{hash.Clear();}
	public Object Get_by(int key)			{return hash.Get_by(tmp_key.Val_(key));}
	public void Add(int key, Object obj)	{hash.Add(Int_obj_ref.new_(key), obj);}
	public void Add_if_dupe_use_1st(int key, Object obj)	{hash.Add_if_dupe_use_1st(Int_obj_ref.new_(key), obj);}
}
