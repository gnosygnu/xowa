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
package gplx.gfui.ipts;
import gplx.frameworks.invks.GfoMsg;
import gplx.types.basics.lists.List_adp;
public class IptCfgItm {
	public String Key() {return key;} public IptCfgItm Key_(String v) {key = v; return this;} private String key;
	public List_adp Ipt() {return ipt;} public IptCfgItm Ipt_(List_adp v) {ipt = v; return this;} List_adp ipt;
	public GfoMsg Msg() {return msg;} public IptCfgItm Msg_(GfoMsg v) {msg = v; return this;} GfoMsg msg;
	public static IptCfgItm new_() {return new IptCfgItm();} IptCfgItm() {}
}
