/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios;
import gplx.types.commons.GfoDate;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
public class IoItmFil_ {
	public static IoItmFil as_(Object obj) {return obj instanceof IoItmFil ? (IoItmFil)obj : null;}
	public static final String 
		Prop_Size            = "size"
	, Prop_Modified        = "modified";
	public static IoItmFil new_(Io_url url, long size, GfoDate created, GfoDate modified) {return new IoItmFil().ctor_IoItmFil(url, size, modified);}
	public static IoItmFil sub_(String name, long size, GfoDate modified) {
		IoItmFil rv = new IoItmFil();
		rv.ctor_IoItmFil(Io_url_.mem_fil_("mem/" + name), size, modified);
		rv.Name_(name);
		return rv;
	}
}
