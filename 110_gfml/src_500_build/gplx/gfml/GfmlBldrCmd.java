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
package gplx.gfml; import gplx.*;
public interface GfmlBldrCmd {
	String Key();
	void Exec(GfmlBldr bldr, GfmlTkn tkn);
}
class GfmlBldrCmd_ {
	public static final    GfmlBldrCmd Null = new GfmlBldrCmd_null();
}
class GfmlBldrCmd_null implements GfmlBldrCmd {
	public String Key() {return "gfml.nullCmd";}
	public void Exec(GfmlBldr bldr, GfmlTkn tkn) {}
}
class GfmlBldrCmdRegy {
	public void Add(GfmlBldrCmd cmd) {hash.Add(cmd.Key(), cmd);}
	public GfmlBldrCmd GetOrFail(String key) {return (GfmlBldrCmd)hash.Get_by_or_fail(key);}
	Hash_adp hash = Hash_adp_.New();
	public static GfmlBldrCmdRegy new_() {
		GfmlBldrCmdRegy rv = new GfmlBldrCmdRegy();
		rv.Add(GfmlBldrCmd_elemKey_set.Instance);
		rv.Add(GfmlBldrCmd_dataTkn_set.Instance);
		return rv;
	}	GfmlBldrCmdRegy() {}
}
