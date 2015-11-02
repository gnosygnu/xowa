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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class IoUrlInfo_ {
	public static final IoUrlInfo Nil = IoUrlInfo_nil.Instance;
	public static final IoUrlInfo Wnt = IoUrlInfo_wnt.Instance;
	public static final IoUrlInfo Lnx = IoUrlInfo_lnx.Instance;
	public static final IoUrlInfo Mem = IoUrlInfo_mem.new_("mem", IoEngine_.MemKey);

	public static IoUrlInfo mem_(String key, String engineKey) {return IoUrlInfo_mem.new_(key, engineKey);}
	public static IoUrlInfo alias_(String srcRoot, String trgRoot, String engineKey) {return IoUrlInfo_alias.new_(srcRoot, trgRoot, engineKey);}
}
/*
wnt		C:\dir\fil.txt
wce		\dir\fil.txt	
lnx		/dir/fil.txt
mem		mem/dir/fil.txt
alias	app:\dir\fil.txt
*/