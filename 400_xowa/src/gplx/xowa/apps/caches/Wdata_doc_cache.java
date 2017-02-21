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
package gplx.xowa.apps.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.xtns.wbases.*;
public class Wdata_doc_cache {
	private Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Add(byte[] qid, Wdata_doc doc) {hash.Add(qid, doc);}
	public Wdata_doc Get_or_null(byte[] qid) {return (Wdata_doc)hash.Get_by_bry(qid);}
	public void Free_mem_all() {this.Clear();}
	public void Clear() {hash.Clear();}
}
