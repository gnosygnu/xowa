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
package gplx.xowa.wikis.data.site_stats; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.wikis.nss.*;
public class Xowd_site_stats_mgr implements Gfo_invk {	// REF.MW:https://www.mediawiki.org/wiki/Manual:Site_stats_table
	private final    Xow_wiki wiki;
	public Xowd_site_stats_mgr(Xow_wiki wiki) {this.wiki = wiki;}
	public long		Num_pages()		{return num_pages;}				private long num_pages;		// ss_total_pages; pages in entire wiki: 16,299,475
	public long		Num_articles()	{return num_articles;}			private long num_articles;	// ss_good_articles; pages in main ns w/o redirect: 5,072,469
	public long		Num_views()		{return num_views;}				private long num_views;		// ss_total_views
	public long		Num_edits()		{return num_edits;}				private long num_edits;		// ss_total_edits
	public long		Num_users()		{return num_users;}				private long num_users;		// ss_users
	public long		Num_active()	{return num_active;}			private long num_active;	// ss_active_users
	public int		Num_admins()	{return num_admins;}			private int num_admins;		// ss_admins
	public int		Num_files()		{return num_files;}				private int num_files;		// ss_images
	public void Load_by_db(long num_pages, long num_articles, int num_files, long num_edits, long num_views, long num_users, long num_active, int num_admins) {
		this.num_pages = num_pages;
		this.num_articles = num_articles;
		this.num_files = num_files;
		this.num_edits = num_edits;
		this.num_views = num_views;
		this.num_users = num_users;
		this.num_active = num_active;
		this.num_admins = num_admins;
	}
	private Object Number_of_articles_in_ns_(GfoMsg m) {
		int ns_id = m.ReadInt("ns_id");
		int count = m.ReadInt("count");
		Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
		if (ns != null) ns.Count_(count);
		return this;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_number_of_pages_))					num_pages = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_number_of_articles_))				num_articles = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_number_of_files_))					num_files = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_number_of_articles_in_ns_))			return Number_of_articles_in_ns_(m);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final String Invk_number_of_pages_ = "number_of_pages_", Invk_number_of_articles_ = "number_of_articles_", Invk_number_of_files_ = "number_of_files_", Invk_number_of_articles_in_ns_ = "number_of_articles_in_ns_";
}
