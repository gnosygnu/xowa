/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.bldrs.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
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
