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
package gplx.gfml; import gplx.*;
public class GfmlDoc_ {
	public static GfmlDoc parse_any_eol_(String raw) {return parse(String_.Replace(raw, String_.CrLf, String_.Lf));}
	public static GfmlDoc parse(String raw) {
		GfmlBldr bldr = GfmlBldr_.default_();
		return bldr.XtoGfmlDoc(raw);
	}
}
class GfmlUsrMsgs {
	public static UsrMsg fail_HndTkn_alreadyExists()	{return UsrMsg.new_("hndTkn already exists");}
	public static UsrMsg fail_KeyTkn_alreadyExists()	{return UsrMsg.new_("keyTkn already exists");}
	public static UsrMsg fail_DatTkn_notFound()			{return UsrMsg.new_("datTkn not found");}
	public static UsrMsg fail_Frame_danglingBgn()		{return UsrMsg.new_("dangling frame");}
	public static void MakeErr(GfmlBldr bldr, UsrMsg um, String raw) {
		bldr.Doc().UsrMsgs().Add(um);
		GfmlStringHighlighter sh = GfmlStringHighlighter.new_();
		sh.Raw_(raw).Mark_(bldr.StreamPos(), '*', "failed");
		um.Add("errorPos", bldr.StreamPos());
		um.Add("errorHighlight", String_.CrLf + String_.Concat_lines_crlf(sh.Gen()));
	}
	public static Err gfmlParseError(GfmlBldr bldr) {
		Err rv = Err_.new_wo_type("gfml parse error");
		for (int i = 0; i < bldr.Doc().UsrMsgs().Count(); i++) {
			UsrMsg um = (UsrMsg)bldr.Doc().UsrMsgs().Get_at(i);
			rv.Args_add("err" + Int_.Xto_str(i), um.To_str());
		}
		return rv;
	}
}
