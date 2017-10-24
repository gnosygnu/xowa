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
			rv.Args_add("err" + Int_.To_str(i), um.To_str());
		}
		return rv;
	}
}
