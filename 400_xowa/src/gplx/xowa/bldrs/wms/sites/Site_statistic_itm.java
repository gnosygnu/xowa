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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
public class Site_statistic_itm implements To_str_able {
	public Site_statistic_itm Ctor(long pages, long articles, long edits, long images, long users, long activeusers, long admins, long jobs, long queued_massmessages) {
		this.pages = pages;
		this.articles = articles;
		this.edits = edits;
		this.images = images;
		this.users = users;
		this.activeusers = activeusers;
		this.admins = admins;
		this.jobs = jobs;
		this.queued_massmessages = queued_massmessages;
		return this;
	}
	public long Pages() {return pages;} private long pages;
	public long Articles() {return articles;} private long articles;
	public long Edits() {return edits;} private long edits;
	public long Images() {return images;} private long images;
	public long Users() {return users;} private long users;
	public long Activeusers() {return activeusers;} private long activeusers;
	public long Admins() {return admins;} private long admins;
	public long Jobs() {return jobs;} private long jobs;
	public long Queued_massmessages() {return queued_massmessages;} private long queued_massmessages;
	public String To_str() {return String_.Concat_with_obj("|", pages, articles, edits, images, users, activeusers, admins, queued_massmessages);}
}
