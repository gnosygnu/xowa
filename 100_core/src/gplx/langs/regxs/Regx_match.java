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
package gplx.langs.regxs;
import gplx.types.basics.utls.StringUtl;
public class Regx_match {        
	public Regx_match(boolean rslt, int find_bgn, int find_end, Regx_group[] groups) {
		this.rslt = rslt;
		this.find_bgn = find_bgn;
		this.find_end = find_end;
		this.groups = groups;
	}
	public boolean Rslt() {return rslt;} private final boolean rslt;
	public boolean Rslt_none() {return !rslt;}    // NOTE: was "|| find_end - find_bgn == 0"; DATE:2013-04-11; DATE:2014-09-02
	public int Find_bgn() {return find_bgn;} private final int find_bgn;
	public int Find_end() {return find_end;} private final int find_end;
	public int Find_len() {return find_end - find_bgn;}
	public String Find_str(String s) {return StringUtl.Mid(s, find_bgn, find_end);}
	public Regx_group[] Groups() {return groups;} private final Regx_group[] groups;

	public static final Regx_match[] Ary_empty = new Regx_match[0];
}
