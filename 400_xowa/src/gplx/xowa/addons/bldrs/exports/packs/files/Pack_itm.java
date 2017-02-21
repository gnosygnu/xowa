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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
class Pack_itm {
	public Pack_itm(int tid, Io_url zip_url, Io_url... raw_urls) {
		this.tid = tid;
		this.zip_url = zip_url;
		this.raw_urls = raw_urls;
	}
	public int			Tid()			{return tid;}			private int tid;
	public Io_url		Zip_url()		{return zip_url;}		private Io_url zip_url;
	public Io_url[]		Raw_urls()		{return raw_urls;}		private Io_url[] raw_urls;	public void Raw_urls_(Io_url[] v) {this.raw_urls = v;}
	public long			Raw_size()		{return raw_size;}		private long raw_size;		public void Raw_size_(long v) {this.raw_size = v;}
	public int			Step_id()		{return step_id;}		private int step_id;		public void Step_id_(int v) {this.step_id = v;}
}
