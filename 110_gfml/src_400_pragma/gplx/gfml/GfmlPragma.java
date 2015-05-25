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
import gplx.lists.*;/*Hash_adp_list*/
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
	Hash_adp pragmas = Hash_adp_.new_(); Hash_adp_list bgnCmds = Hash_adp_list.new_(), endCmds = Hash_adp_list.new_();
        public static GfmlPragmaMgr new_() {return new GfmlPragmaMgr();} GfmlPragmaMgr() {}
}
