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
package gplx;
public class OrderedHash_ {
	public static OrderedHash new_()			{return new OrderedHash_base();}
	public static OrderedHash new_bry_()		{return new OrderedHash_bry();}
}
class OrderedHash_bry extends OrderedHash_base {
	private Bry_obj_ref lkp = Bry_obj_ref.null_();
	@Override protected void Add_base(Object key, Object val)	{super.Add_base(Bry_obj_ref.new_((byte[])key), val);}
	@Override protected void Del_base(Object key)				{super.Del_base(lkp.Val_((byte[])key));}
	@Override protected boolean Has_base(Object key)				{return super.Has_base(lkp.Val_((byte[])key));}
	@Override protected Object Fetch_base(Object key)			{return super.Fetch_base(lkp.Val_((byte[])key));}
}
