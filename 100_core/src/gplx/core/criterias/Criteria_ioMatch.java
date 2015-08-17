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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
import gplx.texts.*;
public class Criteria_ioMatch implements Criteria { // EX: url IOMATCH '*.xml|*.txt'
	public Criteria_ioMatch(boolean match, RegxPatn_cls_ioMatch pattern) {this.match = match; this.pattern = pattern;}
	public byte				Tid() {return Criteria_.Tid_iomatch;}
	public boolean				Negated() {return !match;} private final boolean match;
	public void				Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public void				Val_as_obj_(Object v) {this.pattern = (RegxPatn_cls_ioMatch)v;}
	public RegxPatn_cls_ioMatch Pattern() {return pattern;} private RegxPatn_cls_ioMatch pattern;
	public boolean Matches(Object compObj) {
		Io_url comp = (Io_url)compObj;
		boolean rv = pattern.Matches(comp.XtoCaseNormalized());
		return match ? rv : !rv;
	}
	public String To_str() {return String_.Concat_any("IOMATCH ", pattern);}

	public static final String TokenName = "IOMATCH";
	public static Criteria_ioMatch as_(Object obj) {return obj instanceof Criteria_ioMatch ? (Criteria_ioMatch)obj : null;}
	public static Criteria_ioMatch parse_(boolean match, String raw, boolean caseSensitive) {return new Criteria_ioMatch(match, RegxPatn_cls_ioMatch_.parse_(raw, caseSensitive));}
}
