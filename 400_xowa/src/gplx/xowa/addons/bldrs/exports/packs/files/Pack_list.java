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
package gplx.xowa.addons.bldrs.exports.packs.files;
import gplx.libs.files.Io_url;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
class Pack_list {
	private final Ordered_hash list = Ordered_hash_.New();
	public Pack_list(int tid) {this.tid = tid;}
	public int			Tid()				{return tid;}		private final int tid;
	public int			Len()				{return list.Len();}
	public Pack_itm		Get_at(int i)		{return (Pack_itm)list.GetAt(i);}
	public void			Add(Pack_itm itm)	{list.Add(itm.Zip_url().Raw(), itm);}
	public void			Clear()				{list.Clear();}
	public boolean Has(Io_url url) {
		return list.Has(url.Raw());
	}
}
