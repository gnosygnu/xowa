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
package gplx.xowa.xtns.wbases.core; import gplx.*;
import gplx.objects.strings.AsciiByte;
import gplx.xowa.xtns.wbases.*;
public class Wbase_pid {
	public static final int Id_null = -1;
	public static int To_int_or_null(byte[] pid_ttl) {	// EX: "p123" -> "123"
		int len = pid_ttl.length; if (len == 0) return Wbase_pid.Id_null;
		byte ltr_p = pid_ttl[0];	// make sure 1st char is "P" or "p"
		switch (ltr_p) {
			case AsciiByte.Ltr_P:
			case AsciiByte.Ltr_p:	break;
			default:				return Wbase_pid.Id_null;
		}
		return Bry_.To_int_or(pid_ttl, 1, len, Wbase_pid.Id_null);
	}
	public static byte[] Prepend_property_if_needed(byte[] bry) { // "p1" -> "Property:p1" or "P1" -> "Property:P1"
		int len = bry == null ? 0 : bry.length;
		return	len > 1
			&&	AsciiByte.CaseLower(bry[0]) == AsciiByte.Ltr_p
			&&	AsciiByte.IsNum(bry[1])
			?	Bry_.Add(Wdata_wiki_mgr.Ns_property_name_bry, AsciiByte.ColonBry, bry)	// if ttl starts with "p#", prepend "Property:" to get "Property:p#"; NOTE: do not ucase P b/c it breaks a test; DATE:2014-02-18
			:	bry;
	}
	public static String Ucase_pid_as_str(String pid) {
		return String_.Has_at_bgn(pid, "p")
			? String_.Upper(pid)	// convert "p123" to "P123"; note (a) Scrib.v2 calls as "P123"; (b) db stores as "p123"; (c) XO loads as "P123"; DATE:2016-12-03
			: pid;
	}
}
