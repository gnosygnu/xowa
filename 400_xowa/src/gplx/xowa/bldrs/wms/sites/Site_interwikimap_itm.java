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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
class Site_interwikimap_itm implements To_str_able {
	public Site_interwikimap_itm(byte[] prefix, boolean local
		, boolean extralanglink, byte[] linktext, byte[] sitename
		, byte[] language, boolean localinterwiki, byte[] url, boolean protorel) {
		this.Prefix = prefix;
		this.Local = local;
		this.Extralanglink = extralanglink;
		this.Linktext = linktext;
		this.Sitename = sitename;
		this.Language = language;
		this.Localinterwiki = localinterwiki;
		this.Url = url;
		this.Protorel = protorel;
	}
	public final    byte[]		Prefix;
	public final    boolean		Local;
	public final    boolean		Extralanglink;
	public final    byte[]		Linktext;
	public final    byte[]		Sitename;
	public final    byte[]		Language;
	public final    boolean		Localinterwiki;
	public final    byte[]		Url;
	public final    boolean		Protorel;
	public String To_str() {return String_.Concat_with_obj("|", Prefix, Local, Extralanglink, Linktext, Sitename, Language, Localinterwiki, Url, Protorel);}
}
