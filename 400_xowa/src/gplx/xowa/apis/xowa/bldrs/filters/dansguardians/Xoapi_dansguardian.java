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
package gplx.xowa.apis.xowa.bldrs.filters.dansguardians; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*; import gplx.xowa.apis.xowa.bldrs.*; import gplx.xowa.apis.xowa.bldrs.filters.*;
import gplx.ios.*; import gplx.xowa.bldrs.filters.dansguardians.*;
public class Xoapi_dansguardian implements GfoInvkAble {
	public void Ctor_by_app(Xoa_app app) {
		root_dir = app.Fsys_mgr().Bin_xowa_dir().GenSubDir_nest("cfg", "bldr", "filter");
	}
	public boolean		Enabled()			{return enabled;}			private boolean enabled = Bool_.N;
	public Io_url	Root_dir()			{return root_dir;}			private Io_url root_dir;
	public int		Score_init()		{return score_init;}		private int score_init = 0;
	public int		Score_fail()		{return score_fail;}		private int score_fail = 0;
	public boolean		Case_match()		{return case_match;}		private boolean case_match = Bool_.N;
	public boolean		Wildcard_enabled()	{return wildcard_enabled;}	private boolean wildcard_enabled = Bool_.N;
	public byte[]	Wildcard_char()		{return wildcard_char;}		private byte[] wildcard_char = Byte_ascii.Space_bry;
	public byte[][]	Wildcard_list()		{return wildcard_list;}		private byte[][] wildcard_list = Bry_.Ary(Byte_ascii.Space_bry);//	\s,.!?:;'"-()	/@#$%^&*()[]{}<>_+=|\~`
	public int		Target_tid()		{return target_tid;}		private int target_tid = Dg_match_mgr.Target_tid_wikitext;
	public boolean		Log_enabled()		{return log_enabled;}		private boolean log_enabled = Bool_.Y;
	public Dg_match_mgr New_mgr(String domain_str, Io_url wiki_root_dir) {
		if (!enabled) return null;
		Io_url log_url = wiki_root_dir.GenSubFil("dansguardian_log.sqlite3");
		Dg_match_mgr rv = new Dg_match_mgr(root_dir.GenSubDir(domain_str), score_init, score_fail, case_match, log_enabled, log_url);
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled)) 							return Yn.Xto_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_)) 							enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_root_dir)) 							return root_dir.Raw();
		else if	(ctx.Match(k, Invk_root_dir_)) 							root_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_score_init)) 						return Int_.Xto_str(score_init);
		else if	(ctx.Match(k, Invk_score_init_)) 						score_init = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_score_fail)) 						return Int_.Xto_str(score_fail);
		else if	(ctx.Match(k, Invk_score_fail_)) 						score_fail = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_case_match)) 						return Yn.Xto_str(case_match);
		else if	(ctx.Match(k, Invk_case_match_)) 						case_match = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_wildcard_enabled)) 					return Yn.Xto_str(wildcard_enabled);
		else if	(ctx.Match(k, Invk_wildcard_enabled_)) 					wildcard_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_wildcard_char)) 						return String_.new_u8(wildcard_char);
		else if	(ctx.Match(k, Invk_wildcard_char_)) 					wildcard_char = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_wildcard_list)) 						return "";
		else if	(ctx.Match(k, Invk_wildcard_list_)) 					{}
		else if	(ctx.Match(k, Invk_target_tid)) 						return Int_.Xto_str(target_tid);
		else if	(ctx.Match(k, Invk_target_tid_)) 						target_tid = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_log_enabled)) 						return Yn.Xto_str(log_enabled);
		else if	(ctx.Match(k, Invk_log_enabled_)) 						log_enabled = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_enabled				= "enabled"				, Invk_enabled_				= "enabled_"
	, Invk_root_dir				= "root_dir"			, Invk_root_dir_			= "root_dir_"
	, Invk_score_init			= "score_init"			, Invk_score_init_			= "score_init_"
	, Invk_score_fail			= "score_fail"			, Invk_score_fail_			= "score_fail_"
	, Invk_case_match			= "case_match"			, Invk_case_match_			= "case_match_"
	, Invk_wildcard_enabled		= "wildcard_enabled"	, Invk_wildcard_enabled_	= "wildcard_enabled_"
	, Invk_wildcard_char		= "wildcard_char"		, Invk_wildcard_char_		= "wildcard_char_"
	, Invk_wildcard_list		= "wildcard_list"		, Invk_wildcard_list_		= "wildcard_list_"
	, Invk_target_tid			= "target_tid"			, Invk_target_tid_			= "target_tid_"
	, Invk_log_enabled			= "log_enabled"			, Invk_log_enabled_			= "log_enabled_"
	;
}
