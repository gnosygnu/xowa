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