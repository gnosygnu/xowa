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
package gplx.langs.regxs; import gplx.*; import gplx.langs.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Regx_rslt {// THREAD.UNSAFE
	private int src_pos;
	private Regx_group tmp_grp = new Regx_group(false, -1, -1, null);
		public Matcher match;
	public int             Groups__len()         {return match.groupCount() + 1;}	// +1 to include group=0 which is entire pattern
	public Regx_group      Groups__get_at(int i) {		
		tmp_grp.Init(true, match.start(i), match.end(i), null);
		return tmp_grp;
	}
	public void Init(Regx_adp regex, String src, int src_bgn) {
		match = regex.Under().matcher(src);
		this.src_pos = src_bgn;
	}
	public boolean Match_next() {
		this.found = match.find(src_pos);
		if (found) {
			this.find_bgn = match.start();
			this.find_end = match.end();
			this.src_pos = find_end;
		}
		return found;
	}
		public boolean         Found()      {return found;}      private boolean found;
	public int             Find_bgn()   {return find_bgn;}   private int find_bgn;
	public int             Find_end()   {return find_end;}   private int find_end;
}