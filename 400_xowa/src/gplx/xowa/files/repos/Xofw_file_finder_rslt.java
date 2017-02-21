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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.parsers.utils.*;
public class Xofw_file_finder_rslt {
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte[] Redirect() {return redirect;} private byte[] redirect;
	public int Repo_idx() {return repo_idx;} private int repo_idx;
	public byte[] Repo_wiki_key() {return repo_wiki_key;} private byte[] repo_wiki_key;
	public void Init(byte[] ttl) {
		this.ttl = ttl; redirect = Xop_redirect_mgr.Redirect_null_bry; repo_wiki_key = null; repo_idx = Byte_.Max_value_127;
	}
	public void Done(int repo_idx, byte[] repo_wiki_key, byte[] redirect) {
		this.repo_idx = repo_idx; this.repo_wiki_key = repo_wiki_key; this.redirect = redirect;
	}
}
