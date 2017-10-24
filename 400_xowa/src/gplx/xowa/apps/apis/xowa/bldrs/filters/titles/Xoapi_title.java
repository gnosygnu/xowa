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
package gplx.xowa.apps.apis.xowa.bldrs.filters.titles; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.bldrs.*; import gplx.xowa.apps.apis.xowa.bldrs.filters.*;
public class Xoapi_title implements Gfo_invk {
	public void Init_by_kit(Xoae_app app) {
//			wordlist_dir = app.Fsys_mgr().Bin_xtns_dir().GenSubDir_nest("xowa", "DansGuardian");
	}
	public boolean		Enabled()		{return enabled;}		private boolean enabled = Bool_.Y;
	public Io_url	Wordlist_dir()	{return wordlist_dir;}	private Io_url wordlist_dir;
	public int		Score_init()	{return score_init;}	private int score_init = 0;
	public int		Score_pass()	{return score_pass;}	private int score_pass = 0;
	public boolean		Log_enabled()	{return log_enabled;}	private boolean log_enabled = Bool_.Y;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled)) 							return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_)) 							enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_wordlist_dir)) 						return Int_.To_str(score_init);
		else if	(ctx.Match(k, Invk_wordlist_dir_)) 						wordlist_dir= m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_score_init)) 						return Int_.To_str(score_init);
		else if	(ctx.Match(k, Invk_score_init_)) 						score_init = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_score_pass)) 						return Int_.To_str(score_pass);
		else if	(ctx.Match(k, Invk_score_pass_)) 						score_pass = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_log_enabled)) 						return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk_log_enabled_)) 						log_enabled = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_enabled				= "enabled"				, Invk_enabled_				= "enabled_"
	, Invk_wordlist_dir			= "wordlist_dir"		, Invk_wordlist_dir_		= "wordlist_dir_"
	, Invk_score_init			= "score_init"			, Invk_score_init_			= "score_init_"
	, Invk_score_pass			= "score_pas"			, Invk_score_pass_			= "score_pass_"
	, Invk_log_enabled			= "log_enabled"			, Invk_log_enabled_			= "log_enabled_"
	;
}
