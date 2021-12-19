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
package gplx.gfml;
import gplx.core.texts.*; /*CharStream*/
import gplx.frameworks.evts.Gfo_evt_itm;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
public interface GfmlLxr extends Gfo_evt_itm {
	String Key();
	String[] Hooks();
	GfmlTkn CmdTkn();
	void CmdTkn_set(GfmlTkn val); // needed for lxr pragma
	GfmlTkn MakeTkn(CharStream stream, int hookLength);
	GfmlLxr SubLxr();
	void SubLxr_Add(GfmlLxr... lexer);
}
class GfmlLxrRegy {
	public int Count() {return hash.Len();}
	public void Add(GfmlLxr lxr) {hash.Add(lxr.Key(), lxr);}
	public GfmlLxr Get_by(String key) {return (GfmlLxr)hash.GetByOrNull(key);}
	Hash_adp hash = Hash_adp_.New();
}
