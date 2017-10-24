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
package gplx.xowa.addons.wikis.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
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
	public boolean	Version_id__needs_upgrade() {return version_id < Srch_db_upgrade.Version__link_score;}
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
