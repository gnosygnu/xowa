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
package gplx.xowa.addons.apps.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*;
public class Srch_db_cfg {
	public Srch_db_cfg(int version_id, long page_count, int word_count, int link_count_score_max, int link_count_score_cutoff, int link_score_max) {
		this.version_id = version_id;
		this.page_count = page_count;
		this.word_count = word_count;
		this.link_count_score_max = link_count_score_max;
		this.link_count_score_cutoff = link_count_score_cutoff;
		this.link_score_max = link_score_max;
	}
	public int		Version_id() {return version_id;} private int version_id;
	public boolean		Version_id__needs_upgrade() {return version_id < Srch_db_upgrade.Version__link_score;}
	public long		Page_count() {return page_count;} private long page_count;
	public int		Word_count() {return word_count;} private int word_count;
	public int		Link_count_score_max() {return link_count_score_max;} private int link_count_score_max;
	public int		Link_count_score_cutoff() {return link_count_score_cutoff;} private int link_count_score_cutoff;
	public int		Link_score_max() {return link_score_max;} private int link_score_max;
	public void		Update_link(int link_score_max) {this.link_score_max = link_score_max;}
	public void		Update_word(int word_count, int link_count_score_max, int link_count_score_cutoff) {
		this.version_id = Srch_db_upgrade.Version__link_score;
		this.word_count = word_count;
		this.link_count_score_max = link_count_score_max;
		this.link_count_score_cutoff = link_count_score_cutoff;
	}
}
