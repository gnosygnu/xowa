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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
class Pack_list {
	private final    List_adp list = List_adp_.New();
	public Pack_list(int tid) {this.tid = tid;}
	public int			Tid()				{return tid;}		private final    int tid;
	public int			Len()				{return list.Len();}
	public Pack_itm		Get_at(int i)		{return (Pack_itm)list.Get_at(i);}
	public void			Add(Pack_itm itm)	{list.Add(itm);}
	public void			Clear()				{list.Clear();}
}
