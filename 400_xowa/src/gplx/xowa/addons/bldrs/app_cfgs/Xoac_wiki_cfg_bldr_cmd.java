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
package gplx.xowa.addons.bldrs.app_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.core.strings.*;
public class Xoac_wiki_cfg_bldr_cmd {
	public Xoac_wiki_cfg_bldr_cmd(String key, String text) {this.key = key; this.text = text;}
	public String Key() {return key;} private String key;
	public String Text() {return text;} private String text;
	public String Exec(String_bldr sb, String wiki, String src) {
		String sect_txt_bgn = sb.Add("// ").Add(key).Add(".bgn\n").To_str_and_clear();
		String sect_txt_end = sb.Add("// ").Add(key).Add(".end\n").To_str_and_clear();
		String sect_txt_all = sb.Add(sect_txt_bgn).Add(text + "\n").Add(sect_txt_end).To_str_and_clear();	// NOTE: always add \n; convenience for single line cmds
		return src + sect_txt_all;
	}
}
