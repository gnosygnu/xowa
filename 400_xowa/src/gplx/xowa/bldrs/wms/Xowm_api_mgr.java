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
package gplx.xowa.bldrs.wms; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.net.*;
public class Xowm_api_mgr {
	public static byte[] Call_by_qarg(Gfo_usr_dlg usr_dlg, Gfo_inet_conn inet_conn, String domain_str, String api_args) {return Call_by_url(usr_dlg, inet_conn, domain_str, Bld_api_url(domain_str, api_args));}
	public static byte[] Call_by_url (Gfo_usr_dlg usr_dlg, Gfo_inet_conn inet_conn, String domain_str, String url) {
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) return null;
		usr_dlg.Prog_many("", "", "wm.api:calling; wiki=~{0} api=~{1}", domain_str, url);
		byte[] rslt = inet_conn.Download_as_bytes_or_null(url); if (rslt == null) usr_dlg.Warn_many("", "", "wm.api:wmf api returned nothing; api=~{0}", url);
		return rslt;
	}
	public static String Bld_api_url(String wiki, String args) {return String_.Concat("https://", wiki, "/w/api.php?", args);}	// EX: https://en.wikipedia.org/w/api.php?action=query&meta=siteinfo&siprop=namespaces
}
