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
import gplx.core.lists.*;/*Hash_adp_list*/
interface GfmlPragma {
	String KeyOfPragma();
	void Exec(GfmlBldr bldr, GfmlNde pragmaNde);
	GfmlType[] MakePragmaTypes(GfmlTypeMakr typeMakr);
}
class GfmlPragmaMgr {
	public void Pragmas_add(GfmlPragma cmd) {pragmas.Add(cmd.KeyOfPragma(), cmd);}		
	public boolean Pragmas_compile(String ndeName, GfmlBldr bldr) {
		if (pragmas.Count() == 0) return false;
		GfmlPragma cmd = (GfmlPragma)pragmas.Get_by(ndeName); if (cmd == null) return false;
		GfmlNde pragmaNde = bldr.CurNde();
		pragmaNde.ObjType_set_pragma();
		cmd.Exec(bldr, pragmaNde);
		return true;
	}
	public void BgnCmds_add(GfmlDocPos pos, GfmlBldrCmd cmd) {bgnCmds.AddInList(pos.Path(), cmd);}
	public void BgnCmds_del(GfmlDocPos pos, GfmlBldrCmd cmd) {bgnCmds.DelInList(pos.Path(), cmd);}
	public void BgnCmds_exec(GfmlDocPos pos, GfmlBldr bldr)  {Exec(pos, bldr, bgnCmds);}
	public void EndCmds_add(GfmlDocPos pos, GfmlBldrCmd cmd) {endCmds.AddInList(pos.Path(), cmd);}
	public void EndCmds_del(GfmlDocPos pos, GfmlBldrCmd cmd) {endCmds.DelInList(pos.Path(), cmd);}
	public void EndCmds_exec(GfmlDocPos pos, GfmlBldr bldr)  {Exec(pos, bldr, endCmds);}
	static void Exec(GfmlDocPos pos, GfmlBldr bldr, Hash_adp_list cmds) {
		List_adp list = cmds.Get_by(pos.Path()); if (list == null) return;
		for (int i = 0; i < list.Count(); i++) {
			GfmlBldrCmd cmd = (GfmlBldrCmd)list.Get_at(i);
			cmd.Exec(bldr, GfmlTkn_.Null);
		}
	}
	Hash_adp pragmas = Hash_adp_.New(); Hash_adp_list bgnCmds = Hash_adp_list.new_(), endCmds = Hash_adp_list.new_();
        public static GfmlPragmaMgr new_() {return new GfmlPragmaMgr();} GfmlPragmaMgr() {}
}
