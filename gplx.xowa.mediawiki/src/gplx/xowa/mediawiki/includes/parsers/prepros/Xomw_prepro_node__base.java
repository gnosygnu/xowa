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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public abstract class Xomw_prepro_node__base implements Xomw_prepro_node {
	private List_adp subs;
	public int Subs__len() {return subs == null ? 0 : subs.Len();}
	public Xomw_prepro_node Subs__get_at(int i) {return subs == null ? null : (Xomw_prepro_node)subs.Get_at(i);}
	public void Subs__add(Xomw_prepro_node sub) {
		if (subs == null) subs = List_adp_.New();
		subs.Add(sub);
	}
	public abstract void To_xml(Bry_bfr bfr);
}
