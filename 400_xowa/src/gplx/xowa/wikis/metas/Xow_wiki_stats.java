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
package gplx.xowa.wikis.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.nss.*;
public class Xow_wiki_stats implements GfoInvkAble {
	public Xow_wiki_stats(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
	public int NumPages() {return num_pages;} public Xow_wiki_stats NumPages_(int v) {num_pages = v; return this;} private int num_pages;					// in entire wiki: 28,433,596
	public int NumArticles() {return num_articles;} public Xow_wiki_stats NumArticles_(int v) {num_articles = v; return this;} private int num_articles;	// in main ns: 4,074,996
	public int NumFiles() {return num_files;} public Xow_wiki_stats NumFiles_(int v) {num_files = v; return this;} private int num_files;
	public int NumEdits() {return num_edits;} public Xow_wiki_stats NumEdits_(int v) {num_edits = v; return this;} private int num_edits;
	public int NumViews() {return num_views;} public Xow_wiki_stats NumViews_(int v) {num_views = v; return this;} private int num_views;
	public int NumUsers() {return num_users;} public Xow_wiki_stats NumUsers_(int v) {num_users = v; return this;} private int num_users;
	public int NumUsersActive() {return num_users_active;} public Xow_wiki_stats NumUsersActive_(int v) {num_users_active = v; return this;} private int num_users_active;
	public int NumAdmins() {return num_admins;} public Xow_wiki_stats NumAdmins_(int v) {num_admins = v; return this;} private int num_admins;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_number_of_pages_))					num_pages = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_number_of_articles_))				num_articles = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_number_of_files_))					num_files = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_number_of_articles_in_ns_))			return Number_of_articles_in_ns_(m);
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_number_of_pages_ = "number_of_pages_", Invk_number_of_articles_ = "number_of_articles_", Invk_number_of_files_ = "number_of_files_", Invk_number_of_articles_in_ns_ = "number_of_articles_in_ns_";
	Object Number_of_articles_in_ns_(GfoMsg m) {
		int ns_id = m.ReadInt("ns_id");
		int count = m.ReadInt("count");
		Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
		if (ns != null) ns.Count_(count);
		return this;
	}
}
