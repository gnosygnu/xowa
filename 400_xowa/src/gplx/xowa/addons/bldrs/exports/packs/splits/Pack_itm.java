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
package gplx.xowa.addons.bldrs.exports.packs.splits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
class Pack_itm {
	public Pack_itm(int idx, int type, Io_url zip_url, Io_url[] raw_urls) {
		this.idx = idx;
		this.type = type;
		this.zip_url = zip_url;
		this.raw_urls = raw_urls;
	}
	public int			Idx()		{return idx;}		private final    int idx;
	public int			Type()		{return type;}		private final    int type;
	public Io_url[]		Raw_urls()	{return raw_urls;}	private final    Io_url[] raw_urls;
	public Io_url		Zip_url()	{return zip_url;}	private final    Io_url zip_url;
}
