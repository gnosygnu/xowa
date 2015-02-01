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
package gplx.xowa; import gplx.*;
import gplx.core.strings.*;
public class Xoac_wiki_cfg_bldr_cmd {
	public Xoac_wiki_cfg_bldr_cmd(String key, String text) {this.key = key; this.text = text;}
	public String Key() {return key;} private String key;
	public String Text() {return text;} private String text;
	public String Exec(String_bldr sb, String wiki, String src) {
		String sect_txt_bgn = sb.Add("// ").Add(key).Add(".bgn\n").Xto_str_and_clear();
		String sect_txt_end = sb.Add("// ").Add(key).Add(".end\n").Xto_str_and_clear();
		String sect_txt_all = sb.Add(sect_txt_bgn).Add(text + "\n").Add(sect_txt_end).Xto_str_and_clear();	// NOTE: always add \n; convenience for single line cmds
//			int sect_pos_bgn = String_.FindFwd(src, sect_txt_bgn);
//			if (sect_pos_bgn == String_.Find_none)	// new cmd; add to end of file
			return src + sect_txt_all;
//			int sect_pos_end = String_.FindFwd(src, sect_txt_end);
//			if (sect_pos_end == String_.Find_none)
//				throw Err_.new_("section_fail: " + wiki + " " + key);
//			try {
//				return sb.Add(String_.Mid(src, 0, sect_pos_bgn)).Add(sect_txt_all).Add(String_.Mid(src, sect_pos_end + String_.Len(sect_txt_end), String_.Len(text))).Xto_str_and_clear();
//			} catch (Exception e) {Err_.Noop(e); throw Err_.new_("section_fail: " + wiki + " " + key);}
	}
}
