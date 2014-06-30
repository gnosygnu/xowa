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
package gplx.xowa.bldrs.servers; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.setup.maints.*;
import gplx.xowa.bldrs.servers.jobs.*;
public class Xob_core_server {
	private Xob_wmf_mgr wmf_mgr;
	public Xob_core_server(Xoa_app app) {
		wmf_mgr = new Xob_wmf_mgr(app);
	}
	public void Server_init() {
		// load cmds		bld_all
		// load cses		wiki_updated,commons_updated,wikidata_update
		// load jobs		bld_all,en.wikipedia.org
	}
	public void Server_bgn() {
		while (true) {
			if (   wmf_mgr.Sync()		// new wmf_stats
//					|| jar_mgr.Updated()	// new jar
			) {
//					RunJobs();
			}
		}
		/*
			check wikimedia
			if (change)
				check jobs
				run jobs
		*/
	}
	public void Server_end() {
	}
}
